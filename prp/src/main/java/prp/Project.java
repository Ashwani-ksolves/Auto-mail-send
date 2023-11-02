package prp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.FileReader;
import java.io.IOException;
import java.lang.module.ModuleDescriptor.Exports;
import java.util.Properties;

import org.apache.commons.codec.binary.*;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import net.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Global;

public class Project {
	ExtentReports reports;
	ExtentTest test;
	FirefoxDriver driver; 
	Properties p1=new Properties();
	long start;
	long end;
	@BeforeTest
	public void St()
	{
		driver = new FirefoxDriver();
		driver.get("http://lokbest.kappso.com/customer/account/login/");
		System.out.println(driver.getTitle());
		Assert.assertEquals("Login Verkäufer", driver.getTitle());
		reports = new ExtentReports(System.getProperty("user.dir")+"/insert/ExtentReportResults.html",true);
		test = reports.startTest("ExtentDemo");
	}
	@Test(priority=1)
	public void faul_mail_send() throws IOException
	{
		start=System.currentTimeMillis();
		System.out.println(start);
		System.out.println("prior 2");
		//driver.navigate().refresh();
		driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("test1@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("Abhinav@ksolve");
		driver.findElement(By.xpath("//*[@id=\"send2\"]")).click();
		String exp="Marketplace Seller Dashboard";
		if(driver.getTitle().equals(exp))
		{
			System.out.println("Login Successfull");
			
			 end=System.currentTimeMillis();
		        System.out.println(end);
		        test.log(LogStatus.PASS, "Status passed");
			 // Recipient's email ID needs to be mentioned.
	        
		}
		else
		{
			
			 FileReader reader = new FileReader("/home/ashwaniksi147/eclipse-workspace/prp/Fetch");
			 Properties p = new Properties();
			 p.load(reader);
			System.out.println("Else part");
			//p.load(reader);
//			String to = "t90973979@gmail.com";
//
//	        // Sender's email ID needs to be mentioned
//	        String from = "ashwanikum13@gmail.com";
//
//	        // Assuming you are sending email from through gmails smtp
//	       String host = "smtp.gmail.com";
	        
	        // Get system properties
	        Properties properties = System.getProperties();
	        String to=p.getProperty("to");
	        String from = p.getProperty("from");
	        String host=p.getProperty("host");

	        // Setup mail server
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");
	        
	        byte[] decodedString=Base64.decodeBase64("a2lhbiBpZHRzIHRkaWYgcXJhcw==");
	        String s=new String(decodedString);
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication("ashwanikum13@gmail.com",s);
	            }

	        });
	        // Used to debug SMTP issues
	        //session.setDebug(true);
	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Set Subject: header field
	            message.setSubject("Subject: Lokbest login");

	            // Now set the actual message
	            message.setText("lokbest login failed...");

	            System.out.println("sending...");
	            // Send message
	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	            end=System.currentTimeMillis();
		        System.out.println(end);
		      
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	        test.log(LogStatus.FAIL, "Falied");
	        Assert.assertEquals("Marketplace Seller Dashboard", driver.getTitle());
	       // Assert.assertEquals(exp, "Marketplace Seller Dashboard");
	       
		}
		
		//System.out.println("Successfull...");
	}
	@Test(priority=2)
	public void timecheck()
	{
		long time=end-start;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(time); 
		System.out.println("seconds="+seconds);
		if(seconds>15)
		{
			Assert.assertEquals(false,"Time should be less then 15 seconds");
		}
		test.log(LogStatus.PASS, "Passed");
	}
@AfterTest
public void jj()
{
	System.out.println("After test");
	driver.close();
	reports.endTest(test);
	reports.close();
}
}
