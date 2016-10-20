package coxaxle.Admin;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.AdminHomePage;
import com.coxAxle.admin.CustomerDetailsPage;
import com.coxAxle.admin.CustomersPage;
import com.coxAxle.admin.SignInPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Get the Customer Information
 * @author  Sabitha Adama
 * @date 	29/09/2016
 */
public class AT_04_GetCustomerInformation extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_04_VerifyDealerDetails.xlsx","Data").getTestData();
			return excelData;
		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, "An error occured with accessing the data provider: " + e);
		}
		return new Object[][] {{}};
	}

	@BeforeTest
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion","operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest,String browserVersion, 
			String operatingSystem, String environment) {
		setApplicationUnderTest("COXAXLE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		testStart("AT_04_GetCustomerInformation");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String AdminEmail,String AdminPassword, 
			String changePassword) {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as Admin");
		SignInPage.loginWithCredentials(AdminEmail,AdminPassword);

		//Verify the Menu items and Click on Customers tab
		TestReporter.logStep("Verify the Menu items and Click on Contact Dealers tab");
		AdminHomePage adminHomePage = new AdminHomePage(driver);
		adminHomePage.validateMainMenuItems();
		adminHomePage.clickCustomersTab();

		//Clicking on the Specified Customer
		TestReporter.logStep("Clicking on the Specified Customer");
		CustomersPage customersPage = new CustomersPage(driver);
		customersPage.clickOnSpecifiedCustomer(email);

		//Get the Customer Details
		TestReporter.logStep("Get the Customer Details");
		CustomerDetailsPage customerDetailsPage = new CustomerDetailsPage(driver);
		customerDetailsPage.getCustomerDetails();
		customerDetailsPage.getVehicleList();
		customerDetailsPage.getSavedSearch();

		//------------------------Search and Clear-----------------------------------------
		//Entering Customer name , Dealer code and clicking on Search
		TestReporter.logStep("Entering Customer name , Dealer code and clicking on Search");
		adminHomePage.clickCustomersTab();
		customersPage.setSearchData(email);
		customersPage.clickSearch();

		//Entering Customer name , Dealer code and clicking on Clear
		TestReporter.logStep("Entering Customer name , Dealer code and clicking on Clear");
		adminHomePage.clickCustomersTab();
		customersPage.setSearchData(email);
		customersPage.clickClear();

	}
}



