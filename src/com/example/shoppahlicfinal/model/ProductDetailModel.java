package com.example.shoppahlicfinal.model;

import android.graphics.Bitmap;

public class ProductDetailModel {
	Bitmap image;
	public String name;
	public int price;
	public int saleprice;
	public String description;
	public int stock;
	/**
	 * @param args
	 */
	
	public ProductDetailModel(){
		
	}
	
	public ProductDetailModel(Bitmap image, String name, int price, int saleprice, String description, int stock){
		this.image=image;
		this.name=name;
		this.price=price;
		this.saleprice=saleprice;
		this.description=description;
		this.stock=stock;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
