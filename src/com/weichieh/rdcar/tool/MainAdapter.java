package com.weichieh.rdcar.tool;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MainAdapter extends PagerAdapter {
  
    private List<View> viewList;  
    private List<String> titleList;  
    
    public MainAdapter(List<View> viewList) {  
        // TODO Auto-generated constructor stub  
        this.viewList = viewList;  
         
    }  
      
    public MainAdapter(List<View> viewList,List<String> titleList) {  
        // TODO Auto-generated constructor stub  
        this.viewList = viewList;  
        this.titleList = titleList;  
    }  
  
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return viewList.size();  
    }  
  
    @Override  
    public boolean isViewFromObject(View arg0, Object arg1) {  
        // TODO Auto-generated method stub  
        return arg0==arg1;  
    }  
      
    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  
        // TODO Auto-generated method stub  
        container.addView(viewList.get(position));       
        return viewList.get(position);  
    }  
      
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object) {  
        // TODO Auto-generated method stub  
        container.removeView(viewList.get(position));  
    }  
      
//    @Override  
//    public CharSequence getPageTitle(int position) {  
//        // TODO Auto-generated method stub  
//        return titleList.get(position);  
//    }  
}  
