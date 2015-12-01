package com.weichieh.rdcar.service;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.weichieh.rdcar.R;

public class TopVisibleService extends TopMostWindowService {
	@Override
	public void onStart(Intent paramIntent, int paramInt) {
		super.onStart(paramIntent, paramInt);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected View createView() {
		View myView = ((LayoutInflater) getSystemService("layout_inflater"))
				.inflate(R.layout.topmostview, null);
		
		return myView;
	}

	@Override
	protected void destroyView(View paramView) {
	}

	@Override
	protected void onCallStateChanged(int paramInt, String paramString) {
		super.onCallStateChanged(paramInt, paramString);
	}

}
