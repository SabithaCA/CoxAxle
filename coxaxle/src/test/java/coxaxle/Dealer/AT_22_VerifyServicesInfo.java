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
import com.coxAxle.dealer.Feedback.FeedbackDetailsPage;
import com.coxAxle.dealer.Feedback.FeedbackPage;
import com.coxAxle.dealer.Notifications.CreateNotificationPage;
import com.coxAxle.dealer.Notifications.NotificationsPage;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Delete a customer
 * @author  Sabitha Adama
 * @date 	24/10/2016
 */
public class AT_22_VerifyServicesInfo extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_21_FeedBack.xlsx","Data").getTestData();
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
		testStart("AT_22_VerifyServicesInfo");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String customerName) throws InterruptedException {

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
		homePage.clickFeedBackTab();

		// Validate the feedback page fields and clicking on specified customer name
		TestReporter.logStep("Validate the feedback page fields and clicking on specified services customer name");
		FeedbackPage feedbackPage = new FeedbackPage(driver);
		feedbackPage.validateFeedbackFields();
		feedbackPage.clickServices();
		feedbackPage.clickOnSpecifiedServicesCustomerName(customerName);

		//Get the customer Details
		TestReporter.logStep("Getting the customer details");
		FeedbackDetailsPage feedbackDetails = new FeedbackDetailsPage(driver);
		feedbackDetails.getCustomerDetails();	
		
		//---------------------------Search and clear-----------------------------
		//Search and clear feedback data
		TestReporter.logStep("Search and clear feedback data");
		homePage.clickFeedBackTab();
		feedbackPage.setSearchData(customerName);
		feedbackPage.clickSearch();
		//Data in sales tab
		feedbackPage.verifySalesSearchData(customerName);
		//Data in services tab
		feedbackPage.clickServices();
		feedbackPage.verifyServicesSearchData(customerName);
		feedbackPage.clickClear();
		
	}
}
