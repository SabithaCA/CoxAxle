package com.coxAxle.admin;

import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Link;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class SignInPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "email")	private Textbox txtEmail;
	@FindBy(name = "password")	private Textbox txtPassword;
	@FindBy(id = "submitbutton")	private Button btnLogin;
	@FindBy(linkText = "Sign Up")	private Link lnkSignup;
	@FindBy(linkText = "Forgot Password")  private Link lnkForgotPassword;

	/**Constructor**/
	public SignInPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	public void verifyPageIsLoaded(){

	}


	public void login(){
	}

	/**
	 * This method logins to the application.  Note - the location drop down field only
	 * displays if the user has more than 1 location associated to it.  So if the location
	 * drop down does not display, then will not attempt to select a location
	 * @author
	 * @param username
	 * @param location
	 * @param password
	 */
	public void loginWithCredentials(String Email,String Password){

		//System.out.println(userCredentialRepo.getString(Email));
		//userCredentialRepo.getString(Email)
		txtEmail.set(Email);
		//txtPassword.set(userCredentialRepo.getString("PASSWORD"));
		txtPassword.set(Password);
		btnLogin.click();

	}

	//Allowed link names SignUp, Login, Forgot Password
	public void clickLink(String linkText) {
		Element ln = driver.findLink(By.linkText(linkText));
		ln.click();
	}

	public void validateSignInPageFields(){
		//txtEmail.syncVisible();
		TestReporter.assertTrue(txtEmail.syncVisible(10, false), "Email textbox is visible on Sign In page");
		TestReporter.assertTrue(txtPassword.syncVisible(10, false), "Password textbox is visible on Sign In page");
		TestReporter.assertTrue(btnLogin.syncEnabled(10, false), "Sign In button is visible on Sign In page");
		TestReporter.assertTrue(lnkSignup.syncVisible(10, false), "Sign Up link is visible on Sign In page");
		TestReporter.assertTrue(lnkForgotPassword.syncVisible(10, false), "Forgot Password link is visible on Sign In page");

	}
}
