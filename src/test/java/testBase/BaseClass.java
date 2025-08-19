package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager; //Log4j
import org.apache.logging.log4j.Logger;//Log4j

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups={"Sanity","Regression","Master"/*,"DataDriven"*/})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		//loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties(); //creating object for properties class
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());  //captures current class and load log4j file for that class
		
		//for grid setup
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
		    DesiredCapabilities capabilities = new DesiredCapabilities(); //for setting os & browser

		    // os
		    if(os.equalsIgnoreCase("windows"))
		    {
		        capabilities.setPlatform(Platform.WIN10);
		    }
		    else if (os.equalsIgnoreCase("mac"))
		    {
		        capabilities.setPlatform(Platform.MAC);
		    }
		    else
		    {
		        System.out.println("No matching os");
		        return;
		    }

		    // browser
		    switch(br.toLowerCase())
		    {
		        case "chrome": capabilities.setBrowserName("chrome"); break;
		        case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
		        default: System.out.println("No matching browser"); return;
		    }
		    
		    driver=new RemoteWebDriver(new URL("http://192.168.1.103:4444/wd/hub"),capabilities);
		    //driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}

	    if(p.getProperty("execution_env").equalsIgnoreCase("local"))
	    {
	    	switch(br.toLowerCase())
			{
			case "chrome": driver=new ChromeDriver(); break;
			case "edge": driver=new EdgeDriver(); break;
			case "firefox": driver=new FirefoxDriver(); break;
			default: System.out.println("Invalid browser name.."); return;
			}
	    }

		
		driver.manage().deleteAllCookies(); //deletes all cookies from driver
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(p.getProperty("appURL")); //reading URL from properties file
	}
	//http://localhost/opencart/upload/index.php
	
	@AfterClass(groups={"Sanity","Regression","Master"/*,"DataDriven"*/})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomString()  //to generate string randomly
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}
	
	public String randomNumber()  //to generate string randomly
	{
		String generatednumber=RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}
	
	public String randomAlphaNumeric()  //to generate string randomly
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(3);
		String generatednumber=RandomStringUtils.randomNumeric(3);
		return (generatedstring+"@"+generatednumber);
	}
	
	public String captureScreen(String tname) throws IOException {
	    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

	    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

	    String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
	    File targetFile = new File(targetFilePath);

	    sourceFile.renameTo(targetFile);

	    return targetFilePath;
	}

}
