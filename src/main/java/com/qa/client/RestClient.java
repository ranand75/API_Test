package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {
	
	
	//Rest client
	//1. Get Method without Headers
	public CloseableHttpResponse get(String url) throws ParseException, IOException, ClientProtocolException
	{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
		return closeableHttpResponse;
	}
	
	
	//1. Get Method with Headers
	public CloseableHttpResponse get(String url, HashMap<String,String> hashMap) throws ParseException, IOException, ClientProtocolException
	{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		
		for(Map.Entry<String, String> entry : hashMap.entrySet())
		{
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
		return closeableHttpResponse;
	}
	
	
	//Post Method
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);  // http post request
		httppost.setEntity(new StringEntity(entityString)); //for payload
		
		//for headers
		for(Map.Entry<String, String> entry : headerMap.entrySet())
		{
			httppost.addHeader(entry.getKey(),entry.getValue());
		}
		
		CloseableHttpResponse  closeableHttpResponse = httpClient.execute(httppost);
		return closeableHttpResponse;
	}
	
	
	
	
	public static void main(String args[]) throws ParseException, IOException, ClientProtocolException
	{
		
		//String url = "http://services.groupkt.com/country/get/iso2code/IN";
		String url = "http://services.groupkt.com/country/get/all";
		RestClient restClient = new RestClient(); 
		restClient.get(url);
	}
	
}
	
