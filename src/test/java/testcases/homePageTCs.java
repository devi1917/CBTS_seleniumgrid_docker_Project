package testcases;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import docker.dockerWithChrome;
import repository.homePage;

public class homePageTCs extends dockerWithChrome{

	
	
	@BeforeTest
	void setUp() throws InterruptedException, IOException {
		//setupChrome();
		Runtime.getRuntime().exec("cmd /c start start_dockergrid.bat");
		Thread.sleep(15000);
	}
	
	@Test(enabled = true)
	public void tc001_Login() throws InterruptedException {
		homePage hmpg = new homePage(driver);
		hmpg.clickContinueShoppingButton();
		hmpg.clickHeaderDropDownSignIn();
		hmpg.enterMobileNoOrEmailAndContinue();
		hmpg.enterPasswordAndClickOnSignIn();
		hmpg.verifyUserIsLoggedIn();
	}
	
	@Test (enabled = true)
	public void tc002_VerifySearchFunctionality() {
		homePage hmpg = new homePage(driver);
		hmpg.clickContinueShoppingButton();
		hmpg.searchkeyword(true, "flower");
		hmpg.searchkeyword(false, "asdfgh jkl mnbvcxz");
	}
	
	@AfterTest
	void tearDown() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("cmd /c start stop_dockergrid.bat");
		Thread.sleep(15000);
		//driver.quit();
	}
}
