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
import com.coxAxle.dealer.Banners.BannersPage;
import com.coxAxle.dealer.Banners.NewBannerPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Add Banner operation
 * @author  Sabitha Adama
 * @date 	12/10/2016
 */
public class AT_11_ValidateAddBanner extends TestEnvironment{

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
		testStart("AT_11_ValidateAddBanner");
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
		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Banners tab
		TestReporter.logStep("Click on Banners tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickBannersTab();

		//Validating Banner page fields and Clicking on Add Banner button
		TestReporter.logStep("Validating Banner page fields and Clicking on Add Banner button");
		BannersPage bannersPage = new BannersPage(driver);
		bannersPage.validateBannerFields();
		int bannerCount_BeforeCancel = bannersPage.getBannerListCount();
		bannersPage.clickAddBanner();

		//Validating Add Banner page fields
		TestReporter.logStep("Validating Add Banner page fields");
		NewBannerPage newBannerPage = new NewBannerPage(driver);
		newBannerPage.validateNewBannerFields();

		//Entering New Banner Information and Clicking on Cancel button
		TestReporter.logStep("Entering New Banner Information and Clicking on Cancel button");
		newBannerPage.enterAddBannerFieldsInfo(imageName,imagePath);
		newBannerPage.clickCancel();
		int bannerCount_AfterCancel = bannersPage.getBannerListCount();
		//System.out.println("Banners count after cancel : "+ bannerCount_AfterCancel);

		//Clicking on Add Banner button, Entering information and clicking on Submit button
		TestReporter.logStep("Clicking on Add Banner button, Entering information and clicking on Submit button");
		bannersPage.clickAddBanner();
		newBannerPage.enterAddBannerFieldsInfo(imageName,imagePath);
		newBannerPage.clickSubmit();
		int bannerCount_AfterSubmit = bannersPage.getBannerListCount();
		//System.out.println("Banners count after submit : " + bannerCount_AfterSubmit);

		//Verifying the Newly added Banner status
		TestReporter.logStep("Verifying the Newly added Banner status");
		bannersPage.getStatusOfSpecifiedBanner(imageName);

		//Validating the count of the banners in the list with cancel and submit operations
		TestReporter.logStep("Validating the count of the banners in the list with cancel and submit operations");
		TestReporter.assertEquals(bannerCount_BeforeCancel, bannerCount_AfterCancel, 
				"Validating the Banner list with Cancel operation");
		TestReporter.assertEquals(bannerCount_AfterCancel, bannerCount_AfterSubmit-1, 
				"Validating the Banner list with Submit Operation operation");
	}
}



