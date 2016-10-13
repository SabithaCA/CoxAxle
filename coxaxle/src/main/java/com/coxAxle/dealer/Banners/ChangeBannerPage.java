package com.coxAxle.dealer.Banners;

import java.util.ResourceBundle;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class ChangeBannerPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "banner_image") private Textbox txtBannerImage;
	@FindBy(name = "banner_name")  private Textbox txtBannerName;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = "//form/div[3]/button") private Button btnCancel;

	/**Constructor**/
	public ChangeBannerPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		txtBannerImage.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}
	
	public void validateChangeBannerFields(){
		TestReporter.assertTrue(txtBannerImage.syncEnabled(15, false), "Browse image button is enabled");
		TestReporter.assertTrue(txtBannerName.syncVisible(15, false), "Banner Name field is visible");
		TestReporter.assertTrue(btnSubmit.syncEnabled(15, false), "Submit button is enabled");
		TestReporter.assertTrue(btnCancel.syncEnabled(15, false), "Cancel button is enabled");
	}
	
	//Click on Change Logo button
	public void clickBrowseAndUploadImage(String imagePath, String imageName){
		pageLoaded(txtBannerImage);
		txtBannerName.set(imageName);
		TestReporter.assertTrue(txtBannerImage.syncEnabled(15, false), "Browse image button is enabled");
		//btnBrowse.click();
		txtBannerImage.sendKeys(imagePath);
	}

	//Click Submit
	public void clickSubmit() {
		TestReporter.assertTrue(btnSubmit.syncEnabled(10, false), "Submit button is visible");
		btnSubmit.click();
	}

	//Click Cancel
	public void clickCancel(){
		TestReporter.assertTrue(btnCancel.syncEnabled(10, false), "Cancel button is visible");
		btnCancel.click();
	}
}