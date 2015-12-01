package com.weichieh.rdcar.system;

import com.weichieh.rdcar.MainActivity;
import com.weichieh.rdcar.service.TopVisibleService;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MyHandler extends Handler{
	
	private final static String CALL = "android.intent.action.CALL";
	MainActivity Activity;
	public MyHandler(MainActivity Act){
		Activity = Act;
		
	} 
	
	
	String TAG ="MyHandler";
	@Override
	public void handleMessage(Message msg) {
		Log.v(TAG,"joey MyHandler :"+msg);
		super.handleMessage(msg);
		
		switch (msg.what) {
		case 0: 
			Intent it = new Intent(Activity,TopVisibleService.class);
			Activity.stopService(it);
			
			break;
		case 1: 
			Intent call = new Intent(CALL, Uri.parse("tel:"
					+ "0937122297"));
			Activity.startActivity(call);
			break;
			
		case 2: 

			break;
		case 3: 

			break;
	
		default:
			break;
		}
	}
}
