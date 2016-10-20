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
import com.coxAxle.dealer.Notifications.NotificationsPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Delete a customer
 * @author  Sabitha Adama
 * @date 	17/10/2016
 */
public class AT_18_AddNotification extends TestEnvironment{

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
		testStart("AT_18_AddNotification");
	}

	@AfterTest
	public void close(ITestContext testResults){
		//endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String AdminEmail,String AdminPassword,
			String startDate, String expiryDate, String type, String text, String title, String image) {

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
		TestReporter.logStep("Validating the Notificatoion page fields and clicking on Add Notification");
		NotificationsPage notificationPage = new NotificationsPage(driver);
		notificationPage.validateNotificationsFields();
		notificationPage.clickAddNotification();	
		
		//Validating the Create Notification fields
		TestReporter.logStep("Validating the Create Notification fields");
		CreateNotificationPage createNotificationPage = new CreateNotificationPage(driver);
		createNotificationPage.validateCreateNotificationsFields();
		
		//Adding Notification details
		TestReporter.logStep("Adding Notification details");
		createNotificationPage.addNotificationDetails(startDate, expiryDate, type, text, title, image);
	}
}
