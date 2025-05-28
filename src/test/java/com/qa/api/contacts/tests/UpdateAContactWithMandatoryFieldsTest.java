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

public class UpdateAContactWithMandatoryFieldsTest extends BaseTest {

	private String tokenId;

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
		Contacts contact = Contacts.builder().birthdate("1999-01-01").email(StringUtils.getRandomEmailId())
				.phone("9898987778").firstName("Ash").lastName("B").city("Mumbai").country("India").postalCode(123456)
				.stateProvince("MH").street1("Goodluck road").street2("near ABC college").build();

		System.out.println("===================================================================================================");

		// 1.Post- create a contact
		Response postResponse = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		String contactId = postResponse.jsonPath().getString("_id");
		System.out.println("contact id is : " + contactId);

		Assert.assertEquals(postResponse.statusCode(), 201);
		Assert.assertTrue(postResponse.statusLine().contains("Created"));
		Assert.assertNotNull(contactId);
		Assert.assertEquals(postResponse.jsonPath().getString("firstName"), contact.getFirstName());
		Assert.assertEquals(postResponse.jsonPath().getString("lastName"), contact.getLastName());

		System.out.println(
				"===================================================================================================");

		// 2.Get - get the contact using same contactId
		Response getResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(contactId, getResponse.jsonPath().getString("_id"));
		Assert.assertEquals(postResponse.jsonPath().getString("firstName"), contact.getFirstName());
		Assert.assertEquals(postResponse.jsonPath().getString("lastName"), contact.getLastName());

		System.out.println("===================================================================================================");

		// 3. Put - update only mandatory fields for same contactId
		Contacts contact1 = Contacts.builder().firstName("Kriya").email(StringUtils.getRandomEmailId()).lastName("D").build();
		Response putResponse = restClient.put(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, contact1, null,
						null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(putResponse.statusCode(), 200);
		Assert.assertTrue(putResponse.statusLine().contains("OK"));
		Assert.assertEquals(putResponse.jsonPath().getString("firstName"), contact1.getFirstName());
		Assert.assertEquals(putResponse.jsonPath().getString("email"), contact1.getEmail());
		Assert.assertEquals(putResponse.jsonPath().getString("lastName"), contact1.getLastName());

		System.out.println("===================================================================================================");

		// 4.Get - get the updated contact using same contactId
		getResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertTrue(getResponse.statusLine().contains("OK"));
		Assert.assertEquals(getResponse.jsonPath().getString("firstName"), contact1.getFirstName());
		Assert.assertNull(getResponse.jsonPath().getString("birthdate"));
		Assert.assertEquals(getResponse.jsonPath().getString("email"), contact1.getEmail());
		Assert.assertEquals(getResponse.jsonPath().getString("lastName"), contact1.getLastName());
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
