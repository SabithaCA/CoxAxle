package coxaxle.Dealer;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.SignInPage;
import com.coxAxle.dealer.HomePage;
import com.coxAxle.dealer.Customers.CustomersPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Get the Customer Status
 * @author  Sabitha Adama
 * @date 	17/10/2016
 */
public class AT_16_CustomerStatusCheck extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_04_VerifyCustomers.xlsx","Data").getTestData();
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
		testStart("AT_16_CustomerStatusCheck");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String AdminEmail,String AdminPassword, 
			String changePassword,String data) {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as Dealer");
		SignInPage.loginWithCredentials(email,password);

		//Verify the Menu items and Click on Customers tab
		TestReporter.logStep("Verify the Menu items and Click on Contact Dealers tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickCustomersTab();

		//Deactivate and activate customer
		TestReporter.logStep("Deactivate and activate customer");
		CustomersPage customersPage = new CustomersPage(driver);
		customersPage.checkStatusOfDealer(data);
		customersPage.checkStatusOfDealer(data);
		
		//Verify the Check box presence for the customer
		TestReporter.logStep("Verify the Check box presence for the customer");
		customersPage.checkBoxCheck(data);	
	}
}

