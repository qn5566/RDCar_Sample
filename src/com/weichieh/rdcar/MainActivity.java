package com.weichieh.rdcar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseAnalytics;
import com.weichieh.rdcar.service.TopVisibleService;
import com.weichieh.rdcar.system.MyHandler;
import com.weichieh.rdcar.system.SysApplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

	String TAG = "MainActivity";
	private MainHolder mHolder;
	private MainListener mListener;
	MyHandler mHandler;
	// Location請求物件
	LocationRequest locationRequest;
	GoogleApiClient googleApiClient;

	private final static String CALL = "android.intent.action.CALL";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ParseAnalytics.trackAppOpened(getIntent());
		initView();
		mHandler = new MyHandler(this);
		SysApplication.setHandler(mHandler);

		// 建立Google API用戶端物件
		configGoogleApiClient();

		// 建立Location請求物件
		configLocationRequest();
	}

	@Override
	protected void onResume() {
		super.onResume();

		TopView();
	}

	private void initView() {
		mHolder = new MainHolder(this, new MainListener(this));
		mListener = new MainListener(this);
		mHolder.registerListener(mListener);

	}

	/*
	 * 懸浮窗口
	 */
	public void TopView() {
		Intent it = new Intent(this, TopVisibleService.class);
		startService(it);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			showMenu();
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.v(TAG, "joey onKeyDown KEYCODE_BACK : return true");
			backButtonHandler();
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}

	// 直接回首頁
	public void BackHome(View v) {
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStack();
		Log.v(TAG, "joey BackHome");
	}

	/*
	 * 防止離開
	 */
	public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("是不是按錯?");
		alertDialog.setMessage("確定要離開日達汽車嗎?");
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setPositiveButton("確定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// SuperUser();
						dialog.cancel();
						callBoss();
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("按錯啦~抱歉",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});

		alertDialog.show();
	}

	/*
	 * 打電話給老闆
	 */
	public void callBoss() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("要不要我幫你打給老闆??");
		alertDialog.setMessage("透過此APP打給老闆,修車有優惠喔~");
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setPositiveButton("好啊,我要優惠!",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// SuperUser();
						CallMe();
						finish();
					}

				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("怎麼這麼好~我下次一定打!!",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						finish();
					}
				});

		alertDialog.show();
	}

	public void CallMe() {
		Intent call = new Intent(CALL, Uri.parse("tel:"
				+ "0937122297"));
		startActivity(call);
	}
	
	public void showMenu() {
		mHolder.getDrawerLayout().openDrawer(Gravity.LEFT);
	}

	public void closeMenu() {
		mHolder.getDrawerLayout().closeDrawers();
	}

	// GoogleMAP--------------------------------------------

	// 建立Google API用戶端物件
	// private synchronized void configGoogleApiClient() {
	//  GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
	//             .addConnectionCallbacks(this)
	//             .addOnConnectionFailedListener(this)
	//             .addApi(LocationServices.API)
	//             .build();
	// }
	// ConnectionCallbacks

	// 建立Google API用戶端物件
	private synchronized void configGoogleApiClient() {
		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	// 建立Location請求物件
	private void configLocationRequest() {
		locationRequest = new LocationRequest();
		// 設定讀取位置資訊的間隔時間為一秒（1000ms）
		locationRequest.setInterval(1000);
		// 設定讀取位置資訊最快的間隔時間為一秒（1000ms）
		locationRequest.setFastestInterval(1000);
		// 設定優先讀取高精確度的位置資訊（GPS）
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	@Override
	public void onConnected(Bundle bundle) {
		// 已經連線到Google Services
		// 啟動位置更新服務
		// 位置資訊更新的時候，應用程式會自動呼叫LocationListener.onLocationChanged
		LocationServices.FusedLocationApi.requestLocationUpdates(
				googleApiClient, locationRequest, MainActivity.this);
	}

	// ConnectionCallbacks
	@Override
	public void onConnectionSuspended(int i) {
		// Google Services連線中斷
		// int參數是連線中斷的代號
	}

	// OnConnectionFailedListener
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// Google Services連線失敗
		// ConnectionResult參數是連線失敗的資訊
		int errorCode = connectionResult.getErrorCode();

		// 裝置沒有安裝Google Play服務
		if (errorCode == ConnectionResult.SERVICE_MISSING) {
			Toast.makeText(this, "沒有安裝Google服務", Toast.LENGTH_LONG).show();
		}
	}

	// LocationListener
	@Override
	public void onLocationChanged(Location location) {
		// 位置改變
		// Location參數是目前的位置
		// currentLocation = location;
		// LatLng latLng = new LatLng(
		// location.getLatitude(), location.getLongitude());
		//
		// // 設定目前位置的標記
		// if (currentMarker == null) {
		// currentMarker = mMap.addMarker(new MarkerOptions().position(latLng));
		// }
		// else {
		// currentMarker.setPosition(latLng);
		// }
		//
		// // 移動地圖到目前的位置
		// moveMap(latLng);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		SysApplication.getHandler().sendEmptyMessage(0);
		// closeHandler.sendEmptyMessageDelayed(1, ADVERTISE_CLOSE_TIME);
		// Intent it = new Intent(MainActivity.this,
		// TopVisibleService.class);
		// stopService(it);
		super.onDestroy();
	}

}
