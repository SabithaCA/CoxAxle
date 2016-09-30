package com.coxAxle.dealer.Contacts;

import java.util.ResourceBundle;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class ChangeMobileLogoPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(id = "dealer_logo")	private Button btnBrowse;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = "//form/div[3]/button") private Button btnCancel;
	@FindBy(xpath = "//form/div[2]/div/div/div/div/div/div/input[2]") private Element eleLogoName;

	/**Constructor**/
	public ChangeMobileLogoPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Click on Change Logo button
	public void clickBrowseAndUploadImage(String imagePath){
		pageLoaded(btnBrowse);
		TestReporter.assertTrue(btnBrowse.syncEnabled(15, false), "Browse button is enabled");
		//btnBrowse.click();
		btnBrowse.sendKeys(imagePath);
	}

	//Click Submit
	public void clickSubmit() throws InterruptedException{
		TestReporter.assertTrue(btnSubmit.syncEnabled(10, false), "Submit button is visible");
		btnSubmit.click();
		Thread.sleep(2000);
	}

	//Click Cancel
	public void clickCancel(){
		TestReporter.assertTrue(btnCancel.syncEnabled(10, false), "Cancel button is visible");
		btnCancel.click();
	}

}
