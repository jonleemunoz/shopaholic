package com.example.shoppahlicfinal.model;

import android.graphics.Bitmap;

public class SaleDetailWeb {
	public Bitmap salebanner;
	public String salename;
	public String description;
	public String datestart;
	public String dateend;
	public int hits;
	public int id;
	
	public SaleDetailWeb(int id,Bitmap salebanner, String salename, String description, String datestart, String dateend, int hits){
		this.salebanner=salebanner;
		this.salename=salename;
		this.description=description;
		this.datestart=datestart;
		this.dateend=dateend;
		this.hits=hits;
		this.id=id;
	}
}
