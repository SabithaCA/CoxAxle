package com.coxAxle.dealer.Banners;

import java.util.ResourceBundle;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class NewBannerPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "banner_name")	private Textbox txtBannerName;
	@FindBy(name = "banner_image")  private Button btnBannerImage;
	@FindBy(xpath = "//input[@value='Submit']") private Button btnSubmit;
	@FindBy(xpath = "//input[@value='CANCEL']") private Button btnCancel;

	/**Constructor**/
	public NewBannerPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnBannerImage.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Validate the presence of Buttons on Banners page
	public void validateNewBannerFields(){
		pageLoaded();
		TestReporter.assertTrue(txtBannerName.syncVisible(15, false), "Banner Name textbox is visible");
		TestReporter.assertTrue(btnBannerImage.syncVisible(15, false), "Banner Image button is visible");
		TestReporter.assertTrue(btnSubmit.syncVisible(15, false), "Submit button is visible");
		TestReporter.assertTrue(btnCancel.syncVisible(15, false), "Cancel button is visible");
	}

	//Click on Submit Button
	public void clickSubmit(){
		pageLoaded();
		TestReporter.assertTrue(btnSubmit.syncEnabled(20, false), "Submit button is enabled");
		btnSubmit.click();
	}

	//Click on Cancel Button
	public void clickCancel(){
		pageLoaded();
		TestReporter.assertTrue(btnCancel.syncEnabled(20, false), "Cancel button is enabled");
		btnCancel.click();
	}

	//method to enter the Add New Banner information
	public void enterAddBannerFieldsInfo(String imageName,String imagePath){
		txtBannerName.set(imageName);
		btnBannerImage.sendKeys(imagePath);
	}
}
