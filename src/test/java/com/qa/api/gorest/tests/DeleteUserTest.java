package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest {

	private String tokenId;

	@BeforeClass
	public void setUpToken() {
		tokenId = "b949db127e312a464aef7af4e192be1c0d1649b475f0b9c17e89e02d50b9fcf5";
		ConfigManager.set("bearertoken", tokenId);
	}

	@Test
	public void deleteUserTest() {
		ChainTestListener.log("delete user test");
		// 1.Post-create a user using builder patter
		// User user= new User("Crane", StringUtils.getRandomEmailId(), "male", "active");
		
		User user = User.builder().name("ash").email(StringUtils.getRandomEmailId()).gender("female").status("active")
				.build();

		Response postResponse = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(postResponse.statusCode(), 201);
		Assert.assertEquals(postResponse.jsonPath().getString("name"), "ash");
		Assert.assertNotNull(postResponse.jsonPath().getString("id"));

		// fetch the userid
		String userid = postResponse.jsonPath().getString("id");
		System.out.println("user id is: " + userid);
		ChainTestListener.log("user id: "+userid);

		System.out.println("==========================================================================================");

		// 2.Get- fetch the user using the same userid
		Response getResponse = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userid, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(getResponse.jsonPath().getString("id"), userid);

		System.out.println("==========================================================================================");

		// 3.Delete- delete the user with same userid
		Response deleteResponse = restClient.delete(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userid, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(deleteResponse.statusCode(), 204);
		Assert.assertTrue(deleteResponse.statusLine().contains("No Content"));

		System.out.println("==========================================================================================");

		// 4.Get- fetch the deleted user using the same userid
		getResponse = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userid, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.statusCode(), 404);
		Assert.assertTrue(getResponse.statusLine().contains("Not Found"));
		Assert.assertEquals(getResponse.jsonPath().getString("message"), AppConstants.GOREST_DELETE_USER_MSG);
	}
}
