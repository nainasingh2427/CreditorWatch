package sanitySuite;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Google_Account;


public class TestCase_2_GoogleCreateAccount extends TestBase{

	Google_Account google_account;


	@Test (priority=1, description = "Create Google Account")
	public void create_account() throws InterruptedException {
		log.info("Open Google Account URL.");
		driver.get(data.getProperty("base.url"));

		log.info("Navigates to Create Account and use test data from properties file");
		google_account = new Google_Account(driver);
		google_account.click_create_account();
		google_account.click_for_myself();
		google_account.enter_first_name(data.getProperty("Testdata.firstName"));
		google_account.enter_last_name(data.getProperty("Testdata.lastName"));
		google_account.enter_username(data.getProperty("Testdata.username"));
		google_account.enter_password(data.getProperty("Testdata.password"));
		google_account.enter_confirm_password(data.getProperty("Testdata.confirmPassword"));
		google_account.next_account();
	//	Assert.assertEquals(getPropertyValue("Testdata.title"), google_account.get_title());  // since google restricts reading from controls, I have spent more time on framework, this is just an example of how assertions can be implemented
	}
}
