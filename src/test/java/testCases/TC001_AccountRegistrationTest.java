package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("***starting TC001_AccountRegistrationTest***");
			
		try
		{
		HomePage hp=new HomePage(driver); //creating object for home page & passing the driver
		hp.clickMyaccount();
		logger.info("***clicked on MyAccount link***");
		
		hp.clickRegister();
		logger.info("***clicked on Register link***");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver); //creating object for AR page & passing the driver
		
		logger.info("Providing customer details..");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");
		regpage.setPhn(randomNumber());
		
		String password=randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setcnfmPassword(password);
		
		regpage.clickPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message..");
		String confmsg=regpage.getConfirmationMsg();
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!!");
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***Finished TC001_AccountRegistrationTest***");
	}
	
	
}
