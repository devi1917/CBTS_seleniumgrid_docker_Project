package testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import docker.DockerListener;

public class LogInVerification extends DockerListener{
	
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

}
