package com.coxAxle.admin;

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

public class DealerPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//table/tbody")	private Webtable wtDealerTabel;
	@FindBy(linkText = "NEXT")  private Button btnNext;
	@FindBy(xpath = "//div[2]/table/tbody") private Webtable wtDealerDetails;
	@FindBy(id = "name") private Textbox txtName;
	@FindBy(id = "dealercode") private Textbox txtDealerCode;
	@FindBy(id = "address") private Textbox txtAddress;
	@FindBy(xpath = "//input[@value='Search']") private Button btnSearch;
	@FindBy(xpath = "//div/div/button") private Button btnClear;


	/**Constructor**/
	public DealerPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Method to get the Emails of Dealers
	public String verifyDealerDetail(){
		String value="";
		//String[] table_Values=null;
		List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		//System.out.println("tr's in table: "+rows_count);
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			String celtext = Columns_row.get(3).getText();
			//System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
			value=value+celtext+"_";
			//table_Values=value.split("_");
		}
		return value;
	}

	//Method to check the status of the NEXT button
	private boolean validateButtonsEnabledOrDisabled(Element locatorName) {
		boolean isEnabled = true;
		pageLoaded( locatorName);
		// Verifying Button status
		if (locatorName.getAttribute("href").contains("#")) {
			isEnabled = false;
		}
		if (isEnabled == true) {
			// Validating enabled button
			TestReporter.assertTrue(isEnabled,
					locatorName.getElementIdentifier() + " button is enabled");
		} else {
			// Validating Disabled button
			TestReporter.assertFalse(isEnabled,
					locatorName.getElementIdentifier() + " button is disabled");
		}
		return isEnabled;
	}

	//Method to click on Specified dealer with next pages
	public  void clickOnSpecifiedDealer(String Email){
		String[] Emails=null;
		String A=verifyDealerDetail();
		Emails=A.split("_");
		clickOnNameLink(Emails,Email);
		while(btnNext.syncVisible()==true && validateButtonsEnabledOrDisabled(btnNext)==true){
			btnNext.click();
			A=A+verifyDealerDetail();
			Emails=A.split("_");
			clickOnNameLink(Emails,Email);
		}

	}

	//Click on specified dealer name link
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
						//System.out.println("gng inside loop");
						Columns_row_link.get(0).click();
						break; 
					}
				}	
			}
		}
	}

	//Method to verify the dealer details
	public String[] verifyDealerDetails(){
		String value="";
		String[] table_Values=null;
		List<WebElement> rows_table = wtDealerDetails.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		//System.out.println("tr's in table: "+rows_count);
		for (int row=1; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			//System.out.println("Number of cells In Row "+row+" are "+columns_count);

			for (int column=0; column<columns_count; column++){
				String celtext = Columns_row.get(column).getText();
				//System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
				value=value+celtext+" ";
			}
			value=value+"_";
			//System.out.println("*********** "+value);
			table_Values=value.split("_");
			//System.out.println("--------------------------------------------------");
		}
		/*for (int i = 0; i < table_Values.length; i++) {
			System.out.println("Values : "+table_Values[i]);
		}*/
		//return Arrays.asList(table_Values);
		return table_Values;
	}

	//Method to get the title of the dealer status
	public void getTitleOfStatusImage(String[] Emails, String Email){
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
						Columns_row.get(5).findElement(By.tagName("img")).click();
						break; 
					}
				}	
			}
		}
	}

	//Check the status of Dealer
	public  void checkStatusOfDealer(String Email){
		String[] Emails=null;
		String A=verifyDealerDetail();
		Emails=A.split("_");
		getTitleOfStatusImage(Emails,Email);
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			A=A+verifyDealerDetail();
			Emails=A.split("_");
			getTitleOfStatusImage(Emails,Email);
			i++;
		} 
	}

	//Getting the count of pages in pagination
	private int validateButtonsEnabledDisabledWithTotalPagesCount() {
		String data = driver.findElement(By.xpath(".//*[@id='content']/ul/li[6]/a")).getText();
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

	//Method to get the search fields info from dealer details
	public String getSpecificDealerDetails(String[] Emails, String Email){
		String text="";
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=0; row<Emails.length; row++){
					List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
					if(Columns_row.get(3).getText().equalsIgnoreCase(Email)){
						text=Columns_row.get(0).getText();
						text=text+"_"+Columns_row.get(1).getText();
						text=text+"_"+Columns_row.get(2).getText();
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
		int count =validateButtonsEnabledDisabledWithTotalPagesCount();
		int i=1;
		while(i<count){
			btnNext.click();
			A=A+verifyDealerDetail();
			Emails=A.split("_");
			text=getSpecificDealerDetails(Emails,Email);
			i++;
		}
		return text;
	}

	//Method to set the search data
	public void setSearchData(String Email){
		String data = getDealerCode(Email);
		String[] input=data.split("_"); 
		pageLoaded(btnSearch);
		txtName.set(input[0]);
		txtDealerCode.set(input[1]);
		txtAddress.set(input[2]);	
	}

	//Clicking on Search
	public void clickSearch(){
		pageLoaded(btnSearch);
		TestReporter.assertTrue(btnSearch.syncEnabled(20, false), "Search button is enabled");
		btnSearch.click();
	}

	//Clicking on Clear
	public void clickClear(){
		pageLoaded(btnClear);
		TestReporter.assertTrue(btnClear.syncEnabled(20, false), "Clear button is enabled");
		btnClear.click();
	}
}
