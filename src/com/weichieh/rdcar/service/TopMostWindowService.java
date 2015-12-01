package com.weichieh.rdcar.service;

import com.weichieh.rdcar.system.SysApplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public abstract class TopMostWindowService extends Service implements
		View.OnTouchListener {

	private String TAG = "TopMostWindowService";

	private final IBinder mBinder;

	private float mTouchStartX;

	private float mTouchStartY;

	private float x;

	private float y;

	private final WindowManager.LayoutParams mParams;

	private MyPhoneStateListener mPhoneStateListener;

	private View mView;

	private WindowManager mWindowManager;

	public TopMostWindowService() {
		mBinder = new TopWindowServiceBinder();
		mParams = new WindowManager.LayoutParams();
	}

	private void start(Intent paramIntent) {
		if (this.mView == null) {
			openWindow();
			centerWindow();
		}
	}

	protected final void centerWindow() {
		this.mView.measure(0, 0);
		mParams.x = 30;// (mWindowManager.getDefaultDisplay().getWidth() -
						// mView.getMeasuredWidth()) / 2;
		mParams.y = 30;// (mWindowManager.getDefaultDisplay().getHeight() -
						// mView.getMeasuredHeight()) / 2;
		if (onWindowLayoutChanged(mParams)) {
			mWindowManager.updateViewLayout(mView, mParams);
		}
		Log.i(TAG, "centerWindow x=" + mParams.x + "y=" + mParams.y);

	}

	protected final void closeWindow() {
		if (this.mView != null) {
			this.mWindowManager.removeView(mView);
			destroyView(mView);
			this.mView = null;
		}
	}

	protected abstract View createView();

	protected abstract void destroyView(View paramView);

	protected LayoutParams getWindowAttributes() {
		return this.mParams;
	}

	protected View getWindowView() {
		return this.mView;
	}

	public IBinder onBind(Intent paramIntent) {
		return this.mBinder;
	}

	protected void onCallStateChanged(int paramInt, String paramString) {
		if (paramInt != 0)
			stopSelf();
	}

	public void onCreate() {
		this.mWindowManager = (WindowManager) getSystemService("window");
		;
		this.mPhoneStateListener = new MyPhoneStateListener();
		TelephonyManager lcTm = (TelephonyManager) getSystemService("phone");
		lcTm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	public void onDestroy() {
		closeWindow();
		TelephonyManager ltm = (TelephonyManager) getSystemService("phone");
		ltm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}

	public void onStart(Intent paramIntent, int paramInt) {
		start(paramIntent);
	}

	public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
		start(paramIntent);
		return 1;
	}

	public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {

		x = paramMotionEvent.getRawX();
		y = paramMotionEvent.getRawY() - 25;
		Log.i("currP", "currX" + x + "====currY" + y);
		switch (paramMotionEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			SysApplication.getHandler().sendEmptyMessageDelayed(1,
					1500);
			mTouchStartX = paramMotionEvent.getX();
			mTouchStartY = paramMotionEvent.getY();
			Log.i("startP", "startX" + mTouchStartX + "====startY"
					+ mTouchStartY);
			break;
		case MotionEvent.ACTION_MOVE:
			SysApplication.getHandler().removeMessages(1);
			updateWindowLayout();
			
			break;
		case MotionEvent.ACTION_UP:
			updateWindowLayout();
			mTouchStartX = mTouchStartY = 0;
			SysApplication.getHandler().removeMessages(1);
			break;
		}
		return true;
	}
	
	

	protected boolean onWindowLayoutChanged(
			WindowManager.LayoutParams paramLayoutParams) {
		return true;
	}

	protected void onWindowLayoutInit(
			WindowManager.LayoutParams paramLayoutParams) {
	}

	/*
	 * 打開視窗的大小
	 */
	protected final void openWindow() {
		if (mView == null) {
			mView = createView();
			if (mView != null) {
				mView.setOnTouchListener(this);
				mView.measure(0, 0);
				mParams.x = 50;// (mWindowManager.getDefaultDisplay().getWidth()
								// - mView.getMeasuredWidth()) / 2;
				mParams.y = 50;// (mWindowManager.getDefaultDisplay().getHeight()
								// - mView.getMeasuredHeight()) / 2;
				mParams.width = 150;
				mParams.height = 150;
				mParams.type = 2003;
				mParams.gravity = 51;
				mParams.flags = 520;
				mParams.format = 1;
				onWindowLayoutInit(mParams);
				mWindowManager.addView(mView, mParams);
				Log.i(TAG, "openWindow x=" + mParams.x + "y=" + mParams.y);
			}
		}
	}

	protected void updateWindowLayout() {

		mParams.x = (int) (x - mTouchStartX);
		mParams.y = (int) (y - mTouchStartY);
		Log.i(TAG, "x=" + mParams.x + "y=" + mParams.y);
		mWindowManager.updateViewLayout(mView, mParams);

	}

	class MyPhoneStateListener extends PhoneStateListener {
		private MyPhoneStateListener() {
		}

		public void onCallStateChanged(int paramInt, String paramString) {
			TopMostWindowService.this.onCallStateChanged(paramInt, paramString);
		}
	}

	public class TopWindowServiceBinder extends Binder {
		public TopWindowServiceBinder() {
		}

		TopMostWindowService getService() {
			return TopMostWindowService.this;
		}
	}
}
