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
import com.example.shoppahlicfinal.controllers.feedlist;
import com.example.shoppahlicfinal.model.ProductTable;
import com.example.shoppahlicfinal.model.SaleDetailWeb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Saledetails extends Activity {
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	private static String url_get_participants = "http://10.0.2.2/shopaholic_api/getsale.php";
	private static String url_get_products= "http://10.0.2.2/shopaholic_api/getproduct.php";
	JSONArray sale = null;
	JSONArray product=null;
	SaleDetailWeb saleinfo;
	ArrayList<ProductTable> pt=new ArrayList<ProductTable>();
	
	
	TextView event;
	TextView description;
	TextView datestart;
	TextView dateend;
	ImageView salebanner;
	TextView eventhits;
	 Typeface eventTypeface;
	int saleid;
	GridView grid;
	TableLayout tl;
	TableRow tr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
       saleid=getIntent().getExtras().getInt("saleid");
   //  eventTypeface = Typeface.createFromAsset(getAssets(), "BOBEL_FONT_BY_PITTERS.TTF");
       
       new getSaleDetail().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_saledetails, menu);
        return true;
    }
    
    void setup(){
    	
    	
    	ImageView butfeed= (ImageView) findViewById(R.id.butfeed);
    	ImageView butsale=(ImageView) findViewById(R.id.butsale);
    	ImageView butevent=(ImageView) findViewById(R.id.butevent);
    	ImageView butnotif=(ImageView) findViewById(R.id.butnot);
    	ImageView butmenu= (ImageView) findViewById(R.id.butmenu);
		
		butfeed.setOnClickListener(new View.OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Saledetails.this, feedlist.class);
				startActivity(intent);
			    
			}
		});
		
		butsale.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Saledetails.this, SaleFeed.class);
				startActivity(intent);
			    
			}
		});
		
		butevent.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Saledetails.this, EventFeed.class);
				startActivity(intent);
			    
			}
		});
		
butnotif.setOnClickListener(new View.OnClickListener() {
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Saledetails.this, Notification.class);
		startActivity(intent);
	    
	}
});

butmenu.setOnClickListener(new View.OnClickListener() {

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Saledetails.this, Gridlayout.class);
		startActivity(intent);
	    
	}
});

    	 event=(TextView) findViewById(R.id.saleeventname);
         description=(TextView) findViewById(R.id.saleeventdescription);
         
         salebanner=(ImageView) findViewById(R.id.saleeventbanner);
         datestart=(TextView) findViewById(R.id.saleeventdate);
         dateend=(TextView) findViewById(R.id.saleeventdateended);
         eventhits=(TextView) findViewById(R.id.saleeventhits);
         grid=(GridView) findViewById(R.id.gridproduct);
         MyGridAdapter adapter = new MyGridAdapter(this);
	        grid.setAdapter(adapter);
	        
	       
	
    }

    
    class getSaleDetail extends AsyncTask<String, String, String> {
		Bitmap bitmap;
		Bitmap picbitmap;
				
		  
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
    		pDialog = new ProgressDialog(Saledetails.this);
    		pDialog.setMessage("Loading...");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(false);
    		pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			
			// TODO Auto-generated method stub
			// Build parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("sale_id",Integer.toString(saleid)));
			
			// Getting JSON object
			JSONObject json = jParser.makeHttpRequest(url_get_participants, params);
		
			// Check for success tag
			try {
				int success = json.getInt("success");
				if (success == 1) {

					//Get array of products
					sale = json.getJSONArray("saleevent");
				
					
					//Looping through all products
					for(int i=0; i<sale.length(); i++){
						 JSONObject c  = sale.getJSONObject(i);
						
						//Storing each json item in variable
						String uri=c.getString("photo");
						String salename=c.getString("salename");
						String description=c.getString("description");
						String datestart=c.getString("datestart");
						String dateend=c.getString("dateend");
						int hits=c.getInt("hits");
						int saleid=c.getInt("saleid");
						
						 
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
					
					        
					    } catch (MalformedURLException e) {
					        e.printStackTrace();
					    } 
						
					 saleinfo=new SaleDetailWeb(saleid,bitmap, salename, description, datestart, dateend, hits);
						
					}
					}else {
					// failed to retrieved
					//countOfParticipants = 0;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			//product
			
			List<NameValuePair> params2 = new ArrayList<NameValuePair>();
			params2.add(new BasicNameValuePair("saleeventid",Integer.toString(saleid)));
			
			JSONObject json2= jParser.makeHttpRequest(url_get_products, params2);
			
			try{
				int success=json2.getInt("success");
				if(success==1){
					product=json2.getJSONArray("product");
					
			
					for(int i=0; i<product.length(); i++){
						JSONObject o= product.getJSONObject(i);
						
						String pic=o.getString("productphoto");
						int productid=o.getInt("productid"); 
						
						try{
							URL thumbnail=new URL(pic);
							HttpURLConnection connection = null;
							try {
								connection = (HttpURLConnection) thumbnail.openConnection();
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
					       picbitmap = BitmapFactory.decodeStream(inputStream);
					       
					      ProductTable ptable=new ProductTable(productid, picbitmap);
						  pt.add(ptable);
						 
						}catch(MalformedURLException e){
							e.printStackTrace();
						}
					}
					
				}else{
					
				}
			}catch(JSONException e){
				e.printStackTrace();
				pDialog.dismiss();
				Toast.makeText(Saledetails.this, "No network connection!", Toast.LENGTH_SHORT).show();
			}
			
			
			return null;
		}
			
		
		@Override
	protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
	pDialog.dismiss();
setContentView(R.layout.activity_saledetails);
			setup();
			
			
			event.setText(saleinfo.salename);
			description.setText(saleinfo.description);
			eventhits.setText(saleinfo.hits+" hits");
			datestart.setText(saleinfo.datestart);
			dateend.setText(saleinfo.dateend);
			salebanner.setImageBitmap(saleinfo.salebanner);
			    
	         TextView onsale=(TextView)findViewById(R.id.onsale);
	         event.setTypeface(eventTypeface);
	         onsale.setTypeface(eventTypeface);
	         
	         final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
	         animation.setDuration(500); // duration - half a second
	         animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
	         animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
	         animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
	         final TextView tv = (TextView) findViewById(R.id.onsale);
         tv.startAnimation(animation);
         
         	//product table
         	
		}
	}	
    
    public class MyGridAdapter extends BaseAdapter {
   	 

   	 final int NumberOfItem = pt.size();
   	  private Bitmap[] bitmap = new Bitmap[NumberOfItem];
   	   
   	  private Context context;
   	  private LayoutInflater layoutInflater;
   	   
   	  MyGridAdapter(Context c){
   	   context = c;
   	   layoutInflater = LayoutInflater.from(context);
   	    
   	   //init dummy bitmap,
   	   //using R.drawable.icon for all items
   	   for(int i = 0; i < pt.size(); i++){
   	   bitmap[i]=pt.get(i).productimage;
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
   	    final int pos=position;
   	   View grid;
   	   if(convertView==null){
   	    grid = new View(context);
   	    grid = layoutInflater.inflate(R.layout.productgridlayout, null); 
   	   }else{
   	    grid = (View)convertView; 
   	   }
   	    
   	   ImageView imageView = (ImageView) grid.findViewById(R.id.productimage);
   	   imageView.setImageBitmap(bitmap[position]);
   	  
       imageView.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent in=new Intent(Saledetails.this, Productdetails.class);
			in.putExtra("productid", pt.get(pos).productid);
			startActivity(in);
			
		}
	});
   	   
   	    
   	   return grid;
   	  }
   	 
   	 }
}
