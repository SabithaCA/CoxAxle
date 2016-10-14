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
	@FindBy(linkText = "NEXT")  private Button btnNext;
	@FindBy(xpath = "//td/a") private Webtable wtStatus;


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
		TestReporter.assertTrue(btnAddBanner.syncEnabled(20, false), "Add Banner button is enabled");
		btnAddBanner.click();
	}

	//method to get the list count
	public int getBannerListCount(){
		List<WebElement> table_Row = wtBannersList.findElements(By.tagName("tr"));
		int count=table_Row.size();
		int total_count = 0;
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			table_Row = wtBannersList.findElements(By.tagName("tr"));
			total_count=count+table_Row.size();
			i++;
		} 
		//System.out.println("Row count "+total_count);
		return total_count;
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

	//Clicking on Specified banner with NEXT pages
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
			System.out.println("gng inside For");
			if(Columns_row.get(0).getText().equalsIgnoreCase(imageName)){
				System.out.println("gng inside IF");
				Columns_row.get(0).findElement(By.tagName("a")).click();
				break;
			}
		}
	}

	//Getting the count of pages in pagination
	private int validateButtonsEnabledDisabledWithTotalPagesCount() {
		String data = driver.findButton(By.xpath(".//*[@id='content']/ul/li[6]/a")).getText();
		String[] data_Array = data.split(" ");
		System.out.println(data_Array[2]);
		int count = Integer.parseInt(data_Array[2]);
		return count;
	}

	//Select the drop down status and verify the displayed records.
	public void selectAndCheckStatus(String status){
		List<WebElement> values= lstStatus.getOptions();
		for (WebElement webElement : values) {
			if(webElement.getText().equalsIgnoreCase(status)){
				lstStatus.select(status);
				btnSearch.click();
				break;
			}
			else{
				System.out.println("status not yet selected");
			}
		}	
		getStatusOfBanner(status);
		btnClear.click();
	}

	//method to get and to verify the status fields
	public void getStatusOfBanner(String status){
		String[] statusValues=null;	
		String Value="";
		List<WebElement> rows_table = driver.findElements(By.xpath("//table/tbody/tr/td[4]/a/img"));
		for (WebElement webElement : rows_table) {
			Value=Value+webElement.getAttribute("title")+"_";
		}
		int i=2;
		while(btnNext.syncVisible()==true && i<=validateButtonsEnabledDisabledWithTotalPagesCount()){
			btnNext.click();
			List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr/td[4]/a/img"));
			rows_table.addAll(rows);
			for (WebElement webElement : rows) {
				Value=Value+webElement.getAttribute("title")+"_";
			}
			i++;
		}
		statusValues=Value.split("_");
		System.out.println("rows_table "+rows_table.size());
		for (String webElement : statusValues) {
			TestReporter.assertTrue(webElement.contains(status),status+ " status Verified");
		}
		TestReporter.logStep("All the banners with "+status+" status are verified");
	}
}