package com.coxAxle.dealer.Banners;

import java.util.ResourceBundle;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class BannerDetailsPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//table/tbody") private Webtable wtBannersList;
	@FindBy(xpath = "//p/button")  private Button btnChangeBanner;
	@FindBy(xpath = "//table/tbody/tr/td/img") private WebElement eleImage;

	/**Constructor**/
	public BannerDetailsPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnChangeBanner.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Click on Add Banner Button
	public void clickChangeBanner(){
		driver.get(driver.getCurrentUrl());
		pageLoaded();
		TestReporter.assertTrue(btnChangeBanner.syncEnabled(20, false), "Change Banner button is enabled");
		btnChangeBanner.jsClick();
	}
	
	public String getImagesource(){
		System.out.println("Image source : "+eleImage.getAttribute("src"));
		String source = eleImage.getAttribute("src");
		return source;
	}
}