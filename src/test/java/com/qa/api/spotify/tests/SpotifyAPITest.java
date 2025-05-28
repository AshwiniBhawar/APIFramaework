package com.qa.api.spotify.tests;

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

public class SpotifyAPITest extends BaseTest{

	private String accessToken;
	
	@BeforeMethod
	public void getOAuthToken() {
		Response response=restClient.post(BASE_URL_OAUTH2_SPOTIFY,SPOTIFY_OAUTH2_TOKEN_ENDPOINT, ConfigManager.get("spotifygranttype"),
				ConfigManager.get("spotifyclientid"), ConfigManager.get("spotifyclientsecret"),ContentType.URLENC);
		accessToken= response.jsonPath().getString("access_token");
		System.out.println("Access Token: "+accessToken);
		ConfigManager.set("bearertoken", accessToken);
	}
	
	@Test
	public void getFlightDetailsTest() {
		
		Response response=restClient.get(BASE_URL_OAUTH2_ALBUM_SPOTIFY, SPOTIFY_ALBUM_ENDPOINT, null ,null , AuthType.BEARER_TOKEN, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
	
	}
}
