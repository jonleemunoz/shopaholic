package com.example.shoppahlicfinal.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.shoppahlicfinal.R;
import com.example.shoppahlicfinal.R.id;
import com.example.shoppahlicfinal.R.layout;
import com.example.shoppahlicfinal.controllers.JSONParser;
import com.example.shoppahlicfinal.controllers.feedlist;
import com.example.shoppahlicfinal.model.NotificationModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Notification extends Activity{

	ListView notiflist;
	//TextView tvNoParticipants;
	NotificationModel[] notif;
	//String[] namesOfParticipants;
	//String loggedInUser;
	//private int countOfParticipants;
	// Header views
	//TextView tvTitle;
	//Button btnBack;
	
	// Database Connectivity attributes
	//private String eventId;
	ArrayList<NotificationModel> notifylist;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	private static String url_get_participants = "http://10.0.2.2/shopaholic_api/get_notifications.php";
	JSONArray notifs = null;
	ImageView logo;
	TextView shop;
	TextView eventname;
	TextView date;
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		// Load participants
		new LoadAllNotif().execute();

	}

	private void setup() {
		logo= (ImageView) findViewById(R.id.notiflogo);
		shop=(TextView) findViewById(R.id.storename);
		eventname=(TextView) findViewById(R.id.salename);
		date=(TextView) findViewById(R.id.notifdate);
		ImageView butfeed, butsale, butevent, butnotif, butmenu;
		butfeed= (ImageView) findViewById(R.id.butfeed);
		butsale=(ImageView) findViewById(R.id.butsale);
		butevent=(ImageView) findViewById(R.id.butevent);
		butnotif=(ImageView) findViewById(R.id.butnot);
		butmenu= (ImageView) findViewById(R.id.butmenu);
		
		butfeed.setOnClickListener(new View.OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Notification.this, feedlist.class);
				startActivity(intent);
			    
			}
		});
		
		butsale.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Notification.this, SaleFeed.class);
				startActivity(intent);
			    
			}
		});
		
		butevent.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Notification.this, EventFeed.class);
				startActivity(intent);
			    
			}
		});
		
butnotif.setOnClickListener(new View.OnClickListener() {
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Notification.this, Notification.class);
		startActivity(intent);
	    
	}
});

butmenu.setOnClickListener(new View.OnClickListener() {

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Notification.this, Gridlayout.class);
		startActivity(intent);
	    
	}
});

	
		
	}
	
	// Methods for database query
		class LoadAllNotif extends AsyncTask<String, String, String> {
			Bitmap bitmap;
		
			
			  
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
	    		pDialog = new ProgressDialog(Notification.this);
	    		pDialog.setMessage("Loading Notifications...");
	    		pDialog.setIndeterminate(false);
	    		pDialog.setCancelable(false);
	    		pDialog.show();
			}
			
			@Override
			protected String doInBackground(String... args) {
				// TODO Auto-generated method stub
				// Build parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				// Getting JSON object
				JSONObject json = jParser.makeHttpRequest(url_get_participants, params);

				// Check for success tag
				try {
					int success = json.getInt("success");
					if (success == 1) {

						//Get array of products
						notifs = json.getJSONArray("notifications");
					
						notifylist = new ArrayList<NotificationModel>();
						
						//Looping through all products
						for(int i=0; i < notifs.length(); i++){
							JSONObject c  = notifs.getJSONObject(i);
							
							//Storing each json item in variable
							
								String uri=c.getString("logo");
								String shop=c.getString("shop");
								String event=c.getString("event");
								String date=c.getString("date");
								
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
						      
						        
						    } catch (MalformedURLException e) {
						        e.printStackTrace();
						    } 
							
							NotificationModel note=new NotificationModel(bitmap, shop, event, date);
							notifylist.add(note);
						}
					} else {
						// failed to retrieved
						//countOfParticipants = 0;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					pDialog.dismiss();
					Toast.makeText(Notification.this, "No network connection!", Toast.LENGTH_SHORT).show();
				}

				return null;
			}
			
			@Override
		protected void onPostExecute(String result) {
//				// TODO Auto-generated method stub
		pDialog.dismiss();
//		
				setup();
//				}
			}
		}	
		
		public class NotificationAdapter extends ArrayAdapter{
			Context context;
			NotificationModel[] values=null;
			
			public NotificationAdapter(Context context, ArrayList<NotificationModel> objects) {
				super(context, R.layout.activity_notifications, objects);
				// TODO Auto-generated constructor stub
				this.context = context;
				this.values = objects.toArray(new NotificationModel[objects.size()]);
				
			}
			
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				
				
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View rowView = inflater.inflate(R.layout.activity_notif_rowlayout, parent, false);
				
				logo= (ImageView) findViewById(R.id.notiflogo);
				shop=(TextView) findViewById(R.id.storename);
				eventname=(TextView) findViewById(R.id.salename);
				date=(TextView) findViewById(R.id.feeddate);
				logo.setImageBitmap(values[position].logo);
				shop.setText(values[position].shop);
				eventname.setText(values[position].eventname);
				date.setText(values[position].date);
				
				rowView.setTag(values[position]);
				return rowView;
			}
			
			

		}


}
