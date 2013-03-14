package com.example.shoppahlicfinal.model;

import android.graphics.Bitmap;

public class ProductTable {
	public int productid;
	public Bitmap productimage;
	
	public ProductTable(){
		
	}
	
	public ProductTable(int productid, Bitmap productimage){
		this.productid=productid;
		this.productimage=productimage;
	}

}
