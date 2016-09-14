package com.coxAxle.dealer;

import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Label;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class RegistrationPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "name")  private Textbox txtName;
	@FindBy(name = "email") private Textbox txtemail;
	@FindBy(name = "phone") private Textbox txtPhone;
	@FindBy(name = "dealer_code") private Textbox txtDealerCode;
	@FindBy(name = "zipcode") private Textbox txtZipCode;
	@FindBy(name = "address") private Textbox txtAddress;
	@FindBy(name = "dealer_logo") private Button btnDealerLogo;
	@FindBy(name = "password") private Textbox txtPassword;
	@FindBy(name = "passwordcheck") private Textbox txtConfirmPassword;
	@FindBy(name = "dealer_twiter_page_link") private Textbox txtTwitter;
	@FindBy(name = "dealer_fb_page_link") private Textbox txtFB;
	@FindBy(xpath = "//input[@value='CANCEL']") private Button btnCancel;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = "//form/div/div/ul/li") private Element eleAlert;
	@FindBy(xpath = "//form/div[1]/div/div[1]/div/div") private Element eleDealerNameMandatory;
	@FindBy(xpath = "//form/div[1]/div/div[2]/div[1]/div") private Element eleEmailMandatory;
	@FindBy(xpath = "//form/div[1]/div/div[3]/div/div") private Element elePhoneMandatory;
	@FindBy(xpath = "//form/div[1]/div/div[4]/div/div") private Element eleDealerCodeMandatory;
	@FindBy(xpath = "//form/div[1]/div/div[8]/div[1]/div") private Element elePwdMandatory;
	@FindBy(xpath = "//form/div[1]/div/div[9]/div/div[1]") private Element eleConfirmPwdMandatory;


	/**Constructor**/
	public RegistrationPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	private void pageLoaded(){
		btnDealerLogo.syncVisible(20, false);
	}

	public void registerUser() {

		btnDealerLogo.sendKeys("D:\\PicsDemo\\Jellyfish.jpg");

	}

	public void enterRegistrationDetails(String name, String email, String phone, String code,
			String password, String comfirmPassword, String zipcode ){

		pageLoaded();
		txtName.set(name);
		txtemail.set(email);
		txtPhone.set(phone);
		txtDealerCode.set(code);
		txtZipCode.set(zipcode);
		txtPassword.set(password);
		txtConfirmPassword.set(comfirmPassword);	
	}

	public void clickSubmit(){
		pageLoaded(btnSubmit);
		btnSubmit.click();
	}

	public void clickCancel(){
		pageLoaded(btnCancel);
		btnCancel.click();
	}


	public void verifyActivateAccountAlert(String alertMsg){
		try{
			pageLoaded(eleAlert);
			boolean status = eleAlert.getText().trim().equalsIgnoreCase(alertMsg.trim()); 
			TestReporter.assertTrue(status, "Activate account message is validated");
		}
		catch(Exception e){
			TestReporter.logStep("Error occured while creating the dealer account");
		}
	}

	public void validatingSignUpFields(){
		TestReporter.assertTrue(txtName.syncVisible(10, false),"Dealer name field is visible on SignUp page");
		TestReporter.assertTrue(txtemail.syncVisible(10, false),"Email field is visible on SignUp page");
		TestReporter.assertTrue(txtPhone.syncVisible(10, false),"Phone field is visible on SignUp page");
		TestReporter.assertTrue(txtDealerCode.syncVisible(10, false),"Dealer Code field is visible on SignUp page");
		TestReporter.assertTrue(txtZipCode.syncVisible(10, false),"Zip Code field is visible on SignUp page");
		TestReporter.assertTrue(txtAddress.syncVisible(10, false),"Address field is visible on SignUp page");
		TestReporter.assertTrue(btnDealerLogo.syncVisible(10, false),"Dealer Logo field is visible on SignUp page");
		TestReporter.assertTrue(txtPassword.syncVisible(10, false),"Password field is visible on SignUp page");
		TestReporter.assertTrue(txtConfirmPassword.syncVisible(10, false),"Confirm Password field is visible on SignUp page");
		TestReporter.assertTrue(txtTwitter.syncVisible(10, false),"Dealer Twitter Page Link  field is visible on SignUp page");
		TestReporter.assertTrue(txtFB.syncVisible(10, false),"Dealer FB Page Link  field is visible on SignUp page");
	}

	public void validateMandatoryFields(){
		TestReporter.assertEquals(eleDealerNameMandatory.getText(), "*", "Dealer name field is mandatory");
		TestReporter.assertEquals(eleEmailMandatory.getText(), "*", "Email field is mandatory");
		TestReporter.assertEquals(elePhoneMandatory.getText(), "*", "Phone field is mandatory");
		TestReporter.assertEquals(eleDealerCodeMandatory.getText(), "*", "Dealer code field is mandatory");
		TestReporter.assertEquals(elePwdMandatory.getText(), "*", "Password field is mandatory");
		TestReporter.assertEquals(eleConfirmPwdMandatory.getText(), "*", "Confirm Password field is mandatory");
	}
}