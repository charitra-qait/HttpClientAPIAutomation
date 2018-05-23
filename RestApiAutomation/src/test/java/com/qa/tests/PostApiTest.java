package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.text.html.parser.Entity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Client.RestClient;
import com.qa.base.TestBase;
import com.qa.data.Users;

public class PostApiTest extends TestBase {
	TestBase testBase;
	String url;
	String apiURL;
	String serviceURL;
	RestClient restClient;
	CloseableHttpResponse httpResponse;

	@BeforeMethod
	public void setup() {
		testBase = new TestBase();
		apiURL = prop.getProperty("URL");
		serviceURL = prop.getProperty("serviceURL");
		url = apiURL + serviceURL;
	}

	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();

		// For Header
		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");

		// Jackson API
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader");

		// Object to json file
		mapper.writeValue(new File("/home/qainfotech/Desktop/RestApiAutomation/src/main/java/com/qa/data/users.json"),
				users);

		// Java Object to Json string : Marshelling
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);

		httpResponse = restClient.post(url, usersJsonString, headerMap);

		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);

		// Json String
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

		// To Json Object
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from Json is : " + responseJson);

		// Json to Java Object
		Users userResObj = mapper.readValue(responseString, Users.class);
		System.out.println(userResObj);

		Assert.assertTrue(users.getName().equals(userResObj.getName()));
		Assert.assertTrue(users.getJob().equals(userResObj.getJob()));
		System.out.println("Name : " + userResObj.getName());
		System.out.println("Job : " + userResObj.getJob());
		System.out.println("Id : " + userResObj.getId());
		System.out.println("Created At : " + userResObj.getCreatedAt());

	}

}
