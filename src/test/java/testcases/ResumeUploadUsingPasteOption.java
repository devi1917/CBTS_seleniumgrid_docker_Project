package testcases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import docker.DockerListener;

public class ResumeUploadUsingPasteOption extends DockerListener{
	
	private static final Logger logger = LogManager.getLogger(HireSenseTests.class);

	@Test(enabled = true)
	public void ResumeUploadUsingPaste() throws IOException
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

}
