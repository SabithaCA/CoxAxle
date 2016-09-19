package coxaxle;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.coxAxle.admin.SignInPage;
import com.coxAxle.dealer.Account.AccountPage;
import com.coxAxle.dealer.Account.UpdateAccountPage;
import com.coxAxle.dealer.HomePage;
import com.vensai.utils.ArrayUtil;
import com.vensai.utils.TestEnvironment;
import com.vensai.utils.TestReporter;
import com.vensai.utils.dataProviders.ExcelDataProvider;

/**
 * @summary Validate Dealer Account UI
 * @author  Sabitha Adama
 * @date 	19/09/2016
 */
public class AT_05_VerifyUpdateAccount extends TestEnvironment{

	// **************
	// Data Provider
	// **************
	@DataProvider(name = "dataScenario")
	public Object[][] scenarios() {
		try {
			Object[][] excelData = new ExcelDataProvider("/datasheets/AT_05_VerifyUpdateAccount.xlsx","Data").getTestData();
			return excelData;
		}
		catch (RuntimeException e){
			TestReporter.assertTrue(false, "An error occured with accessing the data provider: " + e);
		}
		return new Object[][] {{}};
	}

	@BeforeTest
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion","operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest,String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("COXAXLE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		testStart("AT_05_VerifyUpdateAccount");
	}

	@AfterTest
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "dataScenario")
	public void registerUser(String email,String password,String name,String phone,String dealerCode,String address) {

		//Validating Sign In page elements
		SignInPage SignInPage = new SignInPage(driver);
		TestReporter.logStep("Validating Sign In page elements");
		SignInPage.validateSignInPageFields();

		TestReporter.logStep("Login as dealer");
		SignInPage.loginWithCredentials(email,password);

		//Click on Account tab
		TestReporter.logStep("Click on Account tab");
		HomePage homePage = new HomePage(driver);
		homePage.clickAccountTab();

		//Click on Update Account button
		TestReporter.logStep("Click on Update Account button");
		AccountPage accountPage = new AccountPage(driver);
		accountPage.clickUpdateAccount();

		//Validating the fields available under accounts page
		TestReporter.logStep("Validating the fields available under accounts page");
		UpdateAccountPage updateAccount=new UpdateAccountPage(driver);
		updateAccount.validateUpdateAccountFields();
		String[] data_BeforeCancel=updateAccount.getUpdateAccountFieldsInformation();
		/*System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println(Arrays.toString(array1));
		System.out.println("++++++++++++++++++++++++++++++++");*/

		//Entering the data and canceling the update
		TestReporter.logStep("Entering the data and canceling the update");
		updateAccount.enterDetailsToUpdate( name,  email,  phone,  dealerCode, address);
		updateAccount.clickCancel();
		String[] data_AfterCancel=accountPage.verifyAccountDetails();

		//Comparing account details with cancel operation
		TestReporter.logStep("Comparing account details with cancel operation");
		ArrayUtil arrayutil = new ArrayUtil();
		arrayutil.comparisonOfOneToMany(data_BeforeCancel,data_AfterCancel);

		//Click on Update Account button
		TestReporter.logStep("Click on Update Account button");
		accountPage.clickUpdateAccount();

		//Entering the data and Updating
		TestReporter.logStep("Entering the data and Updating");
		updateAccount.enterDetailsToUpdate( name,  email,  phone,  dealerCode, address);
		updateAccount.clickSubmit();
		String[] data_BeforeUpdate=accountPage.verifyAccountDetails();

		//Click on Update Account button
		TestReporter.logStep("Click on Update Account button");
		accountPage.clickUpdateAccount();
		String[] data_AfterUpdate=updateAccount.getUpdateAccountFieldsInformation();

		//Comparing details with update operation
		TestReporter.logStep("Comparing details with update operation");
		arrayutil.comparisonOfOneToMany(data_AfterUpdate,data_BeforeUpdate);
	}

}


