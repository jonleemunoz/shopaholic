package com.example.shoppahlicfinal.views;

import com.example.shoppahlicfinal.R;
import com.example.shoppahlicfinal.R.layout;
import com.example.shoppahlicfinal.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.support.v4.app.NavUtils;

public class ListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }

    
}
