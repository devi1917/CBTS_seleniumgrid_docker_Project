package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import docker.DockerListener;

public class TestCase2 extends DockerListener {
	
   @Test	
   public void loginApp() throws InterruptedException 
	{
	    Thread.sleep(3000);
		driver().navigate().to("https://dev.d1m4a8cud2psxj.amplifyapp.com/");
		System.out.println(driver().getTitle());
		//test.log(LogStatus.PASS, "HireSense logged in successfully"+test.addScreenCapture(getScreenShot()));
	 
	}

}
