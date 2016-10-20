package com.coxAxle.dealer.Notifications;

import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Listbox;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class NotificationsPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "notification_type") private Textbox txtNotificationType;
	@FindBy(name = "notification_title")  private Textbox txtNotificationTitle;
	@FindBy(id = "notification_status") private Listbox lstNotificationStatus;
	@FindBy(xpath = "//input[@value='Search']")  private Button btnSearch;
	@FindBy(xpath = "//div/div/button") private Button btnClear;
	@FindBy(xpath = "//input[@value='Add Notification']")  private Button btnAddNotification;

	/**Constructor**/
	public NotificationsPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnAddNotification.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Click on Add Banner Button
	public void clickAddNotification(){
		driver.get(driver.getCurrentUrl());
		pageLoaded();
		TestReporter.assertTrue(btnAddNotification.syncEnabled(20, false), "Add Notification button is enabled");
		btnAddNotification.jsClick();
	}

	//Validate the presence of Buttons on Banners page
	public void validateNotificationsFields(){
		pageLoaded();
		TestReporter.assertTrue(txtNotificationType.syncVisible(15, false), "Notification type textbox is visible");
		TestReporter.assertTrue(txtNotificationTitle.syncVisible(15, false), "Notification title textbox is visible");
		TestReporter.assertTrue(btnClear.syncVisible(15, false), "Clear button is visible");
		TestReporter.assertTrue(btnSearch.syncVisible(15, false), "Search button is visible");
		List<WebElement> values= lstNotificationStatus.getOptions();
		for (WebElement webElement : values) {
			TestReporter.logStep(webElement.getText()+" is visible");
		}
	}
}