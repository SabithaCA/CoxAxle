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
import com.coxAxle.dealer.Account.ChangePortalLogoPage;
import com.coxAxle.dealer.HomePage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Dealer Change Logo
 * @author  Sabitha Adama
 * @date 	14/10/2016
 */
public class AT_06_VerifyChangeLogo extends TestEnvironment{

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
		testStart("AT_06_VerifyChangeLogo");
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

		//Login as dealer
		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Account tab
		TestReporter.logStep("Click on Account tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickAccountTab();

		String ImageSrc_BeforeCancel = homePage.getImagesource();
		//Click on Update Account button
		TestReporter.logStep("Click on Change Portal Logo button");
		AccountPage accountPage = new AccountPage(driver);
		accountPage.clickChangeLogo();

		//Uploading image and clicking on cancel button
		TestReporter.logStep("Uploading image and clicking on cancel button");
		ChangePortalLogoPage changePortallogoPage = new ChangePortalLogoPage(driver);
		changePortallogoPage.uploadingImage(imagePath);
		changePortallogoPage.clickCancel();
		String ImageSrc_AfterCancel = homePage.getImagesource();
		String[] imagename = imagePath.split("/");
		String ImageSrc_BeforeSubmit=  imagename[imagename.length-1];

		//Clicking on Change Portal logo and uploading image with submit operation. 
		TestReporter.logStep("Clicking on Change Portal logo and uploading image with submit operation");
		accountPage.clickChangeLogo();
		changePortallogoPage.uploadingImage(imagePath);
		changePortallogoPage.clickSubmit();
		String ImageSrc_AfterSubmit = homePage.getImagesource();

		//Comparison of images with cancel and submit operations
		TestReporter.logStep("Comparison of images with cancel and submit operations");
		TestReporter.assertEquals(ImageSrc_BeforeCancel, ImageSrc_AfterCancel, 
				"Image is validated with Cancel operation");
		TestReporter.assertTrue(ImageSrc_AfterSubmit.contains(ImageSrc_BeforeSubmit), 
				"Image is validated with Submit operation");
	}

}



