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
import com.coxAxle.dealer.Notifications.CreateNotificationPage;
import com.coxAxle.dealer.Notifications.NotificationDetailsPage;
import com.coxAxle.dealer.Notifications.NotificationsPage;
import com.vensai.utils.ArrayUtil;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Delete a customer
 * @author  Sabitha Adama
 * @date 	21/10/2016
 */
public class AT_19_UpdateNotifications extends TestEnvironment{

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
		testStart("AT_19_UpdateNotifications");
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

		//Getting the Notification Details before update with cancel operation
		TestReporter.logStep("Getting the Notification Details before update with cancel operation");
		NotificationDetailsPage notificationDetailsPage = new NotificationDetailsPage(driver);
		String[] notificationDetails_BeforeCancel=notificationDetailsPage.verifyNotificationDetails();
		for (int i = 0; i < notificationDetails_BeforeCancel.length; i++) {
			System.out.println(notificationDetails_BeforeCancel[i]);
		}
		notificationDetailsPage.clickUpdateButton();
		CreateNotificationPage createNotificationPage = new CreateNotificationPage(driver);
		createNotificationPage.addNotificationDetails(startDate, expiryDate, type, text, title, image);
		createNotificationPage.clickCancel();
		ArrayUtil arrayUtil=new ArrayUtil();
		String[] notificationDetails_AfterCancel = notificationDetailsPage.verifyNotificationDetails();

		//Comparing contact details with Cancel operation
		TestReporter.logStep("Comparing contact details with Cancel operation");
		arrayUtil.comparisonOfOneToMany(notificationDetails_AfterCancel,notificationDetails_BeforeCancel);

		//Getting the Notification Details before update with Submit operation
		TestReporter.logStep("Getting the Notification Details before update with Submit operation");
		notificationDetailsPage.clickUpdateButton();
		createNotificationPage.addNotificationDetails(startDate, expiryDate, type, text, title, image);
		createNotificationPage.clickSubmit();
		String[] notificationDetails_BeforeSubmit= {"20-Feb-2017" , type, text, title};
		String[] notificationDetails_AfterSubmit = notificationDetailsPage.verifyNotificationDetails();

		//Comparing contact details with Submit operation
		TestReporter.logStep("Comparing contact details with Submit operation");
		arrayUtil.comparisonOfOneToMany(notificationDetails_BeforeSubmit,notificationDetails_AfterSubmit);

	}
}
