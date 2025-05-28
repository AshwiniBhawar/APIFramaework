package com.qa.api.amadeus.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{

	private String accessToken;
	
	@BeforeMethod
	public void getOAuthToken() {
		Response response=restClient.post(BASE_URL_OAUTH2_AMADEUS,AMADEUS_OAUTH2_TOKEN_ENDPOINT, ConfigManager.get("amdeusgranttype"),
				ConfigManager.get("amdeusclientid"), ConfigManager.get("amdeusclientsecret"),ContentType.URLENC);
		accessToken= response.jsonPath().getString("access_token");
		System.out.println("Access Token: "+accessToken);
		ConfigManager.set("bearertoken", accessToken);
	}
	
	@Test(enabled=false)
	public void getFlightDetailsTest() {
		
		Map<String,String> queryParams= new HashMap<String, String>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice","200");
		
		Response response=restClient.get(BASE_URL_OAUTH2_AMADEUS, AMADEUS_FLIGHT_DESTINATION_ENDPOINT, queryParams,null , AuthType.BEARER_TOKEN, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
	
	}
}
