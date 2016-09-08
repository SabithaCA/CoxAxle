package com.coxAxle.signup;

import java.util.ResourceBundle;

import org.openqa.selenium.support.FindBy;

import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Listbox;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class RegistrationPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "dealer_logo")	private Element eleBrowse;
	@FindBy(name = "name")  private Textbox txtName;
	@FindBy(name = "email") private Textbox txtemail;
	@FindBy(name = "phone") private Textbox txtPhone;
	@FindBy(name = "dealer_code") private Textbox txtCode;
	@FindBy(name = "zipcode") private Textbox txtZipcode;
	@FindBy(name = "password") private Textbox txtPassword;
	@FindBy(name = "passwordcheck") private Textbox txtConfirmPassword;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = "//form/div/div/ul/li") private Element eleAlert;

	/**Constructor**/
	public RegistrationPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}


	public void registerUser() {

		eleBrowse.sendKeys("D:\\PicsDemo\\Jellyfish.jpg");

	}



	public void enterRegistrationDetails(String name, String email, String phone, String code,
			String password, String comfirmPassword, String zipcode ){

		pageLoaded(txtName);
		txtName.set(name);

		pageLoaded(txtemail);
		txtemail.set(email);

		pageLoaded(txtPhone);
		txtPhone.set(phone);

		pageLoaded(txtCode);
		txtCode.set(code);


		pageLoaded(txtZipcode);
		txtZipcode.set(zipcode);

		pageLoaded(txtPassword);
		txtPassword.set(password);

		pageLoaded(txtConfirmPassword);
		txtConfirmPassword.set(comfirmPassword);

		pageLoaded(btnSubmit);
		btnSubmit.click();
	}

	
	public void verifyActivateAccountAlert(String alertMsg){
		
		pageLoaded(eleAlert);
		boolean status = eleAlert.getText().trim().equalsIgnoreCase(alertMsg.trim()); 
		TestReporter.assertTrue(status, "Activate account message is validated");
		
	}
}
