package com.coxAxle.dealer.Account;

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

public class AccountPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//div[4]/div/button[2]")	private Button btnChangeLogo;
	@FindBy(xpath = "//div[4]/div/button[1]")  private Button btnChangePassword;
	@FindBy(xpath = "//input[@value='Update Account']") private Button btnUpdateAccount;
	@FindBy(xpath = "//div[2]/table/tbody") private Webtable WtAccountDetails;

	/**Constructor**/
	public AccountPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Validate the presence of Buttons on Accounts page
	public void validateDealerAccountButtons(){
		pageLoaded(btnChangeLogo);
		TestReporter.assertTrue(btnUpdateAccount.syncVisible(15, false), "Update account button is visible");
		TestReporter.assertTrue(btnChangePassword.syncVisible(15, false), "Change password button is visible");
		TestReporter.assertTrue(btnChangeLogo.syncVisible(15, false), "Change logo button is visible");

	}
	
	//Method to get the details on account page
	public String[] verifyAccountDetails(){
		pageLoaded(WtAccountDetails);
		String value="";
		String[] table_Values=null;
		List<WebElement> rows_table = WtAccountDetails.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		//System.out.println("tr's in table: "+rows_count);
		for (int row=0; row<rows_count; row++){
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
	
	//Click on Update Account button
	public void clickUpdateAccount(){
		TestReporter.assertTrue(btnUpdateAccount.syncEnabled(20, false), "Update Account button is enabled");
		btnUpdateAccount.click();
	}
	
	//Click on Change Password button
	public void clickChangePassword() throws InterruptedException{
		Thread.sleep(1000);
		driver.get(driver.getCurrentUrl());
		pageLoaded(btnChangePassword);
		TestReporter.assertTrue(btnChangePassword.syncEnabled(20, false), "Change Password button is enabled");
		btnChangePassword.jsClick();
		Thread.sleep(1000);
	}
	
	//Click on Change Logo button
	public void clickChangeLogo(){
		TestReporter.assertTrue(btnChangeLogo.syncEnabled(20, false), "Change Logo button is enabled");
		btnChangeLogo.click();
	}
}
