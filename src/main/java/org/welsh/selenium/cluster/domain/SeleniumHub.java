package org.welsh.selenium.cluster.domain;

/**
 * Simple POJO Representing the Selenium Hub
 * 
 * @author dwelsh
 *
 */
public class SeleniumHub {

	private String serverHost;
	private int serverPort;
	
	private boolean xmlParsed = false;
	
	private Double percentageUsed;
	private Integer testsQueued;

	/**
	 * Default Constructor requiring Server Host and Server Port.
	 * Ex: localhost & 4444
	 * 
	 * @param serverHost
	 * @param serverPort
	 */
	public SeleniumHub(String serverHost, int serverPort) {
		super();
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}

	/**
	 * Method to get String containing the Server Host
	 * 
	 * @return String with Server Host
	 */
	public String getServerHost() {
		return serverHost;
	}

	/**
	 * Method to set Server Host. Ex: localhost
	 * 
	 * @param serverHost String containing Server Host
	 */
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	/**
	 * Method to get Server Port.
	 * 
	 * @return int containing server port
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Method to set Server Port. Ex: 4444
	 * 
	 * @param serverPort int containing Server Port
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	/**
	 * Method to set Percentage Used of Hub from XML. Example: 20.0
	 * 
	 * @return Double containing percentage used
	 */
	public Double getPercentageUsed() {
		return percentageUsed;
	}

	/**
	 * Method to get percentage used of Hub from XML
	 * 
	 * @param percentageUsed Double containing percentage used
	 */
	public void setPercentageUsed(Double percentageUsed) {
		this.percentageUsed = percentageUsed;
	}

	/**
	 * Method to get number of tests queued for Hub from XML
	 * 
	 * @return Integer containing number of tests queued
	 */
	public Integer getTestsQueued() {
		return testsQueued;
	}

	/**
	 * Method to set number of tests queued for Hub from XML
	 * 
	 * @param testsQueued Integer containing number of tests queued
	 */
	public void setTestsQueued(Integer testsQueued) {
		this.testsQueued = testsQueued;
	}
	

	public boolean isXmlParsed() {
		return xmlParsed;
	}

	public void setXmlParsed(boolean xmlParsed) {
		this.xmlParsed = xmlParsed;
	}

	@Override
	public String toString() {
		return "SeleniumHub [serverHost=" + serverHost + ", serverPort="
				+ serverPort + ", xmlParsed=" + xmlParsed + ", percentageUsed="
				+ percentageUsed + ", testsQueued=" + testsQueued + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SeleniumHub) {
			SeleniumHub incoming = (SeleniumHub) obj;
			
			if(incoming.getServerHost().equals(this.serverHost) 
					&& incoming.getServerPort() == this.serverPort) {
				return true;
			}
		}
		
		return false;
	}
}
