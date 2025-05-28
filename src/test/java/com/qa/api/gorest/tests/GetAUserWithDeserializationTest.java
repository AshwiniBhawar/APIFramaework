package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUserWithDeserializationTest extends BaseTest{
	private String tokenId;

	@BeforeClass
	public void setUpToken() {
		tokenId = "b949db127e312a464aef7af4e192be1c0d1649b475f0b9c17e89e02d50b9fcf5";
		ConfigManager.set("bearertoken", tokenId);
	}

	@Test
	public void createASingleUserTest() {
		User user = new User(null,"Crane", StringUtils.getRandomEmailId(), "male", "active");

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertTrue(response.statusLine().contains("Created"));
		Assert.assertEquals(response.jsonPath().getString("name"), "Crane");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		String userId=response.jsonPath().getString("id");
		
		//Get- fetch the user using the same id
		Response getResponse=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.statusCode(),200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(getResponse.jsonPath().getString("id"),userId);
		
		User userResponse=JsonUtils.deserialize(getResponse, User.class);
		Assert.assertEquals(userResponse.getName(), user.getName());
		
		
	}
}
