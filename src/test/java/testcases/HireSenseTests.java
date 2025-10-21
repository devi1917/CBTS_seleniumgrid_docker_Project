package testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import docker.DockerListener;


public class HireSenseTests extends DockerListener{
	
	String title;
	private static final Logger logger = LogManager.getLogger(HireSenseTests.class);
	
	@Test(enabled=true)
	public void LogInVerification()
	{
		try
		{
		String title = driver().getTitle();
		if(title.equalsIgnoreCase("Hire Sense"))
		   {
		   test.log(LogStatus.PASS, "HireSense logged in successfully"+test.addScreenCapture(getScreenShot()));
		   logger.info("HireSense logged in successfully");
	       //driver.quit();
		   }
		
		   else
		   {
			   test.log(LogStatus.FAIL, "Hiresense login failed"+test.addScreenCapture(getScreenShot()));
			   logger.error("Error in logging HireSense");
		   }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	@Test(enabled = true)
	public void HireSenseResumeUpload() throws AWTException, InterruptedException, IOException
	{
		Thread.sleep(5000);
		test.log(LogStatus.INFO,"HireSense Resume upload Test started");
		driver().findElement(By.xpath("//a[@class= 'btn btn-home-upload' and contains(text(),'Upload Resume')]")).click();
		System.out.println(driver().getTitle());
		String text = driver().findElement(By.xpath("//h1[contains(text(), 'Resumes')]")).getText();
		System.out.println("PageTitle: " +text);
		
		WebElement uploadResume = driver().findElement(By.xpath("//input[@type='file']"));
		((JavascriptExecutor) driver()).executeScript("arguments[0].style.display='block';", uploadResume);
		//driver.setFileDetector(new LocalFileDetector());

		//File file = new File("/home/seluser/uploads/Nareendhiran GR Resume.docx");
		uploadResume.sendKeys("/home/seluser/uploads/Yosuva Immanuel.docx");
		
		  driver().findElement(By.xpath("//button[@class='btn btn-primary']")).click();
		  WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(20));
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='card-header-success-text']")));
			if(driver().findElement(By.xpath("//p[@class='card-header-success-text']")).
			  getText().equalsIgnoreCase("1 resume uploaded successfully")) {
			  System.out.println("Resume uploaded successfully"); 
			  test.log(LogStatus.PASS, "Resume uploaded successfully "+test.addScreenCapture(getScreenShot()));
			  logger.info("Resume uploaded successfully");
			  } 
			  else {
			     System.out.println("Resume not uploaded");
			     test.log(LogStatus.FAIL, "Resume upload failed "+test.addScreenCapture(getScreenShot()));
			     logger.error("Resume upload failed");
			  }
		//driver.quit();
		/*
		 * if (!file.exists()) { throw new RuntimeException("❌ File not found: " +
		 * file.getAbsolutePath()); }
		 */
		/*
		 * if(driver instanceof RemoteWebDriver){ ((RemoteWebDriver)
		 * driver).setFileDetector(new LocalFileDetector()); }
		 */
		
        //WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='table-container-card']")));
		
		/*
		 * WebElement uploadResume = driver.findElement(By.
		 * xpath("//div[contains(text(), 'Drop your resume here or click to browse')]"))
		 * ; uploadResume.
		 * sendKeys("C:\\Users\\vijayalakshmi.s\\Documents\\Sample_Resumes\\Nareendhiran GR Resume.docx"
		 * );
		 */
		                                     
		/*
		 * String filePath =
		 * "C:\\Users\\vijayalakshmi.s\\Documents\\Sample_Resumes\\Nareendhiran GR Resume.docx"
		 * ; StringSelection selection = new StringSelection(filePath);
		 * Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection,
		 * null);
		 * 
		 * Robot robot = new Robot(); robot.delay(2000);
		 * 
		 * robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_V);
		 * robot.keyRelease(KeyEvent.VK_V); robot.keyRelease(KeyEvent.VK_CONTROL);
		 * 
		 * robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER);
		 * 
		 * Thread.sleep(3000); driver.quit();
		 */
		
	
	}
	
	@Test(enabled = true)
	public void ResumeUploadUsingPasteOption() throws IOException
	{

		try
		{
		  String resumeText =
		  Files.readString(Path.of(".\\src\\test\\resources\\testcase\\resume.txt"));
		  //System.out.println(" Full resume: " + resumeText);
		  driver().findElement(By.xpath("//textarea[@class='resume-textarea']")).sendKeys("Giridharan resume"); 
		  WebDriverWait waits = new WebDriverWait(driver(), Duration.ofSeconds(20));
		  waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn-primary' and contains(text(), 'Upload Resume Text')]"))); 
		  driver().findElement(By.xpath("//button[@class='btn btn-primary' and contains(text(), 'Upload Resume Text')]")).click();
		  System.out.println("Resume uploaded successfully using paste option");
		  test.log(LogStatus.PASS, "Resume uploaded successfully using paste option"+test.addScreenCapture(getScreenShot()));
		  logger.info("Resume uploaded successfully using paste option");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Resume upload failed using paste option");
		}
	}
	
	@Test (enabled = true)
	public void UploadFileValidation()
	{
		try
		{
		driver().findElement(By.xpath("//input[@class='form-control form-control-search']")).sendKeys("Giridharan");
		String StringName = driver().findElement(By.xpath("//tbody[@class='align-top']/tr[1]/td[1]")).getText();
		if(StringName.equalsIgnoreCase("Giridharan"))
			{
				System.out.println("Verified uploaded resume successfully");
				test.log(LogStatus.PASS, "Resume listed successfully "+test.addScreenCapture(getScreenShot()));
				logger.info("Resume listed successfully");
			}
		else
		{
			System.out.println("Uploaded resume not present in the list");
			test.log(LogStatus.FAIL, "Resume is not shown in the list "+test.addScreenCapture(getScreenShot()));
			logger.error("Resume is not shown in the list");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Test(enabled=false)
	public void UploadJobRequisition()
	{
		driver().findElement(By.xpath("//a[@class= 'btn btn-home-upload' and contains(text(),'Job Requisition')]")).click();
		driver().findElement(By.xpath("//textarea[@class='resume-textarea']")).sendKeys("Project Manager");
		driver().findElement(By.xpath("//button[contains(text(), 'Upload Job Requisition Text')]")).click();
		System.out.println("Uploaded Job Requisition text successfully");
		test.log(LogStatus.PASS, "Uploaded Job Requisition text successfully"+test.addScreenCapture(getScreenShot()));
	}
	
	
	

}
