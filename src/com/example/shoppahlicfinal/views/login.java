package com.example.shoppahlicfinal.views;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.shoppahlicfinal.R;
import com.example.shoppahlicfinal.R.id;
import com.example.shoppahlicfinal.R.layout;
import com.example.shoppahlicfinal.R.menu;
import com.example.shoppahlicfinal.controllers.JSONParser;
import com.example.shoppahlicfinal.controllers.feedlist;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {

	// Activity Views
	EditText Username, Password;
	Button btnLogin;
	Button btnRegister;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	int successIn; // to check if user successfully logged in for notifications
	
	// url to log in user
	private static String url_login_user = "http://10.0.2.2/shopaholic_api/login_user.php";
	
	// JSON tags
	private static final String TAG_SUCCESS = "success";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    private void setup(){
    	// Setup views
    	Username = (EditText)findViewById(R.id.username);
    	Password = (EditText)findViewById(R.id.password);
		btnRegister = (Button)findViewById(R.id.signup);
		btnLogin = (Button)findViewById(R.id.signin);
	
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				successIn = 0;
				Username = (EditText)findViewById(R.id.username);
		    	Password = (EditText)findViewById(R.id.password);
		    	String username=Username.getText().toString();
		    	String password=Password.getText().toString();
		    	
				if(username.trim().equals("") || password.trim().equals(""))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
				    builder.setMessage("Please fill up all the fields!");
				    builder.setCancelable(false);
				    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
				    AlertDialog alert = builder.create();
				    alert.show();
					
				}
				else{
				new LoginUser().execute();
				}
				}
		});
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(login.this, Registration.class));
				finish();
			}
		});
	}
    
    private String hashPassword(String password){
		String hashed = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte byteData[] = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for(int i=0; i < byteData.length; i++)
				 sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			hashed = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashed.substring(0, 39);
	}
    
    // Background Async Task to Login User
    class LoginUser extends AsyncTask<String, String, String>{

    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		pDialog = new ProgressDialog(login.this);	
    		pDialog.setMessage("Signing in...");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(false);
    		pDialog.show();
    	}
    	
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
				
			// Get values
			String username = Username.getText().toString();	
			String password = Password.getText().toString();
			//password = hashPassword(password);
		
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			
			// Getting JSON Object
			JSONObject json = jsonParser.makeHttpRequest(url_login_user, params);
			
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
				Toast.makeText(login.this, "No network connection!", Toast.LENGTH_SHORT).show();
				
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			if(successIn == 0)
				Toast.makeText(login.this, "Invalid username/password", Toast.LENGTH_SHORT).show();
			else{
				Intent i = new Intent(login.this, feedlist.class);
				// Store username in App Controller
					
				startActivity(i);
			} 	
		}
    }
}
