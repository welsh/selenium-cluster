package org.welsh.selenium.cluster;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.welsh.selenium.cluster.domain.SeleniumHub;
import org.welsh.selenium.cluster.exception.NoServerAvailableException;

import com.thoughtworks.selenium.Selenium;

/**
 * 
 * 
 * @author dwelsh
 *
 */
public class SeleniumClusterTest {

	private SeleniumCluster seleniumCluster;
	
	private final String URL = "http://www.google.ca";
	private final String BROWSER = "*firefox";
	
	private final SeleniumHub SELENIUM_HUB_VALID_1 = new SeleniumHub("localhost", 4444);
	private final SeleniumHub SELENIUM_HUB_VALID_2 = new SeleniumHub("localhost", 5444);
	private final SeleniumHub SELENIUM_HUB_INVALID_1 = new SeleniumHub("localhost", 6444);
	private final SeleniumHub SELENIUM_HUB_INVALID_2 = new SeleniumHub("localhost", 7444);
	
	@Before
	public void setUp() throws Exception {
		seleniumCluster = new SeleniumCluster(BROWSER, URL);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test to Verify that providing multiple good hubs correctly
	 * yields a Selenium object and no exception is thrown.
	 */
	@Test
	public void testGetAvailableSeleniumAllAvailable() {
		seleniumCluster.addSeleniumHub(SELENIUM_HUB_VALID_1);
		seleniumCluster.addSeleniumHub(SELENIUM_HUB_VALID_2);
		
		try {
			Selenium selenium = seleniumCluster.getAvailableSelenium();
			
			assertTrue(selenium != null);
		} catch (NoServerAvailableException e) {
			fail("NoServerAvailableException || " + e.getLocalizedMessage());
		}
	}

	/**
	 * Test to Verify that providing multiple bad hubs correctly throws the
	 * NoServerAvailableException.
	 */
	@Test
	public void testGetAvailableSeleniumNoneAvailable() {
		seleniumCluster.addSeleniumHub(SELENIUM_HUB_INVALID_1);
		seleniumCluster.addSeleniumHub(SELENIUM_HUB_INVALID_2);
		
		try {
			seleniumCluster.getAvailableSelenium();
			
			fail("Hubs Available when they shouldn't be.");
		} catch (NoServerAvailableException e) {
			assertTrue(true);
		}
	}
}
