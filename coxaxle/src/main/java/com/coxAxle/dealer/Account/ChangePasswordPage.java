package com.coxAxle.dealer.Account;

import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class ChangePasswordPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "password")	private Textbox txtPassword;
	@FindBy(name = "passwordcheck")  private Textbox txtPasswordConfirm;
	//@FindBy(id = "submitbutton") private Button btnSubmit;
	// value="Submit"
	@FindBy(xpath = "//*[@id='form']/div[@class='modal-footer']/input[@type='submit']") private Button btnSubmit;
	@FindBy(xpath = "//*[@id='form']/div[@class='modal-footer']/button[@type='button']") private Button btnCancel;

	/**Constructor**/
	public ChangePasswordPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	// Validate the presence of Fields on Change password page
	public void validateChangePasswordPageFields(){
		TestReporter.assertTrue(txtPassword.syncVisible(10, false),"Password field is visible on Change Password page");
		TestReporter.assertTrue(txtPasswordConfirm.syncVisible(10, false),"Password Confirmation field is visible on Change Password page");
		TestReporter.assertTrue(btnSubmit.isDisplayed(),"Submit button is visible on Change Password page");
		TestReporter.assertTrue(btnCancel.syncVisible(15, false),"Cancel button is visible on Change Password page");
		
	}
	
	//Validate enable and Click on Submit button
	public void clickSubmit(){
		pageLoaded(btnSubmit);
		TestReporter.assertTrue(btnSubmit.syncEnabled(20, false), "Submit button is enabled");
		btnSubmit.click();
	}
	
	//Validate enable and Click on Cancel button
	public void clickCancel(){
		pageLoaded(btnCancel);
		TestReporter.assertTrue(btnCancel.syncEnabled(20, false), "Cancel button is enabled");
		btnCancel.click();
	}

	//Enter the new password to change the password
	public void enterPasswords(String changePassword){
		//txtPassword.clear();
		txtPassword.set(changePassword);
		//txtPasswordConfirm.clear();
		txtPasswordConfirm.set(changePassword);	
	}
	
}
