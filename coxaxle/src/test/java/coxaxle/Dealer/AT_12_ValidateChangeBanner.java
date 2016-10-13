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
import com.coxAxle.dealer.Banners.BannerDetailsPage;
import com.coxAxle.dealer.Banners.BannersPage;
import com.coxAxle.dealer.Banners.ChangeBannerPage;
import com.coxAxle.dealer.Banners.NewBannerPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Change Banner
 * @author  Sabitha Adama
 * @date 	13/10/2016
 */
public class AT_12_ValidateChangeBanner extends TestEnvironment{

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
		testStart("AT_12_ValidateChangeBanner");
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

		//Validating Banner page fields and Clicking on specified banner name
		TestReporter.logStep("Validating Banner page fields and Clicking on specified banner name");
		BannersPage bannersPage = new BannersPage(driver);
		bannersPage.validateBannerFields();
		bannersPage.clickOnSpecifiedBanner(imageName);

		//Getting Image source and clicking on Change Banner Button
		TestReporter.logStep("Getting Image source and clicking on Change Banner Button");
		BannerDetailsPage bannerDetailsPage = new BannerDetailsPage(driver);
		String ImageSrc_BeforeCancel = bannerDetailsPage.getImagesource();
		bannerDetailsPage.clickChangeBanner();

		//Validating the Change Banner page fields and Uploading new banner with Cancel operation
		TestReporter.logStep("Validating the Change Banner page fields and Uploading new banner with Cancel operation");
		ChangeBannerPage changeBannerPage = new ChangeBannerPage(driver);
		changeBannerPage.validateChangeBannerFields();
		changeBannerPage.clickBrowseAndUploadImage(imagePath,imageName);
		changeBannerPage.clickCancel();

		//Getting the Source after cancel operation and Image validation with cancel operation
		TestReporter.logStep("Getting the Source after cancel operation and Image validation with cancel operation");
		String ImageSrc_AfterCancel = bannerDetailsPage.getImagesource();
		TestReporter.assertEquals(ImageSrc_BeforeCancel, ImageSrc_AfterCancel, 
				"Image is validated with Cancel operation");

		//Uploading new banner with Submit operation
		TestReporter.logStep("Uploading new banner with Submit operation");
		bannerDetailsPage.clickChangeBanner();
		changeBannerPage.clickBrowseAndUploadImage(imagePath,imageName);
		changeBannerPage.clickSubmit();

		//Getting image source after Submit operation
		TestReporter.logStep("Getting image source after Submit operation");
		String ImageSrc_AfterSubmit = bannerDetailsPage.getImagesource();
		String[] imagename = imagePath.split("/");
		String ImageSrc_BeforeSubmit=  imagename[imagename.length-1];

		//Image validation with Submit operation
		TestReporter.logStep("Image validation with Submit operation");
		TestReporter.assertTrue(ImageSrc_AfterSubmit.contains(ImageSrc_BeforeSubmit), "Image is validated with Submit operation");
	}
}