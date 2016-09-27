package com.coxAxle.dealer.Banners;

import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Listbox;
import com.vensai.core.interfaces.Webtable;
import com.vensai.core.interfaces.impl.internal.ElementFactory;
import com.vensai.utils.Constants;
import com.vensai.utils.TestReporter;
import com.vensai.utils.vensaiDriver;

public class BannersPage {

	private vensaiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);

	/**Page Elements**/
	@FindBy(xpath = "//input[@value='Add Banner']")	private Button btnAddBanner;
	@FindBy(xpath = "//input[@value='Search']")  private Button btnSearch;
	@FindBy(xpath = "//div/div/button") private Button btnClear;
	@FindBy(id = "status") private Listbox lstStatus;
	@FindBy(xpath = "//table/tbody") private Webtable wtBannersList;


	/**Constructor**/
	public BannersPage(vensaiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	private void pageLoaded(){
		btnAddBanner.syncVisible(20, false);
	}

	private void pageLoaded(Element ele){
		ele.syncVisible(20, false);
	}

	//Validate the presence of Buttons on Banners page
	public void validateBannerFields(){
		pageLoaded();
		TestReporter.assertTrue(btnAddBanner.syncVisible(15, false), "Add Banners button is visible");
		TestReporter.assertTrue(btnSearch.syncVisible(15, false), "Search button is visible");
		TestReporter.assertTrue(btnClear.syncVisible(15, false), "Clear button is visible");
		List<WebElement> values= lstStatus.getOptions();
		for (WebElement webElement : values) {
			TestReporter.logStep(webElement.getText()+" is visible");
		}
	}

	//Click on Add Banner Button
	public void clickAddBanner(){
		pageLoaded();
		TestReporter.assertTrue(btnAddBanner.syncEnabled(20, false), "Update Account button is enabled");
		btnAddBanner.click();
	}

	//method to get the list count
	public int getBannerListCount(){
		List<WebElement> table_Row = wtBannersList.findElements(By.tagName("tr"));
		//System.out.println("Row count "+table_Row.size());
		return table_Row.size();
	}

	//Method to get the status of specified banner
	public void getStatusOfSpecifiedBanner(String imageName){
		pageLoaded();
		List<WebElement> rows_table = wtBannersList.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			if(Columns_row.get(0).getText().equalsIgnoreCase(imageName)){
				System.out.println("Status of added new banner : "+Columns_row.get(3).findElement(By.tagName("img")).getAttribute("title"));
				break;
			}
		}

	}
}