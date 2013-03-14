package com.example.shoppahlicfinal.model;

import java.util.Comparator;



public class User {

	
	public String username;
	public String lastname;
	public String firstname;
	public String email;
	public String uid;
	
	//constructors
	public User(){}

	public User(String username, String lastname, String firstname, String email, String uid){
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.uid= uid;
	}
	
}
