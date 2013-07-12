Selenium Cluster
================

Library to provide a clustering ability to the Creation of a `com.thoughtworks.selenium.Selenium` Object.

License
---------

Selenium Cluster is distributed under the GPL v3. For more information please see the [GPL](http://www.gnu.org/licenses/gpl.txt).

The Implementation
---------

Notes on the implementation

* This is a Java Libary JAR file Compiled using JDK 1.6
* Is structured using Maven

Getting Started
---------

To Utilize checkout the source code and run:

    mvn clean install -Dmaven.test.skip=true

And then add the following to your pom.xml Dependencies Section:

    <dependency>
    	<groupId>org.welsh.selenium</groupId>
    	<artifactId>selenium-cluster</artifactId>
    	<version>1.0.0</version>
    </dependency>
    
I am working on getting this into Maven Central.

Usage
---------

And then you can Initialize the SeleniumCluster Object:

    SeleniumCluster seleniumCluster = new SeleniumCluster(BROWSER, URL);

And add some Available Selenium Hubs to it:

    SeleniumHub seleniumHub = new SeleniumHub("localhost", 4444);
    seleniumCluster.addSeleniumHub(seleniumHub);

Or:

    seleniumCluster.addSeleniumHub("localhost", 5444);

And then you can finally collect your `com.thoughtworks.selenium.Selenium` object:

    Selenium selenium = seleniumCluster.getAvailableSelenium();

At which point you can decide what you wish to do regarding the `org.welsh.selenium.cluster.exception.NoServerAvailableException` exception, your two options are:

1. Catch It
2. Throw It.

Change Log
---------

#### Version 1.0.0

* Initial Implementation going purely off Return Code of Grid Console Page

Planned Changes
---------

Planned future features are:

* Smart Load Balancing to Provide the Selenium Server with the Least Load currently on it.