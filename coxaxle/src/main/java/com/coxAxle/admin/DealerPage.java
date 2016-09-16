package com.coxAxle.admin;

import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class DealerPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	/*ArrayList<List<String>> values;
	ArrayList<List<String>> al=new ArrayList<List<String>>();*/

	/**Page Elements**/
	@FindBy(xpath = "//table/tbody")	private Webtable wtDealerTabel;
	@FindBy(linkText = "Next")  private Button btnNext;
	@FindBy(xpath = "//td[3]/table/tbody") private Webtable wtDealerDetails;
	
	/**Constructor**/
	public DealerPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	public String verifyDealerDetail(){
		String value="";
		//String[] table_Values=null;
		List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		//System.out.println("tr's in table: "+rows_count);
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			String celtext = Columns_row.get(3).getText();
			//System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
			value=value+celtext+"_";
			//table_Values=value.split("_");
		}
		return value;
	}

	private boolean validateButtonsEnabledOrDisabled(Element locatorName) {
		boolean isEnabled = true;
		pageLoaded( locatorName);
		// Verifying Button status
		if (locatorName.getAttribute("href").contains("#")) {
			isEnabled = false;
		}
		if (isEnabled == true) {
			// Validating enabled button
			TestReporter.assertTrue(isEnabled,
					locatorName.getElementIdentifier() + " button is enabled");
		} else {
			// Validating Disabled button
			TestReporter.assertFalse(isEnabled,
					locatorName.getElementIdentifier() + " button is disabled");
		}
		return isEnabled;
	}


	public  void clickOnSpecifiedDealer(String Email){
		String[] Emails=null;
		String A=verifyDealerDetail();

		while(validateButtonsEnabledOrDisabled(btnNext)==true){
			btnNext.click();
			A=A+verifyDealerDetail();

			Emails=A.split("_");
			for (String string : Emails) {
				if(string.equalsIgnoreCase(Email)){
					System.out.println(string);
					TestReporter.assertTrue(string.equalsIgnoreCase(Email),"Email Id is verified");
					for (int row=0; row<Emails.length; row++){
						List<WebElement> rows_table = wtDealerTabel.findElements(By.tagName("tr"));
						List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
						List<WebElement> Columns_row_link = rows_table.get(row).findElements(By.tagName("a"));
						if(Columns_row.get(3).getText().equalsIgnoreCase(Email)){
							System.out.println("gng inside loop");
							Columns_row_link.get(0).click();
							break;

						}
						else{

						}
					}	
				}
			}break;	
		}
	}
	
	public String[] verifyDealerDetails(){
		String value="";
		String[] table_Values=null;
		List<WebElement> rows_table = wtDealerDetails.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		//System.out.println("tr's in table: "+rows_count);
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			//System.out.println("Number of cells In Row "+row+" are "+columns_count);

			for (int column=0; column<columns_count; column++){
				String celtext = Columns_row.get(column).getText();
				//System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
				value=value+celtext+" ";
			}
			value=value+"_";
			//System.out.println("*********** "+value);
			table_Values=value.split("_");
			//System.out.println("--------------------------------------------------");
		}
		/*for (int i = 0; i < table_Values.length; i++) {
			System.out.println("Values : "+table_Values[i]);
		}*/
		//return Arrays.asList(table_Values);
		return table_Values;
	}


}
