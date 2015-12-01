package com.weichieh.rdcar.Meun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weichieh.rdcar.R;
import com.weichieh.rdcar.MAP.Directions;
import com.weichieh.rdcar.MAP.Navigator;
import com.weichieh.rdcar.MAP.Navigator.OnPathSetListener;
import com.weichieh.rdcar.Meun.tool.wheel.ArrayWheelAdapter;
import com.weichieh.rdcar.Meun.tool.wheel.OnWheelChangedListener;
import com.weichieh.rdcar.Meun.tool.wheel.WheelView;
import com.weichieh.rdcar.system.SysApplication;
import com.weichieh.rdcar.tool.CustomToast;

public class Map extends SupportMapFragment implements OnMarkerClickListener{

	String TAG = "Map";
	private GoogleMap map;
	SupportMapFragment mapFrag;
	// 記錄目前最新的位置
	private Location currentLocation;
	double lat, lng;
	LocationManager LM;
	// 顯示目前與儲存位置的標記物件
	private Marker currentMarker, itemMarker;
	static final LatLng RDCar = new LatLng(24.960659, 121.246434);
	LatLng HOME;
	Button Nowlocation, Navigator;
	private boolean enableTool;
	Navigator nav;
	public String category1[] = new String[] { "汽車", "公車", "腳踏車", "走路" };
	public int ImageB[] = new int[] { R.drawable.driving, R.drawable.transit,
			R.drawable.bicycling, R.drawable.walking };
	ImageView Back_Home;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Log.v(TAG, "joey Map : onCreateView");
		return inflater.inflate(R.layout.map_layout, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		init(view);
		Listener();
		Map_Camera();

	}

	public void init(View view) {		
		Back_Home = (ImageView) view.findViewById(R.id.back_home);
		// 讀入動畫設定
		Animation am = AnimationUtils.loadAnimation(getActivity(), R.anim.anim);
		// 將動畫寫入ImageView
		Back_Home.setAnimation(am);
		// 開始動畫
		am.startNow();		
		Nowlocation = (Button) view.findViewById(R.id.nowlocation);
		Navigator = (Button) view.findViewById(R.id.navigator);
		if (map == null) {
			mapFrag = (SupportMapFragment) getChildFragmentManager()
					.findFragmentById(R.id.map_fragment);
			map = mapFrag.getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {
				// The Map is verified. It is now safe to manipulate the map.
				nav = new Navigator(map);
			}
		}

	}

	public void Listener() {
		Nowlocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NowLocation();

			}
		});
		Navigator.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ChooseGoWay(getActivity(), "請選擇要導航的方式", category1);
			}
		});
	}
	
	/**
	 * 定義鏡頭
	 */
	public void Map_Camera() {
		// map.setMyLocationEnabled(true);
		// locationMgr.getBestProvider(criteria, enabledOnly)

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(RDCar, 16));
		map.setOnMarkerClickListener(Map.this); //點取Marker動作  
		   // google mark  
		map.addMarker(new MarkerOptions()  
		     .position(RDCar)  
		     .title("日達汽車(中央汽車服務廠)")  
		     .snippet("保養．維修\n\n老闆:徐春基"));  
	}

	/**
	 * 導航
	 */
	public void Nav(int mode, long arrivalTime, int avoid) {
		// Log.v(TAG,"joey 我現在的位置"+MyLocation());
		Log.v(TAG, "joey HOME:" + HOME);
		if (HOME == null) {
			CustomToast.showToast(getActivity(), "定位中..請稍後", Toast.LENGTH_LONG);

		} else {
			nav.setStartPosition(HOME);
			nav.setEndPosition(RDCar);
			for (int i = 0; i < nav.getPathLines().size(); i++) {
				nav.getPathLines().get(i).remove();
			}
			Log.v(TAG, "joey nav.getPathLines().size():"
					+ nav.getPathLines().size());
			nav.findDirections(true);
			nav.setMode(mode, arrivalTime, avoid);
			nav.setOnPathSetListener(new OnPathSetListener() {

				@Override
				public void onPathSetListener(Directions directions) {
					for (int i = 0; i < directions.getRoutes().size(); i++) {
						Log.v(TAG, "joey directions.getRoutes().size():"
								+ directions.getRoutes().size());
					}
				}
			});
		}
	}

	/**
	 * 現在的位置
	 */
	public void NowLocation() {
		LM = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		currentLocation = LM.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 設定定位資訊由
																					// GPS提供
		LM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1,
				oklocationListener);
		LM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1,
				oklocationListener);
		if (!LM.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			new AlertDialog.Builder(getActivity())
					.setTitle("地圖工具")
					.setMessage("您尚未開啟定位服務，要前往設定頁面啟動定位服務嗎？")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									startActivity(new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS));
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(getActivity(),
											"未開啟定位服務，無法使用本工具!!",
											Toast.LENGTH_SHORT).show();
								}

							}).show();

		} else {

			enableTool = true;
		}

	}

	/*
	 * 現在位置的事件處理
	 */
	private final LocationListener oklocationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			lat = location.getLatitude(); // 取得經度
			lng = location.getLongitude(); // 取得緯度
			Log.v(TAG, "joey lat:" + lat);
			Log.v(TAG, "joey lng:" + lng);
			HOME = new LatLng(lat, lng);
//			map.setOnMarkerClickListener(Map.this); //點取Marker動作  
			   // google mark  
			   map.addMarker(new MarkerOptions()  
			     .position(HOME)  
			     .title("你在這裡")  
			     .snippet("快點來喔~或長按小圖找徐師傅為您服務"));  
			map.animateCamera(CameraUpdateFactory.newLatLng(HOME));
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}
	};
	//處理點擊標記後的動作
	@Override
	public boolean onMarkerClick(Marker marker) {
		CustomToast.showToast(getActivity(), "哈囉~歡迎光臨", Toast.LENGTH_LONG);
		return false;
	}

	// 選擇導航方式
	public void ChooseGoWay(Context context, String title, final String[] left) {
		NowLocation();
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
		dialog.setTitle(title);
		LinearLayout llContent = new LinearLayout(getActivity());
		llContent.setOrientation(LinearLayout.HORIZONTAL);
		final WheelView wheelLeft = new WheelView(getActivity());
		wheelLeft.setVisibleItems(category1.length + 1);
		wheelLeft.setCyclic(false);
		wheelLeft.setAdapter(new ArrayWheelAdapter<String>(left));
		LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		paramsLeft.gravity = Gravity.CENTER;

		llContent.addView(wheelLeft, paramsLeft);

		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int leftPosition = wheelLeft.getCurrentItem();
						Nav(leftPosition, 0, 0);
						Navigator.setBackgroundResource(ImageB[leftPosition]);
						dialog.dismiss();
					}
				});
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		dialog.setView(llContent);
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		SupportMapFragment f = (SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.map_fragment);
		if (f != null)
			getFragmentManager().beginTransaction().remove(f).commit();
	}



}
