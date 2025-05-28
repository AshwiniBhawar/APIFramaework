package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPath extends BaseTest{

	@Test
	public void getProductTest() {
		Response response=restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		List<Integer> idsList=JsonPathValidatorUtil.readList(response, "$.[*].id");
		System.out.println(idsList);
		
		List<Integer> ids=JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].id");
		System.out.println(ids);
		
		List<Integer> rates=JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].rating.rate");
		System.out.println(rates);
		
		List<Integer> counts=JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].rating.count");
		System.out.println(counts);
		
		List<Map<String, String>>idTitleList=JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id','title']");
		System.out.println(idTitleList);
		
		List<Map<String, String>> idTitleCatList=JsonPathValidatorUtil.readListOfMaps(response, "$.[*].['id','title','category']");
		System.out.println(idTitleCatList);
		
		List<Map<String, String>> jewlIdTitleList=JsonPathValidatorUtil.readListOfMaps(response, "$[?(@.category=='jewelery')].['id','title', 'category']");
		System.out.println(jewlIdTitleList);
		
		Double price=JsonPathValidatorUtil.read(response, "min($[*].price)");
		System.out.println("min price is==>"+ price);
	}
}
