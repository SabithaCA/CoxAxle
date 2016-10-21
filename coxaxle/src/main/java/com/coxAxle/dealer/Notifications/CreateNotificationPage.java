package com.coxAxle.dealer.Notifications;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Link;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;
import com.vensai.utils.date.DateTimeConversion;

public class CreateNotificationPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "notification_startdate") private Textbox txtNotificationStartDate;
	@FindBy(name = "notification_expirydate")  private Textbox txtNotificationExpiryDate;
	@FindBy(name = "notification_type") private Textbox txtNotificationType;
	@FindBy(name = "notification_text")  private Textbox txtNotificationText;
	@FindBy(name = "notification_title") private Textbox txtNotificationTitle;
	@FindBy(name = "notification_image") private Button btnNotificationImage;
	@FindBy(xpath = "//input[@value='Submit']")  private Button btnSubmit;
	@FindBy(xpath = "//input[@value='CANCEL']")  private Button btnCancel;
	@FindBy(linkText = "Prev") private WebElement lnkPrevious;
	@FindBy(linkText = "Next") private WebElement lnkNext;
	@FindBy(xpath = ".//*[@id='ui-datepicker-div']/div/div") private WebElement dateMMyyyy;
	@FindBy(xpath = "//table/tbody") private Webtable wtDateTabel;

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

	//Add Notification Details
	public void addNotificationDetails(String startDate, String expiryDate, 
			String type, String text, String title, String image) throws InterruptedException{
		txtNotificationStartDate.click();
		Thread.sleep(2000);
		DateTimeConversion dateTimeConversion = new DateTimeConversion();
		datePicker(dateTimeConversion.monthYearFormate(), dateTimeConversion.dateFormate());		
		txtNotificationExpiryDate.click();
		datePicker("February 2017","20");		
		txtNotificationType.set(type);
		txtNotificationText.set(text);
		txtNotificationTitle.set(title);
		if(btnNotificationImage.syncVisible(20, false)){
		btnNotificationImage.sendKeys(image);}
	}

	//Validate enable and Click on Submit button
	public void clickSubmit(){
		pageLoaded(btnSubmit);
		TestReporter.assertTrue(btnSubmit.syncEnabled(20, false), "Submit button is enabled");
		btnSubmit.click();
	}

	//Validate enable and Click on Cancel button
	public void clickCancel(){
		pageLoaded(btnCancel);
		TestReporter.assertTrue(btnCancel.syncEnabled(20, false), "Cancel button is enabled");
		btnCancel.click();
	}
	//Method to pick the month and year from application
	public String getMonthAndYear(){
		List<WebElement> list = dateMMyyyy.findElements(By.tagName("span"));
		String date="";
		for (WebElement webElement : list) {
			date=date+webElement.getText()+" ";
		}
		return date;
	}

	//Method to loop the date selection
	public void datePicker(String monthAndYear, String date){
		compareMonthYear(monthAndYear, date);
		while(lnkNext.isDisplayed()){
			compareMonthYear(monthAndYear, date);
		} 
	}

	//Method to compare Month and Year and Click on Date
	public void compareMonthYear(String monthAndYear, String date){
		String currentDate=getMonthAndYear();
		//DateTimeConversion dateTimeConversion = new DateTimeConversion();
		//String B =dateTimeConversion.monthYearFormate();
		if(currentDate.trim().equalsIgnoreCase(monthAndYear.trim())){
			List<WebElement> rows_table = wtDateTabel.findElements(By.tagName("tr"));
			int rows_count = rows_table.size();
			for (int row=0; row<rows_count; row++){
				List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				for (WebElement webElement : Columns_row) {
					if(webElement.getText().equalsIgnoreCase(date)){
						webElement.click();
						break;
					}
				}	
			}
		}

		else{
			lnkNext.click();
		}
	}
}