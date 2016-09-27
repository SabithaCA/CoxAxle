package coxaxle;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.SignInPage;
import com.coxAxle.dealer.Contacts.ChangeMobileLogoPage;
import com.coxAxle.dealer.Contacts.ContactPage;
import com.coxAxle.dealer.Contacts.UpdateContactsPage;
import com.coxAxle.dealer.HomePage;
import com.vensai.utils.ArrayUtil;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Dealer Mobile Logo
 * @author  Sabitha Adama
 * @date 	26/09/2016
 */
public class AT_09_VerifyChangeMobileLogo extends TestEnvironment{

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
		testStart("AT_09_VerifyChangeMobileLogo");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password, String adminEmail, String adminPassword, 
			String mainContact,String salesContact,String serviceDeskContact,
			String collisionDeskContact,String webLink,String imagePath) throws InterruptedException {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Contacts tab
		TestReporter.logStep("Click on Contacts tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickContactsTab();

		//Getting the Contact Logo and Clicking on Change Mobile Logo button
		TestReporter.logStep("Getting the Contact Logo and Clicking on Change Mobile Logo button");
		ContactPage contactsPage = new ContactPage(driver);
		String logo_BeforeCancel = contactsPage.getContactLogo();
		contactsPage.clickChangeMobileLogo();

		//Uploading an image and Clicking on Cancel button
		TestReporter.logStep("Uploading an image and Clicking on Cancel button");
		ChangeMobileLogoPage changeMobileLogo=new ChangeMobileLogoPage(driver);
		changeMobileLogo.clickBrowseAndUploadImage(imagePath);
		changeMobileLogo.clickCancel();

		//Getting and validating the Logo info with Cancel operation
		TestReporter.logStep("Getting and validating the Logo info with Cancel operation");
		String logo_AfterCancel = contactsPage.getContactLogo();
		TestReporter.assertTrue(logo_BeforeCancel.contains(logo_AfterCancel),
				"Mibile logo validated with Cancel operation");

		//Clicking on Change Mobile Logo button 
		TestReporter.logStep("Clicking on Change Mobile Logo button");
		contactsPage.clickChangeMobileLogo();
		String[] imageName = imagePath.split("/");
		String logo_BeforeSubmit=  imageName[imageName.length-1];

		//Uploading an image and Clicking on Submit button
		TestReporter.logStep("Uploading an image and Clicking on Submit button");
		changeMobileLogo.clickBrowseAndUploadImage(imagePath);
		changeMobileLogo.clickSubmit();

		//Getting and validating the Logo info with Submit operation
		TestReporter.logStep("Getting and validating the Logo info with Submit operation");
		String logo_AfterSubmit = contactsPage.getContactLogo();
		System.out.println("logo src after submit "+logo_AfterSubmit);
		TestReporter.assertTrue(logo_AfterSubmit.contains(logo_BeforeSubmit),
				"Mibile logo validated with Submit operation");
	}
}



