package com.example.shoppahlicfinal.views;

import android.graphics.Bitmap;

public class ShopProfileView {
	public String storename;
	public String description;
	public int subs;
	public String location;
	public Bitmap logo;
	public Bitmap banner;
	
	public ShopProfileView(){
		
	}
	
	public ShopProfileView(String storename, String description, int subs, String location, Bitmap logo, Bitmap banner){
		this.storename=storename;
		this.description=description;
		this.subs=subs;
		this.location=location;
		this.logo=logo;
		this.banner=banner;
	}
	
	
}
