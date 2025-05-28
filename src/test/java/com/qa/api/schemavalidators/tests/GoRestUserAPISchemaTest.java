package com.qa.api.schemavalidators.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import static com.qa.api.utils.SchemaValidator.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestUserAPISchemaTest extends BaseTest {

	@Test
	public void getUsersAPISchemaTest() {
		ConfigManager.set("bearertoken", "b949db127e312a464aef7af4e192be1c0d1649b475f0b9c17e89e02d50b9fcf5");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,
				ContentType.ANY);
		Assert.assertTrue(validateJSONSchema(response,"schema/getuserschema.json"));
	}
	
	@Test
	public void createUsersAPISchemaTest() {
		ConfigManager.set("bearertoken", "b949db127e312a464aef7af4e192be1c0d1649b475f0b9c17e89e02d50b9fcf5");
		User user= User.builder()
				.name("Ash")
				.status("active")
				.email(StringUtils.getRandomEmailId())
				.gender("female")
				.build();
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		Assert.assertTrue(validateJSONSchema(response,"schema/createuserschema.json"));
	}
}
