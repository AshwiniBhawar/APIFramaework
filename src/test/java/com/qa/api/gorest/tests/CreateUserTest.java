package com.qa.api.gorest.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {

	private String tokenId;

	@BeforeClass
	public void setUpToken() {
		tokenId = "b949db127e312a464aef7af4e192be1c0d1649b475f0b9c17e89e02d50b9fcf5";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@DataProvider(name="getuserdata")
	public Object[][] getUserData() {
		Object[][] data= {
				{"Ash b","female","active"},
				{"Kiram","male","inactive"},
				{"Rani","female","active"}
		};		
		return data;
	}

	@Test(dataProvider="getuserdata")
	public void createASingleUserTestWithDP(String name, String gender, String status) {
		
		User user = new User(null, name, StringUtils.getRandomEmailId(), gender, status);

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(response.jsonPath().getString("name"), name);
		Assert.assertEquals(response.jsonPath().getString("gender"), gender);
		Assert.assertEquals(response.jsonPath().getString("status"), status);
		Assert.assertNotNull(response.jsonPath().getString("id"));
		Assert.assertTrue(response.statusLine().contains("Created"));
		ChainTestListener.log("user id: "+ response.jsonPath().getString("id"));
	}
	
	@Test
	public void createASingleUserTest() {
				
		User user = new User(null, "Crane", StringUtils.getRandomEmailId(), "male", "active");

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(response.jsonPath().getString("name"), "Crane");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		Assert.assertTrue(response.statusLine().contains("Created"));
		ChainTestListener.log("user id: "+ response.jsonPath().getString("id"));
	}

	@Test
	public void createASingleUserWithStringTest() {
		String emailId = StringUtils.getRandomEmailId();
		String user = "{\r\n" + "    \"name\": \"Ash b\",\r\n" + "    \"gender\": \"female\",\r\n" + "    \"email\": \""
				+ emailId + "\",\r\n" + "    \"status\": \"active\"\r\n" + "}";

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(response.jsonPath().getString("name"), "Ash b");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		Assert.assertTrue(response.statusLine().contains("Created"));
	}

	@Test
	public void createASingleUserWithFileTest() {
		String emailId = StringUtils.getRandomEmailId();
		String rawJson = null;
		try {
			rawJson = new String(Files.readAllBytes(Paths.get("./src/resources/java/jsons/user.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String updatedJson = rawJson.replace("{{emailID}}", emailId);

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, updatedJson, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(response.jsonPath().getString("name"), "Ash b");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		Assert.assertTrue(response.statusLine().contains("Created"));
	}
}
