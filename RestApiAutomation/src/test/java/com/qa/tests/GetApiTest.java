package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.Client.RestClient;
import com.qa.base.TestBase;
import com.qa.util.TestUtil;

public class GetApiTest extends TestBase {
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

	@Test(priority = 1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		httpResponse = restClient.get(url);

		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code : ------> " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");

		// JSON String
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API :" + responseJson);

		/* Single Value Assertion */

		// Per Page :
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is -----> " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);

		// Total :
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total is ----->" + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		/* Assertion for JSON Array values */
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		Header[] headerArray = httpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array : ----> " + allHeaders);
	}

	@Test(priority = 2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();

		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		httpResponse = restClient.get(url, headerMap);

		// Status Code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code : ------> " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");

		// JSON String
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API :" + responseJson);

		/* Single Value Assertion */

		// Per Page :
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is -----> " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);

		// Total :
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total is ----->" + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		/* Assertion for JSON Array values */

		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		Header[] headerArray = httpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array : ----> " + allHeaders);
	}
}
