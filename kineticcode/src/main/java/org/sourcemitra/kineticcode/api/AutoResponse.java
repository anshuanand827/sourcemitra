package org.sourcemitra.kineticcode.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AutoResponse {

	Map<String, Object> resultMap;
	private String response;
	JsonObject jsonResponse;
	
	public AutoResponse() {
		this.response = null;
		this.resultMap = new HashMap<>();
		jsonResponse = null;
	}
	
	public AutoResponse(AutoRequest request) throws IOException {
		this.response = null;
		this.resultMap = new HashMap<>();
		this.sendRequest(request);
	}

	public Map<String, List<String>> getHeaders() {
		if(this.resultMap.containsKey("headers")) {
			Map<String, List<String>> headers = (Map<String, List<String>>) this.resultMap.get("headers");
			return headers;
		}
		return null;
	}
	
	public List<String> getHeader(String header) {
		if(this.resultMap.containsKey("headers")) {
			Map<String, List<String>> headers = (Map<String, List<String>>) this.resultMap.get("headers");
			if(headers.containsKey(header))
				return headers.get(header);
		}
		return null;
	}
	
	public boolean sendRequest(AutoRequest request) throws IOException {
		int responseCode = request.connection.getResponseCode();
		this.resultMap.put("statusCode", responseCode);

		Map<String, List<String>> headers = request.connection.getHeaderFields();
		this.resultMap.put("headers", headers);

		try (BufferedReader in = new BufferedReader(new InputStreamReader(request.connection.getInputStream()))) {
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			resultMap.put("response", response.toString());
			this.response = response.toString();
			this.jsonResponse = JsonParser.parseString(this.response).getAsJsonObject();

			return true;
		}
		catch(java.io.FileNotFoundException e){
			try (BufferedReader in = new BufferedReader(new InputStreamReader(request.connection.getErrorStream()))) {
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				resultMap.put("response", response.toString());
				this.response = response.toString();
				return false;
			}

		}finally {
			//this.log(resultMap);
		}
		
	}

	public String getBody() {
		return this.response;
	}
	
	public JsonObject getBodyJSON() {
		return this.jsonResponse;
	}
	
	public int getStatusCode() {
		return (int) this.resultMap.get("statusCode");
	}

	public void getJSONValueByPath(String string) {
		// TODO Auto-generated method stub

	}

	public void log(Map<String, Object> result) {

		System.out.println("Response Code: " + result.get("statusCode"));
		System.out.println("\nResponse Headers:");
		Map<String, List<String>> headers = (Map<String, List<String>>) result.get("headers");
		if (headers != null) {
			for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
				String key = entry.getKey();
				List<String> values = entry.getValue();
				System.out.print("\t");
				System.out.println((key != null ? key + ": " : "") + values);
			}
			System.out.println();
		}
		System.out.println("Response:\n\t" + result.get("response"));
	}

}
