package com.example.shoppahlicfinal.views;

import com.example.shoppahlicfinal.R;
import com.example.shoppahlicfinal.R.layout;
import com.example.shoppahlicfinal.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class Rowlayoutlist extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rowlayoutlist);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rowlayoutlist, menu);
        return true;
    }

    
}
