package docker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class dockerWithChrome implements ITestListener{

	public static Properties prop;
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extentReporter;
	public static ExtentTest test;

	protected static RemoteWebDriver driver;
	//protected static WebDriver driver;
	URL url;
    //allure
	public dockerWithChrome(){
		try {
			url = new URL("http://localhost:4444/wd/hub");
			prop = new Properties();
			String filepath = System.getProperty("user.home");
			FileInputStream inputstm = new FileInputStream("/Users/devi.raghu/OneDrive - CBTS I OnX/IdeaProjects/IdeaProjects/CBTSProject/src/main/java/com/onx/qa/config/qa.properties");
			prop.load(inputstm);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStart(ITestContext context){
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\src\\test\\reports\\MyReport.html");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Regression Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		extentReporter = new ExtentReports(".\\src\\test\\reports\\CBTS_Automation_Report.html");
		//extentReporter.attachReporter(sparkReporter);
	} 

	@Override
	public void onTestStart(ITestResult result) {
		//String browserName = prop.getProperty("browser");

		try {
			url = new URL("http://localhost:4444/wd/hub");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String[] browsers = {"chrome", "firefox", "edge"};
		Random random = new Random();
		int randomIndex = random.nextInt(browsers.length);
		//capabilities.setBrowserName(browsers[randomIndex]);
		capabilities.setBrowserName("chrome");
		capabilities.setVersion("latest");
		driver = new RemoteWebDriver(url, capabilities);
		//driver = WebDriverManager.chromedriver().create();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		//driver.timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		test = extentReporter.startTest(result.getName());
		test.log(LogStatus.INFO,"Test Started");
		driver.get("https://www.amazon.com");
		System.out.println(driver.getTitle());
		driver.get("https://www.amazon.in/");
		//driver.get(prop.getProperty("url"));
		//driver.get(prop.getProperty("url"));
		test.log(LogStatus.INFO,"URL Is Loading");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		driver.navigate().refresh();
	}

	@Override
	public void onTestSuccess(ITestResult result){
		//test = extentReporter.createTest(result.getName());
		//reportMethods.reportPass(result.getName());
		test.log(LogStatus.PASS, "Test case Passed is "+ result.getName());

		ThreadLocalExtent.endReport();
		//ThreadLocalDriver.getDriver().quit();
		driver.quit();
	}

	@Override
	public void onTestFailure(ITestResult result){
		//test = extentReporter.createTest(result.getName());
		//reportMethods.reportFail(result.getName());
		test.log(LogStatus.FAIL, "Test case Passed is "+ result.getName());

		ThreadLocalExtent.endReport();
		//ThreadLocalDriver.getDriver().quit();
		driver.quit();
	}

	@Override
	public void onTestSkipped(ITestResult result){
		//test = extentReporter.createTest(result.getName());
		test.log(LogStatus.SKIP, "Test case Skipped is "+ result.getName());

		//ThreadLocalExtent.endReport();
		//ThreadLocalDriver.getDriver().quit();
		driver.quit();
	}

	@Override
	public void onFinish(ITestContext context){
		this.extentReporter.flush();
	}

	public static String getScreenShot() {
		try {
			String var10000 = timeStamp();
			String filePath = var10000 + random(1, 999) + ".png";
			String returnPath = "./screenshot/" + filePath;
			String screenShotFileName = ".\\src\\test\\reports\\Screenshot\\" + filePath;
			TakesScreenshot scrShot = (TakesScreenshot) driver;
			File SrcFile = (File)scrShot.getScreenshotAs(OutputType.FILE);
			File desti = new File(screenShotFileName);
			FileUtils.copyFile(SrcFile, desti);
			return returnPath;
		} catch (Exception e) {
			return null;
		}
	}

	public static int random(int min, int max) {
		//min = min-1;
		max = max + 1;
		Random ran = new Random();
		int x = ran.nextInt(max - min) + min;
		return x;
	}

	public static String timeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Timestamp today;
		today = new Timestamp(date.getTime());
		String time = sdf.format(today);
		return time;
	}


	/*
	public void setupChrome() throws MalformedURLException {

		url = new URL("http://localhost:4444/wd/hub");
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("latest");
        driver = new RemoteWebDriver(url, capabilities);
        driver.get("https://www.amazon.in");
        System.out.println(driver.getTitle());


		driver = WebDriverManager.chromedriver().create();
		driver.get("https://www.amazon.in");
		System.out.println(driver.getTitle());
	}*/



}
