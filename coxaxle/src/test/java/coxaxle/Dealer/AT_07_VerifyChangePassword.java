package coxaxle.Dealer;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.SignInPage;
import com.coxAxle.dealer.Account.AccountPage;
import com.coxAxle.dealer.Account.ChangePasswordPage;
import com.coxAxle.navigation.MainNav;
import com.coxAxle.dealer.HomePage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Dealer Change Password
 * @author  Sabitha Adama
 * @date 	22/09/2016
 */
public class AT_07_VerifyChangePassword extends TestEnvironment{

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
		testStart("AT_07_VerifyChangePassword");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password, String adminEmail, String adminPassword, 
			String changePassword,String data) throws InterruptedException {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as dealer
		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Account tab
		TestReporter.logStep("Click on Account tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickAccountTab();

		//Click on Update Account button
		TestReporter.logStep("Click on Change Portal Logo button");
		AccountPage accountPage = new AccountPage(driver);
		accountPage.clickChangePassword();

		//Validate the fields present on change password page
		TestReporter.logStep("Validate the fields present on change password page");
		ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
		changePasswordPage.validateChangePasswordPageFields();

		//Enter password and click on cancel
		TestReporter.logStep("Enter password and click on cancel");
		changePasswordPage.enterPasswords(changePassword);
		changePasswordPage.clickCancel();

		//Enter password and click on Submit
		TestReporter.logStep("Enter password and click on Submit");
		AccountPage accountPg = new AccountPage(driver);
		accountPg.clickChangePassword();
		changePasswordPage.enterPasswords(changePassword);
		changePasswordPage.clickSubmit();

		//Logout
		MainNav mainNav = new MainNav(getDriver());
		TestReporter.assertTrue(mainNav.isLogoutDisplayed(), "Verify user is successfully logged in");
		mainNav.clickLogout();

		//Login with new password
		TestReporter.logStep("Login with new password");
		SignInPage.loginWithCredentials(email,changePassword);

	}

}



