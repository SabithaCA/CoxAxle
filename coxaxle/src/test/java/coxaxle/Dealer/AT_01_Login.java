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
import com.coxAxle.navigation.MainNav;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Dealer Login
 * @author  Sabitha Adama
 * @date 	13/09/2016
 */

public class AT_01_Login extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_01_Login.xlsx","Data").getTestData();
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
		testStart("AT_01_Login");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void exampleMethod(String email, String password){

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(getDriver());
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as dealer
		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Validating Dealer home page menu items
		TestReporter.logStep("Validating Dealer home page menu items");
		HomePage homepage=new HomePage(driver);
		homepage.validateMainMenuItems();

		//Logout
		TestReporter.logStep("Logout from appication");
		MainNav mainNav = new MainNav(getDriver());
		TestReporter.assertTrue(mainNav.isLogoutDisplayed(), "Verify user is successfully logged in");
		mainNav.clickLogout();
	}
}


