package com.weichieh.rdcar;

import com.parse.ParseAnalytics;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	String TAG ="MainActivity";
	private MainHolder mHolder;
	private MainListener mListener;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ParseAnalytics.trackAppOpened(getIntent());
        initView();
    }
  
    
    @Override
	protected void onResume() {
		super.onResume();
		
		mHolder.registerListener(mListener);
	}


	private void initView(){
    	mHolder = new MainHolder(this);
    	mListener = new MainListener(this);
    }
    
    @Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			showMenu();
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {

			this.finish();
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}
    
    public void showMenu() {
		mHolder.getDrawerLayout().openDrawer(Gravity.LEFT);
	}

	public void closeMenu() {
		mHolder.getDrawerLayout().closeDrawers();
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
}
