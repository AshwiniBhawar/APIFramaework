package com.qa.api.utils;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SchemaValidator {

	public static boolean validateJSONSchema(Response response, String fileName) {
		try {
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(fileName));
			System.out.println("Schema validation is passed for : "+fileName);
			return true;
		} catch (Exception e) {
			System.out.println("Schema validation is failed: " + e.getMessage());
			return false;
		}

	}
	
	public static boolean validateXMLSchema(Response response, String fileName) {
		try {
			response.then().assertThat().body(RestAssuredMatchers.matchesXsdInClasspath(fileName));
			System.out.println("Schema validation is passed for : "+fileName);
			return true;
		} catch (Exception e) {
			System.out.println("Schema validation is failed: " + e.getMessage());
			return false;
		}

	}
}
