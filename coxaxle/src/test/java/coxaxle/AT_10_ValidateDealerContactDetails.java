package coxaxle;

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
import com.coxAxle.dealer.Contacts.ContactPage;
import com.coxAxle.navigation.MainNav;
import com.coxAxle.dealer.HomePage;
import com.vensai.utils.ArrayUtil;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Dealer Contact Details
 * @author  Sabitha Adama
 * @date 	27/09/2016
 */
public class AT_10_ValidateDealerContactDetails extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_04_VerifyDealerDetails.xlsx","Data").getTestData();
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
		testStart("AT_10_ValidateDealerContactDetails");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String AdminEmail,
			String AdminPassword, String changePassword) throws InterruptedException {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		//Login as Dealer
		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Contacts tab
		TestReporter.logStep("Click on Contacts tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickContactsTab();

		//Getting the Contact information
		TestReporter.logStep("Getting the Dealer Contact information");
		ContactPage contactsPage = new ContactPage(driver);
		String[] dealer_Details = contactsPage.getContactDetails();
		String dealer_Logo = contactsPage.getContactLogo();
		System.out.println(dealer_Details[0]);
		String[] code = dealer_Details[0].split(" ");
		System.out.println(code[1]);

		//Dealer Logout
		TestReporter.logStep("Dealer Logout");
		MainNav mainNav = new MainNav(getDriver());
		TestReporter.assertTrue(mainNav.isLogoutDisplayed(), "Verify user is successfully logged in");
		mainNav.clickLogout();

		//Login as Admin
		TestReporter.logStep("Login as Admin");
		SignInPage.loginWithCredentials(AdminEmail,AdminPassword);

		//Clicking on Contact Dealer Tab
		TestReporter.logStep("Clicking on Contact Dealer Tab");
		AdminHomePage adminHomePage = new AdminHomePage(driver);
		adminHomePage.clickContactDealersTab();

		//Getting the Dealer Contact information through Admin Login
		TestReporter.logStep("Getting the Dealer Contact information through Admin Login");
		ContactDealerPage dealercontactPage = new ContactDealerPage(driver);
		dealercontactPage.verifyDealerCode(code[1]);
		String[] contactDetails = dealercontactPage.verifyDealerDetails();
		String dealerAdminLogin_Logo = dealercontactPage.getDealerContactLogo();
		//System.out.println(Arrays.toString(contactDetails));

		//Comparing Dealer contact information
		TestReporter.logStep("Comparing Dealer contact information");
		ArrayUtil arrayutil = new ArrayUtil();
		arrayutil.comparisonOfOneToMany(dealer_Details,contactDetails);
		TestReporter.log(dealer_Logo.equalsIgnoreCase(dealerAdminLogin_Logo)+" Dealer Contact logo verified");
	}
}



