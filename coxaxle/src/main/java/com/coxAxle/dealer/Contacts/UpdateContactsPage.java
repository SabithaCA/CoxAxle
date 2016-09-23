package com.coxAxle.dealer.Contacts;

import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Textbox;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class UpdateContactsPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(id = "main_contact_number")	private Textbox txtMainContact;
	@FindBy(id = "sale_contact")  private Textbox txtSaleContact;
	@FindBy(id = "service_desk_contact") private Textbox txtServicedeskContact;
	@FindBy(id = "collision_desk_contact") private Textbox txtCollisiondeskContact;
	@FindBy(id = "web_link") private Textbox txtWebLink;
	@FindBy(id = "submitbutton") private Button btnSubmit;
	@FindBy(xpath = "//input[@value = 'CANCEL']") private Button btnCancel;
	@FindBy(xpath = ".//*[@id='form1']/div[1]/div/div/div//*[@type='text']") private List<WebElement> UpadteContactInfo;


	/**Constructor**/
	public UpdateContactsPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnCancel.syncVisible(20, false);
	}

	//Validate the presence of Buttons on Accounts page
	public void validateDealerContactFields(){
		String[] contactFields={"main_contact_number","sale_contact","service_desk_contact",
				"collision_desk_contact","web_link"};
		List<WebElement> list=UpadteContactInfo;
		for (int i = 0; i < contactFields.length; ) {
			for (WebElement webElement : list) {
				TestReporter.assertTrue(webElement.getAttribute("name").contains(contactFields[i]), 
						webElement.getAttribute("name")+" : is present on the Update Contacts page");
				i++;
			}	
			break;
		}
	}

	public void enterUpdateContactData(String mainContact,String salesContact,String serviceDeskContact,
			String collisionDeskContact,String webLink){
		String[] contactFields={mainContact,salesContact,serviceDeskContact,
				collisionDeskContact,webLink};
		List<WebElement> list=UpadteContactInfo;
		for (int i = 0; i < contactFields.length; ) {
			for (WebElement webElement : list) {
				webElement.clear();
				webElement.sendKeys(contactFields[i]);
				System.out.println("**************************");
				i++;
			}
			System.out.println("((((((((((((((((");
			break;
		}
	}

	public void clickSubmit(){
		TestReporter.assertTrue(btnSubmit.syncEnabled(10, false), "Submit button is visible");
		btnSubmit.click();
	}

	public void clickCancel(){
		TestReporter.assertTrue(btnCancel.syncEnabled(10, false), "Cancel button is visible");
		btnCancel.click();
	}

	/*public String[] getUpdateContactDetails(){
		String value="";
		String[] contactFields=null;
		List<WebElement> list=UpadteContactInfo;
		for (int i = 0; i < list.size(); ) {
			for (WebElement webElement : list) {
				value=value+webElement.getAttribute("value")+"_";
				i++;
			}
			break;
		}
		contactFields=value.split("_");
		return contactFields;
	}*/


}

