package com.coxAxle.dealer.Notifications;

import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class NotificationDetailsPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//p/button") private Button btnChangeNotificationImage;
	@FindBy(xpath = "//input[@value='Update Notification']") private Button btnUpdateNotifications;
	@FindBy(xpath = "//div[2]/div[2]/table/tbody") private Webtable wtNotificationDetails;
	@FindBy(name = "notification_image") private Button btnImage;
	@FindBy(xpath = ".//*[@id='myModal']/div/div/form/div[3]/input") private Button btnSubmit;
	@FindBy(xpath = ".//*[@id='myModal']/div/div/form/div[3]/button") private Button btnCancel;

	/**Constructor**/
	public NotificationDetailsPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnUpdateNotifications.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Method to verify the Notification details
	public String[] verifyNotificationDetails(){
		pageLoaded();
		String value="";
		String[] table_Values=null;
		List<WebElement> rows_table = wtNotificationDetails.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=1; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();

			for (int column=0; column<columns_count; column++){
				String celtext = Columns_row.get(column).getText();
				value=value+celtext+" ";
			}
			value=value+"_";
			table_Values=value.split("_");
		}
		return table_Values;
	}

	//Click on Update button
	public void clickUpdateButton(){
		driver.get(driver.getCurrentUrl());
		TestReporter.assertTrue(btnUpdateNotifications.syncEnabled(20, false), "Update Button button is enabled");
		btnUpdateNotifications.click();
	}

	//Click on Update button
	public void clickChangeNotificationImageButton(){
		driver.get(driver.getCurrentUrl());
		TestReporter.assertTrue(btnChangeNotificationImage.syncEnabled(20, false), "Change Notification Image Button button is enabled");
		btnChangeNotificationImage.click();
	}

	//method to upload image
	public void uploadingImage(String imagepath){
		pageLoaded(btnImage);
		TestReporter.assertTrue(btnImage.syncEnabled(20, false), "Browse button is enabled");
		btnImage.sendKeys(imagepath);

	}

	//Clicking on Submit
	public void clickSubmit(){
		pageLoaded(btnSubmit);
		TestReporter.assertTrue(btnSubmit.syncEnabled(20, false), "Submit button is enabled");
		btnSubmit.click();
	}

	//Clicking on Cancel
	public void clickCancel(){
		pageLoaded(btnCancel);
		TestReporter.assertTrue(btnCancel.syncEnabled(20, false), "Cancel button is enabled");
		btnCancel.click();
	}
}