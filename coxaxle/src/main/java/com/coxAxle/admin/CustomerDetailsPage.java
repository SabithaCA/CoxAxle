package com.coxAxle.admin;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class CustomerDetailsPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//div/form/div/div[2]/table/tbody")	private Webtable wtCustomerDetails;
	@FindBy(xpath = "//div/div[2]/div[2]/table/tbody") private Webtable wtVehicleList;
	@FindBy(xpath = "//div/div[4]/div[2]/table/tbody") private Webtable wtSavedSearches;
	@FindBy(xpath = "//div/div[3]/div[2]/table/tbody") private Webtable wtServiceList;


	/**Constructor**/
	public CustomerDetailsPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		wtCustomerDetails.syncVisible(20, false);
	}

	//Method to get the Customer Details
	public void getCustomerDetails(){
		pageLoaded();
		String[]  details= null;
		String data="";
		List<WebElement> rows_table = wtCustomerDetails.findElements(By.tagName("tr"));
		for (int row = 0; row <rows_table.size(); row++) {
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			for (WebElement webElement : Columns_row) {
				//System.out.println(row+" row values are "+webElement.getText());
				data = data+webElement.getText()+" ";

			}
			data=data+"_";
		}
		details=data.split("_");
		TestReporter.logStep("Customer details : "+"\n"+Arrays.toString(details));
	}

	//Method to get the Vehicle info
	public void getVehicleList(){
		pageLoaded();
		String[]  details= null;
		String data="";
		List<WebElement> rows_table = wtVehicleList.findElements(By.tagName("tr"));
		for (int row = 0; row <rows_table.size(); row++) {
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			for (WebElement webElement : Columns_row) {
				//System.out.println(row+" row values are "+webElement.getText());
				data = data+webElement.getText()+" ";

			}
			data=data+"_";
		}
		details=data.split("_");
		TestReporter.logStep("Vehicle List details : "+"\n"+Arrays.toString(details));
	}

	//Method to get the saved search data
	public void getSavedSearch(){
		pageLoaded();
		String[]  details= null;
		String data="";
		List<WebElement> rows_table = wtSavedSearches.findElements(By.tagName("tr"));
		for (int row = 0; row <rows_table.size(); row++) {
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			for (WebElement webElement : Columns_row) {
				//System.out.println(row+" row values are "+webElement.getText());
				data = data+webElement.getText()+" ";
			}
			data=data+"_";
		}
		details=data.split("_");
		TestReporter.logStep("Saved Search details : "+"\n"+Arrays.toString(details));
	}

	//Method to get the Service List
	public void getServiceList(){
		pageLoaded();
		String[]  details= null;
		String data="";
		List<WebElement> rows_table = wtServiceList.findElements(By.tagName("tr"));
		for (int row = 0; row <rows_table.size(); row++) {
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			for (WebElement webElement : Columns_row) {
				//System.out.println(row+" row values are "+webElement.getText());
				data = data+webElement.getText()+" ";
			}
			data=data+"_";
		}
		details=data.split("_");
		TestReporter.logStep("Service List details : "+"\n"+Arrays.toString(details));
	}
}
