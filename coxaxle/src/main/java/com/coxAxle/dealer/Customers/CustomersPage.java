package com.coxAxle.dealer.Customers;

import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class CustomersPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//table/tbody")	private Webtable wtDealerTabel;
	@FindBy(linkText = "NEXT")  private Button btnNext;
	@FindBy(xpath = "//div[2]/table/tbody") private Webtable wtDealerDetails;
	@FindBy(id = "dealercode") private Textbox txtDealerCode;
	@FindBy(xpath = "//input[@value='Search']") private Button btnSearch;
	@FindBy(xpath = "//div/div/button") private Button btnClear;
	@FindBy(id = "first_name") private Textbox txtName;

	/**Constructor**/
	public CustomersPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Method to get the Emails of customers
	public String verifyCustomerDetail(){
		String value="";
		List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			//int columns_count = Columns_row.size();
			String celtext = Columns_row.get(3).getText();
			value=value+celtext+"_";
		}
		return value;
	}

	//Click on Specified Customer with NEXT pages
	public  void clickOnSpecifiedCustomer(String Email){
		String[] Emails=null;
		String A=verifyCustomerDetail();
		Emails=A.split("_");
		clickOnNameLink(Emails,Email);
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			A=A+verifyCustomerDetail();
			Emails=A.split("_");
			clickOnNameLink(Emails,Email);
			i++;
		} 
	}

	//Click on Specified Customer name
	public void clickOnNameLink(String[] Emails, String Email){
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=0; row<Emails.length; row++){
					List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

					List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
					if(Columns_row.get(3).getText().equalsIgnoreCase(Email)){
						Columns_row_link.get(0).click();
						break; 
					}
				}	
			}
		}
	}

	//VErify Customer details
	public String[] verifyCustomerDetails(){
		String value="";
		String[] table_Values=null;
		List<WebElement> rows_table = wtDealerDetails.findElements(By.tagName("tr"));
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

	//Method to get the Customer Emails
	public String verifyDealerDetail(){
		String value="";
		List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			String celtext = Columns_row.get(3).getText();
			value=value+celtext+"_";
		}
		return value;
	}

	//Getting the count of pages in pagination
	private int validateButtonsEnabledDisabledWithTotalPagesCount() {
		String data = driver.findButton(By.partialLinkText("Total Page")).getText();
		String[] data_Array = data.split(" ");
		System.out.println(data_Array[2]);
		int count = Integer.parseInt(data_Array[2]);
		return count;
	}

	//Handling Status popup
	public void clickPopup_OkORCancel(String input){
		Alert simpleAlert = driver.switchTo().alert();
		if(input=="OK"){
			simpleAlert.accept();
		}
		else{
			simpleAlert.dismiss();
		}
	}

	//Method to get the Search info from Dealer details
	public String getSpecificDealerDetails(String[] Emails, String Email){
		String text="";
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=1; row<Emails.length; row++){
					List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
					List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
					if(Columns_row.get(3).getText().equalsIgnoreCase(Email)){
						text=Columns_row_link.get(0).getText();
						//rows_table.get(row).findElements(By.tagName("a"));
						//text=text+"_"+Columns_row.get(1).getText();
						text=text+"_"+Columns_row.get(3).getText();
						break; 
					}
				}	
			}
		}
		return text;
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
			} }
		return text;
	}

	//Method to set the search data
	public void setSearchData(String Email){
		String data = getDealerCode(Email);
		//System.out.println(data);
		String[] input=data.split("_"); 
		pageLoaded(btnSearch);
		txtName.set(input[0]);
		//txtDealerCode.set(input[1]);
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

	//Method to get the title of the dealer status
	public String getTitleOfStatusImage(String[] Emails, String Email){
		String text="";
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=0; row<Emails.length; row++){
					List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

					List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
					if(Columns_row.get(3).getText().equalsIgnoreCase(Email)){
						//System.out.println("gng inside loop");
						System.out.println(Columns_row.get(5).findElement(By.tagName("img")).getAttribute("title"));
						text=Columns_row.get(5).findElement(By.tagName("img")).getAttribute("title");
						Columns_row.get(5).findElement(By.tagName("img")).click();
						break; 
					}
				}	
			}
		}
		return text;
	}
	//Check the status of Dealer
	public  void checkStatusOfDealer(String Email){
		String[] Emails=null;
		String A=verifyDealerDetail();
		Emails=A.split("_");
		String text = getTitleOfStatusImage(Emails,Email);
		if(text.isEmpty()){
			int i=2;
			while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
				btnNext.click();
				A=A+verifyDealerDetail();
				Emails=A.split("_");
				text =	getTitleOfStatusImage(Emails,Email);
				i++;
			} 
		}
	}

	//Clicking on the specified customer checkbox
	public String checkBoxpresence(String[] Emails, String Email){
		String text="";
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=0; row<Emails.length; row++){
					List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

					List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
					if(Columns_row.get(3).getText().equalsIgnoreCase(Email)){
						//System.out.println("gng inside loop");
						TestReporter.logStep(Columns_row.get(0).findElement(By.tagName("input")).getAttribute("type")+" is present");
						text = Columns_row.get(0).findElement(By.tagName("input")).getAttribute("type");
						Columns_row.get(0).findElement(By.tagName("input")).click();
						break; 
					}
				}	
			}
		}
		return text;
	}

	//Clicking on the specified customer checkbox with next pages
	public void checkBoxCheck(String Email){
		String[] Emails=null;
		String A=verifyDealerDetail();
		Emails=A.split("_");
		String text = checkBoxpresence(Emails,Email);
		if(text.isEmpty()){
			int i=2;
			while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
				btnNext.click();
				A=A+verifyDealerDetail();
				Emails=A.split("_");
				text = checkBoxpresence(Emails,Email);
				i++;
			} 
		}
	}

	//Clicking on the specified customer checkbox
	public void deleteButtonpresence(String[] Emails, String Email){
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=0; row<Emails.length; row++){
					List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

					List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
					if(Columns_row.get(3).getText().equalsIgnoreCase(Email)){
						//System.out.println("gng inside loop");
						TestReporter.logStep(Columns_row.get(6).findElement(By.tagName("img")).getAttribute("title")+" is present");
						Columns_row.get(6).findElement(By.tagName("img")).click();
						break; 
					}
				}	
			}
		}
	}

	//Clicking on the specified customer checkbox with next pages
	public void deleteACustomer(String Email){
		String[] Emails=null;
		String A=verifyDealerDetail();
		Emails=A.split("_");
		deleteButtonpresence(Emails,Email);
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			A=A+verifyDealerDetail();
			Emails=A.split("_");
			deleteButtonpresence(Emails,Email);
			i++;
		} 
	}
}
