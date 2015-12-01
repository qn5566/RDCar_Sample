package com.weichieh.rdcar;

import com.weichieh.rdcar.Meun.Map;
import com.weichieh.rdcar.system.SysApplication;
import com.weichieh.rdcar.tool.CustomToast;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainListener extends SimpleOnPageChangeListener implements
		OnClickListener, OnItemClickListener {

	static Fragment fragment = null;
	static FragmentTransaction tran;
	MainHolder MHolder;
	String TAG = "MainListener";
	private MainActivity mActivity;

	public MainListener(MainActivity activity) {
		mActivity = activity;
		MHolder = new MainHolder(activity);

	}

	@Override
	public void onClick(View v) {
		Log.v(TAG, "joey onClick:" + v.getId());
		switch (v.getId()) {
		case R.id.back_button:

			if (!SysApplication.LeftMeun) {

				mActivity.showMenu();
			} else {

				mActivity.closeMenu();
			}
			break;

		case R.id.live_liantong:

			selectMenu(1);

			break;

		case R.id.setting_button:

			CustomToast.showToast(mActivity, "版本 :" + SysApplication.version,
					Toast.LENGTH_SHORT);

			break;

		case R.id.remote_control:

			CustomToast
					.showToast(mActivity, R.string.car_1, Toast.LENGTH_SHORT);
			break;
		case R.id.doc_send:

			CustomToast
					.showToast(mActivity, R.string.car_2, Toast.LENGTH_SHORT);
			break;
		case R.id.net_video:

			CustomToast
					.showToast(mActivity, R.string.car_3, Toast.LENGTH_SHORT);
			break;
		case R.id.web_site:

			CustomToast
					.showToast(mActivity, R.string.car_4, Toast.LENGTH_SHORT);
			break;
		case R.id.app_setup:

			CustomToast
					.showToast(mActivity, R.string.car_5, Toast.LENGTH_SHORT);
			break;
		case R.id.plus_app:

			CustomToast
					.showToast(mActivity, R.string.car_6, Toast.LENGTH_SHORT);
			break;
		case R.id.local_photo:
			
			mActivity.CallMe();
			
			break;
		}
	}

	/*
	 * 選擇切換Menu
	 */
	public void selectMenu(int position) {

		// Searchbtn.setImageResource(R.drawable.search_selector);
		switch (position) {
		case 0:
			

			return;

		case 1:
			fragment = new Map();

			FragmentT(fragment);
			break;

		case 2:

			break;

		case 3:

			break;

		default:

			return;
		}

	}

	// Fragment 切換
	public void FragmentT(Fragment fragment) {
		tran = mActivity.getSupportFragmentManager().beginTransaction();
		tran.addToBackStack(null);
		tran.replace(R.id.fragment_layout, fragment).commit();
	}
	
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.v(TAG, "joey onItemClick:" + parent);
		if (parent.getId() == R.id.media_options) {
			switch (position) {
			case 0://
				Log.v(TAG, "joey onItemClick : ");
				break;
			case 1://
				Log.v(TAG, "joey onItemClick : ");
				break;
			case 2://
				Log.v(TAG, "joey onItemClick : ");
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

	// 換頁選擇
	public void onPageSelected(int position) {
		Log.i(TAG, "page selected " + position);
		if (position == 1) {
			// Openinfo();
		}
		// currentPage = position;
	}
}
