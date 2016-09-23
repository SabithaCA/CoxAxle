package com.coxAxle.navigation;

import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.vensaiDriver;

public class MainNav {

	private vensaiDriver driver = null;


	/**Page Elements**/

	@FindBy(xpath = "//a[@title='Logout']") private Button btnLogout;


	/**Constructor**/

	public MainNav(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	public boolean isLogoutDisplayed(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return btnLogout.syncVisible(20, false);
	}

	// Method for Application Logout 
	public void clickLogout(){
		isLogoutDisplayed();
		btnLogout.click();
	}
}
