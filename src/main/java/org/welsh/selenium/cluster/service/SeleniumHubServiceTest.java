package org.welsh.selenium.cluster.service;

import static org.junit.Assert.*;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.welsh.selenium.cluster.domain.SeleniumHub;

public class SeleniumHubServiceTest {
	
	private SeleniumHubService seleniumHubService;
	private SeleniumHub hubOnline;
	private SeleniumHub hubOffline;

	@Before
	public void setUp() throws Exception {
		seleniumHubService = new SeleniumHubService();
		
		hubOnline = new SeleniumHub("localhost", 5444);
		hubOffline = new SeleniumHub("localhost", 6444);
	}

	@After
	public void tearDown() throws Exception {
		seleniumHubService = null;
		
		hubOnline = null;
		hubOffline = null;
	}

	@Test
	public void testMatchesHttpResponseCodeHubOnline() {
		assertTrue(seleniumHubService.matchesHttpResponseCode(hubOnline, HttpStatus.SC_OK));
	}
	
	@Test
	public void testMatchesHttpResponseCodeHubOffline() {
		assertFalse(seleniumHubService.matchesHttpResponseCode(hubOffline, HttpStatus.SC_OK));
	}

	@Test
	public void testSetFieldsFromStatusPageHubOnline() {
		try {
			seleniumHubService.setFieldsFromStatusPage(hubOnline);
			
			assertTrue(hubOnline.getPercentageUsed() != null);
			assertTrue(hubOnline.getTestsQueued() != null);
			assertTrue(hubOnline.isXmlParsed());
		} catch (Exception e) {
			fail("Unexpected Exception Thrown || " + e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testSetFieldsFromStatusPageHubOffline() {
		try {
			seleniumHubService.setFieldsFromStatusPage(hubOffline);
			
			assertFalse(hubOffline.getPercentageUsed() != null);
			assertFalse(hubOffline.getTestsQueued() != null);
			assertFalse(hubOnline.isXmlParsed());
		} catch (Exception e) {
			fail("Unexpected Exception Thrown || " + e.getLocalizedMessage());
		}
		
		assertTrue(hubOffline.getPercentageUsed() == null);
		assertTrue(hubOffline.getTestsQueued() == null);
	}

}
