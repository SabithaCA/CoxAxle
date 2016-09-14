package com.coxAxle.dealer;

import java.util.ResourceBundle;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
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

	/**Constructor**/
	public AccountPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	
	public void validateDealerAccountButtons(){
		pageLoaded(btnChangeLogo);
		TestReporter.assertTrue(btnUpdateAccount.syncVisible(15, false), "Update account button is visible");
		TestReporter.assertTrue(btnChangePassword.syncVisible(15, false), "Change password button is visible");
		TestReporter.assertTrue(btnChangeLogo.syncVisible(15, false), "Change logo button is visible");
		
	}
	
	
}
