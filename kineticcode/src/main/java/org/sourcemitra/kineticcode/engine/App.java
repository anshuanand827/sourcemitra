package org.sourcemitra.kineticcode.engine;

import java.io.IOException;
import java.net.URISyntaxException;

import org.sourcemitra.kineticcode.api.AutoRequest;
import org.sourcemitra.kineticcode.api.AutoResponse;

public class App
{
    public static void main( String[] args ) throws IOException, URISyntaxException
    {
    	AutoRequest request = new AutoRequest();
    	request.setURL("https://reqres.in/api/users?page=2");
    	request.setHTTPMethod("GET");
    	request.setReqHeader("content-type", "application/json");
    	
    	AutoResponse response = new AutoResponse();
    	boolean successFlag = response.sendRequest(request);
    	if (successFlag) {
    		System.out.println("Response Code: " + response.getStatusCode());
    		System.out.println("Response Headers: " + response.getHeader("Content-Type"));
    		System.out.println("Response : \n" + response.getResBody());
    	}
    	
    }
}
