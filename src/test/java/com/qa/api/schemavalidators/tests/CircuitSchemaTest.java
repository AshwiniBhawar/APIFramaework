package com.qa.api.schemavalidators.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import static com.qa.api.utils.SchemaValidator.*;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CircuitSchemaTest extends BaseTest{
	
	
	@Test
	public void getCircuitAPISchemaTest() {
		Response response = restClient.get(BASE_URL_ERGAST_CIRCUIT, ERGAST_CIRCUIT_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.XML);
		Assert.assertTrue(validateXMLSchema(response,"schema/circuitschema.xml"));
	}

}
