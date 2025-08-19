package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	WebDriver driver;
	//every page object class requires constructor 
	//so creating here and inheriting in all classes
	public BasePage(WebDriver driver) //it is a constructor it name should be same as class name
	{
		this.driver=driver;
		//assigning the class driver to driver that passed from test case
		//This will invoke the driver
		PageFactory.initElements(driver,this);
	}
}
