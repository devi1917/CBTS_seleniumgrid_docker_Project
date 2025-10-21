package testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import docker.DockerListener;

public class ResumeUploadValidation extends DockerListener{
	
	private static final Logger logger = LogManager.getLogger(HireSenseTests.class);
	
	@Test (enabled = true)
	public void UploadFileValidate()
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

}
