package coxaxle.Admin;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.AdminHomePage;
import com.coxAxle.admin.BannersPage;
import com.coxAxle.admin.SignInPage;
import com.coxAxle.dealer.Banners.NewBannerPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Get the Banner Information
 * @author  Sabitha Adama
 * @date 	30/09/2016
 */
public class AT_05_GetBannerInformation extends TestEnvironment{

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
		testStart("AT_05_GetBannerInformation");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password, String adminEmail, String adminPassword, 
			String mainContact,String salesContact,String serviceDeskContact,String collisionDeskContact,
			String webLink,String imagePath,String imageName,String code) {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as Admin");
		SignInPage.loginWithCredentials(adminEmail,adminPassword);

		//Verify the Menu items and Click on Customers tab
		TestReporter.logStep("Verify the Menu items and Click on Contact Dealers tab");
		AdminHomePage adminHomePage = new AdminHomePage(driver);
		adminHomePage.validateMainMenuItems();
		adminHomePage.clickBannersTab();

		//Validating Add Banner page fields and Add Banner button
		TestReporter.logStep("Validating Add Banner page fields and Add Banner button");
		BannersPage bannerPage = new BannersPage(driver);
		bannerPage.validateBannerFields();
		bannerPage.verifyAddBannerDisplayed();

		//Clicking on Specified Banner and getting Details
		TestReporter.log("Clicking on Specified Banner, Checking banner Details and "
				+ "checking for Change Banner button Display ");
		bannerPage.clickOnSpecifiedBanner(imageName);
		bannerPage.getBannerDetails();
		bannerPage.verifyChangeBannerDisplayed();

		//Checking for Status button display
		TestReporter.log("Checking for Status button display");
		adminHomePage.clickBannersTab();
		bannerPage.checkStatusDisplay();

		//-------------------------Search and Clear-------------------------
		TestReporter.logStep("Entering Dealer code and clicking on Search");
		bannerPage.clickSearch(code);

		TestReporter.logStep("Entering Dealer code and clicking on Clear");
		adminHomePage.clickBannersTab();
		bannerPage.clickClear(code);

	}
}



