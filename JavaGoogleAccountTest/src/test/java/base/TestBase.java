package base;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;



public abstract class TestBase {
	public static Properties env=null;
	public static Properties config=null;
	public static Properties data=null;
	private static boolean isInitalized=false;
	public static Logger log = null;
	public static WebDriver driver = null;
	public static WebDriverWait wait = null;
	private static FileInputStream ip = null;

	protected TestBase() {
		if(!isInitalized){
			initLogs();
			initConfig();
			initDriver();
		}
	}

	/**
	 * Initialize Logger.
	 */
	private static void initLogs(){
		if (log == null){
			// Initialize Log4j logs

			DOMConfigurator.configure(System.getProperty("user.dir")+File.separator+"config"+File.separator+"log4j.xml");
			log = Logger.getLogger("CustomLogger");
			log.info("Logger is initialized..");
		}
	}


	private static void initConfig() {
		if (config == null) {
			try {
				//initialize env properties file
//				env = new Properties();
//				String fileName = "env"+".properties";;
//				String fileName = "env"+".properties";
//				ip = new FileInputStream(System.getProperty("user.dir")+ File.separator + "config" + File.separator+ fileName);
//				env.load(ip);
//				log.info("Environment = "+ env.getProperty("Environment"));

				//initialize config properties file
				config = new Properties();
				String config_fileName = "config.properties";
				String config_path = System.getProperty("user.dir") + File.separator+ "config" + File.separator + config_fileName;
				FileInputStream config_ip = new FileInputStream(config_path);
				config.load(config_ip);
//				log.info("Config file initialized for environment = "+ env.getProperty("Environment"));

				//initialize data properties file
				data = new Properties();
				String data_fileName = "data.properties";
				String data_path = System.getProperty("user.dir") + File.separator+ "config" + File.separator + data_fileName;
				FileInputStream data_ip = new FileInputStream(data_path);
				data.load(data_ip);
//				log.info("Data file initialized for environment = "+ env.getProperty("Environment"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}


		}
	}


	/**
	 * Initialize Driver.
	 */
	private static void initDriver(){

		if(config.getProperty("browser").equalsIgnoreCase("Firefox") ||config.getProperty("browser").equalsIgnoreCase("FF") ){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+ File.separator+"drivers"+ File.separator  +"geckodriver.exe");
			log.info(config.getProperty("browser")+" driver is initialized..");
		}
		else if (config.getProperty("browser").equals("InternetExplorer")||config.getProperty("browser").equalsIgnoreCase("IE")){
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+ File.separator+"drivers"+ File.separator  +"IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			log.info(config.getProperty("browser")+" driver is initialized..");
		}
		else if (config.getProperty("browser").equals("GoogleChrome")||config.getProperty("browser").equalsIgnoreCase("CHROME")){
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ File.separator +"drivers"+ File.separator +"chromedriver.exe");
			driver = new ChromeDriver();
			log.info(config.getProperty("browser")+" driver is initialized..");
		}

		String waitTime = "15";
		driver.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
		driver.manage().window().setPosition(new Point(0, 0));
		driver.manage().window().maximize();

		//Explicit Wait + Expected Conditions
		wait=new WebDriverWait(driver, 120);
	}
	
	@AfterSuite
	public void tearDown() {
		quitDriver();
	}

	/**
	 * Read Properties.
	 */
	protected static String getPropertyValue(String PropertyKey){
		Properties props=null;
		FileInputStream fin =null;
		String PropertyValue = null;

		try {
			File f = new File(System.getProperty("user.dir")+File.separator+"config"+File.separator+"env.properties");
			fin = new FileInputStream(f);
			props = new Properties();
			props.load(fin);
			PropertyValue = props.getProperty(PropertyKey);
		} catch(Exception e){
			System.out.println(e.getMessage());
		} 

		return PropertyValue;
	}


	/**
	 * Define path for Screenshot file.
	 */
	protected String getScreenshotSavePath() {
		String packageName = this.getClass().getPackage().getName();
		File dir = new File(System.getProperty("user.dir")+File.separator+"screenshot"+File.separator + packageName + File.separator);
		dir.mkdirs();
		return dir.getAbsolutePath();
	}


	/**
	 * Take Screenshot on failure.
	 */
	protected void getScreenshot() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf.format(new Date());
		String url = driver.getCurrentUrl().replaceAll("[\\/:*\\?\"<>\\|]", "_");
		String ext = ".png";
		String path = getScreenshotSavePath() + File.separator + date + "_" + url + ext;

		try {
			if (driver instanceof TakesScreenshot) {
				File tmpFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				org.openqa.selenium.io.FileHandler.copy(tmpFile, new File(path));
				log.error("Captured Screenshot for Failure: "+path);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Assert Actual and Expected Strings
	 */

	protected void assertStrings(String actual, String expected){


		try{
			Assert.assertEquals(actual, expected);
			log.info("Actual string: [ "+actual+" ] does match with Expected string: [ "+expected+" ]");		

		}catch(AssertionError e){
			log.error("Actual string: [ "+actual+" ] does not match with Expected string: [ "+expected+" ]");
			getScreenshot();
			Assert.fail();

		}

	} 

	/**
	 * Quit Driver.
	 */
	public static void quitDriver() {

		driver.quit();
		driver = null;
		log.info("Closing Browser.");

	}

}
