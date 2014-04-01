package com.androidhive.utils;

public class DatabaseHandlerTestModel {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    String _des;
    String _price;
     
	// Empty constructor
    public DatabaseHandlerTestModel(){
         
    }
    // constructor
    public DatabaseHandlerTestModel(int id, String name,String price,String des, String phone_number){
        this._id = id;
        this._name = name;
        this._price = price;
        this._des = des;
        this._phone_number = phone_number;
    }
     
    // constructor
    public DatabaseHandlerTestModel(String name,String price,String des, String phone_number){
        this._name = name;
        this._price = price;
        this._des = des;
        this._phone_number = phone_number;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting name
    public String getName(){
        return this._name;
    }
     
    // setting name
    public void setName(String name){
        this._name = name;
    }

    public String getPrice() {
		return _price;
	}
    
	public void setPrice(String price) {
		this._price = price;
	}
	
	public String getDescription() {
		return _des;
	}
	
	public void setDescription(String description) {
		this._des = description;
	}
	
    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }
     
    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }
}
