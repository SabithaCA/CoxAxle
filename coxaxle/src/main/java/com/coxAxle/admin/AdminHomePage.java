package com.coxAxle.admin;

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

public class AdminHomePage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(id = "main-menu")	private Element eleMenu;
	@FindBy(linkText = "Home")  private Link lnkHome;
	@FindBy(linkText = "Customers") private Link lnkCustomers;
	@FindBy(linkText = "Dealers") private Link lnkDealers;
	@FindBy(linkText = "Dealers Contact") private Link lnkDealersContact;
	@FindBy(linkText = "Vehicles Inventory") private Link lnkVehiclesInventory;
	@FindBy(linkText = "Banners") private Link lnkBanners;
	@FindBy(name = "passwordcheck") private Textbox txtConfirmPassword;
	@FindBy(id = "submitbutton") private Button btnSubmit;

	/**Constructor**/
	public AdminHomePage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	public void validateMainMenuItems(){
		TestReporter.assertTrue(lnkHome.syncVisible(10, false), "Home menu item is visible");
		TestReporter.assertTrue(lnkCustomers.syncVisible(10, false), "Customers menu item is visible");
		TestReporter.assertTrue(lnkDealers.syncEnabled(10, false), "Dealers menu item is visible");
		TestReporter.assertTrue(lnkDealersContact.syncVisible(10, false), "Dealers Contacts menu item is visible");
		TestReporter.assertTrue(lnkVehiclesInventory.syncVisible(10, false), "Vehicles Inventory menu item is visible");
		TestReporter.assertTrue(lnkBanners.syncVisible(10, false), "Banners menu item is visible");
	}

	public void clickDealersTab(){
		pageLoaded(lnkDealers);
		TestReporter.assertTrue(lnkDealers.syncEnabled(10, false), "Dealers menu item is visible");
		//lnkDealers.highlight(driver);
		lnkDealers.click();
	}
}
