package coxaxle.Admin;

import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.AdminHomePage;
import com.coxAxle.admin.DealerPage;
import com.coxAxle.admin.SignInPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Get the Dealer Information
 * @author  Sabitha Adama
 * @date 	28/09/2016
 */
public class AT_01_GetTheDealerInformation extends TestEnvironment{

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
		testStart("AT_01_GetTheDealerInformation");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String adminEmail,String adminPassword,
			String changePassword,String data) {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as Admin");
		SignInPage.loginWithCredentials(adminEmail,adminPassword);

		//Verify the Menu items and Click on Dealers tab
		TestReporter.logStep("Verify the Menu items and Click on Dealers tab");
		AdminHomePage adminHomePage = new AdminHomePage(driver);
		adminHomePage.validateMainMenuItems();
		adminHomePage.clickDealersTab();

		//Clicking on Particular Dealer name
		TestReporter.logStep("Clicking on Particular Dealer name");
		DealerPage dealerPage = new DealerPage(driver);
		dealerPage.clickOnSpecifiedDealer(email);		

		String[] Dealer_Details=dealerPage.verifyDealerDetails();
		TestReporter.logStep(Arrays.toString(Dealer_Details));
	}
}



