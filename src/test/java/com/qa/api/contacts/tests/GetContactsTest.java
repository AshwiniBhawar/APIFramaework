package com.qa.api.contacts.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetContactsTest extends BaseTest {
	private String tokenId;
	private String contactId;

	// generate a tokenid

	@BeforeMethod
	public void getToken() {
		ContactsCredentials contactsCredentials = new ContactsCredentials("bhawar.ashwini2892@gmail.com", "tester1234");
		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, contactsCredentials, null, null,
				AuthType.NO_AUTH, ContentType.JSON);

		tokenId = response.jsonPath().getString("token");
		System.out.println("contacts login token======>" + tokenId);
		ConfigManager.set("bearertoken", tokenId);
	}

	// 1.Get all contacts
	@Test
	public void getAllContactsTest() {
		Response getResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		List<String> useridslist = getResponse.jsonPath().getList("_id");
		contactId = useridslist.get(0);
		System.out.println("user id is: " + contactId);
		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
	}

	// 1.Get a contact
	@Test(dependsOnMethods = "getAllContactsTest")
	public void getAContactTest() {
		Response getResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
	}
	
	// logout contactapi
	
	@AfterMethod
	public void logOutUser() {
		Response postResponse = restClient.postWithoutBody(BASE_URL_CONTACTS, CONTACTS_LOGOUT_ENDPOINT, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(postResponse.statusCode(), 200);
		Assert.assertTrue(postResponse.statusLine().contains("OK"));
	}

}
