package com.coxAxle.login;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Link;
import com.vensai.core.interfaces.Listbox;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.vensaiDriver;

public class LoginPage {
	
	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	/**Page Elements**/
	@FindBy(name = "email")	private Textbox txtEmail;
	@FindBy(name = "password")	private Textbox txtPassword;
	@FindBy(id = "submitbutton")	private Button btnLogin;
	@FindBy(linkText = "SignUp")	private Link lnkSignup;
	
	/**Constructor**/
	public LoginPage(vensaiDriver driver){
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
	public void loginWithCredentials(String Email){
		
		//System.out.println(userCredentialRepo.getString(Email));
		//userCredentialRepo.getString(Email)
		txtEmail.set(Email);
		txtPassword.set(userCredentialRepo.getString("PASSWORD"));
		btnLogin.click();

	}
	
	//Allowed link names SignUp, Login, Forgot Password
	public void clickLink(String linkText) {
		Element ln = driver.findLink(By.linkText(linkText));
		ln.click();
	}
	
	
}
