package com.weichieh.rdcar.system;

import com.parse.Parse;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.weichieh.rdcar.MainActivity;
import com.weichieh.rdcar.R;

import android.app.Application;
import android.widget.Toast;

public class SysApplication extends Application {

	String TAG = "SysApplication";
	String YOUR_APP_ID = "IrY0IDFpwXwJaIoDGlOfk253VsJw9G20DuSqHcgr";
	String YOUR_CLIENT_KEY = "V7HstieGm5DwSWn7mSP024qmABb9HSAUTsEk3PGs";
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, YOUR_APP_ID, YOUR_CLIENT_KEY);
		
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
//					Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_dialog_success, Toast.LENGTH_SHORT);
//					toast.show();
				} else {
					e.printStackTrace();

//					Toast toast = Toast.makeText(getApplicationContext(), R.string.alert_dialog_failed, Toast.LENGTH_SHORT);
//					toast.show();
				}
			}
		});
		
	}
	
	
	

}
