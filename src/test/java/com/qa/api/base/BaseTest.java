package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import io.qameta.allure.restassured.AllureRestAssured;
import com.qa.api.client.RestClient;
import io.restassured.RestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest {

	protected RestClient restClient;
	//*******************************API Base Urls***********************************//
	protected static final  String BASE_URL_GOREST="https://gorest.co.in";
	protected static final  String BASE_URL_CONTACTS="http://thinking-tester-contact-list.herokuapp.com";
	protected static final  String BASE_URL_REQRES="https://reqres.in";
	protected static final  String BASE_URL_BASICAUTH="https://the-internet.herokuapp.com";
	protected static final  String BASE_URL_PRODUCTS="https://fakestoreapi.com";
	protected static final  String BASE_URL_OAUTH2_AMADEUS="https://test.api.amadeus.com";
	protected static final  String BASE_URL_OAUTH2_SPOTIFY="https://accounts.spotify.com";
	protected static final  String BASE_URL_OAUTH2_ALBUM_SPOTIFY="https://api.spotify.com";
	protected static final String BASE_URL_EARGST_CIRCUIT="http://ergast.com";
	
	//*******************************API Endpoints***********************************//
	protected static final  String GOREST_USERS_ENDPOINT="/public/v2/users";
	protected static final  String CONTACTS_LOGIN_ENDPOINT="/users/login";
	protected static final  String CONTACTS_ENDPOINT="/contacts";
	protected static final  String CONTACTS_LOGOUT_ENDPOINT="/users/logout";
	protected static final  String REQRES_ENDPOINT="/api/users";
	protected static final  String BASICAUTH_ENDPOINT="/basic_auth";
	protected static final  String PRODUCTS_ENDPOINT="/products";
	protected static final  String AMADEUS_OAUTH2_TOKEN_ENDPOINT="/v1/security/oauth2/token";
	protected static final  String AMADEUS_FLIGHT_DESTINATION_ENDPOINT="/v1/shopping/flight-destinations?origin=PAR&maxPrice=200";
	protected static final  String SPOTIFY_OAUTH2_TOKEN_ENDPOINT="/api/token";
	protected static final  String SPOTIFY_ALBUM_ENDPOINT="/v1/albums/4aawyAB9vmqN3uQ7FjRGTy";
	protected static final  String EARGST_CIRCUIT_ENDPOINT="/api/f1/2017/circuits.xml";
	
	@BeforeSuite
	public void setUpAllureReport() {
		RestAssured.filters(new AllureRestAssured());
	}
	
	@BeforeTest
	public void setUp() {
		restClient=new RestClient();
		
	}
}
