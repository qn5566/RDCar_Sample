package com.weichieh.rdcar;


import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainListener extends SimpleOnPageChangeListener implements
		OnClickListener, OnItemClickListener {

	String TAG = "MainListener";
	private MainActivity mActivity;
	
	public MainListener(MainActivity activity){
		mActivity = activity;
		
		
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.getId() == R.id.media_options) {
			switch (position) {
			case 0:// 
				
				break;
			case 1:// 
				
				break;
			case 2:// 
				
				break;
			case 3:// 
				
				break;
			default:
				break;
			}

		} else if (parent.getId() == R.id.remote_options) {
			switch (position) {
			case 0:// 
				
				break;
			case 1:// 
				
				break;
			case 2:// 
				
				break;
			case 3:// 
					
				break;

			default:
				break;
			}

		} else if (parent.getId() == R.id.interactive_options) {
			switch (position) {
			case 0:// 
				
				break;
			case 1:// 
				
				break;
			case 2:// 
				
				break;
			case 3:// 
				
				break;

			default:
				break;
			}
		}
		mActivity.closeMenu();
	}


	//換頁選擇
	public void onPageSelected(int position) {
		Log.i(TAG, "page selected " + position);
		if (position == 1) {
			// Openinfo();
		}
		// currentPage = position;
	}
}
