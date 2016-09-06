package com.vensai.core.interfaces;

import com.vensai.core.interfaces.impl.LinkImpl;
import com.vensai.core.interfaces.impl.internal.ImplementedBy;

/**
 * Interface that wraps a WebElement in Link functionality.
 */
@ImplementedBy(LinkImpl.class)
public interface Link extends Element {

	/**
	 * @summary - Click the button using the default Selenium click
	 */
	@Override
	public void click();

	/**
	 * @summary - Click the link using a JavascriptExecutor click
	 */
	@Override
	public void jsClick();

	public String getURL();
}