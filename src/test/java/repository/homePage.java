package repository;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import docker.dockerWithChrome;

public class homePage extends dockerWithChrome{
	private RemoteWebDriver driver;
	//private WebDriver driver;



	public homePage(RemoteWebDriver driver){
	//public homePage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	

	//Locators
	//By headerDropDownSignIn = By.xpath("//div[@id='nav-link-accountList']");	

	@FindBy(xpath="//div[@id='nav-link-accountList']") 
	WebElement headerDropDownSignIn;
	@FindBy(xpath="//input[@id='twotabsearchtextbox']") 
	WebElement searchBox;
	@FindBy(xpath="//input[@id='ap_email_login']") 
	WebElement mobileEmailTextField;
	@FindBy(xpath="//span[@id='continue']") 
	WebElement continueButton;
	@FindBy(xpath="//input[@id='ap_password']") 
	WebElement passwordTextField;
	@FindBy(xpath="//span[@id='auth-signin-button']") 
	WebElement signInButton;
	@FindBy(xpath="//span[@id='nav-link-accountList-nav-line-1']") 
	WebElement headerText;
	@FindBy(xpath="//input[@id='nav-search-submit-button']") 
	WebElement searchButton;
	@FindBy(xpath="//div[contains(@class,'breadcrumb-all-filters-button-container')]//span[contains(text(),'results for')]") 
	List<WebElement> searchResultsBreadCrumb;
	@FindBy(xpath="//span[contains(text(),'No results for')]") 
	List<WebElement> searchNoResults;
	@FindBy(xpath="//span[text()='Try checking your spelling or use more general terms']") 
	List<WebElement> searchTryCheckingYourSpellText;
	@FindBy(xpath="//button[text()='Continue shopping']") 
	List<WebElement> continueShoppingButton;
	


	//Methods

	/**
	 * clickContinueShoppingButton
	 */
	public void clickContinueShoppingButton() {
		if(continueShoppingButton.size()!=0 && continueShoppingButton.get(0).isDisplayed()) {
			continueShoppingButton.get(0).click();
		}
	}
	
	/**
	 * click Header Drop Down SignIn
	 * @throws InterruptedException
	 */
	public void clickHeaderDropDownSignIn() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='twotabsearchtextbox']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBox);
		headerDropDownSignIn.click();
	}

	/**
	 * enter Mobile No Or Email And Continue
	 */
	public void enterMobileNoOrEmailAndContinue() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ap_email_login']")));
		mobileEmailTextField.sendKeys("9962905981");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='continue']")));
		continueButton.click();
	}

	/**
	 * enter Password And Click On SignIn
	 */
	public void enterPasswordAndClickOnSignIn() {
		passwordTextField.sendKeys("Testing@123");
		signInButton.click();
	}

	/**
	 * verify User Is LoggedIn
	 */
	public void verifyUserIsLoggedIn() {
		if(headerText.getText().equals("Hello, rdevi.raghu333@gmail.com")) {
			test.log(LogStatus.PASS, "User is logged in successfully "+test.addScreenCapture(getScreenShot()));
		}
		else {
			test.log(LogStatus.FAIL, "Element is not present"+test.addScreenCapture(getScreenShot()));
		}
	}

	public void searchkeyword(boolean action, String keyword) {
		searchBox.sendKeys(keyword);
		searchButton.click();
		if(action) {
			if(searchResultsBreadCrumb.size()!=0 && searchResultsBreadCrumb.get(0).isDisplayed()) {
				test.log(LogStatus.PASS, "Search result is displayed for searched keyword"+test.addScreenCapture(getScreenShot()));}
			else {
				test.log(LogStatus.FAIL, "Search result is not found for searched keyword"+test.addScreenCapture(getScreenShot()));
			}
		}
		else {
			if((searchNoResults.size()!=0 && searchNoResults.get(0).isDisplayed()) &&
					(searchTryCheckingYourSpellText.size()!=0 && searchTryCheckingYourSpellText.get(0).isDisplayed())) {
				test.log(LogStatus.PASS, "Search result is displayed for searched keyword"+test.addScreenCapture(getScreenShot()));}
			else {
				test.log(LogStatus.FAIL, "Search result is not found for searched keyword"+test.addScreenCapture(getScreenShot()));	
			}
		}
		searchBox.clear();

	}



}
