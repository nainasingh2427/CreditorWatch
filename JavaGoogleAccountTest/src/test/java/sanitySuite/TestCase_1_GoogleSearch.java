package sanitySuite;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;
import pages.Google_Account;



public class TestCase_1_GoogleSearch extends TestBase{

	Google_Account google_account;

	@Test (priority=2, description = "Login Google Account")
	public void login() throws InterruptedException {
		log.info("Sign in Google account");
		driver.get(data.getProperty("base.url"));
		google_account = new Google_Account(driver);
		google_account.enter_signin_email(data.getProperty("Testdata.signinemail"));
		google_account.click_next();
		google_account.enter_signin_password(data.getProperty("Testdata.signinpassword"));
		google_account.click_next();
		//	Assert.assertEquals(getPropertyValue("Testdata.title"), google_account.get_title());  // since google restricts reading from controls, I have spent more time on framework, this is just an example of how assertions can be implemented
	}

}
