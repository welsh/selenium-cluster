package org.welsh.selenium.cluster.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bouncycastle.pqc.math.linearalgebra.GoppaCode.MaMaPe;
import org.openqa.grid.internal.Registry;
import org.openqa.grid.internal.RemoteProxy;
import org.openqa.grid.web.servlet.RegistryBasedServlet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.google.common.io.ByteStreams;

public class SeleniumClusterHubStatus extends RegistryBasedServlet {
	
	/**
	 * Logger
	 */
	private static final Logger log = Logger.getLogger(SeleniumClusterHubStatus.class.getName());

	private static final long serialVersionUID = -3167071109736458146L;

	public SeleniumClusterHubStatus() {
		super(null);
	}
	
	public SeleniumClusterHubStatus(Registry registry) {
		super(registry);
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	process(request, response);
    }
    
    /**
     * Copied from {@}
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ParserConfigurationException 
     * @throws TransformerConfigurationException 
     */
    protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	InputStream in = new ByteArrayInputStream("Error Generating XML".getBytes("UTF-8"));
    	
    	try {
	    	Integer currentSessionsInt = getRegistry().getActiveSessions().size();
	    	Integer queueSizeInt = getRegistry().getNewSessionRequestCount();
	    	Integer maximumTotalSessionsInt = new Integer(0);
	    	
	    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document document = docBuilder.newDocument();
			Element rootElement = document.createElement("grid");
			document.appendChild(rootElement);
			
			Element currentSessions = document.createElement("currentSessions");
			currentSessions.appendChild(document.createTextNode(currentSessionsInt.toString()));
			rootElement.appendChild(currentSessions);
			
			Element maximumTotalSessions = document.createElement("maximumTotalSessions");
			rootElement.appendChild(maximumTotalSessions);
			
			Element queueSize = document.createElement("queueSize");
			queueSize.appendChild(document.createTextNode(queueSizeInt.toString()));
			rootElement.appendChild(queueSize);
			
			Element percentageUtilized = document.createElement("percentageUtilized");
			rootElement.appendChild(percentageUtilized);
			
			Element nodes = document.createElement("nodes");
			nodes.setAttribute("size", getRegistry().getAllProxies().size() + "");
			rootElement.appendChild(nodes);
	    	
			for(RemoteProxy proxy : getRegistry().getAllProxies()) {
				Integer maxProxySessionsInt = proxy.getMaxNumberOfConcurrentTestSessions();
				maximumTotalSessionsInt = maximumTotalSessionsInt + maxProxySessionsInt;
				
				Element node = document.createElement("node");
				node.setAttribute("currentSessions", proxy.getTotalUsed() + "");
				node.setAttribute("maxSessions", maxProxySessionsInt.toString());
				nodes.appendChild(node);
			}
			
			maximumTotalSessions.appendChild(document.createTextNode(maximumTotalSessionsInt.toString()));
			
			Double percentageUtilizedDouble = (double) currentSessionsInt / (double) maximumTotalSessionsInt;
			
			if(Double.isNaN(percentageUtilizedDouble)) {
				percentageUtilizedDouble = 0.0;
			} else {
				percentageUtilizedDouble = percentageUtilizedDouble * 100.0;
			}
			
			percentageUtilized.appendChild(document.createTextNode(percentageUtilizedDouble.toString()));
			
			DOMImplementationLS domImplLS = (DOMImplementationLS) document.getImplementation();
			LSSerializer serializer = domImplLS.createLSSerializer();
	    	
			in = new ByteArrayInputStream(serializer.writeToString(rootElement).getBytes("UTF-8"));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
        try {
           ByteStreams.copy(in, response.getOutputStream());
        } finally {
           in.close();
           response.getOutputStream().close();
        }
    }
}
