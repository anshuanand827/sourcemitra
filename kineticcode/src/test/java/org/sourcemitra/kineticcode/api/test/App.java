package org.sourcemitra.kineticcode.api.test;

import java.io.IOException;
import java.net.URISyntaxException;
import org.sourcemitra.kineticcode.api.AutoRequest;
import org.sourcemitra.kineticcode.api.AutoResponse;

public class App
{
	public void Test1() throws URISyntaxException, IOException {
		AutoRequest request = new AutoRequest();
    	request.setURL("https://postman-echo.com/basic-auth");
    	request.setHTTPMethod("GET");
    	request.basicAuth("postman", "password");
    	
    	AutoResponse response = new AutoResponse();
    	boolean successFlag = response.sendRequest(request);
    	if (successFlag) {
    		System.out.println("Request Body: \n" + request.getBody());
    		System.out.println("Response Code: " + response.getStatusCode());
    		System.out.println("Response Headers: " + response.getHeader("Content-Type"));
    		System.out.println("Response : \n" + response.getBody());
    	}
	}
	
	public void Test2() throws URISyntaxException, IOException {
		AutoRequest request = new AutoRequest();
    	request.setURL("https://reqres.in/api/users");
    	request.setHTTPMethod("POST");
    	String body = "{\r\n"
    			+ "    \"name\": \"morpheus\",\r\n"
    			+ "    \"job\": \"leader\"\r\n"
    			+ "}";
    	request.setBody(body);
    	request.setHeader("content-type", "application/json");
    	
    	AutoResponse response = new AutoResponse();
    	boolean successFlag = response.sendRequest(request);
    	if (successFlag) {
    		System.out.println("Request Body: \n" + request.getBody());
    		System.out.println("Response Code: " + response.getStatusCode());
    		System.out.println("Response Headers: " + response.getHeader("Content-Type"));
    		System.out.println("Response : \n" + response.getBody());
    	}
	}
	
    public static void main(String[] args) throws URISyntaxException, IOException
    {
    	App call = new App();
    	call.Test2();
    	
    }
}
