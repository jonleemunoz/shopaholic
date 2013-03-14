package com.example.shoppahlicfinal.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.shoppahlicfinal.R;
import com.example.shoppahlicfinal.R.id;
import com.example.shoppahlicfinal.R.layout;
import com.example.shoppahlicfinal.R.menu;
import com.example.shoppahlicfinal.controllers.JSONParser;
import com.example.shoppahlicfinal.controllers.ShopSaleEvent;
import com.example.shoppahlicfinal.controllers.feedlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Shoppprofile extends Activity {
	TextView storename;
	ImageView logo;
	TextView desc;
	ImageView banner;
	TextView subs;
	TextView location;
	int id;
	Button sales,events;
	
	ShopProfileView Shops;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	private static String url_get_participants = "http://10.0.2.2/shopaholic_api/getshopprofile.php";
	JSONArray store = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
        id=getIntent().getExtras().getInt("shopid");
        System.out.println("intent id:"+id);
        new getShopProfile().execute();
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_shoppprofile, menu);
        return true;
    }

    private void setup() {
		storename=(TextView) findViewById(R.id.shopname);
		logo=(ImageView) findViewById(R.id.profilelogo);
		desc=(TextView) findViewById(R.id.malldesc);
		banner=(ImageView) findViewById(R.id.shoppic);
		subs=(TextView) findViewById(R.id.subscribe);
		location=(TextView) findViewById(R.id.profilelocation);
		
		sales=(Button) findViewById(R.id.salesbtn);
		events=(Button) findViewById(R.id.eventbtn);
		
		sales.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=new Intent(Shoppprofile.this, ShopSaleEvent.class);
			intent.putExtra("storeid", id);
			startActivity(intent);
			}
		});
		
		ImageView butfeed= (ImageView) findViewById(R.id.butfeed);
    	ImageView butsale=(ImageView) findViewById(R.id.butsale);
    	ImageView butevent=(ImageView) findViewById(R.id.butevent);
    	ImageView butnotif=(ImageView) findViewById(R.id.butnot);
    	ImageView butmenu= (ImageView) findViewById(R.id.butmenu);
		
		butfeed.setOnClickListener(new View.OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Shoppprofile.this, feedlist.class);
				startActivity(intent);
			    
			}
		});
		
		butsale.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Shoppprofile.this, SaleFeed.class);
				startActivity(intent);
			    
			}
		});
		
		butevent.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Shoppprofile.this, EventFeed.class);
				startActivity(intent);
			    
			}
		});
		
butnotif.setOnClickListener(new View.OnClickListener() {
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Shoppprofile.this, Notification.class);
		startActivity(intent);
	    
	}
});

butmenu.setOnClickListener(new View.OnClickListener() {

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Shoppprofile.this, Gridlayout.class);
		startActivity(intent);
	    
	}
});
	}
	
	// Methods for database query
		class getShopProfile extends AsyncTask<String, String, String> {
			Bitmap bitmap;
			Bitmap bitmap2;
			
			  
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
	    		pDialog = new ProgressDialog(Shoppprofile.this);
	    		pDialog.setMessage("Loading Profile...");
	    		pDialog.setIndeterminate(false);
	    		pDialog.setCancelable(false);
	    		pDialog.show();
			}
			
			@Override
			protected String doInBackground(String... args) {
				
				// TODO Auto-generated method stub
				// Build parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("store_id",Integer.toString(id)));
				
				// Getting JSON object
				JSONObject json = jParser.makeHttpRequest(url_get_participants, params);

				// Check for success tag
				try {
					int success = json.getInt("success");
					if (success == 1) {

						//Get array of products
						store = json.getJSONArray("shopprofile");
					
						
						//Looping through all products
						for(int i=0; i<store.length(); i++){
							 JSONObject c  = store.getJSONObject(i);
							
							//Storing each json item in variable
							
							int subs= c.getInt("subscription");
							String name = c.getString("storename");
							String desc = c.getString("description");
							String location = c.getString("location");
							String uri= c.getString("logo");
							//int logo = getResources().getIdentifier(uri, null, getPackageName());
							//System.out.println("logo id:"+logo);
							//int profPic = getResources().getIdentifier(uri, null, getPackageName());
							String uri2=c.getString("banner");
							//int banner = getResources().getIdentifier(uri2, null, getPackageName());
						    	
							//String description= c.getString("description");
						    
							try {
						        URL logo = new URL(uri);
						        HttpURLConnection connection = null;
								try {
									connection = (HttpURLConnection) logo.openConnection();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        InputStream inputStream = null;
								try {
									inputStream = connection.getInputStream();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        bitmap = BitmapFactory.decodeStream(inputStream);
						
						        URL banner = new URL(uri2);
						        HttpURLConnection connection2 = null;
								try {
									connection2 = (HttpURLConnection) banner.openConnection();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        InputStream inputStream2 = null;
								try {
									inputStream2 = connection2.getInputStream();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        bitmap2 = BitmapFactory.decodeStream(inputStream2);
						        
						      
						        
						    } catch (MalformedURLException e) {
						        e.printStackTrace();
						    } 
							
							Shops=new ShopProfileView(name, desc,subs, location, bitmap, bitmap2);
							
						}
						}else {
						// failed to retrieved
						//countOfParticipants = 0;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					pDialog.dismiss();
					Toast.makeText(Shoppprofile.this, "No network connection!", Toast.LENGTH_SHORT).show();
				}
				
				
				return null;
			}
				
			
			@Override
		protected void onPostExecute(String result) {
//				// TODO Auto-generated method stub
		pDialog.dismiss();
	 setContentView(R.layout.activity_shoppprofile);
				setup();
				
			storename.setText(Shops.storename);
			desc.setText(Shops.description);
			subs.setText(Shops.subs+" people subscribed");
			location.setText(Shops.location);
			logo.setImageBitmap(Shops.logo);
			banner.setImageBitmap(Shops.banner);
		

			}
		}	
		
		
    
}
