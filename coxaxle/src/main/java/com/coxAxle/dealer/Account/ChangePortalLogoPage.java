package com.coxAxle.dealer.Account;

import java.util.ResourceBundle;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class ChangePortalLogoPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "dealer_logo")	private Button btnDealerLogo;
	@FindBy(xpath = ".//*[@id='myModal']/div/div/form/div[3]/input") private Button btnSubmit;
	@FindBy(xpath = ".//*[@id='myModal']/div/div/form/div[3]/button") private Button btnCancel;

	/**Constructor**/
	public ChangePortalLogoPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//method to upload image
	public void uploadingImage(String imagepath){
		pageLoaded(btnDealerLogo);
		TestReporter.assertTrue(btnDealerLogo.syncEnabled(20, false), "Browse button is enabled");
		btnDealerLogo.sendKeys(imagepath);

	}

	//Clicking on Submit
	public void clickSubmit(){
		pageLoaded(btnSubmit);
		TestReporter.assertTrue(btnSubmit.syncEnabled(20, false), "Submit button is enabled");
		btnSubmit.click();
	}

	//Clicking on Cancel
	public void clickCancel(){
		pageLoaded(btnCancel);
		TestReporter.assertTrue(btnCancel.syncEnabled(20, false), "Cancel button is enabled");
		btnCancel.click();
	}

}
