package com.coxAxle.admin;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.vensai.core.interfaces.Button;
import com.vensai.core.interfaces.Element;
import com.vensai.core.interfaces.Listbox;
import com.vensai.core.interfaces.Textbox;
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
	@FindBy(linkText = "NEXT")  private Button btnNext;
	@FindBy(id = "dealercode") private Textbox txtDealerCode;
	@FindBy(xpath = "//div[2]/table/tbody") private Webtable wtBannerInformation;
	@FindBy(linkText = "Change Banner") private Button btnChangeBanner;
	@FindBy(xpath = "//tr[1]/td[5]/img") private Button btnStatus;


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
		TestReporter.assertTrue(txtDealerCode.syncVisible(15, false), "Add Banners button is visible");
		TestReporter.assertTrue(btnSearch.syncVisible(15, false), "Search button is visible");
		TestReporter.assertTrue(btnClear.syncVisible(15, false), "Clear button is visible");
		List<WebElement> values= lstStatus.getOptions();
		for (WebElement webElement : values) {
			TestReporter.logStep(webElement.getText()+" is visible");
		}
	}

	//Verify display of Add Banner Button
	public void verifyAddBannerDisplayed(){
		pageLoaded();
		TestReporter.assertFalse(btnAddBanner.syncEnabled(20, false), "Add Banner button is not visible");
		//btnAddBanner.click();
	}

	//Verify display of Change Banner Button
	public void verifyChangeBannerDisplayed(){
		TestReporter.assertFalse(btnChangeBanner.syncEnabled(20, false), "Change Banner button is not visible");
		//btnAddBanner.click();
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
				TestReporter.log("Status of added new banner : "+Columns_row.get(4).findElement(By.tagName("img")).getAttribute("title"));
				break;
			}
		}
	}

	//Clicking on Specified banner by checking with next pages
	public  void clickOnSpecifiedBanner(String imageName) {
		clickOnSpecifiedBannerName(imageName);
		//Thread.sleep(2000);
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			clickOnSpecifiedBannerName(imageName);
			i++;
		} 
	}

	//Click on Specified Banner
	public void clickOnSpecifiedBannerName(String imageName){
		pageLoaded();
		List<WebElement> rows_table = wtBannersList.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		for (int row=0; row<rows_count; row++){
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			if(Columns_row.get(0).getText().equalsIgnoreCase(imageName)){
				Columns_row.get(0).findElement(By.tagName("a")).click();
				break;
			}
		}
	}

	//Method to get the Banner Details
	public void getBannerDetails(){
		pageLoaded();
		String[]  details= null;
		String data="";
		List<WebElement> rows_table = wtBannerInformation.findElements(By.tagName("tr"));
		for (int row = 0; row <rows_table.size(); row++) {
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			for (WebElement webElement : Columns_row) {
				//System.out.println(row+" row values are "+webElement.getText());
				data = data+webElement.getText()+" ";

			}
			data=data+"_";
		}
		details=data.split("_");
		System.out.println(Arrays.toString(details));
	}

	//Checking the status display
	public void checkStatusDisplay(){
		pageLoaded(btnStatus);
		TestReporter.assertEquals(btnStatus.getAttribute("onclick"), null, "Status images are disabled for admin");
	}

	//Enter data and clicking on Search
	public void clickSearch(String code){
		pageLoaded(btnSearch);
		txtDealerCode.set(code);
		TestReporter.assertTrue(btnSearch.syncEnabled(20, false), "Search button is enabled");
		btnSearch.click();
	}

	//Enter Data and clicking on clear
	public void clickClear(String code){
		pageLoaded(btnClear);
		txtDealerCode.set(code);
		TestReporter.assertTrue(btnClear.syncEnabled(20, false), "Clear button is enabled");
		btnClear.click();
	}

	//Getting the count of pages in pagination
	private int validateButtonsEnabledDisabledWithTotalPagesCount() {
		String data = driver.findButton(By.xpath(".//*[@id='content']/ul/li[9]/a")).getText();
		String[] data_Array = data.split(" ");
		System.out.println(data_Array[2]);
		int count = Integer.parseInt(data_Array[2]);
		return count;
	}
}