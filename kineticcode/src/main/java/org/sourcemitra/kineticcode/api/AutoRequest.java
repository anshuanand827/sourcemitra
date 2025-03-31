package org.sourcemitra.kineticcode.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

public class AutoRequest {
	private String HOST;
	private String RESOURCE_PATH;
	private int auth;
	public String REQUEST_URL;
	private String[] basicAuth;
	private String response;
	private String reqBody;
	private int ReqMethod;
	private HashMap<String, String> headers;
	HttpURLConnection connection;

	public AutoRequest() {
		this.HOST = null;
		this.RESOURCE_PATH = null;
		this.auth = 0;
		this.REQUEST_URL = null;
		this.response = null;
		this.headers = new HashMap<>();
	}



	public AutoRequest(String endpoint) throws URISyntaxException, IOException {
		try {
			URI uri = new URI(endpoint);
			URL url = uri.toURL();

			String host = url.getProtocol() + "://" + url.getHost();
			if (url.getPort() != -1) {
				host += ":" + url.getPort();
			}
			String resource = url.getPath();
			if (url.getQuery() != null) {
				resource += "?" + url.getQuery();
			}
			if(url.getRef() != null){
				resource += "#" + url.getRef();
			}

			this.HOST = host;
			this.RESOURCE_PATH = resource;
			this.REQUEST_URL = endpoint;
		} catch (MalformedURLException | URISyntaxException e) {
			System.err.println("Invalid URL: " + endpoint);
		}finally {
			init(endpoint);
		}
	}

	public HttpURLConnection init(String REQUEST_URL) throws URISyntaxException, IOException {
		URI uri = new URI(REQUEST_URL);
		URL url = uri.toURL();
		this.connection = (HttpURLConnection) url.openConnection();
		return connection;
	}

	public AutoRequest(String host, String resourcepath) throws URISyntaxException, IOException {
		this.HOST = host;
		this.RESOURCE_PATH = resourcepath;
		this.REQUEST_URL = host + resourcepath;
		this.init(this.REQUEST_URL);
	}

	public void setURL(String URL) throws URISyntaxException, IOException {
		this.REQUEST_URL = URL;
		this.init(this.REQUEST_URL);
	}

	public void setHTTPMethod(String method) throws ProtocolException {
		method = method.toUpperCase();
		switch(method) {
		case "GET":
			this.ReqMethod = 1;
			break;
		case "POST":
			this.ReqMethod = 2;
			break;
		case "PUT":
			this.ReqMethod = 3;
			break;
		case "DELETE":
			this.ReqMethod = 4;
			break;
		case "PATCH":
			this.ReqMethod = 4;
			break;
		default:
			this.ReqMethod = 0;
		}		

		this.connection.setRequestMethod(method);

	}

	public String getBody() {
		return this.reqBody;
	}

	public void setHeader(String headerName, String headerValue) {
		this.headers.put(headerName, headerValue);
	}

	public void setBody(String body) throws IOException {
        this.connection.setDoOutput(true);
		try (OutputStream os = this.connection.getOutputStream()) {
			byte[] input = body.getBytes(StandardCharsets.UTF_8);
			os.write(input, 0, input.length);
		}
		this.reqBody = body;
	}

	public String getReqHeader(String headerName) {
		return this.headers.get(headerName);
	}

	public HashMap<String, String> getReqHeaders() {
		return this.headers;
	}

	private boolean ntlm(String username, String password, String domain) { 	                                                                        
		return true;                                                        
	}                                                                       

	private boolean oAuth2() {                                              	                                                                        
		return true;                                                        
	}                                                                       

	public boolean basicAuth(String username, String password) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		this.connection.setRequestProperty("Authorization", authHeaderValue);
		return true;
	}

}
