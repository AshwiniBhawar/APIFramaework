package com.qa.api.schemavalidators.tests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import static com.qa.api.utils.SchemaValidator.*;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.response.Response;

public class CircuitSchemaTest extends BaseTest{
	
	
	@Test
	public void getCircuitAPISchemaTest() {
		Response response = restClient.get(BASE_URL_EARGST_CIRCUIT, EARGST_CIRCUIT_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.XML);
		Assert.assertTrue(validateXMLSchema(response,"schema/circuitschema.xml"));
	}

}
