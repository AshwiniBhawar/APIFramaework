package com.qa.api.client;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIExceptions;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestClient {
	
	//define response specs:
	ResponseSpecification responseSpec200=expect().statusCode(200);
	ResponseSpecification responseSpec201=expect().statusCode(201);
	ResponseSpecification responseSpec204=expect().statusCode(204);
	ResponseSpecification responseSpec400=expect().statusCode(400);
	ResponseSpecification responseSpec404=expect().statusCode(404);
	
	ResponseSpecification responseSpec204or200=expect().statusCode(anyOf(equalTo(204),equalTo(200)));
	ResponseSpecification responseSpec200or201=expect().statusCode(anyOf(equalTo(200),equalTo(201)));
	ResponseSpecification responseSpec200or404=expect().statusCode(anyOf(equalTo(200),equalTo(404)));
	
	private RequestSpecification setUpRequest(String baseUrl, AuthType authType, ContentType contentType) {
		ChainTestListener.log("API Base Url :"+baseUrl);
		ChainTestListener.log("Auth Type :"+authType.toString());
		
		RequestSpecification request=RestAssured.given().log().all()
												.baseUri(baseUrl)
												.contentType(contentType)
												.accept(contentType);
		switch(authType) {
		case BEARER_TOKEN: 
			request.header("Authorization","Bearer "+ConfigManager.get("bearertoken"));
			break;

		case API_KEY: 
			request.header("x-api-key","api key");
			break;
			
		case BASIC_AUTH: 
			request.header("Authorization","Basic "+ generateBasicAuthToken());
			break;
			
		case NO_AUTH: 
			System.out.println("Auth is not required...");
			break;
		
		default:
			System.out.println("This auth is not supported..Please pass the right AuthType...");
			throw new APIExceptions("=====Invalid Auth=====");	
		}
		
		return request;
	}

	private String generateBasicAuthToken() {
		String basicauthcredentials=ConfigManager.get("basicauthusername") + ":" + ConfigManager.get("basicauthpassword");
		//btoa('admin'+":"+'admin')==>  "YWRtaW46YWRtaW4="   (base64 encoded value)
 		return Base64.getEncoder().encodeToString(basicauthcredentials.getBytes());
	}
	
	private void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams) {
		ChainTestListener.log("Query Params: "+queryParams);
		ChainTestListener.log("Path Params: "+pathParams);
		if(queryParams !=null) {
			request.queryParams(queryParams);
		}
		if(pathParams !=null) {
			request.pathParams(pathParams);
		}
		
	}

	//CRUD
	
	//get:
	/**
	 * This method is used to call Get APIs
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return It returns the GET API call response
	 */
	
	@Step("calling the get api with base url:{0}")
	public Response get(String baseUrl, String endPoint, Map<String, String> queryParams, 
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification request=setUpRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response getResponse=request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		getResponse.prettyPrint();
		return getResponse;
	}
	
	//post: T is reserve keyword in java which accept any format of body
	
	public <T> Response post(String baseUrl, String endPoint, T body, Map<String, String> queryParams, 
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification request=setUpRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response postResponse=request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		postResponse.prettyPrint();
		return postResponse;
	}

	//post-File body
	
	public Response post(String baseUrl, String endPoint, File file, Map<String, String> queryParams, 
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification request=setUpRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response postResponse=request.body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
		postResponse.prettyPrint();
		return postResponse;
	}
	
	public Response post(String baseUrl, String endPoint, String grantType, String clientId, String clientSecret, ContentType contentType) {
		Response response =RestAssured.given()
									   .contentType(contentType)
									   .formParam("grant_type", grantType)
									   .formParam("client_id", clientId)
									   .formParam("client_secret", clientSecret)
									   .when()
									   .post(baseUrl+endPoint);
		response.prettyPrint();
		return response;
		}
	
	public Response postWithoutBody(String baseUrl, String endPoint, Map<String, String> queryParams, 
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification request=setUpRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response postResponse=request.post(endPoint).then().spec(responseSpec200or201).extract().response();
		postResponse.prettyPrint();
		return postResponse;
	}
	//put
	
	public <T>Response put(String baseUrl, String endPoint, T body, Map<String, String> queryParams, 
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification request=setUpRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response putResponse=request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
		putResponse.prettyPrint();
		return putResponse;
	}
	
	//patch
	public <T>Response patch(String baseUrl, String endPoint,  T body, Map<String, String> queryParams, 
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification request=setUpRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response patchResponse=request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
		patchResponse.prettyPrint();
		return patchResponse;
	}
	
	//delete
	public Response delete(String baseUrl, String endPoint, Map<String, String> queryParams, 
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		
		RequestSpecification request=setUpRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response deleteResponse=request.delete(endPoint).then().spec(responseSpec204or200).extract().response();
		deleteResponse.prettyPrint();
		return deleteResponse;
	}
	
	
}
