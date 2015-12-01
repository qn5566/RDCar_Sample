package com.weichieh.rdcar;

import java.util.ArrayList;
import java.util.List;

import com.weichieh.rdcar.tool.GoogleAD;
import com.weichieh.rdcar.tool.MainAdapter;
import com.weichieh.rdcar.tool.MenuUIAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainHolder {

	private MainActivity mContext;
	String TAG = "MainHolder";
	private MenuUIAdapter remoteAdapter;
	private MenuUIAdapter mediaAdapter;
	private MenuUIAdapter interactiveAdapter;
	private DrawerLayout drawer_Layout;
	private TextView device_name;
	private TextView menu_device_name;
	// 左側選單，listview
	private ListView remote_list;
	private ListView local_media_list;
	private ListView interactive_list;
	private ViewPager mViewPager;
	private List<View> viewList;
	private LinearLayout AD_Google;
	private GoogleAD Google_AD;
	private ImageView Back_Button,Setting_Button;
	private ImageView Live_Liantong,Remote_control,Doc_send
			,Net_video,Web_site,App_setup,Plus_app,Local_photo;
	MainListener mMainListener;
	
	public MainHolder(MainActivity context) {

		mContext = context;
		
		
	}
	
	public MainHolder(MainActivity context,MainListener mMain) {

		mContext = context;
		mMainListener = mMain;
		findView();
	}

	private void findView() {
		if (mContext == null) {
			return;
		}
		drawer_Layout = (DrawerLayout) mContext
				.findViewById(R.id.drawer_layout);

		AD_Google = (LinearLayout) mContext.findViewById(R.id.ad_google);
		Back_Button = (ImageView) mContext.findViewById(R.id.back_button);
		Setting_Button = (ImageView) mContext.findViewById(R.id.setting_button);
		MainMenu();
		drawerMenuinit();
		AD_Google();
	}

	// 增加廣告
	public void AD_Google() {
		Google_AD = new GoogleAD(mContext, AD_Google);
	}

	/**
	 * 首頁初始化
	 */
	private void MainMenu() {
		LayoutInflater mInflater = mContext.getLayoutInflater().from(mContext);

		View v1 = mInflater.inflate(R.layout.home_1, null);
		View v2 = mInflater.inflate(R.layout.home_2, null);

		Live_Liantong = (ImageView) v1.findViewById(R.id.live_liantong);
		Remote_control = (ImageView) v1.findViewById(R.id.remote_control);
		Doc_send = (ImageView) v1.findViewById(R.id.doc_send);
		Net_video = (ImageView) v1.findViewById(R.id.net_video);
		Web_site = (ImageView) v1.findViewById(R.id.web_site);
		App_setup = (ImageView) v1.findViewById(R.id.app_setup);
		Plus_app = (ImageView) v1.findViewById(R.id.plus_app);
		Local_photo = (ImageView) v1.findViewById(R.id.local_photo);
		// 加上頁面數據
		viewList = new ArrayList<View>();
		viewList.add(v1);
		viewList.add(v2);
		// 實列化
		mViewPager = (ViewPager) mContext.findViewById(R.id.viewpager);
		mViewPager.setAdapter(new MainAdapter(viewList));
		mViewPager.setCurrentItem(0); // 設置默認當前頁
		// mViewPager.setPageTransformer(arg0, arg1);
		// View view = viewList.get(0);
//		mViewPager.setOnPageChangeListener(new PageListener());
//		mViewPager.setOnClickListener(mMainListener);
//		Live_Liantong.setOnClickListener(mMainListener);
	}

	/**
	 * 左側選單初始化
	 */
	private void drawerMenuinit() {
		FrameLayout frameLayout = (FrameLayout) mContext
				.findViewById(R.id.content_menu);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View childView = inflater.inflate(R.layout.slidmenu, null);
		frameLayout.addView(childView);
		remote_list = (ListView) childView.findViewById(R.id.remote_options);
		local_media_list = (ListView) childView
				.findViewById(R.id.media_options);
		interactive_list = (ListView) childView
				.findViewById(R.id.interactive_options);

		menu_device_name = (TextView) childView
				.findViewById(R.id.menu_device_name);

		remoteAdapter = new MenuUIAdapter(mContext, mContext.getResources()
				.getStringArray(R.array.remote_array), remoteIcons);
		mediaAdapter = new MenuUIAdapter(mContext, mContext.getResources()
				.getStringArray(R.array.local_media_array), localMediaIcons);
		interactiveAdapter = new MenuUIAdapter(mContext, mContext
				.getResources().getStringArray(R.array.interactive_array),
				interactiveIcons);

		remote_list.setAdapter(remoteAdapter);
		local_media_list.setAdapter(mediaAdapter);
		interactive_list.setAdapter(interactiveAdapter);

		MainListener_list Mainlist = new MainListener_list(mContext);
		remote_list.setOnItemClickListener(Mainlist);
		local_media_list.setOnItemClickListener(Mainlist);
		interactive_list.setOnItemClickListener(Mainlist);
		
		frameLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// 攔截menu的touch事件，以免透到下面的home頁面上
				return true;
			}

		});

	}

	public void registerListener(MainListener Listener) {
		if (mContext == null) {

			return;
		}
		mViewPager.setOnPageChangeListener(Listener);
//		remote_list.setOnItemClickListener(Listener);
//		local_media_list.setOnItemClickListener(Listener);
//		interactive_list.setOnItemClickListener(Listener);
		Back_Button.setOnClickListener(Listener);
		Setting_Button.setOnClickListener(Listener);
		Live_Liantong.setOnClickListener(Listener);
		Remote_control.setOnClickListener(Listener);
		Doc_send.setOnClickListener(Listener);
		Net_video.setOnClickListener(Listener);
		Web_site.setOnClickListener(Listener);
		App_setup.setOnClickListener(Listener);
		Plus_app.setOnClickListener(Listener);
		Local_photo.setOnClickListener(Listener);
		
	}

	private int[] localMediaIcons = { R.drawable.meun_4, R.drawable.meun_5,
			R.drawable.meun_6 };
	// R.drawable.menu_icon_doc
	private int[] remoteIcons = { R.drawable.meun_1, R.drawable.meun_2,
			R.drawable.meun_3 /*
							 * , R.drawable. menu_icon_game
							 */};
	private int[] interactiveIcons = { R.drawable.meun_7, R.drawable.meun_8 };

	public void setDeviceName(String state, String name) {
		if (name != null) {
			device_name.setText(state + name);
			menu_device_name.setText(name);
		} else {
			device_name.setText(state);
			menu_device_name.setText(state);
		}
	}

	public DrawerLayout getDrawerLayout() {
		return drawer_Layout;
	}

}
	
 	class MainListener_list implements OnItemClickListener {
 		
 		MainActivity mCon;
 		public MainListener_list(MainActivity mContext){
 			
 			mCon = mContext;
 		}
 		
 		String TAG = "MainListener_list";
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
		mCon.closeMenu();
	}

}