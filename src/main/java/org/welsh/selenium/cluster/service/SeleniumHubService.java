package org.welsh.selenium.cluster.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.welsh.selenium.cluster.cmd.Constants;
import org.welsh.selenium.cluster.domain.SeleniumHub;
import org.xml.sax.SAXException;

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

	public void setFieldsFromStatusPage(SeleniumHub seleniumHub) throws ParserConfigurationException {
		String url = "http://" + seleniumHub.getServerHost() + ":" 
				+ seleniumHub.getServerPort() + Constants.GRID_HUB_STATUS_PAGE;
		
		log.fine("URL: " + url);
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new URL(url).openStream());
			
			document.getDocumentElement().normalize();
			
			DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
			LSSerializer serializer = domImplLS.createLSSerializer();
			log.fine("Recevied XML: " + serializer.writeToString(document));
			
			Element rootElement = document.getDocumentElement();
			
			NodeList nodes = rootElement.getChildNodes();
			
			for(int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				
				if(node.getNodeName().equals("queueSize")) {
					seleniumHub.setTestsQueued(Integer.parseInt(node.getTextContent()));
				}
				
				if(node.getNodeName().equals("percentageUtilized")) {
					seleniumHub.setPercentageUsed(Double.parseDouble(node.getTextContent()));
				}
			}
			
			
			seleniumHub.setXmlParsed(true);
		} catch (Exception e) {
			log.warning("Error Processing XML. Exception: " + e.getLocalizedMessage());
		}
	}
}
