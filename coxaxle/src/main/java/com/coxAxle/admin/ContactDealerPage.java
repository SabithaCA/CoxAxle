package com.coxAxle.admin;

import java.util.List;
import java.util.ResourceBundle;
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

public class ContactDealerPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//table/tbody")	private Webtable wtDealerTabel;
	@FindBy(linkText = "NEXT")  private Button btnNext;
	@FindBy(xpath = "//div[2]/table/tbody") private Webtable wtDealerDetails;
	@FindBy(xpath = "//table/tbody/tr/td[1]/img") private Element eleContactLogo;
	@FindBy(id = "dealercode") private Textbox txtDealerCode;
	@FindBy(xpath = "//input[@value='Search']") private Button btnSearch;
	@FindBy(xpath = "//div/div/button") private Button btnClear;

	/**Constructor**/
	public ContactDealerPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Method to get the dealer Emails 
	public String verifyDealerContacts(int columnNum){
		String value="";
		List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			//int columns_count = Columns_row.size();
			String celtext = Columns_row.get(columnNum).getText();
			value=value+celtext+"_";
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

	//Clicking on the specified dealer name
	public void clickOnNameLink(String[] Emails, String Email, int columnNum){
		for (String string : Emails) {
			if(string.equalsIgnoreCase(Email)){
				System.out.println(string);
				TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
				for (int row=0; row<Emails.length; row++){
					List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
					List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

					List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
					if(Columns_row.get(columnNum).getText().equalsIgnoreCase(Email)){
						System.out.println("gng inside loop");
						Columns_row_link.get(0).click();
						break; 
					}
				}	
			}
		}
	}

	//Method to verify the dealer Details
	public String[] verifyDealerDetails(){
		String value="";
		String[] table_Values=null;
		List<WebElement> rows_table = wtDealerDetails.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
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

	//Method to verify the dealer code and clicking the dealer name related to dealer code
	public void verifyDealerCode(String code){
		String[] Emails=null;
		String A=verifyDealerContacts(1);
		Emails=A.split("_");
		clickOnNameLink(Emails,code,1);
		while(btnNext.syncVisible()==true && validateButtonsEnabledOrDisabled(btnNext)==true){
			btnNext.click();
			A=A+verifyDealerContacts(1);
			Emails=A.split("_");
			clickOnNameLink(Emails,code,1);
		}
	}

	//To get the contact Logo src
	public String getDealerContactLogo(){
		pageLoaded(eleContactLogo);
		TestReporter.assertTrue(eleContactLogo.getAttribute("src")
				.contains(""),
				"Logo is Validated");
		return eleContactLogo.getAttribute("src");
	}

	//Enter data and clicking on Search
	public void clickSearch(String code){
		pageLoaded(btnSearch);
		txtDealerCode.set(code);
		TestReporter.assertTrue(btnSearch.syncEnabled(20, false), "Search button is enabled");
		btnSearch.click();
	}

	//Enter Data and clicking on clear
	public void clickClear(String code){
		pageLoaded(btnClear);
		txtDealerCode.set(code);
		TestReporter.assertTrue(btnClear.syncEnabled(20, false), "Clear button is enabled");
		btnClear.click();
	}
}