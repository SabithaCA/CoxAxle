package com.vensai.core.interfaces.impl;

import org.openqa.selenium.WebElement;

import com.vensai.core.interfaces.Label;
import com.vensai.utils.vensaiDriver;

/**
 * Wraps a label on a html form with some behavior.
 */
public class LabelImpl extends ElementImpl implements Label {
	/**
	 * Creates an Element for a given WebElement.
	 *
	 * @param element
	 *            element to wrap up
	 */
	public LabelImpl(WebElement element) {
		super(element);
	}

	public LabelImpl(WebElement element, vensaiDriver driver) {
		super(element, driver);
	}

	@Override
	public String getFor() {
		return getWrappedElement().getAttribute("for");
	}
}