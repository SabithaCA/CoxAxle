package com.coxAxle.dealer.Feedback;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class FeedbackDetailsPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//table/tbody") private Webtable wtCustomerDetails;

	/**Constructor**/
	public FeedbackDetailsPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	//Method to get the Customer Details
	public void getCustomerDetails(){
		String[]  details= null;
		String data="";
		List<WebElement> rows_table = wtCustomerDetails.findElements(By.tagName("tr"));
		for (int row = 0; row <rows_table.size(); row++) {
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			for (WebElement webElement : Columns_row) {
				//System.out.println(row+" row values are "+webElement.getText());
				data = data+webElement.getText()+" ";

			}
			data=data+"_";
		}
		details=data.split("_");
		TestReporter.logStep("Customer details : "+"\n"+Arrays.toString(details));
	}

}