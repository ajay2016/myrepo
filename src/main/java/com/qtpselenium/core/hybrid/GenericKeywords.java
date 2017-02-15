package com.qtpselenium.core.hybrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qtpselenium.core.hybrid.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenericKeywords {
	public WebDriver driver;
	public Properties prop;
	ExtentTest test;
	
	public GenericKeywords(ExtentTest test){
		this.test =test;
		prop =new Properties();
		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//project.properties");
			prop.load(fs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openBrowser(String browserType){	
		
		test.log(LogStatus.INFO, "Opening Browser");
		
		//Initialize WebDriver and point the reference to the implementing classes
		
		if(browserType.equals("Mozilla")){
			//Creating Profile
			System.setProperty("webdriver.gecko.driver", "D:\\webdriver\\geckodriver.exe");
			driver = new FirefoxDriver();
			
			
		}else if (browserType.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "D:\\webdriver\\chromedriver.exe");
			driver = new ChromeDriver();
			
			
		}else if (browserType.equals("IE")){
			System.setProperty("webdriver.ie.driver", "D:\\webdriver\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
					
	}
	
	public void navigate(String urlKey){
		driver.get(prop.getProperty(urlKey));
		test.log(LogStatus.INFO, "Navigating to the url "+prop.getProperty(urlKey));
		
		
	}
	
	public void click(String locatorKey){
		test.log(LogStatus.INFO, "Clicking on link"+prop.getProperty(locatorKey));		
		WebElement e = getElement(locatorKey);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(e));
		e.click();	
			
	}
	
	public void input(String locatorKey, String data) {
		test.log(LogStatus.INFO,"Typing on "+prop.getProperty(locatorKey));			
		WebElement e = getElement(locatorKey);	
		//WebDriverWait wait = new WebDriverWait(driver, 20);
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(locatorKey))));		
		e.sendKeys(data);	
		
	}
	
	public void closeBrowser(){
		test.log(LogStatus.INFO, "Closing Browser");  
		
		//driver.quit();
	}
	public void verifyText(String locatorKey, String expectedText){
		WebElement e = getElement(locatorKey);
		String actualText = e.getText();
		
		
	}
	
	/************************Utility functions****************************/
	//if element jis not found error is reported
	public WebElement getElement(String locatorKey){
		WebElement e = null;
		try{
			if(locatorKey.endsWith("_id"))
				e = driver.findElement(By.id(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_xpath"))
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				e = driver.findElement(By.name(prop.getProperty(locatorKey)));
		}catch(Exception ex){
			reportFailure ("Failure in Element Extraction - "+ locatorKey);
			test.log(LogStatus.FAIL, "Failure in Element Extraction - "+ locatorKey);
			Assert.fail("Failure in Element Extraction - "+ locatorKey);
		}
		
		return e;

	}
	
	
	/**************Reporting******************/
	
	public void reportFailure(String failureMessage){
		takeScreenShot();
		test.log(LogStatus.FAIL,failureMessage);
	}
	public void takeScreenShot(){
		// decide name - time stamp
		Date d = new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
		String path=Constants.SCREENSHOT_PATH+screenshotFile;
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// embedding image in the report
		test.log(LogStatus.INFO, test.addScreenCapture(path));
	}

	

}
