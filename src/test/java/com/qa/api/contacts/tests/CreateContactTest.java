package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.Contacts;
import com.qa.api.pojo.ContactsCredentials;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateContactTest extends BaseTest {

	private String tokenId;

	// login to generate a tokenid

	@BeforeMethod
	public void getToken() {
		ContactsCredentials contactsCredentials = new ContactsCredentials("bhawar.ashwini2892@gmail.com", "tester1234");
		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, contactsCredentials, null, null,
				AuthType.NO_AUTH, ContentType.JSON);

		tokenId = response.jsonPath().getString("token");
		System.out.println("contacts login token======>" + tokenId);
		ConfigManager.set("bearertoken", tokenId);
	}

	@Test
	public void createAContact() {

		// 1.Post-create a contact using builder pattern

		Contacts contact = Contacts.builder().birthdate("1999-01-01").email(StringUtils.getRandomEmailId())
				.phone("9898987778").firstName("Ash").lastName("B").city("Mumbai").country("India").postalCode(123456)
				.stateProvince("MH").street1("Goodluck road").street2("near ABC college").build();

		System.out.println("===================================================================================================");

		Response postResponse = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		String contactId = postResponse.jsonPath().getString("_id");
		System.out.println("contact id is : " + contactId);

		Assert.assertEquals(postResponse.statusCode(), 201);
		Assert.assertTrue(postResponse.statusLine().contains("Created"));
		Assert.assertNotNull(postResponse.jsonPath().getString("_id"));
		Assert.assertEquals(postResponse.jsonPath().getString("firstName"), contact.getFirstName());
		Assert.assertEquals(postResponse.jsonPath().getString("lastName"), contact.getLastName());

		System.out.println("===================================================================================================");

		// 2.Get- fetch the contact using the same contactId

		Response getResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(contactId, getResponse.jsonPath().getString("_id"));
		Assert.assertEquals(getResponse.jsonPath().getString("firstName"), contact.getFirstName());
		Assert.assertEquals(getResponse.jsonPath().getString("lastName"), contact.getLastName());
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
