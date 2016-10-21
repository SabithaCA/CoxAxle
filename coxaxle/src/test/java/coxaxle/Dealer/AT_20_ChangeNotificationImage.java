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
import com.coxAxle.dealer.Notifications.NotificationDetailsPage;
import com.coxAxle.dealer.Notifications.NotificationsPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Delete a customer
 * @author  Sabitha Adama
 * @date 	21/10/2016
 */
public class AT_20_ChangeNotificationImage extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_18_NotificationDetails.xlsx","Data").getTestData();
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
		testStart("AT_20_ChangeNotificationImage");
	}

	@AfterTest
	public void close(ITestContext testResults){
		//endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String AdminEmail,String AdminPassword,
			String startDate, String expiryDate, String type, String text, String title, String image) throws InterruptedException {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as Dealer");
		SignInPage.loginWithCredentials(email,password);

		//Verify the Menu items and Click on Notifications tab
		TestReporter.logStep("Verify the Menu items and Click on Notifications tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickNotificationsTab();

		//Validating the Notificatoion page fields
		TestReporter.logStep("Validating the Notificatoion page fields and clicking on specified Notification type");
		NotificationsPage notificationPage = new NotificationsPage(driver);
		notificationPage.validateNotificationsFields();
		notificationPage.clickOnSpecifiedNotification(type);

		//Getting Image source
		String ImageSrc_BeforeCancel = homePage.getImagesource();

		//Clicking on change notification image and uploading image with cancel operation
		TestReporter.logStep("Clicking on change notification image and uploading image with cancel operation");
		NotificationDetailsPage notificationDetailsPage = new NotificationDetailsPage(driver);
		notificationDetailsPage.clickChangeNotificationImageButton();
		notificationDetailsPage.uploadingImage(image);
		notificationDetailsPage.clickCancel();
		String ImageSrc_AfterCancel = homePage.getImagesource();
		String[] imagename = image.split("/");
		String ImageSrc_BeforeSubmit=  imagename[imagename.length-1];

		//Clicking on change notification image and uploading image with submit operation
		TestReporter.logStep("Clicking on change notification image and uploading image with submit operation");
		notificationDetailsPage.clickChangeNotificationImageButton();
		notificationDetailsPage.uploadingImage(image);
		notificationDetailsPage.clickSubmit();
		String ImageSrc_AfterSubmit = homePage.getImagesource();

		//Comparison of images with cancel and submit operations
		TestReporter.logStep("Comparison of images with cancel and submit operations");
		TestReporter.assertEquals(ImageSrc_BeforeCancel, ImageSrc_AfterCancel, 
				"Image is validated with Cancel operation");
		TestReporter.assertTrue(ImageSrc_AfterSubmit.contains(ImageSrc_BeforeSubmit), 
				"Image is validated with Submit operation");

		//------------------------Search and Clear-----------------------------------------
		//Entering Customer name , Dealer code and clicking on Search
		TestReporter.logStep("Entering Customer name , Dealer code and clicking on Search");
		homePage.clickNotificationsTab();
		notificationPage.setSearchData(type);
		notificationPage.clickSearch();

		//Entering Customer name , Dealer code and clicking on Clear
		TestReporter.logStep("Entering Customer name , Dealer code and clicking on Clear");
		homePage.clickNotificationsTab();
		notificationPage.setSearchData(type);
		notificationPage.clickClear();
	}
}
