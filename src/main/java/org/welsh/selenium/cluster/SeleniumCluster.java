package org.welsh.selenium.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.welsh.selenium.cluster.cmd.Constants;
import org.welsh.selenium.cluster.domain.SeleniumHub;
import org.welsh.selenium.cluster.exception.NoServerAvailableException;
import org.welsh.selenium.cluster.service.SeleniumHubService;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * SeleniumCluster is a utility class to return a import com.thoughtworks.selenium.Selenium
 * object for a List of SeleniumHubs. Allowing you to create a clustered environment of Selenium Hubs
 * and code to utilize the cluster instead of hardcoding the hub values.
 * 
 * @author dwelsh
 *
 */
public class SeleniumCluster {
	
	/**
	 * Logger
	 */
	private static final Logger log = Logger.getLogger(SeleniumCluster.class.getName());

	/**
	 * Browser to Run On
	 */
	private String browserStartCommand;
	
	/**
	 * Base URL of Browser to Start With
	 */
	private String browserURL;
	/**
	 * Service to manipulate Hubs
	 */
	private SeleniumHubService seleniumHubService;

	/**
	 * List of Available SeleniumHub objects
	 */
	private List<SeleniumHub> seleniumHubs;

	private SeleniumCluster() {
		seleniumHubs = new ArrayList<SeleniumHub>();
		seleniumHubService  = new SeleniumHubService();
	}

	/**
	 * Constructor to create SeleniumCluster with an empty list of
	 * SeleniumHub objects.
	 * 
	 * @param browserStartCommand
	 * @param browserURL
	 */
	public SeleniumCluster(String browserStartCommand, String browserURL) {
		this();

		this.browserStartCommand = browserStartCommand;
		this.browserURL = browserURL;
	}

	/**
	 * Constructor to create SeleniumCluster with re-determined list of
	 * SeleniumHub objects.
	 * 
	 * @param browserStartCommand
	 * @param browserURL
	 * @param seleniumHubs
	 */
	public SeleniumCluster(String browserStartCommand, String browserURL, List<SeleniumHub> seleniumHubs) {
		this();

		this.browserStartCommand = browserStartCommand;
		this.browserURL = browserURL;
		this.seleniumHubs = seleniumHubs;
	}
	
	/**
	 * Will return com.thoughtworks.selenium.Selenium for the first
	 * SeleniumHub instance that returns a 200 Response Code. If no
	 * SeleniumHub instances are available throws NoServerAvailableException.  
	 * 
	 * @return com.thoughtworks.selenium.Selenium of First Available Selenium Hub
	 * @throws NoServerAvailableException Thrown if no SeleniumHub is available
	 */
	public Selenium getFirstAvailableSelenium() throws NoServerAvailableException {
		SeleniumHub availableHub = null;

		// Loop through Selenium Hubs and find available
		for (SeleniumHub seleniumHub : seleniumHubs) {
			log.fine("Checking Selenium Hub: " + seleniumHub.getServerHost() + ":" + seleniumHub.getServerPort());
			
			// Stop at first Hub
			if(availableHub == null && seleniumHubService.matchesHttpResponseCode(seleniumHub, HttpStatus.SC_OK)) {
				availableHub = seleniumHub;
			}
		}
			
		// Throw Exception if no Hubs Available
		if (availableHub == null) {
			log.warning("No Hubs Available.");
			throw new NoServerAvailableException("No Server Available in provided List.");
		}
		
		log.info("Available Hub: " + availableHub.getServerHost() + ":" + availableHub.getServerPort());
		
		return new DefaultSelenium(availableHub.getServerHost(), availableHub.getServerPort(), browserStartCommand, browserURL);
	}


	/**
	 * Will return com.thoughtworks.selenium.Selenium based off the set
	 * load balancing rules. Preferred method to get Selenium Object. If no
	 * SeleniumHub instances are available throws NoServerAvailableException.
	 *
	 * @return com.thoughtworks.selenium.Selenium of First Available Selenium Hub
	 * @throws NoServerAvailableException Thrown if no SeleniumHub is available
	 */
	public Selenium getAvailableSelenium() throws NoServerAvailableException {
		return getFirstAvailableSelenium();
	}

	/**
	 * Method to get String containing the Browser to Run.
	 * 
	 * @return String
	 */
	public String getBrowserStartCommand() {
		return browserStartCommand;
	}

	/**
	 * Method to set Browser to Run
	 * 
	 * @param browserStartCommand String containing Browser to run. Ex: *firefox
	 */
	public void setBrowserStartCommand(String browserStartCommand) {
		this.browserStartCommand = browserStartCommand;
	}

	/**
	 * Method to get String containing the base URL
	 * 
	 * @return String containing base URL.
	 */
	public String getBrowserURL() {
		return browserURL;
	}

	/**
	 * Method to set Base Browser URL
	 * 
	 * @param browserURL String containing Base Browser URL. Ex: http://www.google.ca
	 */
	public void setBrowserURL(String browserURL) {
		this.browserURL = browserURL;
	}

	/**
	 * Method to get List<SeleniumHub> of Possible Selenium Hubs
	 * 
	 * @return List<SeleniumHub> List of Possible Selenium Hubs
	 */
	public List<SeleniumHub> getSeleniumHubs() {
		return seleniumHubs;
	}

	/**
	 * Method to set List<SeleniumHub> of Possible Selenium Hubs
	 * 
	 * @param seleniumHubs List<SeleniumHub> containing List of Possible Selenium Hubs
	 */
	public void setSeleniumHubs(List<SeleniumHub> seleniumHubs) {
		this.seleniumHubs = seleniumHubs;
	}

	/**
	 * Method to add SeleniumHub Object to Possible Selenium Hub List
	 * 
	 * @param seleniumHub
	 */
	public void addSeleniumHub(SeleniumHub seleniumHub) {
		seleniumHubs.add(seleniumHub);
	}
	
	/**
	 * Method to create a new Selenium Hub Object and add to the Possible
	 * Selenium Hub List
	 * 
	 * @param serverHost String containing server host. Ex. localhost
	 * @param serverPort int containing server port. Ex. 4444
	 */
	public void addSeleniumHub(String serverHost, int serverPort) {
		seleniumHubs.add(new SeleniumHub(serverHost, serverPort));
	}
}
