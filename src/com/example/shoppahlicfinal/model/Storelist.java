package com.example.shoppahlicfinal.model;

import android.graphics.Bitmap;

public class Storelist {
	public String storename;
	public String salename;
	public Bitmap logo;
    public int hits;
	public String date;
	public Bitmap banner;
	public String description;
	public int id;
	public int saleid;
	
	public Storelist(){
		
	}
	
	public Storelist(int id,String storename, String salename, int saleid, Bitmap logo, String date, Bitmap banner, int hits, String description){
		this.storename=storename; 
		this.salename=salename;
		this.logo=logo;
		this.hits=hits;
		this.date=date;
		this.banner=banner;
		this.description=description;
		this.id=id;
		this.saleid=saleid;
		
		//System.out.println("logo:"+this.logo);
	}
	
	
}
