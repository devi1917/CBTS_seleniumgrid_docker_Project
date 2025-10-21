package testcases;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import docker.DockerListener;

public class TestCase10 extends DockerListener{
	
	@Test
	public void Login() throws IOException, InterruptedException {
		
//		FileReader reader = new FileReader("C:\\Users\\vijayalakshmi.s\\eclipse-workspace\\SeleniumGridDocker\\src\\test\\resources\\config.properties");
//		
//		Properties prop = new Properties();
//		prop.load(reader);
//		driver.get(prop.getProperty("appURL"));
//	
		driver().navigate().to("https://demo.nopcommerce.com/");
		System.out.println(driver().getTitle());
		//test.log(LogStatus.PASS, "HireSense logged in successfully"+test.addScreenCapture(getScreenShot()));

	}

}
