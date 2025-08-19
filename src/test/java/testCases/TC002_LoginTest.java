package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups={"Sanity","Master"})
	public void verifyLogin()
	{
		logger.info("***starting Tc002_LoginTest***");
		
		try
		{
		//HomePage
		HomePage hp=new HomePage(driver);
		hp.clickMyaccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));  //should keep in "" only
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExist();
		
		//Assert.assertEquals(targetPage, true,"Login Failed"); //if assert fails the msg will print
		Assert.assertTrue(targetPage);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***Finished Tc002_LoginTest***");
	}
}
