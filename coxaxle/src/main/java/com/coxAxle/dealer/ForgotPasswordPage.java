package com.coxAxle.dealer;

import java.util.ResourceBundle;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class ForgotPasswordPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "email")	private Textbox txtEmail;
	@FindBy(name = "Submit")  private Button btnSubmit;

	/**Constructor**/
	public ForgotPasswordPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnSubmit.syncVisible(20, false);
	}

	// Validate Forgot Password Fields
	public void validateForgotPasswordFields(){
		TestReporter.assertTrue(txtEmail.syncVisible(10, false), "Email textbox is visible");
		TestReporter.assertTrue(btnSubmit.syncEnabled(20, false), "Submit button is enabled");
	}

	//Enter Email and Click Submit button
	public void enterEmailAndClickSubmit(String email) throws InterruptedException{
		pageLoaded();
		txtEmail.set(email);
		btnSubmit.click();
		Thread.sleep(2000);
	}
}
