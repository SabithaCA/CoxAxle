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

public class UpdateAccountPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(name = "name")  private Textbox txtName;
	@FindBy(name = "email") private Textbox txtemail;
	@FindBy(name = "phone") private Textbox txtPhone;
	@FindBy(name = "dealer_code") private Textbox txtDealerCode;
	@FindBy(name = "zipcode") private Textbox txtZipCode;
	@FindBy(name = "address") private Textbox txtAddress;
	@FindBy(name = "dealer_twiter_page_link") private Textbox txtTwitter;
	@FindBy(name = "dealer_fb_page_link") private Textbox txtFB;
	@FindBy(xpath = "//input[@value='CANCEL']") private Button btnCancel;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = ".//*[@id='form1']/div[1]/div/div/div") private Webtable wtUpdateAccountInfo;
	@FindBy(id = "form1") private Webtable wtUpadateAccount;
	@FindBy(linkText = "Contact Information") private Button btnContactInfo;
	@FindBy(linkText  = "Working Hours") private Button btnWorkingHours;


	/**Constructor**/
	public UpdateAccountPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}	

	public void validateUpdateAccountFields(){
		TestReporter.assertTrue(txtName.syncVisible(10, false),"Dealer name field is visible on Upadte Account page");
		//TestReporter.assertTrue(txtemail.syncVisible(10, false),"Email field is visible on Upadte Account page");
		//TestReporter.assertTrue(txtPhone.syncVisible(10, false),"Phone field is visible on Upadte Account page");
		TestReporter.assertTrue(txtZipCode.syncVisible(10, false), "Zipcode field is visible on update account page" );
		TestReporter.assertTrue(txtDealerCode.syncVisible(10, false),"Dealer Code field is visible on Upadte Account page");
		TestReporter.assertTrue(txtAddress.syncVisible(10, false),"Address field is visible on Upadte Account page");
		//TestReporter.assertTrue(txtTwitter.syncVisible(10, false),"Dealer Twitter Page Link  field is visible on Upadte Account page");
		//TestReporter.assertTrue(txtFB.syncVisible(10, false),"Dealer FB Page Link  field is visible on Upadte Account page");
	}


	public String[] getUpdateAccountFieldsInformation(){
		/*String value=txtName.getText()+"_"+txtemail.getText()+"_"+txtPhone.getText()+"_"
				+txtDealerCode.getText()+"_"+txtAddress.getText()+"_"+txtTwitter.getText()+"_"+txtFB.getText();*/
		String value=txtName.getText()+"_"+txtZipCode.getText()+"_"
				+txtDealerCode.getText()+"_"+txtAddress.getText()+"_";
		btnContactInfo.click();
		value=value+txtemail.getText()+"_"+txtPhone.getText();
		btnWorkingHours.click();
		String[] fieldValues=value.split("_");
		for (String string : fieldValues) {
			System.out.println(string);
		}
		return fieldValues;
	}

	public void clickCancel(){
		TestReporter.assertTrue(btnCancel.syncEnabled(10, false), "Cancel button is visible");
		btnCancel.click();
	}

	public void enterDetailsToUpdate(String name, String email, String phone, String dealerCode,
			String address,String ziopCode ){

		//pageLoaded(btnSubmit);
		txtName.set(name);
		//txtemail.set(email);
		//txtPhone.set(phone);
		txtDealerCode.set(dealerCode);
		txtZipCode.set(ziopCode);
		txtAddress.set(address);
		
		btnContactInfo.click();
		txtemail.set(email);
		txtPhone.set(phone);
		btnWorkingHours.click();
		
	}

	public void clickSubmit(){
		TestReporter.assertTrue(btnSubmit.syncEnabled(10, false), "Cancel button is visible");
		btnSubmit.click();
	}

}
