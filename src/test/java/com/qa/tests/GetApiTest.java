package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetApiTest extends TestBase
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
		
		url = serviceUrl+apiUrl;
	}
	
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ParseException, ClientProtocolException, IOException
	{
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);
		
		
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code--->"+statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is NOT 200");
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject  responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API--->"+responseJSON);
		String restResponsePage = TestUtil.getValueByJPath(responseJSON,"/per_page");
		System.out.println("Per Page value is--->"+restResponsePage);
		Assert.assertEquals(Integer.parseInt(restResponsePage), 3);
		String restResponseTotal = TestUtil.getValueByJPath(responseJSON,"/total");
		System.out.println("Total value is--->"+restResponseTotal);
		Assert.assertEquals(Integer.parseInt(restResponseTotal), 12);
		
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		String last_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		System.out.println("First_Name	:"+first_name);
		System.out.println("Last_Name	:"+last_name);
		System.out.println("Id		:"+id);
		System.out.println("Avatar		:"+avatar);
		
		//Get the value from an JSON Array
		Header[] headersArray = closeableHttpResponse.getAllHeaders();		
		HashMap<String, String> allHeaders = new HashMap<String, String>();		
		for(Header header : headersArray)
		{
			allHeaders.put(header.getName(), header.getValue());
		}		
		System.out.println("Headers Array--->"+allHeaders);
		
	}
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ParseException, ClientProtocolException, IOException
	{
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
/*		headerMap.put("username", "test");
		headerMap.put("password", "test123");
		headerMap.put("Auth-Token", "12345");*/
		
		closeableHttpResponse = restClient.get(url,headerMap);
				
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code--->"+statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is NOT 200");
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject  responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API--->"+responseJSON);
		String restResponsePage = TestUtil.getValueByJPath(responseJSON,"/per_page");
		System.out.println("Per Page value is--->"+restResponsePage);
		Assert.assertEquals(Integer.parseInt(restResponsePage), 3);
		String restResponseTotal = TestUtil.getValueByJPath(responseJSON,"/total");
		System.out.println("Total value is--->"+restResponseTotal);
		Assert.assertEquals(Integer.parseInt(restResponseTotal), 12);
		
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		String last_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		System.out.println("First_Name	:"+first_name);
		System.out.println("Last_Name	:"+last_name);
		System.out.println("Id		:"+id);
		System.out.println("Avatar		:"+avatar);
		
		//Get the value from an JSON Array
		Header[] headersArray = closeableHttpResponse.getAllHeaders();		
		HashMap<String, String> allHeaders = new HashMap<String, String>();		
		for(Header header : headersArray)
		{
			allHeaders.put(header.getName(), header.getValue());
		}		
		System.out.println("Headers Array--->"+allHeaders);
		
	}

}
