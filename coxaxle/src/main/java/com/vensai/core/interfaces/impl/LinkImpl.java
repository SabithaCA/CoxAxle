package com.vensai.core.interfaces.impl;

import org.openqa.selenium.WebElement;

import com.vensai.core.interfaces.Link;
import com.vensai.utils.vensaiDriver;
import com.vensai.utils.TestReporter;

/**
 * Wraps a label on a html form with some behavior.
 */
public class LinkImpl extends ElementImpl implements Link {
	/**
	 * Creates a Element for a given WebElement.
	 *
	 * @param element
	 *            element to wrap up
	 */
	public LinkImpl(WebElement element) {
		super(element);
	}

	public LinkImpl(WebElement element, vensaiDriver driver) {
		super(element, driver);
	}

	@Override
	public void jsClick() {

		try {
			driver.executeJavaScript(
					"if( document.createEvent ) {var click_ev = document.createEvent('MouseEvents'); click_ev.initEvent('click', true , true )"
							+ ";arguments[0].dispatchEvent(click_ev);} else { arguments[0].click();}",
					element);
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]", true);
			throw rte;
		}
		TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");

	}

	@Override
	public void click() {
		try {
			getWrappedElement().click();
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]", true);
			throw rte;
		}
		TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
	}

	@Override
	public String getURL() {
		return getWrappedElement().getAttribute("href");
	}

	/*
	@Override
	public vensaiDriver getWrappedDriver() {
		if (driver == null)
			return getWrappedDriver();
		return driver;
	}
	*/
}
