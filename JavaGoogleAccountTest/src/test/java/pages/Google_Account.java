package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.PageBase;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Google_Account extends PageBase {

	public Google_Account(WebDriver _driver) {
		super(_driver);
		driver = _driver;
	}

	/*******************************************************************************************
	 * All WebElements are identified by @FindBy annotation
	 *******************************************************************************************/

	WebDriver driver;
	// Web Element for First Name
	@FindBy(id = "firstName")
	WebElement firstName;

	@FindBy(id = "lastName")
	WebElement lastName;

	@FindBy(id = "username")
	WebElement username;

	@FindBy(name = "Passwd")
	WebElement password;

	@FindBy(name = "ConfirmPasswd")
	WebElement confirmPassword;

	@FindBy(xpath = "(//button//div)[1]")
	WebElement next;

	@FindBy(xpath = "//h1//span")
	WebElement headingTitle;

	// Since google's id are randomly generated identifying therefore using xpath and text
	@FindBy(xpath = "//span[contains(text(),'account')]")
	WebElement createAccount;

	@FindBy(xpath = "//div[text()='For myself']")
	WebElement forMyself;

	@FindBy(xpath = "//input[@type='email']")
	WebElement signinEmail;

	@FindBy(xpath = "//input[@type='password']")
	WebElement signinPassword;

	@FindBy(xpath = "//h1")
	WebElement signinMessage;
	/*******************************************************************************************
	 * All Methods for performing actions
	 *******************************************************************************************/

	public void enter_first_name(String _firstName){
		log.info("Enter first name: "+_firstName);
		firstName.sendKeys(_firstName);
	}

	public void enter_last_name(String _lastName){
		log.info("Enter last name: "+_lastName);
		lastName.sendKeys(_lastName);
	}

	public void enter_username(String _username){
		log.info("Enter username: "+_username);
		username.sendKeys(_username);
	}

	public void enter_password(String _password) {
		password.sendKeys(_password);
	}

	public void enter_confirm_password(String _confirmPassword) {
		confirmPassword.sendKeys(_confirmPassword);
	}
	public void click_next() throws InterruptedException {
//		driver.findElement(By.xpath("(//button//div)[1]")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;    // Tricky part :)
		js.executeScript("arguments[0].click();",driver.findElement(By.xpath("(//button//div)[1]")));
		PageBase.wait(10);  // Can be converted to explicit waits for better performance
//		next.click();
	}

	public String title_text() throws InterruptedException {
		return headingTitle.getText();
	}

	public void click_create_account() throws InterruptedException {
		PageBase.wait(3); // sometimes create element doesn't load with page
		createAccount.click();
		PageBase.wait(3); // element doesn't load with page
	}

	public void click_for_myself() {
		forMyself.click();
	}

	public void enter_signin_email(String _email) {
		signinEmail.sendKeys(_email);
	}

	public void enter_signin_password(String _password) {
		signinPassword.sendKeys(_password);
	}

	public void next_account() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();",driver.findElement(By.xpath("//span[text()='Next']")));
		PageBase.wait(10);  // Can be converted to explicit waits for better performance
//		next.click();
	}

	public String get_title() {
		return driver.getTitle();
	}
}
