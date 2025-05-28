package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest{
	
private String tokenId;
	
	@BeforeClass
	public void setUpToken(){
		tokenId="b949db127e312a464aef7af4e192be1c0d1649b475f0b9c17e89e02d50b9fcf5";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void updateUserTest() {
		//1.Post-create a user using builder patter
		//User user= new User("Crane", StringUtils.getRandomEmailId(), "male", "active");
		User user= User.builder()
				.name("ash")
				.email(StringUtils.getRandomEmailId())
				.gender("female")
				.status("active")
				.build(); 
		
		Response postResponse=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
				
		Assert.assertEquals(postResponse.statusCode(),201);
		Assert.assertEquals(postResponse.jsonPath().getString("name"), "ash");
		Assert.assertNotNull(postResponse.jsonPath().getString("id"));
		
		//fetch the userid from response
		String userId=postResponse.jsonPath().getString("id");
		System.out.println("user id is: "+userId);
		
		System.out.println("==========================================================================================");
		
		//2.Get- fetch the user using the same userid
		Response getResponse=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.statusCode(),200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(getResponse.jsonPath().getString("id"),userId);
		
		System.out.println("==========================================================================================");
		
		//3.Put- update the same userid using setters
		user.setName("ash b");
		user.setStatus("inactive");
		Response putResponse=restClient.put(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(putResponse.statusCode(),200);
		Assert.assertTrue(putResponse.statusLine().contains("OK"));
		Assert.assertEquals(putResponse.jsonPath().getString("id"),userId);
		Assert.assertEquals(putResponse.jsonPath().getString("name"), "ash b");
		Assert.assertEquals(putResponse.jsonPath().getString("status"), "inactive");
		
		System.out.println("==========================================================================================");
		
		//4.Get- fetch the same userid after updation
		getResponse=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.statusCode(),200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(getResponse.jsonPath().getString("id"),userId);
		Assert.assertEquals(getResponse.jsonPath().getString("name"), "ash b");
		Assert.assertEquals(getResponse.jsonPath().getString("status"), "inactive");
		
	}
}
