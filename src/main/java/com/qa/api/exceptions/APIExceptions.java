package com.qa.api.exceptions;

public class APIExceptions extends RuntimeException{
	
	public APIExceptions(String mesg){
		super(mesg);						//use to throw customize exception
	}

}
