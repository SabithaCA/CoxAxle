package com.vensai.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.vensai.core.interfaces.Element;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.SauceREST;

/**
 * 
 * @author 
 * @summary This class is designed to be extended by page classes and
 *          implemented by test classes. It houses test environment data and
 *          associated getters and setters, setup for both local and remote
 *          WebDrivers as well as page class methods used to sync page behavior.
 *          The need for this arose due to the parallel behavior that indicated
 *          that WebDriver instances were crossing threads and testing on the
 *          wrong os/browser configurations
 * @date 
 *
 */
public class TestEnvironment {
	/*
	 * Test Environment Fields
	 */
	protected String applicationUnderTest = "";
	protected String browserUnderTest = "";
	protected String browserVersion = "";
	protected String operatingSystem = "";
	protected String runLocation = "";
	protected String environment = "";
	protected String testName = "";
	protected String pageUrl = "";
	/*
	 * WebDriver Fields
	 */
	protected vensaiDriver driver;
	protected ThreadLocal<vensaiDriver> threadedDriver = new ThreadLocal<vensaiDriver>();
	private boolean setThreadDriver = false;
	protected ThreadLocal<String> sessionId = new ThreadLocal<String>();
	// private WebDriver initDriver;
	/*
	 * Define a collection of webdrivers and test names inside a Map. This
	 * allows for more than one driver to be used within a test class. This also
	 * allows for a particular driver to be tied to a specific test based on
	 * test name.
	 */
	// protected Map<String, vensaiDriver> drivers = new HashMap<String,
	// vensaiDriver>();
	/*
	 * URL and Credential Repository Field
	 */
	protected ResourceBundle appURLRepository = ResourceBundle.getBundle(Constants.ENVIRONMENT_URL_PATH);
	/*
	 * Selenium Hub Field
	 */
	protected String seleniumHubURL = "http://10.238.242.50:4444/wd/hub";


	public TestEnvironment() {
	};

	public TestEnvironment(String application, String browserUnderTest, String browserVersion, String operatingSystem,
			String setRunLocation, String environment) {
		setApplicationUnderTest(application);
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(setRunLocation);
		setTestEnvironment(environment);
		setSeleniumHubURL(seleniumHubURL);
	}

	public TestEnvironment(TestEnvironment te) {
		setApplicationUnderTest(te.getApplicationUnderTest());
		setBrowserUnderTest(te.getBrowserUnderTest());
		setBrowserVersion(te.getBrowserVersion());
		setOperatingSystem(te.getOperatingSystem());
		setRunLocation(te.getRunLocation());
		setTestEnvironment(te.getTestEnvironment());
		setTestName(te.getTestName());
		setSeleniumHubURL(seleniumHubURL);
		setDriver(te.getDriver());
	}

	/*
	 * Getter and setter for application under test
	 */
	protected void setApplicationUnderTest(String aut) {
		applicationUnderTest = aut;
	}

	public String getApplicationUnderTest() {
		return applicationUnderTest;
	}

	/*
	 * Getter and setter for application under test
	 */
	protected void setPageURL(String url) {
		pageUrl = url;
	}

	public String getPageURL() {
		return pageUrl;
	}

	/*
	 * Getter and setter for browser under test
	 */
	protected void setBrowserUnderTest(String but) {
		if (but.equalsIgnoreCase("jenkinsParameter")) {
			browserUnderTest = System.getProperty("jenkinsBrowser").trim();
		} else {
			browserUnderTest = but;
		}
	}

	public String getBrowserUnderTest() {
		return browserUnderTest;
	}

	/*
	 * Getter and setter for browser version
	 */
	protected void setBrowserVersion(String bv) {
		if (bv.equalsIgnoreCase("jenkinsParameter")) {
			if (System.getProperty("jenkinsBrowserVersion") == null
					|| System.getProperty("jenkinsBrowserVersion") == "null") {
				this.browserVersion = "";
			} else {
				this.browserVersion = System.getProperty("jenkinsBrowserVersion").trim();
			}
		} else {
			this.browserVersion = bv;
		}
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	/*
	 * Getter and setter for operating system
	 */
	protected void setOperatingSystem(String os) {
		if (os.equalsIgnoreCase("jenkinsParameter")) {
			this.operatingSystem = System.getProperty("jenkinsOperatingSystem").trim();
		} else {
			this.operatingSystem = os;
		}
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	/*
	 * Getter and setter for run location
	 */
	protected void setRunLocation(String rl) {
		if (rl.equalsIgnoreCase("jenkinsParameter")) {
			this.runLocation = System.getProperty("jenkinsRunLocation".trim());
		} else {
			this.runLocation = rl;
		}
	}

	public String getRunLocation() {
		return runLocation;
	}

	/*
	 * Getter and setter for environment
	 */
	protected void setTestEnvironment(String env) {
		this.environment = env;
	}

	public String getTestEnvironment() {
		return environment;
	}

	/*
	 * Getter and setter for test name
	 */
	protected void setTestName(String tn) {
		this.testName = tn;
	}

	public String getTestName() {
		return testName;
	}

	/*
	 * Getter and setter for default test timeout
	 */
	public void setDefaultTestTimeout(int timeout) {
		System.setProperty(Constants.TEST_DRIVER_TIMEOUT, Integer.toString(timeout));
	}

	public static int getDefaultTestTimeout() {
		return Integer.parseInt(System.getProperty(Constants.TEST_DRIVER_TIMEOUT));
	}

	/*
	 * Getter and setter for the Selenium Hub URL
	 */
	public String getRemoteURL() {
		return seleniumHubURL;
	}

	protected void setSeleniumHubURL(String url) {
		System.setProperty(Constants.SELENIUM_HUB_URL, url);
	}

	// ************************************
	// ************************************
	// ************************************
	// WEBDRIVER SETUP
	// ************************************
	// ************************************
	// ************************************

	/*
	 * Getter and setter for the actual WebDriver
	 */
	private void setDriver(vensaiDriver driverSession) {
		if (isThreadedDriver())
			threadedDriver.set(driverSession);
		else
			this.driver = driverSession;
	}

	public vensaiDriver getDriver() {
		if (isThreadedDriver())
			return threadedDriver.get();
		else
			return this.driver;
	}

	/**
	 * User controls to see the driver to be threaded or not. Only use when
	 * using data provider threading
	 */
	private boolean isThreadedDriver() {
		return setThreadDriver;
	}

	public void setThreadDriver(boolean setThreadDriver) {
		this.setThreadDriver = setThreadDriver;
	}

	/*
	 * Method to retrive the URL and Credential Repository
	 */
	protected ResourceBundle getEnvironmentURLRepository() {
		return appURLRepository;
	}

	/**
	 * Launches the application under test. Gets the URL from an environment
	 * properties file based on the application.
	 * 
	 * @param None
	 * @version 
	 * @author 
	 * @return Nothing
	 */
	// @Step("Launch \"{0}\"")
	private void launchApplication(String URL) {
		getDriver().get(URL);
	}

	private void launchApplication() {
		if (this.getTestEnvironment().isEmpty()) {
			launchApplication(appURLRepository.getString(getApplicationUnderTest().toUpperCase()));
		} else {
			launchApplication(appURLRepository
					.getString(getApplicationUnderTest().toUpperCase() + "_" + getTestEnvironment().toUpperCase()));
		}
	}

	/**
	 * @return the {@link WebDriver} for the current thread
	 */

	/**
	 * Initializes the webdriver, sets up the run location, driver type,
	 * launches the application.
	 * 
	 * @param testName
	 *            - Name of the test
	 * @version 
	 * @author 
	 */
	protected vensaiDriver testStart(String testName) {
		// Uncomment the following line to have TestReporter outputs output to
		// the console
		TestReporter.setPrintToConsole(true);
		setTestName(testName);
		driverSetup();
		if (getPageURL().isEmpty())
			launchApplication();
		else
			launchApplication(getPageURL());
		return getDriver();
	}

	protected void endTest(String testName) {
		if (getDriver() != null && getDriver().getWindowHandles().size() > 0) {
			getDriver().quit();
		}
	}

	/*
	 * Use ITestResult from @AfterMethod to determine run status before closing
	 * test if reporting to sauce labs
	 */
	protected void endTest(String testName, ITestResult testResults) {
		endTest(testName);
	}

	/*
	 * Use ITestContext from @AfterTest or @AfterSuite to determine run status
	 * before closing test if reporting to sauce labs
	 */
	protected void endTest(String testName, ITestContext testResults) {
		endTest(testName);
	}


	/**
	 * Sets up the driver type, location, browser under test, os
	 * 
	 * @param None
	 * @version 
	 * @author 
	 * @return Nothing
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void driverSetup() {
		DesiredCapabilities caps = new DesiredCapabilities();
		// If the location is local, grab the drivers for each browser type from
		// within the project
		if (getRunLocation().equalsIgnoreCase("local")) {

			File file = null;

			switch (getOperatingSystem().toLowerCase().trim().replace(" ", "")) {
			case "windows":
				if (getBrowserUnderTest().equalsIgnoreCase("Firefox") || getBrowserUnderTest().equalsIgnoreCase("FF")) {
					caps = DesiredCapabilities.firefox();
				}
				// Internet explorer
				else if (getBrowserUnderTest().equalsIgnoreCase("IE")
						|| getBrowserUnderTest().replace(" ", "").equalsIgnoreCase("internetexplorer")) {
					caps = DesiredCapabilities.internetExplorer();
					caps.setCapability("ignoreZoomSetting", true);
					caps.setCapability("enablePersistentHover", false);
					file = new File(
							this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "IEDriverServer.exe").getPath());
					System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
					caps = DesiredCapabilities.internetExplorer();

				}
				// Chrome
				else if (getBrowserUnderTest().equalsIgnoreCase("Chrome")) {
					file = new File(
							this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "chromedriver.exe").getPath());
					System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
					caps = DesiredCapabilities.chrome();

				}
				// Headless - HTML unit driver
				else if (getBrowserUnderTest().equalsIgnoreCase("html")) {
					caps = DesiredCapabilities.htmlUnitWithJs();
				}

				// Safari
				else if (getBrowserUnderTest().equalsIgnoreCase("safari")) {
					caps = DesiredCapabilities.safari();

				} else if (getBrowserUnderTest().equalsIgnoreCase("microsoftedge")) {
					file = new File(this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "MicrosoftWebDriver.exe")
							.getPath());
					System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
					caps = DesiredCapabilities.edge();
				} else {
					throw new RuntimeException("Parameter not set for browser type");
				}
				break;
			case "mac":
			case "macos":
				if (getBrowserUnderTest().equalsIgnoreCase("Firefox") || getBrowserUnderTest().equalsIgnoreCase("FF")) {
					caps = DesiredCapabilities.firefox();

				}
				// Internet explorer
				else if (getBrowserUnderTest().equalsIgnoreCase("IE")
						|| getBrowserUnderTest().replace(" ", "").equalsIgnoreCase("internetexplorer")) {
					throw new RuntimeException("Currently there is no support for IE with Mac OS.");
				}
				// Chrome
				else if (getBrowserUnderTest().equalsIgnoreCase("Chrome")) {
					file = new File(
							this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "mac/chromedriver").getPath());
					System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
					try {
						// Ensure the permission on the driver include
						// executable permissions
						Process proc = Runtime.getRuntime()
								.exec(new String[] { "/bin/bash", "-c", "chmod 777 " + file.getAbsolutePath() });
						proc.waitFor();
						caps = DesiredCapabilities.chrome();

					} catch (IllegalStateException ise) {
						ise.printStackTrace();
						throw new IllegalStateException(
								"This has been seen to occur when the chromedriver file does not have executable permissions. In a terminal, navigate to the directory to which Maven pulls the drivers at runtime (e.g \"/target/classes/drivers/\") and execute the following command: chmod +rx chromedriver");
					} catch (IOException ioe) {
						ioe.printStackTrace();
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
				// Headless - HTML unit driver
				else if (getBrowserUnderTest().equalsIgnoreCase("html")) {
					caps = DesiredCapabilities.htmlUnitWithJs();

				}
				// Safari
				else if (getBrowserUnderTest().equalsIgnoreCase("safari")) {
					caps = DesiredCapabilities.safari();
				} else {
					throw new RuntimeException("Parameter not set for browser type");
				}
				break;
			case "linux":
				if (getBrowserUnderTest().equalsIgnoreCase("html")) {
					caps = DesiredCapabilities.htmlUnitWithJs();
				} else if (getBrowserUnderTest().equalsIgnoreCase("Firefox")
						|| getBrowserUnderTest().equalsIgnoreCase("FF")) {
					caps = DesiredCapabilities.firefox();
				}
			default:
				break;
			}

			setDriver(new vensaiDriver(caps));
			// Code for running on the selenium grid
		} else if (getRunLocation().equalsIgnoreCase("grid")) {
			caps.setCapability(CapabilityType.BROWSER_NAME, getBrowserUnderTest());
			if (getBrowserVersion() != null) {
				caps.setCapability(CapabilityType.VERSION, getBrowserVersion());
			}

			caps.setCapability(CapabilityType.PLATFORM, getGridPlatformByOS(getOperatingSystem()));
			if (getBrowserUnderTest().toLowerCase().contains("ie")
					|| getBrowserUnderTest().toLowerCase().contains("iexplore")) {
				caps.setCapability("ignoreZoomSetting", true);
			}

			try {
				setDriver(new vensaiDriver(caps, new URL(getRemoteURL())));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}  else {
			throw new RuntimeException(
					"Parameter for run [Location] was not set to 'Local', 'Grid', 'Sauce', or 'Remote'");
		}

		getDriver().setElementTimeout(Constants.ELEMENT_TIMEOUT);
		getDriver().setPageTimeout(Constants.PAGE_TIMEOUT);
		getDriver().setScriptTimeout(Constants.DEFAULT_GLOBAL_DRIVER_TIMEOUT);
		// setDefaultTestTimeout(Constants.DEFAULT_GLOBAL_DRIVER_TIMEOUT);
		if (!getBrowserUnderTest().toLowerCase().contains("edge")) {
			getDriver().manage().deleteAllCookies();
			getDriver().manage().window().maximize();
		}
	}

	// ************************************
	// ************************************
	// ************************************
	// PAGE OBJECT METHODS
	// ************************************
	// ************************************
	// ************************************

	/**
	 * loops for a predetermined amount of time (defined by
	 *          WebDriverSetup.getDefaultTestTimeout()) to determine if the DOM
	 *          is in a ready-state
	 * @return boolean: true is the DOM is completely loaded, false otherwise
	 * @param N
	 *            /A
	 */
	public boolean pageLoaded() {
		return new PageLoaded().isDomComplete(getDriver());
	}

	/**
	 * loops for a predetermined amount of time (defined by
	 *          WebDriverSetup.getDefaultTestTimeout()) to determine if the
	 *          Element is not null
	 * @return boolean: true is the DOM is completely loaded, false otherwise
	 * @param clazz
	 *            - page class that is calling this method
	 * @param element
	 *            - element with which to determine if a page is loaded
	 */
	@Deprecated
	public boolean pageLoaded(Class<?> clazz, Element element) {
		return new PageLoaded().isElementLoaded(clazz, getDriver(), element);
	}
	
	/**
	 *  loops for a predetermined amount of time (defined by
	 *          vensaiDriver.getElementTimeout()) to determine if the
	 *          Element is present in the DOM
	 * @param element - element with which to determine if a page is loaded
	 * @return boolean: true if the element is present in the DOM, false otherwise
	 */
	public boolean pageLoaded(Element element) {
		return PageLoaded.syncPresent(getDriver(), element);
	}

	private Platform getGridPlatformByOS(String os) {
		switch (os.toLowerCase()) {
		case "android":
			return Platform.ANDROID;
		case "windows":
			return Platform.WINDOWS;
		case "win8":
			return Platform.WIN8;
		case "win8.1":
			return Platform.WIN8_1;
		case "xp":
			return Platform.XP;
		case "linux":
			return Platform.LINUX;
		case "mac":
			return Platform.MAC;
		case "mavericks":
			return Platform.MAVERICKS;
		case "mountain_lion":
			return Platform.MOUNTAIN_LION;
		case "snow_leopard":
			return Platform.SNOW_LEOPARD;
		case "yosemite":
			return Platform.YOSEMITE;
		default:
			return Platform.ANY;
		}
	}

}
