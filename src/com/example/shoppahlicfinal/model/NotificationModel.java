package com.example.shoppahlicfinal.model;

import android.graphics.Bitmap;

public class NotificationModel {
	public Bitmap logo;
	public String shop;
	public String eventname;
	public String date;
	/**
	 * @param args
	 */
public NotificationModel(Bitmap logo, String shop, String eventname, String date){
	this.logo=logo;
	this.shop=shop;
	this.eventname=eventname;
	this.date=date;
}

}
