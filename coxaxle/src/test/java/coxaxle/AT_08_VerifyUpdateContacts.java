package coxaxle;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.SignInPage;
import com.coxAxle.dealer.Contacts.ContactPage;
import com.coxAxle.dealer.Contacts.UpdateContactsPage;
import com.coxAxle.dealer.HomePage;
import com.vensai.utils.ArrayUtil;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Dealer Update Contacts
 * @author  Sabitha Adama
 * @date 	23/09/2016
 */
public class AT_08_VerifyUpdateContacts extends TestEnvironment{

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
		testStart("AT_08_VerifyUpdateContacts");
	}

	@AfterTest
	public void close(ITestContext testResults){
		//endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password, String adminEmail, String adminPassword, 
			String mainContact,String salesContact,String serviceDeskContact,
			String collisionDeskContact,String webLink) throws InterruptedException {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Account tab
		TestReporter.logStep("Click on Contacts tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickContactsTab();

		//Validating the buttons present on the page and Getting the Contact details
		TestReporter.logStep("Validating the buttons present on the page and"
				+ "Getting the Contact details");
		ContactPage contactsPage = new ContactPage(driver);
		contactsPage.validateDealerContactButtons();
		String[] valuesBefore_Cancel=contactsPage.getContactDetails();
		/*System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(Arrays.toString(valuesBefore_Cancel));
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");*/

		//Click on Update Contacts button
		TestReporter.logStep("Click on Update Contacts button");
		contactsPage.clickUpdateContacts();

		//Validate update contact fields, entering data into fields and Clicking on cancel button
		TestReporter.logStep("Validate update contact fields, entering data into fields and"
				+ " Clicking on cancel button");
		UpdateContactsPage updateContacts=new UpdateContactsPage(driver);
		updateContacts.validateDealerContactFields();
		updateContacts.enterUpdateContactData(mainContact,salesContact,serviceDeskContact,
				collisionDeskContact,webLink);
		updateContacts.clickCancel();

		//Getting the details after cancel operation
		TestReporter.logStep("Getting the details after cancel operation");
		String[] valuesAfter_Cancel=contactsPage.getContactDetails();
		/*System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(Arrays.toString(valuesAfter_Cancel));
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		 */

		//Comparing contact details with Cancel operation
		TestReporter.logStep("Comparing contact details with Cancel operation");
		ArrayUtil arrayUtil=new ArrayUtil();
		arrayUtil.comparisonOfOneToMany(valuesAfter_Cancel,valuesBefore_Cancel);

		//Click on Update Contacts button
		TestReporter.logStep("Click on Update Contacts button");
		contactsPage.clickUpdateContacts();

		// Getting values before submit operation
		TestReporter.logStep("Getting the values before Submit operation");
		String[] valuesBefore_Submit={mainContact,salesContact,serviceDeskContact,collisionDeskContact,webLink};
		/*System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(Arrays.toString(valuesBefore_Submit));
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");*/
		//Validate update contact fields, entering data into fields and Clicking on cancel button
		TestReporter.logStep("Entering data into fields and Clicking on Submit button");
		updateContacts.enterUpdateContactData(mainContact,salesContact,serviceDeskContact,
				collisionDeskContact,webLink);
		updateContacts.clickSubmit();

		//Getting the values after Submit operation
		TestReporter.logStep("Getting the values after Submit operation");
		String[] valuesAfter_Submit=contactsPage.getContactDetails();
		/*System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(Arrays.toString(valuesAfter_Submit));
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");*/

		//Comparing contact details with Submit operation
		TestReporter.logStep("Comparing contact details with Submit operation");
		arrayUtil.comparisonOfOneToMany(valuesBefore_Submit,valuesAfter_Submit);
	}
}



