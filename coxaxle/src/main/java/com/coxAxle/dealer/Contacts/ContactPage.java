package com.coxAxle.dealer.Contacts;

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

public class ContactPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//div[1]/div[2]/button")	private Button btnChangeMobileLogo;
	@FindBy(xpath = "//input[@value='Update Profile']")  private Button btnUpdateContacts;
	@FindBy(xpath = "//table/tbody") private Webtable WtContactDetails;
	@FindBy(xpath = "//div[4]/div[2]/div[1]/div/p[2]/img") private Element eleContactLogo;

	/**Constructor**/
	public ContactPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnChangeMobileLogo.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Validate the presence of Buttons on Accounts page
	public void validateDealerContactButtons(){
		pageLoaded();
		TestReporter.assertTrue(btnUpdateContacts.syncVisible(15, false), "Update Contacts button is visible");
		TestReporter.assertTrue(btnChangeMobileLogo.syncVisible(15, false), "Change Mobile Logo button is visible");
	}

	//Click on Change Mobile Logo button
	public void clickChangeMobileLogo(){
		driver.get(driver.getCurrentUrl());
		pageLoaded();
		TestReporter.assertTrue(btnChangeMobileLogo.syncEnabled(20, false), "Change Mobile Logo button is enabled");
		btnChangeMobileLogo.click();
	}

	//Click on Update Contacts button
	public void clickUpdateContacts(){
		TestReporter.assertTrue(btnUpdateContacts.syncEnabled(20, false), "Update Contacts button is enabled");
		btnUpdateContacts.click();
	}

	//Method to get contact details
	public  String[] getContactDetails(){
		pageLoaded();
		String value="";
		String[] table_Values=null;
		List<WebElement> rows_table = WtContactDetails.findElements(By.tagName("tr"));
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

	//Method to get src of Contact logo
	public String getContactLogo(){
		pageLoaded(eleContactLogo);
		TestReporter.assertTrue(eleContactLogo.getAttribute("src")
				.contains(""),
				"Logo is Validated");
		return eleContactLogo.getAttribute("src");
	}
}