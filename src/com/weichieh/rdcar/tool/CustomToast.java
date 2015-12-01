package com.weichieh.rdcar.tool;

/**
 * 這是一個客製化的 Toast
 * @author 
 */
import com.weichieh.rdcar.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
	private static Toast mToast;
	static TextView Toast_Text;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	public static void showToast(Context mContext, String text, int duration) {

		mHandler.removeCallbacks(r);
		if (mToast != null){
			Toast_Text.setText(text);
		}else{
			mToast = new Toast(mContext);
			View view = LayoutInflater.from(mContext).inflate(R.layout.toast_cus, null);
			Toast_Text  = (TextView) view.findViewById(R.id.toast_text);
			Toast_Text.setText(text);
	        //設定該View
			mToast.setView(view);
		}	
		mHandler.postDelayed(r, duration + 1000);
      
		mToast.show();
	}

	public static void showToast(Context mContext, int resId, int duration) {
		showToast(mContext, mContext.getResources().getString(resId), duration);
	}
}
