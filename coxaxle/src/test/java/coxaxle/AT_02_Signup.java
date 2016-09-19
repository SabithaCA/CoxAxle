package coxaxle;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.coxAxle.admin.SignInPage;
import com.coxAxle.dealer.RegistrationPage;
import com.coxAxle.navigation.MainNav;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Dealer SignUp
 * @author  Sabitha Adama
 * @date 	14/09/2016
 */
public class AT_02_Signup extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_02_Signup.xlsx","sample").getTestData();
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
		testStart("AT_02_Signup");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String name, String email, String phone, String code,
			String password, String comfirmPassword, String zipcode, String alertMsg) {

		//Sign Up
		TestReporter.logStep("Navigating to Sign Up page");
		SignInPage SignInPage = new SignInPage(getDriver());
		SignInPage.clickLink("Sign Up");

		//Validating all the Sign Up page fields and mandatory fields
		TestReporter.logStep("Validating all the Sign Up page fields and mandatory fields");
		RegistrationPage resPage = new RegistrationPage(getDriver());
		resPage.validatingSignUpFields();
		resPage.validateMandatoryFields();

		//Validating Cancel button functionality
		TestReporter.logStep("Validating Cancel button functionality");
		resPage.enterRegistrationDetails(name, email, phone, code,password, comfirmPassword,zipcode);
		resPage.clickCancel();
		SignInPage.clickLink("Sign Up");

		//Submitting the new dealer details
		TestReporter.logStep("Submitting the new dealer details");
		resPage.enterRegistrationDetails(name, email, phone, code,password, comfirmPassword,zipcode);
		resPage.clickSubmit();

		//Validating the account activation message
		TestReporter.logStep("Validating the account activation message");
		resPage.verifyActivateAccountAlert(alertMsg);
		/*SignInPage.clickLink("Sign In");
		SignInPage.loginWithCredentials(email,password);

		//Logout
		MainNav mainNav = new MainNav(getDriver());
		TestReporter.assertTrue(mainNav.isLogoutDisplayed(), "Verify user is successfully logged in");
		mainNav.clickLogout();*/
	}

}


