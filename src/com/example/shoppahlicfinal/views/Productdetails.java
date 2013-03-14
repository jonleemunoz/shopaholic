package com.example.shoppahlicfinal.views;

import com.example.shoppahlicfinal.*;
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
import com.example.shoppahlicfinal.model.ProductDetailModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Productdetails extends Activity {
	JSONArray product = null;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	private static String url_product_detail = "http://10.0.2.2/shopaholic_api/getproductdetail.php";
	ImageView productimage;
	TextView productname, stock, price, des;
	int productid;
	ProductDetailModel pdm;
	Bitmap bitmap;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		productid = getIntent().getExtras().getInt("productid");
		new getProductDetail().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_productdetails, menu);
		return true;
	}

	void setup() {
		productimage = (ImageView) findViewById(R.id.productimage);
		productname = (TextView) findViewById(R.id.productname);
		des = (TextView) findViewById(R.id.productdescription);
		price = (TextView) findViewById(R.id.productprice);
		stock = (TextView) findViewById(R.id.productqty);
		//productimage.setImageBitmap(pdm.image);
		productname.setText(pdm.name);
		des.setText(pdm.description);
		price.setText("from P" + pdm.price + " to P" + pdm.saleprice + "!");
		stock.setText(pdm.stock + " items left!");
	}

	class getProductDetail extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Productdetails.this);
			pDialog.setMessage("Loading Product...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			// TODO Auto-generated method stub
			// Build parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("product_id", Integer
					.toString(productid)));

			// Getting JSON object
			JSONObject json = jParser.makeHttpRequest(url_product_detail,
					params);

			// Check for success tag
			try {
				int success = json.getInt("success");
				if (success == 1) {

					// Get array of products
					product = json.getJSONArray("product");

					// Looping through all products
					for (int i = 0; i < product.length(); i++) {
						JSONObject c = product.getJSONObject(i);

						// Storing each json item in variable
						String uri = c.getString("image");
						String name = c.getString("name");
						int price = c.getInt("price");
						int saleprice = c.getInt("saleprice");
						String des = c.getString("description");
						int qty = c.getInt("qty");

						// String description= c.getString("description");

						try {
							URL logo = new URL(uri);
							HttpURLConnection connection = null;
							try {
								connection = (HttpURLConnection) logo
										.openConnection();
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
							pdm = new ProductDetailModel(bitmap, name, price,
									saleprice, des, qty);

						} catch (MalformedURLException e) {
							e.printStackTrace();
						}

					}
				} else {
					// failed to retrieved
					// countOfParticipants = 0;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				pDialog.dismiss();
				Toast.makeText(Productdetails.this, "No network connection!", Toast.LENGTH_SHORT).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// // TODO Auto-generated method stub
			pDialog.dismiss();
			setContentView(R.layout.activity_productdetails);
			setup();

			// product table

		}
	}

}
