package com.example.shoppahlicfinal.views;

import com.example.shoppahlicfinal.R;
import com.example.shoppahlicfinal.R.drawable;
import com.example.shoppahlicfinal.R.id;
import com.example.shoppahlicfinal.R.layout;
import com.example.shoppahlicfinal.controllers.feedlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class Gridlayout extends Activity{

	 public class MyAdapter extends BaseAdapter {
	 final String[] Items = new String[] { "Shops", "Categories",
				"Subscriptions", "Logout", };
	 final Integer[] Icons = {
	         R.drawable.storegrid, R.drawable.categorygrid,
	         R.drawable.subsgrid, R.drawable.logoutgrid};
	 final int NumberOfItem = 4;
	  private Bitmap[] bitmap = new Bitmap[NumberOfItem];
	   
	  private Context context;
	  private LayoutInflater layoutInflater;
	   
	  MyAdapter(Context c){
	   context = c;
	   layoutInflater = LayoutInflater.from(context);
	    
	   //init dummy bitmap,
	   //using R.drawable.icon for all items
	   for(int i = 0; i < NumberOfItem; i++){
	    bitmap[i] = BitmapFactory.decodeResource(context.getResources(), Icons[i]);
	   }
	  }
	 
	  public int getCount() {
	   // TODO Auto-generated method stub
	   return bitmap.length;
	  }
	 
	  public Object getItem(int position) {
	   // TODO Auto-generated method stub
	   return bitmap[position];
	  }
	 
	  public long getItemId(int position) {
	   // TODO Auto-generated method stub
	   return position;
	  }
	 
	  public View getView(int position, View convertView, ViewGroup parent) {
	   // TODO Auto-generated method stub
	    
	   View grid;
	   if(convertView==null){
	    grid = new View(context);
	    grid = layoutInflater.inflate(R.layout.gridlayout, null); 
	   }else{
	    grid = (View)convertView; 
	   }
	    
	   ImageView imageView = (ImageView)grid.findViewById(R.id.gridimage);
	   imageView.setImageBitmap(bitmap[position]);
	   TextView textView = (TextView)grid.findViewById(R.id.gridtext);
	   textView.setText(Items[position]);
	    
	   return grid;
	  }
	 
	 }
	 
	 GridView gridView;
	  
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_gridlayout);
	        gridView = (GridView)findViewById(R.id.grid);
	       ImageView butfeed, butsale, butevent, butnotif, butmenu;
	    	butfeed= (ImageView) findViewById(R.id.butfeed);
			butsale=(ImageView) findViewById(R.id.butsale);
			butevent=(ImageView) findViewById(R.id.butevent);
			butnotif=(ImageView) findViewById(R.id.butnot);
			butmenu= (ImageView) findViewById(R.id.butmenu);
			
			butfeed.setOnClickListener(new View.OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Gridlayout.this, feedlist.class);
					startActivity(intent);
				    
				}
			});
			
			butsale.setOnClickListener(new View.OnClickListener() {
		
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Gridlayout.this, SaleFeed.class);
					startActivity(intent);
				    
				}
			});
			
			butevent.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Gridlayout.this, EventFeed.class);
					startActivity(intent);
				    
				}
			});
			
	butnotif.setOnClickListener(new View.OnClickListener() {
		

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(Gridlayout.this, Notification.class);
			startActivity(intent);
		    
		}
	});

	butmenu.setOnClickListener(new View.OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(Gridlayout.this, Gridlayout.class);
			startActivity(intent);
		    
		}
	});

	        MyAdapter adapter = new MyAdapter(this);
	        gridView.setAdapter(adapter);
	    }
}
