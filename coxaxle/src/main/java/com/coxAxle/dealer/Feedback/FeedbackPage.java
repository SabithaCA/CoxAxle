package com.coxAxle.dealer.Feedback;

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

public class FeedbackPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "first_name") private Textbox txtCustomerName;
	@FindBy(name = "Search")  private Button btnSearch;
	@FindBy(xpath = "//div/div/div/button") private Button btnClear;
	@FindBy(xpath = ".//*[@id='SalesFeedback']/div/div/div/div/table/tbody") private Webtable wtSalesTable;
	@FindBy(xpath = ".//*[@id='ServiceFeedback']/div/div/div/div/table/tbody") private Webtable wtServicesTable;
	@FindBy(linkText = "Service") private Link lnkServices;
	@FindBy(linkText = "Sales") private Link lnkSales;

	/**Constructor**/
	public FeedbackPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnClear.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Validate the presence of fields on Feedback page
	public void validateFeedbackFields(){
		pageLoaded();
		TestReporter.assertTrue(txtCustomerName.syncVisible(15, false), "Customer Name textbox is visible");
		TestReporter.assertTrue(btnSearch.syncVisible(15, false), "Search button is visible");
		TestReporter.assertTrue(btnClear.syncVisible(15, false), "Clear button is visible");
	}

	//Validate enable and Click on clear button
	public void clickClear(){
		pageLoaded(btnClear);
		TestReporter.assertTrue(btnClear.syncEnabled(20, false), "Submit button is enabled");
		btnClear.click();
	}

	//Validate enable and Click on search button
	public void clickSearch(){
		pageLoaded(btnSearch);
		TestReporter.assertTrue(btnSearch.syncEnabled(20, false), "Cancel button is enabled");
		btnSearch.click();
	}

	//Click on Specified Sales Customer name
	public void clickOnSpecifiedSalesCustomerName(String customerName){
		pageLoaded();
		List<WebElement> rows_table = wtSalesTable.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			if(Columns_row.get(0).getText().equalsIgnoreCase(customerName)){
				Columns_row.get(0).findElement(By.tagName("a")).click();
				break;
			}
		}
	}

	//Click on Specified Services Customer name
	public void clickOnSpecifiedServicesCustomerName(String customerName){
		pageLoaded();
		List<WebElement> rows_table = wtServicesTable.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			if(Columns_row.get(0).getText().equalsIgnoreCase(customerName)){
				Columns_row.get(0).findElement(By.tagName("a")).click();
				break;
			}
		}
	}

	//Validate and Click on Sales tab
	public void clickSales() throws InterruptedException{
		pageLoaded(lnkSales);
		TestReporter.assertTrue(lnkSales.syncVisible(20, false), "Sales tab is visible");
		lnkSales.click();
		Thread.sleep(3000);
	}
	//Validate and Click on Services tab
	public void clickServices() throws InterruptedException{
		pageLoaded(lnkServices);
		TestReporter.assertTrue(lnkServices.syncVisible(20, false), "Services tab is visible");
		lnkServices.click();
		Thread.sleep(3000);
	}

	//Set the data to be searched
	public void setSearchData(String customerName){
		txtCustomerName.set(customerName);
	}

	//Get the count of the sales data displayed after search
	public void verifySalesSearchData(String customerName){
		pageLoaded();
		int count=0;
		List<WebElement> rows_table = wtSalesTable.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			if(customerName.contains(Columns_row.get(0).getText())){
				count++;
			}
		}
		TestReporter.logStep("Total sales search data is : "+count);
	}

	//Get the count of the services data displayed after search
	public void verifyServicesSearchData(String customerName){
		pageLoaded();
		int count=0;
		List<WebElement> rows_table = wtServicesTable.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			if(customerName.contains(Columns_row.get(0).getText())){
				count++;
			}
		}
		TestReporter.logStep("Total Services search data is : "+count);
	}

}