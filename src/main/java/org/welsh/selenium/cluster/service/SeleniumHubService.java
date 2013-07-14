package org.welsh.selenium.cluster.service;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.welsh.selenium.cluster.cmd.Constants;
import org.welsh.selenium.cluster.domain.SeleniumHub;

public class SeleniumHubService {
	
	private static final Logger log = Logger.getLogger(SeleniumHubService.class.getName());

	public SeleniumHubService() {
	}

	public boolean matchesHttpResponseCode(SeleniumHub seleniumHub, int httpStatusCode) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			
			HttpGet httpGet = new HttpGet("http://" + seleniumHub.getServerHost() + ":" 
					+ seleniumHub.getServerPort() + Constants.GRID_CONSOLE_PAGE);
			
			HttpResponse httpResponse = httpclient.execute(httpGet);
			
			log.fine("Response Code: " + httpResponse.getStatusLine().getStatusCode());
			
			// Only Accept 200 Response Codes
			if(httpResponse.getStatusLine().getStatusCode() == httpStatusCode) {
				return true;
			}
		} catch (ClientProtocolException e) {
			log.fine(e.getLocalizedMessage());
		} catch (IOException e) {
			log.fine(e.getLocalizedMessage());
		}			
	
		return false;
	}

}
