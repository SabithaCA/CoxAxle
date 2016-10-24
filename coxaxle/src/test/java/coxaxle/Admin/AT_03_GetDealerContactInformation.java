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
import com.coxAxle.admin.ContactDealerPage;
import com.coxAxle.admin.SignInPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Get the Dealer Contact Information
 * @author  Sabitha Adama
 * @date 	29/09/2016
 */
public class AT_03_GetDealerContactInformation extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_08_VerifyUpdateContacts.xlsx","Data").getTestData();
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
		testStart("AT_03_GetDealerContactInformation");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password, String adminEmail, String adminPassword, 
			String mainContact,String salesContact,String serviceDeskContact,
			String collisionDeskContact,String webLink,String imagePath,String imageName,String code) {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as Admin");
		SignInPage.loginWithCredentials(adminEmail,adminPassword);

		//Verify the Menu items and Click on contact Dealers tab
		TestReporter.logStep("Verify the Menu items and Click on Contact Dealers tab");
		AdminHomePage adminHomePage = new AdminHomePage(driver);
		adminHomePage.validateMainMenuItems();
		adminHomePage.clickContactDealersTab();

		//Clicking on Particular Dealer name
		TestReporter.logStep("Clicking on Particular Dealer name");
		ContactDealerPage contactDealerPage = new ContactDealerPage(driver);
		contactDealerPage.verifyDealerCode(code);	
		String[] contactDetails = contactDealerPage.verifyDealerDetails();
		TestReporter.logStep("Contact Details : "+Arrays.toString(contactDetails));
		String dealerAdminLogin_Logo = contactDealerPage.getDealerContactLogo();
		TestReporter.logStep("Contact Logo source : "+dealerAdminLogin_Logo);

		//--------------------Search and Clear--------------------------------
		TestReporter.logStep("Entering Dealer code and clicking on Search");
		adminHomePage.clickContactDealersTab();
		contactDealerPage.clickSearch(code);

		TestReporter.logStep("Entering Dealer code and clicking on Clear");
		adminHomePage.clickContactDealersTab();
		contactDealerPage.clickClear(code);

	}
}



