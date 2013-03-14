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
import com.example.shoppahlicfinal.model.Storelist;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SaleFeed extends Activity{

	ListView lvfeed;

	Storelist[] storefeed;

	ArrayList<Storelist> storefeedList;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	private static String url_get_participants = "http://10.0.2.2/shopaholic_api/get_allsale.php";
	JSONArray feeds = null;
	ImageView butfeed, butsale, butevent, butnotif, butmenu;
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		// Load participants
		new LoadAllFeeds().execute();

	}

	private void setup() {
		butfeed= (ImageView) findViewById(R.id.butfeed);
		butsale=(ImageView) findViewById(R.id.butsale);
		butevent=(ImageView) findViewById(R.id.butevent);
		butnotif=(ImageView) findViewById(R.id.butnot);
		butmenu= (ImageView) findViewById(R.id.butmenu);
		
		butfeed.setOnClickListener(new View.OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SaleFeed.this, feedlist.class);
				startActivity(intent);
			    
			}
		});
		
		butsale.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SaleFeed.this, SaleFeed.class);
				startActivity(intent);
			    
			}
		});
		
		butevent.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SaleFeed.this, EventFeed.class);
				startActivity(intent);
			    
			}
		});
		
butnotif.setOnClickListener(new View.OnClickListener() {
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(SaleFeed.this, Notification.class);
		startActivity(intent);
	    
	}
});

butmenu.setOnClickListener(new View.OnClickListener() {

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(SaleFeed.this, Gridlayout.class);
		startActivity(intent);
	    
	}
});

		
		
		lvfeed = (ListView) findViewById(R.id.list);
		lvfeed.setAdapter(new ListFeedAdapter(SaleFeed.this, storefeedList));
	lvfeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View v,int arg2, long arg3) {
			// TODO Auto-generated method stub
			// Do nothing as of the moment
		}
	});
		
	}
	
	// Methods for database query
		public class LoadAllFeeds extends AsyncTask<String, String, String> {
			Bitmap bitmap;
			Bitmap bitmap2;
			
			  
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
	    		pDialog = new ProgressDialog(SaleFeed.this);
	    		pDialog.setMessage("Loading");
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
						feeds = json.getJSONArray("feedlist");
					
						storefeedList = new ArrayList<Storelist>();
						
						//Looping through all products
						for(int i=0; i < feeds.length(); i++){
							JSONObject c  = feeds.getJSONObject(i);
							
							//Storing each json item in variable
							
							int hits= c.getInt("hits");
							int id=c.getInt("storeid");
							int saleid=c.getInt("saleid");
							String saleevent = c.getString("saleeventname");
							String storename = c.getString("storename");
							String date = c.getString("date");
							String uri= c.getString("logo");
							//int logo = getResources().getIdentifier(uri, null, getPackageName());
							//System.out.println("logo id:"+logo);
							//int profPic = getResources().getIdentifier(uri, null, getPackageName());
							String uri2=c.getString("banner");
							//int banner = getResources().getIdentifier(uri2, null, getPackageName());
						    
							String description= c.getString("description");
						    
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
							
							Storelist store=new Storelist(id,storename, saleevent, saleid, bitmap, date, bitmap2, hits, description);
							storefeedList.add(store);
						}
					} else {
						// failed to retrieved
						//countOfParticipants = 0;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					pDialog.dismiss();
					Toast.makeText(SaleFeed.this, "No network connection!", Toast.LENGTH_SHORT).show();
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
		
		public class ListFeedAdapter extends ArrayAdapter{
			Context context;
			Storelist[] values=null;
			
			public ListFeedAdapter(Context context, ArrayList<Storelist> objects) {
				super(context, R.layout.activity_list, objects);
				// TODO Auto-generated constructor stub
				this.context = context;
				this.values = objects.toArray(new Storelist[objects.size()]);
				
			}
			
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				
				
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View rowView = inflater.inflate(R.layout.activity_rowlayoutlist, parent, false);
				
				ImageView logo = (ImageView) rowView.findViewById(R.id.logo);
				TextView storename = (TextView) rowView.findViewById(R.id.storename);
				TextView salename= (TextView) rowView.findViewById(R.id.salename);
				TextView hits=(TextView) rowView.findViewById(R.id.hits);
				ImageView banner = (ImageView) rowView.findViewById(R.id.banner);
				TextView date=(TextView) rowView.findViewById(R.id.feeddate);
				logo.setImageBitmap(values[position].logo);
				storename.setText(values[position].storename);
				System.out.println(values[position].logo);
				salename.setText(values[position].salename);
				date.setText(values[position].date);
				banner.setImageBitmap(values[position].banner);
				hits.setText(values[position].hits+ " hits");
			
			
				storename.setOnClickListener(new View.OnClickListener() {
					
				
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(context, Shoppprofile.class);
						i.putExtra("shopid", values[position].id);
					
						startActivity(i);
					}
				});
				
				banner.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i=new Intent(context, Saledetails.class);
						i.putExtra("saleid", values[position].saleid);
						startActivity(i);
					}
				});
				
				
				rowView.setTag(values[position]);
				return rowView;
			}
			
			

		}



}
