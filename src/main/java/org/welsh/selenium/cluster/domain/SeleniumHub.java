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

	@Override
	public String toString() {
		return "SeleniumHub [serverHost=" + serverHost + ", serverPort="
				+ serverPort + "]";
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
