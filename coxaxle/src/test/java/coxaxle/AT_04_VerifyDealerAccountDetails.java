package coxaxle;

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
import com.coxAxle.dealer.Account.AccountPage;
import com.coxAxle.dealer.HomePage;
import com.coxAxle.navigation.MainNav;
import com.vensai.utils.ArrayUtil;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Dealer Account Details
 * @author  Sabitha Adama
 * @date 	15/09/2016
 */
public class AT_04_VerifyDealerAccountDetails extends TestEnvironment{

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
	public void setup(@Optional String runLocation, String browserUnderTest,String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("COXAXLE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		testStart("AT_04_VerifyDealerAccountDetails");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String AdminEmail,String AdminPassword, String changePassword) {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as Dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Account tab
		TestReporter.logStep("Click on Account tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickAccountTab();

		//Verify the Account details od dealer
		TestReporter.logStep("Verify the Account details od dealer");
		AccountPage accountPage = new AccountPage(driver);
		String[] DealerAccount_Details=accountPage.verifyAccountDetails();
		for (int i = 0; i < DealerAccount_Details.length; i++) {
			System.out.println(DealerAccount_Details[i]);
		}

		//Logout
		MainNav mainNav = new MainNav(getDriver());
		TestReporter.assertTrue(mainNav.isLogoutDisplayed(), "Verify user is successfully logged out");
		mainNav.clickLogout();

		//Login as Admin
		TestReporter.logStep("Login as Admin");
		SignInPage.loginWithCredentials(AdminEmail,AdminPassword);

		//Click on Dealers tab
		TestReporter.logStep("Click on Dealers tab");
		AdminHomePage adminHomePage=new AdminHomePage(driver);
		adminHomePage.clickDealersTab();

		//Clicking on specified Dealer and Verifying the Dealer details
		TestReporter.logStep("Clicking on specified Dealer and Verifying the Dealer details");
		DealerPage dealerPage = new DealerPage(driver);
		dealerPage.clickOnSpecifiedDealer(email);
		TestReporter.logStep("Getting details");
		String[] Dealer_Details=dealerPage.verifyDealerDetails();
		for (int i = 0; i < Dealer_Details.length; i++) {
			System.out.println(Dealer_Details[i]);
		}

		//Comparing Dealer and Dealer account details
		TestReporter.logStep("Comparing Dealer and Dealer account details");
		ArrayUtil arrayutil = new ArrayUtil();
		arrayutil.comparisonOfOneToMany(DealerAccount_Details,Dealer_Details);

	}

}


