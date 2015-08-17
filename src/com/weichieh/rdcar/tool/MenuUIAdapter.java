package com.weichieh.rdcar.tool;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weichieh.rdcar.R;

public class MenuUIAdapter extends BaseAdapter {

	private String[] NAMES;
	private int[] ICONS;
	private Context context;
	private LayoutInflater inflater;

	public MenuUIAdapter(Context context) {

		this.context = context;
		inflater = LayoutInflater.from(this.context);

	}

	public MenuUIAdapter(Context context, String[] names) {

		this.context = context;
		inflater = LayoutInflater.from(this.context);
		this.NAMES = names;

	}

	public MenuUIAdapter(Context context, String[] names, int[] icons) {

		this.context = context;
		inflater = LayoutInflater.from(this.context);
		this.NAMES = names;
		this.ICONS = icons;

	}

	@Override
	public int getCount() {
		return ICONS.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MainViews views;
		if (convertView == null) {
			views = new MainViews();
			convertView = inflater.inflate(R.layout.slidmenu_item, null);
			views.imageView = (ImageView) convertView
					.findViewById(R.id.iv_main_icon);
			views.textView = (TextView) convertView
					.findViewById(R.id.tv_main_name);

			convertView.setTag(views);
		} else {
			views = (MainViews) convertView.getTag();
		}
		if (ICONS.length > position && NAMES.length > position) {
			views.imageView.setImageResource(ICONS[position]);
			views.textView.setText(NAMES[position]);
		}

		return convertView;
	}

	private class MainViews {
		ImageView imageView;
		TextView textView;
	}

}
