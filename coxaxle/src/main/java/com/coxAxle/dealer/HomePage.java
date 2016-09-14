package com.coxAxle.dealer;

import java.util.ResourceBundle;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Link;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class HomePage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(id = "main-menu")	private Element eleMenu;
	@FindBy(linkText = "Home")  private Link lnkHome;
	@FindBy(linkText = "Customers") private Link lnkCustomers;
	@FindBy(linkText = "Account") private Link lnkAccount;
	@FindBy(linkText = "Contacts") private Link lnkContacts;
	@FindBy(linkText = "Vehicles") private Link lnkVehicles;
	@FindBy(linkText = "Banners") private Link lnkBanners;
	@FindBy(name = "passwordcheck") private Textbox txtConfirmPassword;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = "//form/div/div/ul/li") private Element eleAlert;

	/**Constructor**/
	public HomePage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}
	
	public void validateMainMenuItems(){
		TestReporter.assertTrue(lnkHome.syncVisible(10, false), "Home menu item is visible");
		TestReporter.assertTrue(lnkCustomers.syncVisible(10, false), "Customers menu item is visible");
		TestReporter.assertTrue(lnkAccount.syncEnabled(10, false), "Account menu item is visible");
		TestReporter.assertTrue(lnkContacts.syncVisible(10, false), "Contacts menu item is visible");
		TestReporter.assertTrue(lnkVehicles.syncVisible(10, false), "Vehicles menu item is visible");
		TestReporter.assertTrue(lnkBanners.syncVisible(10, false), "Banners menu item is visible");
	}

	public void verifyActivateAccountAlert(String alertMsg){

		pageLoaded(eleAlert);
		boolean status = eleAlert.getText().trim().equalsIgnoreCase(alertMsg.trim()); 
		TestReporter.assertTrue(status, "Activate account message is validated");

	}
	
	public void clickAccountTab(){
		pageLoaded(lnkAccount);
		TestReporter.assertTrue(lnkAccount.syncEnabled(10, false), "Account menu item is visible");
		//lnkAccount.highlight(driver);
		lnkAccount.click();
	}
}
