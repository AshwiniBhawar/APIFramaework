package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import io.qameta.allure.restassured.AllureRestAssured;
import com.qa.api.client.RestClient;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest {

	protected RestClient restClient;
	//*******************************API Base Urls***********************************//
	protected static String BASE_URL_GOREST;
	protected static String BASE_URL_CONTACTS;
	protected static String BASE_URL_REQRES;
	protected static String BASE_URL_BASICAUTH;
	protected static String BASE_URL_PRODUCTS;
	protected static String BASE_URL_OAUTH2_AMADEUS;
	protected static String BASE_URL_OAUTH2_SPOTIFY;
	protected static String BASE_URL_OAUTH2_ALBUM_SPOTIFY;
	protected static String BASE_URL_ERGAST_CIRCUIT;
	
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
	protected static final  String SPOTIFY_OAUTH2_ALBUM_ENDPOINT="/v1/albums/4aawyAB9vmqN3uQ7FjRGTy";
	protected static final  String ERGAST_CIRCUIT_ENDPOINT="/api/f1/2017/circuits.xml";
	
	@BeforeSuite
	public void initialSetUp() {
		RestAssured.filters(new AllureRestAssured());
		
		BASE_URL_GOREST=ConfigManager.get("baseurl.gorest");
		BASE_URL_CONTACTS=ConfigManager.get("baseurl.contacts");
		BASE_URL_REQRES=ConfigManager.get("baseurl.reqres");
		BASE_URL_BASICAUTH=ConfigManager.get("baseurl.basicauth");
		BASE_URL_PRODUCTS=ConfigManager.get("baseurl.products");
		BASE_URL_OAUTH2_AMADEUS=ConfigManager.get("baseurl.oauth2.amadeus");
		BASE_URL_OAUTH2_SPOTIFY=ConfigManager.get("baseurl.oauth2.spotify");
		BASE_URL_OAUTH2_ALBUM_SPOTIFY=ConfigManager.get("baseurl.oauth2.album.spotify");
		BASE_URL_ERGAST_CIRCUIT=ConfigManager.get("baseurl.ergast.circuit");
	}
	
	@BeforeTest
	public void setUp() {
		restClient=new RestClient();
		
	}
}
