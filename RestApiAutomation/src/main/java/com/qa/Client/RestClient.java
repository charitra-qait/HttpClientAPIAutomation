package com.qa.Client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import org.apache.http.Header;
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

	// Get Method without Headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); //http get request
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet); //hit the get url
		return httpResponse;
	}

	// Get Method with Headers
	public CloseableHttpResponse get(String url,HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url); //http get request
		
		//For Header
		for(Map.Entry<String,String> entry: headerMap.entrySet())
			httpGet.addHeader(entry.getKey(), entry.getValue());
			
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet); //hit the get url
		return httpResponse;
	}

	//Post Method
	public CloseableHttpResponse post(String url,String entitySring,HashMap<String,String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); //http post request
		httppost.setEntity(new StringEntity(entitySring));
		
		//For Headers
		for(Map.Entry<String,String> entry: headerMap.entrySet())
			httppost.addHeader(entry.getKey(), entry.getValue());
		CloseableHttpResponse httpResponse = httpClient.execute(httppost);
		return httpResponse;
	}
}
