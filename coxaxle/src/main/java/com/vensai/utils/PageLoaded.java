package com.vensai.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.impl.internal.ElementFactory;

/**
 * Several different methods of waiting for a page to finish loading.
 * 
 * 
 * @version 10/16/2014
 * @author Justin Phlegar
 * 
 */
public class PageLoaded {

	private static vensaiDriver driver = null;
	@SuppressWarnings("rawtypes")
	private Class clazz = null;
	private int timeout = 0;
	private static boolean defaultSyncHandler = true;
	
	public PageLoaded() {
		this.timeout = Constants.ELEMENT_TIMEOUT;
	}

	public PageLoaded(vensaiDriver oDriver) {
		driver = oDriver;
		this.timeout = driver.getElementTimeout();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		ElementFactory.initElements(driver, clazz);
	}

	/**
	 * This waits for a specified element on the page to be found on the page by
	 * the driver Uses the default test time out set by WebDriverSetup
	 * 
	 * @param class
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param driver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 10/16/2014
	 * @author Justin Phlegar
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public boolean isElementLoaded(Class clazz, Element obj) {
		this.clazz = clazz;
		int count = 0;

		// set the timeout for looking for an element to 1 second as we are
		// doing a loop and then refreshing the elements
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		try {

			while (!obj.elementWired()) {
				if (count == this.timeout) {
					break;
				} else {
					count++;
					initialize();
				}
			}
		} catch (NullPointerException | NoSuchElementException | StaleElementReferenceException e) {
			// do nothing
		}

		// set the timeout for looking for an element back to the default
		// timeout
		driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT, TimeUnit.SECONDS);

		if (count < this.timeout) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This waits for a specified element on the page to be found on the page by
	 * the driver Uses the default test time out set by WebDriverSetup
	 * 
	 * @param class
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param driver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 10/16/2014
	 * @author Justin Phlegar
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public boolean isElementLoaded(Class clazz, vensaiDriver oDriver, Element obj) {
		driver = oDriver;
		this.clazz = clazz;
		return isElementLoaded(clazz, obj);
	}

	/**
	 * Overloaded method where you can specify the timeout This waits for a
	 * specified element on the page to be found on the page by the driver
	 * 
	 * 
	 * @param class
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param driver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public boolean isElementLoaded(Class clazz, vensaiDriver driver, Element obj, int timeout) {
		this.timeout = timeout;
		return isElementLoaded(clazz, driver, obj);
	}

	/**
	 * This uses the HTML DOM readyState property to wait until a page is
	 * finished loading. It will wait for the ready state to be either
	 * 'interactive' or 'complete'.
	 * 
	 * @param class
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param driver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public boolean isDomInteractive() {
		int count = 0;
		Object obj = null;

		do {
			// this returns a boolean
			obj = driver.executeJavaScript(
					"var result = document.readyState; return (result == 'complete' || result == 'interactive');");
			if (count == this.timeout)
				break;
			else {
				Sleeper.sleep(500);
				count++;

			}
		} while (obj.equals(false));

		if (count < this.timeout * 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This uses the HTML DOM readyState property to wait until a page is
	 * finished loading. It will wait for the ready state to be either
	 * 'interactive' or 'complete'.
	 * 
	 * @param class
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param driver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public boolean isDomInteractive(vensaiDriver oDriver) {
		driver = oDriver;
		return isDomInteractive();
	}

	/**
	 * Overloaded method - gives option of specifying a timeout. This uses the
	 * HTML DOM readyState property to wait until a page is finished loading. It
	 * will wait for the ready state to be either 'interactive' or 'complete'.
	 * 
	 * @param driver
	 *            The webDriver
	 * @param timeout
	 *            Integer value of number seconds to wait for a page to finish
	 *            loaded before quiting
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public boolean isDomInteractive(vensaiDriver driver, int timeout) {
		this.timeout = timeout;
		return isDomInteractive(driver);
	}

	/**
	 * This uses protractor method to wait until a page is ready -
	 * notifyWhenNoOutstandingRequests
	 * 
	 * @param driver
	 *            The webDriver
	 * @version 10/16/2014
	 * @author Justin Phlegar
	 * 
	 */
	public void isAngularComplete() {
		try {
			driver.executeAsyncJavaScript("var callback = arguments[arguments.length - 1];"
					+ "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
		} catch (WebDriverException wde) {
			TestReporter.logFailure(
					"Unable to perform Angular sync. This is most likely because the $browser service is not injected within the Angular Controller. Performing a IsDomComplete instead");
			isDomComplete();
		}

	}

	/**
	 * A more strict version of isDomInteractive. This uses the HTML DOM
	 * readyState property to wait until a page is finished loading. It will
	 * wait for the ready state to be 'complete'.
	 * 
	 * @param class
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param driver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public boolean isDomComplete() {
		int count = 0;
		Object obj = null;

		do {
			// this returns a boolean
			obj = driver.executeJavaScript("var result = document.readyState; return (result == 'complete');");
			if (count == this.timeout)
				break;
			else {
				Sleeper.sleep(500);
				count++;

			}
		} while (obj.equals(false));

		if (count < this.timeout * 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A more strict version of isDomInteractive. This uses the HTML DOM
	 * readyState property to wait until a page is finished loading. It will
	 * wait for the ready state to be 'complete'.
	 * 
	 * @param class
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param driver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public boolean isDomComplete(vensaiDriver oDriver) {
		driver = oDriver;
		return isDomComplete();
	}

	/**
	 * Overloaded method - gives option of specifying a timeout. A more strict
	 * version of isDomInteractive This uses the HTML DOM readyState property to
	 * wait until a page is finished loading. It will wait for the ready state
	 * to be 'complete'.
	 * 
	 * @param driver
	 *            The webDriver
	 * @param timeout
	 *            Integer value of number seconds to wait for a page to finish
	 *            loaded before quiting
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public boolean isDomComplete(vensaiDriver driver, int timeout) {
		this.timeout = timeout;
		return isDomComplete(driver);
	}

	/**
	 * Used in conjunction with WebObjectPresent to determine if the desired
	 * element is present in the DOM Will loop for the time out listed in
	 * com.vensai.utils.Constants If object is not present within the time, throw
	 * an error
	 * 
	 * @author Justin
	 */
	public static boolean syncPresent(vensaiDriver driver, Element element) {
		return syncPresent(driver, driver.getElementTimeout(),getSyncToFailTest(), element);
	}

	/**
	 * Used in conjunction with WebObjectPresent to determine if the desired
	 * element is present in the DOM Will loop for the time out passed in
	 * parameter timeout If object is not present within the time, throw an
	 * error
	 * 
	 * @author Justin
	 */
	public static boolean syncPresent(vensaiDriver driver, int timeout, Element element) {
		return syncPresent(driver, timeout, getSyncToFailTest(), element);
	}

	/**
	 * Used in conjunction with WebObjectPresent to determine if the desired
	 * element is present in the DOM Will loop for the time out passed in
	 * parameter timeout If object is not present within the time, handle error
	 * based on returnError
	 * 
	 * @author Justin
	 */
	public static boolean syncPresent(vensaiDriver driver, int timeout, boolean returnError, Element element) {
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		By locator = element.getElementLocator();
		TestReporter.interfaceLog("<i> Syncing to element [ <b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>PRESENT</b> in DOM within [ " + timeout + " ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (webElementPresent(driver, locator)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout);

		if (!found && returnError) {
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>PRESENT</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not PRESENT on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}

	/**
	 *
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out listed in
	 * org.vensai.chameleon.CONSTANT.TIMEOUT If object is not visible within the
	 * time, throw an error
	 * 
	 * @author Justin
	 */
	public static boolean syncVisible(vensaiDriver driver, Element element) {
		return syncVisible(driver, driver.getElementTimeout(),getSyncToFailTest(),  element);
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, throw an error
	 * 
	 * @author Justin
	 * 
	 */
	public static boolean syncVisible(vensaiDriver driver, int timeout, Element element) {
		return syncVisible(driver, timeout, getSyncToFailTest(), element);
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
	 *
	 */
	public static boolean syncVisible(vensaiDriver driver, int timeout, boolean returnError, Element element) {
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>VISIBLE<b> within [ " + timeout + " ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (webElementVisible(driver, element)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && returnError) {
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>VISIBLE</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not VISIBLE on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is hidden from the screen Will loop for the time out listed in
	 * org.vensai.chameleon.CONSTANT.TIMEOUT If object is not visible within the
	 * time, throw an error
	 * 
	 * @author Justin
	 */
	public static boolean syncHidden(vensaiDriver driver, Element element) {
		return syncHidden(driver, driver.getElementTimeout(),getSyncToFailTest(),  element);
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is hidden from the screen Will loop for the time out listed in
	 * org.vensai.chameleon.CONSTANT.TIMEOUT If object is not visible within the
	 * time, throw an error
	 * 
	 * @author Justin
	 */
	public static boolean syncHidden(vensaiDriver driver, int timeout, Element element) {
		return syncHidden(driver, timeout, getSyncToFailTest(), element);
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 * 
	 * @author Justin
	 */
	public static boolean syncHidden(vensaiDriver driver, int timeout, boolean returnError, Element element) {
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>HIDDEN</b> within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (!webElementVisible(driver, element)) {
				found = true;
				break;
			}
		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && returnError) {
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>HIDDEN</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not HIDDEN on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}

	/**
	 *
	 * Used in conjunction with WebObjectEnabled to determine if the desired
	 * element is enabled on the screen Will loop for the time out listed in
	 * org.vensai.chameleon.CONSTANT.TIMEOUT If object is not enabled within the
	 * time, throw an error
	 * 
	 * @author Justin
	 */
	public static boolean syncEnabled(vensaiDriver driver, Element element) {
		return syncEnabled(driver, driver.getElementTimeout(),getSyncToFailTest(),  element);
	}

	/**
	 * 
	 * Used in conjunction with WebObjectEnabled to determine if the desired
	 * element is enabled on the screen Will loop for the time out passed in the
	 * variable timeout If object is not enabled within the time, throw an error
	 * 
	 * @author Justin
	 * 
	 */
	public static boolean syncEnabled(vensaiDriver driver, int timeout, Element element) {
		return syncEnabled(driver, timeout, getSyncToFailTest(), element);
	}

	/**
	 * Used in conjunction with WebObjectEnabled to determine if the desired
	 * element is enabled on the screen Will loop for the time out passed in the
	 * variable timeout If object is not enabled within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
	 *
	 */
	public static boolean syncEnabled(vensaiDriver driver, int timeout, boolean returnError, Element element) {
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>ENABLED</b> within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (webElementEnabled(driver, element)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && returnError) {
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>ENABLED</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not ENABLED on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}

	/**
	 *
	 * Used in conjunction with WebObjectEnabled to determine if the desired
	 * element is disabled on the screen Will loop for the time out listed in
	 * org.vensai.chameleon.CONSTANT.TIMEOUT If object is not disabled within the
	 * time, throw an error
	 * 
	 * @author Justin
	 */
	public static boolean syncDisabled(vensaiDriver driver, Element element) {
		return syncDisabled(driver, driver.getElementTimeout(), getSyncToFailTest(),  element);
	}

	/**
	 * 
	 * Used in conjunction with WebObjectDisabled to determine if the desired
	 * element is disabled on the screen Will loop for the time out passed in
	 * the variable timeout If object is not disabled within the time, throw an
	 * error
	 * 
	 * @author Justin
	 * 
	 */
	public static boolean syncDisabled(vensaiDriver driver, int timeout, Element element) {
		return syncDisabled(driver, timeout, getSyncToFailTest(), element);
	}

	/**
	 * Used in conjunction with WebObjectDisabled to determine if the desired
	 * element is disabled on the screen Will loop for the time out passed in
	 * the variable timeout If object is not disabled within the time, handle
	 * the error based on the boolean
	 *
	 * @author Justin
	 *
	 */
	public static boolean syncDisabled(vensaiDriver driver, int timeout, boolean returnError, Element element) {
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>DISABLED</b> within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (!webElementEnabled(driver, element)) {
				found = true;
				break;
			}
		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && returnError) {
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>DISABLED</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not DISABLED on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}

	/**
	 *
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * listed in org.vensai.chameleon.CONSTANT.TIMEOUT If text is not present
	 * within the time, throw an error
	 * 
	 * @author Justin
	 */
	public static boolean syncTextInElement(vensaiDriver driver, String text, Element element) {
		return syncTextInElement(driver, text, driver.getElementTimeout(), getSyncToFailTest(), element);
	}

	/**
	 * 
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * throw an error
	 * 
	 * @author Justin
	 * 
	 */
	public static boolean syncTextInElement(vensaiDriver driver, String text, int timeout, Element element) {
		return syncTextInElement(driver, text, timeout, getSyncToFailTest(), element);
	}

	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 *
	 */
	public static boolean syncTextInElement(vensaiDriver driver, String text, int timeout, boolean returnError, Element element) {
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to text [<b>" + text + "</b> ] in element [<b>@FindBy: "
				+ element.getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (webElementTextPresent(driver, element, text)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && returnError) {
			TestReporter.interfaceLog(
					"<i>Element [<b>@FindBy: " + element.getElementLocatorInfo() + " </b>] did not contain the text [ " + text
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] did not contain the text [ " + text
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}

	/**
	 * Use WebDriver Wait to determine if object is present in the DOM or not
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param locator
	 *            {@link By} object to search for
	 * @return TRUE if element is currently present in the DOM, FALSE if the
	 *         element is not present in the DOM
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean webElementPresent(vensaiDriver driver, By locator) {
		Wait wait = new WebDriverWait(driver, 0);

		try {
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator)) != null;
		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}

	}

	/**
	 * Use WebDriver Wait to determine if object is visible on the screen or not
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 *            Element to search for
	 * @return TRUE if element is currently visible on the screen, FALSE if the
	 *         element is not visible on the screen
	 */
	private static boolean webElementVisible(vensaiDriver driver, WebElement element) {
		try {
			Point location = element.getLocation();

			Dimension size = element.getSize();
			if ((location.getX() > 0 & location.getY() > 0) | (size.getHeight() > 0 & size.getWidth() > 0)) {
				if (element.getAttribute("hidden") != null)
					return false;
				if (element.getAttribute("type") != null) {
					if (element.getAttribute("type").equals("hidden"))
						return false;
				}
				return true;
			} else {
				return false;
			}

		} catch (WebDriverException | ClassCastException e) {
			return false;
		}
	}

	/**
	 * Use WebDriver Wait to determine if object is enabled on the screen or not
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 *            Element to search for
	 * @return TRUE if element is currently enabled on the screen, FALSE if the
	 *         element is not enabled on the screen
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean webElementEnabled(vensaiDriver driver, WebElement element) {
		Wait wait = new WebDriverWait(driver, 0);

		try {
			return wait.until(ExpectedConditions.elementToBeClickable(element)) != null;
			// return element.isEnabled();
		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}

	}

	/**
	 * Use WebDriver Wait to determine if object contains the expected text
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 *            Element to search for
	 * @param text
	 *            The text to search for
	 * @return TRUE if element is currently visible on the screen, FALSE if the
	 *         element is not visible on the screen
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean webElementTextPresent(vensaiDriver driver, WebElement element, String text) {
		Wait wait = new WebDriverWait(driver, 0);
		try {

			if (wait.until(ExpectedConditions.textToBePresentInElement(element, text)) != null) {
				return true;
			} else if (wait.until(ExpectedConditions.textToBePresentInElementValue(element, text)) != null) {
				return true;
			} else {
				return false;
			}

		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
			try {
				if (wait.until(ExpectedConditions.textToBePresentInElementValue(element, text)) != null) {
					return true;
				} else {
					return false;
				}
			} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException
					| TimeoutException e2) {
				return false;
			} catch (WebDriverException wde) {
				/*
				 * if(driver instanceof EdgeDriver){ TestReporter.logFailure(
				 * "ExpectedConditions.textToBePresentInElementValue has not been implemented by Edge Driver"
				 * ); }
				 */
				return false;
			}

		}
	}
	
	/**
	 * @summary Used to create all page objects WebElements as proxies (not
	 *          actual objects, but rather placeholders) or to reinitialize all
	 *          page object WebElements to allow for the state of a page to
	 *          change and not fail a test
	 * @return N/A
	 * @param clazz
	 *            - page class that is calling this method for which to
	 *            initialize elements
	 */
	public static void initializePage(Class<?> clazz) {
		try {
			ElementFactory.initElements(driver, clazz.getConstructor(TestEnvironment.class));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @summary Used to create all page objects WebElements as proxies (not
	 *          actual objects, but rather placeholders) or to reinitialize all
	 *          page object WebElements to allow for the state of a page to
	 *          change and not fail a test
	 * @return N/A
	 * @param clazz
	 *            - page class that is calling this method for which to
	 *            initialize elements
	 */
	public static void initializePage(Class<?> clazz, vensaiDriver oDriver) {
		try {
			ElementFactory.initElements(oDriver, clazz.getConstructor(TestEnvironment.class));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean getSyncToFailTest() {
	    return defaultSyncHandler;
	}

	public static void setSyncToFailTest(boolean defaultSyncHandler) {
	    PageLoaded.defaultSyncHandler = defaultSyncHandler;
	}
}
