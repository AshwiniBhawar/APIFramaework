package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest{
	
	@Test
	public void getProductsTest() {
		Response response=restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		Product[] product=JsonUtils.deserialize(response, Product[].class);
		
		for(Product p:product) {
			System.out.println("Id is: "+ p.getId());
			System.out.println("Title is: "+ p.getTitle());
			System.out.println("Price is: "+ p.getPrice());
			System.out.println("Description is: "+ p.getDescription());
			System.out.println("Category is: "+ p.getCategory());
			System.out.println("Image is: "+ p.getImage());
			System.out.println("Rate is: "+ p.getRating().getRate());
			System.out.println("Count is: "+ p.getRating().getCount());
			System.out.println("----------------------------------------");
		
		}		
		
	}

}
