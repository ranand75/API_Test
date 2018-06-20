package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostApiTest extends TestBase
{	
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	

	@BeforeMethod
	public void setUp()
	{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl + apiUrl;
	}
	
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException
	{
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//Jackson API:
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("Anand", "Leader"); // expected user object
		
		//object to json file
		mapper.writeValue(new File("C:\\Users\\Harshi\\eclipse-workspace\\restapi\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
		//java object to json in String
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
		
		closeableHttpResponse = restClient.post(url, usersJsonString, headerMap); // Call the API
		
		//Validate the response from the API
		//1.Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
		
		
		//2.Json String:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The JSON response from API"+responseJson);
		
		//json to java object:
		Users usersResObj = mapper.readValue(responseString, Users.class);  //Actual user object
		
		
		//Comparing actual with expected value
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
		
		
		System.out.println("Name		:"+usersResObj.getName());
		System.out.println("Job		:"+usersResObj.getJob());
		System.out.println("Id		:"+usersResObj.getId());
		System.out.println("CreatedAt	:"+usersResObj.getCreatedAt());
	}

}
