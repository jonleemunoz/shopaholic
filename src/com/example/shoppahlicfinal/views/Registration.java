package com.example.shoppahlicfinal.views;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static String url_register_user = "http://10.0.2.2/shopaholic_api/register_user.php";
	int successIn;
	private static final String TAG_SUCCESS = "success";
	EditText username;
	EditText password;
	EditText email;
	EditText lastname;
	EditText firstname;
	Button submit;
	
	CheckBox accessories, apparel, bags, shoes;
	ArrayList<String> preferences=new ArrayList<String>();
	
	void setup(){
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.password);
		email=(EditText) findViewById(R.id.email);
		lastname=(EditText) findViewById(R.id.lastname);
		firstname=(EditText) findViewById(R.id.firstname);
		submit=(Button) findViewById(R.id.submit);
		accessories=(CheckBox) findViewById(R.id.accessories);
		apparel=(CheckBox) findViewById(R.id.apparel);
		bags= (CheckBox) findViewById(R.id.bags);
		shoes=(CheckBox) findViewById(R.id.shoes);
		
		accessories.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(accessories.isChecked())
					preferences.add("1");
				
					
			}
		});
		
apparel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(apparel.isChecked())
					preferences.add("2");
				
					
			}
		});

bags.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(bags.isChecked())
			preferences.add("3");
		
			
	}
});

shoes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(shoes.isChecked())
			preferences.add("4");
	
	}
});
		
	submit.setOnClickListener(new View.OnClickListener() {
		
	
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new RegisterUser().execute();
		}
	});
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_registration, menu);
        return true;
    }
    
    class RegisterUser extends AsyncTask<String, String, String>{

    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		pDialog = new ProgressDialog(Registration.this);	
    		pDialog.setMessage("Registering User...");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(false);
    		pDialog.show();
    	}
    	
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
				
			// Get values
			String user = username.getText().toString();	
			String pass = password.getText().toString();
			String mail= email.getText().toString();
			String lname=lastname.getText().toString();
			String fname=firstname.getText().toString();
			
			//password = hashPassword(password);

			JSONArray array =new JSONArray(preferences);
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", user));
			params.add(new BasicNameValuePair("password", pass));
			params.add(new BasicNameValuePair("email", mail));
			params.add(new BasicNameValuePair("lastname",lname));
			params.add(new BasicNameValuePair("firstname", fname));
			params.add(new BasicNameValuePair("preferences",array.toString()));
	
			
			
			// Getting JSON Object
			JSONObject json = jsonParser.makeHttpRequest(url_register_user, params);
			
			// Check for success tag
			try{
				int success = json.getInt(TAG_SUCCESS);
				if(success == 1){
					// account successfully logged in
					successIn = 1;
				}else{
					// fail to login   
					successIn = 0;
				}
			}catch (JSONException e){ 
				e.printStackTrace();
				pDialog.dismiss();
				Toast.makeText(Registration.this, "No network connection!", Toast.LENGTH_SHORT).show();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			if(successIn == 0)
				Toast.makeText(Registration.this, "failed to register user.", Toast.LENGTH_SHORT).show();
			else{
				Intent i = new Intent(Registration.this, feedlist.class);
				// Store username in App Controller
					
				startActivity(i);
			} 	
		}
    }

    
}
