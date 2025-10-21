package docker;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

	public class DockerListener implements ITestListener {
		

		public static Properties prop;
		public static ExtentSparkReporter sparkReporter;
		public static ExtentReports extentReporter;
		public static ExtentTest test;
		
	    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	    
	    private static final Logger logger = LogManager.getLogger(DockerListener.class);

		URL url;

		public DockerListener(){
			try {
				url = new URL("http://localhost:4444/wd/hub");
				prop = new Properties();
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
			extentReporter = new ExtentReports(".\\src\\test\\reports\\Docker_SeleniumGrid_Automation Report.html");
			logger.info("Starting the test...");
			//extentReporter.attachReporter(sparkReporter);
		} 
	
		
		@BeforeTest
		@Parameters(value= {"browser"})
	    public void onTestStart(ITestContext result, String browser) {
	   
	        try {
	        
	             browser = result.getCurrentXmlTest().getParameter("browser");
	            if (browser == null) {
	                browser = "chrome"; 
	            }

	            DesiredCapabilities capabilities = new DesiredCapabilities();
	            switch (browser.toLowerCase()) {
	                case "firefox":
	                    capabilities.setBrowserName("firefox");
	                    System.out.println("Launching Firefox...");
	                    break;
	                case "edge":
	                    capabilities.setBrowserName("MicrosoftEdge");
	                    System.out.println("Launching Edge...");
	                    break;
	                case "chrome":
	                default:
	                    capabilities.setBrowserName("chrome");
	                    System.out.println("Launching Chrome...");
	                    break;
	            }
	            
	            WebDriver remoteDriver = new RemoteWebDriver(url, capabilities);
	            driver.set(remoteDriver);
	            driver().manage().window().maximize();
	    		driver().manage().deleteAllCookies();
	    		//driver.timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	    		driver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    		test = extentReporter.startTest(result.getName());
	    		
	    		test.log(LogStatus.INFO,"HireSense LoginTest Started");
	    		driver().get("https://dev.d1m4a8cud2psxj.amplifyapp.com/login");
	    		driver().findElement(By.id("email")).sendKeys("cbts-ta@cbts.com");
	    		driver().findElement(By.id("password")).sendKeys("hiresense");
	    		driver().findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();
	    		logger.info("Logged in Hiresense successfully");
				
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			driver().navigate().refresh();
			}
	    

	    public static WebDriver driver()
		{
			return driver.get();
			
		}
		
	    
	    @Override
		public void onTestSuccess(ITestResult result) {	    	
	    	   
			test.log(LogStatus.PASS, "Test case Passed is "+ result.getName() +test.addScreenCapture(getScreenShot()));
			logger.info("Test case passed" +result.getName());
			ThreadLocalExtent.endReport();
			
		}

		@Override
		public void onTestFailure(ITestResult result){
			
			test.log(LogStatus.FAIL, "Test case Fail is "+ result.getName() +test.addScreenCapture(getScreenShot()));
			logger.error("Test case failed" +result.getName());
			ThreadLocalExtent.endReport();
			
		}

		@Override
		public void onTestSkipped(ITestResult result){
			
			test.log(LogStatus.SKIP, "Test case Skipped is "+ result.getName());
			logger.info("Test case skipped" +result.getName());
			ThreadLocalExtent.endReport();

		}

		/*
		 * @Override public void onFinish(ITestContext context){
		 * this.extentReporter.flush(); }
		 */

		public static String getScreenShot() {
			
			try
			{
				String fileName = timeStamp() + "_" + random(1, 999) + ".png";
				String returnPath = "./screenshot/" + fileName;
				String screenShotFileName = System.getProperty("user.dir")
		                + "/src/test/reports/Screenshot/" + fileName;
				TakesScreenshot scrShot = (TakesScreenshot) driver();
		        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		        File destFile = new File(screenShotFileName);
		        FileUtils.copyFile(srcFile, destFile);
		        return returnPath;
		        
		    } 
			catch (Exception e) {
		        e.printStackTrace();
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

 
	  
		 @AfterTest(alwaysRun = true)
		    public void tearDown() {
		        if (driver.get() != null) {
		            driver.get().quit();
		            driver.remove();
		        }
		    }
		
		 
	    
	}
