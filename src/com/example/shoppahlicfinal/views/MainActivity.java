package com.example.shoppahlicfinal.views;


import com.example.shoppahlicfinal.R;
import com.example.shoppahlicfinal.R.drawable;
import com.example.shoppahlicfinal.R.layout;
import com.example.shoppahlicfinal.controllers.feedlist;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab);
        
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
//        
//        intent=new Intent();
//        if(intent.getExtras().toString()=="shopprofile"){
//        	intent.setClass(this, Shoppprofile.class);
//        }
          
        intent = new Intent().setClass(this, feedlist.class);
        //spec.setIndicator("",res.getDrawable(android.R.drawable.ic_menu_rotate));
        spec = tabHost.newTabSpec("profile").setIndicator("",res.getDrawable(R.drawable.feed)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, Shoppprofile.class);
        
        spec = tabHost.newTabSpec("sales").setIndicator("",res.getDrawable(R.drawable.saleicon)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, Shoppprofile.class);
        
        spec = tabHost.newTabSpec("event").setIndicator("",res.getDrawable(R.drawable.event)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, Shoppprofile.class);
        
        spec = tabHost.newTabSpec("notifications").setIndicator("",res.getDrawable(R.drawable.note)).setContent(intent);
        tabHost.addTab(spec);
        
        
        intent = new Intent().setClass(this, Shoppprofile.class);
        
        spec = tabHost.newTabSpec("Menu").setIndicator("",res.getDrawable(R.drawable.menuicon)).setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
        for(int i=0;i<5;i++) 
        { 
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(12.0f);
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 203;
        } 
        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTextSize(12.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.maintab, menu);
        return true;
    }
    
    public void onTabChanged(String tag) {
    	 TabHost tabHost = getTabHost();
            
		}
    }

