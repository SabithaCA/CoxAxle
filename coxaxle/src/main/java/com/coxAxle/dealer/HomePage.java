package com.coxAxle.dealer;

import java.util.ResourceBundle;
import org.openqa.selenium.WebElement;
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
	@FindBy(linkText = "Account Details") private Link lnkAccount;
	@FindBy(linkText = "Contact Details") private Link lnkContacts;
	//@FindBy(linkText = "Vehicles Inventory") private Link lnkVehicles;
	@FindBy(linkText = "Banners") private Link lnkBanners;
	@FindBy(linkText = "Notifications") private Link lnkNotifications;
	@FindBy(name = "passwordcheck") private Textbox txtConfirmPassword;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = "//div[@class='header-right']/img") private WebElement eleImage;

	/**Constructor**/
	public HomePage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	// Validate Home page menu items
	public void validateMainMenuItems(){
		TestReporter.assertTrue(lnkHome.syncVisible(10, false), "Home menu item is visible");
		TestReporter.assertTrue(lnkCustomers.syncVisible(10, false), "Customers menu item is visible");
		TestReporter.assertTrue(lnkAccount.syncEnabled(10, false), "Account menu item is visible");
		TestReporter.assertTrue(lnkContacts.syncVisible(10, false), "Contacts menu item is visible");
		//	TestReporter.assertTrue(lnkVehicles.syncVisible(10, false), "Vehicles menu item is visible");
		TestReporter.assertTrue(lnkBanners.syncVisible(10, false), "Banners menu item is visible");
	}

	//Click Account tab
	public void clickAccountTab(){
		pageLoaded(lnkAccount);
		TestReporter.assertTrue(lnkAccount.syncEnabled(10, false), "Account menu item is visible");
		//lnkAccount.highlight(driver);
		lnkAccount.click();
	}

	//Click Contacts tab
	public void clickContactsTab(){
		pageLoaded(lnkContacts);
		TestReporter.assertTrue(lnkContacts.syncEnabled(10, false), "Contacts menu item is visible");
		lnkContacts.click();
	}

	/*public void clickVehiclesInventoryTab(){
		pageLoaded(lnkVehicles);
		TestReporter.assertTrue(lnkVehicles.syncEnabled(10, false), "Vehicle Inventory menu item is visible");
		lnkVehicles.click();
	}*/

	//Click Banners tab
	public void clickBannersTab(){
		pageLoaded(lnkBanners);
		TestReporter.assertTrue(lnkBanners.syncEnabled(10, false), "Banners menu item is visible");
		lnkBanners.click();
	}

	//Click Customers tab
	public void clickCustomersTab(){
		pageLoaded(lnkCustomers);
		TestReporter.assertTrue(lnkCustomers.syncEnabled(10, false), "Customers menu item is visible");
		lnkCustomers.click();
	}

	//Click Notifications tab
	public void clickNotificationsTab(){
		pageLoaded(lnkNotifications);
		TestReporter.assertTrue(lnkNotifications.syncEnabled(10, false), "Notifications menu item is visible");
		lnkNotifications.click();
	}

	//Getting the Image source
	public String getImagesource(){
		System.out.println("Image source : "+eleImage.getAttribute("src"));
		String source = eleImage.getAttribute("src");
		return source;
	}
}
