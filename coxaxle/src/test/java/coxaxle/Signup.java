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


public class Signup extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/Signup.xlsx","sample").getTestData();
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
		testStart("Signup");
	}

	@AfterTest
	public void close(ITestContext testResults){
		//endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String name, String email, String phone, String code,
			String password, String comfirmPassword, String zipcode, String alertMsg) {

		SignInPage SignInPage = new SignInPage(getDriver());
		SignInPage.clickLink("Sign Up");

		RegistrationPage resPage = new RegistrationPage(getDriver());
		resPage.validatingSignUpFields();
		resPage.validateMandatoryFields();
		resPage.enterRegistrationDetails(name, email, phone, code,password, comfirmPassword,zipcode);
		resPage.clickCancel();
		resPage.validateMandatoryFields();
		/*resPage.enterRegistrationDetails(name, email, phone, code,password, comfirmPassword,zipcode);
		resPage.clickSubmit();*/
		//resPage.verifyActivateAccountAlert(alertMsg);
	}

}


