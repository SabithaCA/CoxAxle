package com.coxAxle.dealer.Notifications;

import java.util.ResourceBundle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class CreateNotificationPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "notification_startdate") private WebElement txtNotificationStartDate;
	@FindBy(name = "notification_expirydate")  private WebElement txtNotificationExpiryDate;
	@FindBy(name = "notification_type") private Textbox txtNotificationType;
	@FindBy(name = "notification_text")  private Textbox txtNotificationText;
	@FindBy(name = "notification_title") private Textbox txtNotificationTitle;
	@FindBy(name = "notification_image") private Button btnNotificationImage;
	@FindBy(xpath = "//input[@value='Submit']")  private Button btnSubmit;
	@FindBy(xpath = "//input[@value='CANCEL']")  private Button btnCancel;

	/**Constructor**/
	public CreateNotificationPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnNotificationImage.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Validate the presence of Buttons on Banners page
	public void validateCreateNotificationsFields(){
		pageLoaded();
		TestReporter.assertTrue(txtNotificationStartDate.isDisplayed(), "Notification Start Date textbox is visible");
		TestReporter.assertTrue(txtNotificationExpiryDate.isDisplayed(), "Notification Expiry Date textbox is visible");
		TestReporter.assertTrue(txtNotificationType.syncVisible(15, false), "Notification type textbox is visible");
		TestReporter.assertTrue(txtNotificationText.syncVisible(15, false), "Notification text textbox is visible");
		TestReporter.assertTrue(btnNotificationImage.syncVisible(15, false), "Notification Image textbox is visible");
		TestReporter.assertTrue(txtNotificationTitle.syncVisible(15, false), "Notification title textbox is visible");
		TestReporter.assertTrue(btnSubmit.syncVisible(15, false), "Submit button is visible");
		TestReporter.assertTrue(btnCancel.syncVisible(15, false), "Cancel button is visible");
	}

	public void addNotificationDetails(String startDate, String expiryDate, 
			String type, String text, String title, String image){
		txtNotificationStartDate.click();
		txtNotificationExpiryDate.sendKeys(expiryDate);
		txtNotificationType.set(type);
		txtNotificationText.set(text);
		txtNotificationTitle.set(title);
		btnNotificationImage.sendKeys(image);
	}


}