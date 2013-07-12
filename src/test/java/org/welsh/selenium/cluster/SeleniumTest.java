package org.welsh.selenium.cluster;

import static org.junit.Assert.*;

import org.junit.Test;
import org.welsh.selenium.cluster.domain.SeleniumHub;
import org.welsh.selenium.cluster.exception.NoServerAvailableException;

import com.thoughtworks.selenium.Selenium;

/**
 * Class to Test Using the SeleniumCluster to get
 * the Selenium Instance and verify it functions when
 * used.
 * 
 * @author dwelsh
 *
 */
public class SeleniumTest {
	/**
	 * Base URL
	 */
	private static final String URL = "http://www.reddit.com/";
	
	/**
	 * Browser to Use
	 */
	private static final String BROWSER = "*firefox";

	/**
	 * Test to confirm that instance SeleniumCluster returns
	 * is a valid Selenium Object and works.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSeleniumClusterOnSelenium() throws InterruptedException
	{
		try {
			SeleniumCluster seleniumCluster = new SeleniumCluster(BROWSER, URL);
			seleniumCluster.addSeleniumHub("localhost", 6444);  // Unavailable Hub
			seleniumCluster.addSeleniumHub(new SeleniumHub("localhost", 5444)); // Available Hub
			
			Selenium selenium = seleniumCluster.getAvailableSelenium();
			selenium.start();
			selenium.open(URL);
			selenium.windowMaximize();
			
			assertTrue(selenium.isTextPresent("eddit is a website about everything"));
			Thread.sleep(5000);
			
			selenium.stop();
			
			assertTrue(true);
		} catch (NoServerAvailableException e) {
			fail("No Hubs were Found Available. ");
		}
	}
}
