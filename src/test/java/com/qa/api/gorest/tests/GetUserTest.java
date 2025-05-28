package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Epic("Epic 1: Go Rest Get User API Feature")
@Story("UD 1: feature go rst api- get user api")
public class GetUserTest extends BaseTest {

	private String tokenId;
	private int userId;

	@BeforeClass
	public void setUpToken() {
		tokenId = "b949db127e312a464aef7af4e192be1c0d1649b475f0b9c17e89e02d50b9fcf5";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Description("getting all the users....")
	@Owner("Ash B")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAllUsersTest() {
		ChainTestListener.log("get all users api test");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		List<Integer> ids = response.jsonPath().getList("id");
		userId = ids.get(0);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("OK"));

	}

	@Description("getting all the users with query params....")
	@Owner("Ash B")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAllUsersWithQueryParamTest() {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", "Sarada Talwar");
		queryParams.put("status", "active");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, queryParams, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("OK"));

	}

	@Test
	public void getSingleUserTest() {
		// String userId="7899641";
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getInt("id"), userId);
	}

}
