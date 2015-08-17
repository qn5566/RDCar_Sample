package com.weichieh.rdcar.tool;


import android.app.Activity;
import android.widget.LinearLayout;
import com.weichieh.rdcar.R;
import com.google.android.gms.ads.*;

public class GoogleAD {

	private AdView adView;
	private Activity mActivity;
	private LinearLayout Layout;

	public GoogleAD(Activity Act, LinearLayout layout) {
		mActivity = Act;
		Layout = layout;
		
		Init();
	}

	public void Init() {
		// 建立 adView。
		adView = new AdView(mActivity);
		adView.setAdUnitId(mActivity.getResources().getString(
				R.string.ad_google));
		adView.setAdSize(AdSize.BANNER);

		// 假設 LinearLayout 已獲得 android:id="@+id/mainLayout" 屬性，
		// 查詢 LinearLayout。
//		LinearLayout layout = (LinearLayout) findViewById();

		// 在其中加入 adView。
		Layout.addView(adView);

		// 啟動一般請求。
		AdRequest adRequest = new AdRequest.Builder().build();

		// 以廣告請求載入 adView。
		adView.loadAd(adRequest);
	}

}
