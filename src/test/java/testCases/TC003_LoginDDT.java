package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


public class TC003_LoginDDT extends BaseClass{
	
		//name of data provider     //class in which data provider is present
    @Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="DataDriven")  
    public void verify_loginDDT(String email,String pwd,String exp) throws InterruptedException   //if dp present in same class this parameter not req
    {
    	logger.info("*** starting TC003_LoginDDT ***");
    	
    	try
    	{
    	//HomePage
		HomePage hp=new HomePage(driver);
		hp.clickMyaccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);  //should keep in "" only
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExist();
		
        // Add assertion or validation logic here
		
		/*
		 * Data is valid   - login success  - test pass  - logout
		 * Data is valid   - login failed   - test fail
		 * 
		 * Data is invalid - login success  - test fail  - logout
		 * Data is invalid - login failed   - test pass
		 */

		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
    	}catch(Exception e)
    	{
    		Assert.fail();
    	}
    	Thread.sleep(3000);
    	logger.info("*** Finished TC003_LoginDDT ***");
    }
}
