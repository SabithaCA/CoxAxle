package com.coxAxle.dealer.Notifications;

import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Listbox;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.Webtable;
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
	@FindBy(xpath = "//table/tbody") private Webtable wtNotificationsList;
	@FindBy(linkText = "NEXT")  private Button btnNext;
	@FindBy(xpath = "//table/tbody") private Webtable wtNotificationsTabel;

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

	//method to get the list count
	public int getNotificationsListCount() throws InterruptedException{
		Thread.sleep(2000);
		List<WebElement> table_Row = wtNotificationsList.findElements(By.tagName("tr"));
		int count=table_Row.size();
		int total_count = 0;
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			table_Row = wtNotificationsList.findElements(By.tagName("tr"));
			total_count=count+table_Row.size();
			i++;
		} 
		//System.out.println("Row count "+total_count);
		return total_count;
	}

	//Getting the count of pages in pagination
	private int validateButtonsEnabledDisabledWithTotalPagesCount() {
		String data = driver.findButton(By.partialLinkText("Total Page")).getText();
		String[] data_Array = data.split(" ");
		System.out.println(data_Array[2]);
		int count = Integer.parseInt(data_Array[2]);
		return count;
	}

	//Clicking on Specified banner with NEXT pages
	public  void clickOnSpecifiedNotification(String notificationType) {
		clickOnSpecifiedNotificationType(notificationType);
		//Thread.sleep(2000);
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			clickOnSpecifiedNotificationType(notificationType);
			i++;
		}

	}
	//Click on Specified Banner
	public void clickOnSpecifiedNotificationType(String notificationType){
		pageLoaded();
		List<WebElement> rows_table = wtNotificationsList.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			//int columns_count = Columns_row.size();
			if(Columns_row.get(0).getText().equalsIgnoreCase(notificationType)){
				Columns_row.get(0).findElement(By.tagName("a")).click();
				break;
			}
		}
	}

	//Method to set the search data
	public void setSearchData(String Email){
		String data = getDealerCode(Email);
		//System.out.println(data);
		String[] input=data.split("_"); 
		pageLoaded(btnSearch);
		txtNotificationType.set(input[0]);
		txtNotificationTitle.set(input[1]);
	}
	//Check the status of Dealer
	public  String getDealerCode(String Email){
		String[] Emails=null;
		String A=verifyDealerDetail();
		String text="";
		Emails=A.split("_");
		text=getSpecificDealerDetails(Emails,Email);
		if(text.isEmpty()){
			int i=2;
			while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
				btnNext.click();
				A=A+verifyDealerDetail();
				Emails=A.split("_");
				text=getSpecificDealerDetails(Emails,Email);
				i++;
			} 
		}
		return text;
	}

	//Method to get the Customer Emails
	public String verifyDealerDetail(){
		String value="";
		List<WebElement> rows_table = wtNotificationsTabel.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			//int columns_count = Columns_row.size();
			String celtext = Columns_row.get(0).getText();
			value=value+celtext+"_";
		}
		return value;
	}

	//Method to get the Search info from Dealer details
	public String getSpecificDealerDetails(String[] Emails, String Email){
		String text="";
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=1; row<Emails.length; row++){
					List<WebElement> rows_table = wtNotificationsTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
					List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
					if(Columns_row.get(0).getText().equalsIgnoreCase(Email)){
						text=Columns_row_link.get(0).getText();
						//rows_table.get(row).findElements(By.tagName("a"));
						//text=text+"_"+Columns_row.get(1).getText();
						text=text+"_"+Columns_row.get(1).getText();
						break; 
					}
				}	
			}
		}
		return text;
	}

	//Click Search
	public void clickSearch(){
		pageLoaded(btnSearch);
		TestReporter.assertTrue(btnSearch.syncEnabled(20, false), "Search button is enabled");
		btnSearch.click();
	}

	//Click Clear
	public void clickClear(){
		pageLoaded(btnClear);
		TestReporter.assertTrue(btnClear.syncEnabled(20, false), "Clear button is enabled");
		btnClear.click();
	}
}