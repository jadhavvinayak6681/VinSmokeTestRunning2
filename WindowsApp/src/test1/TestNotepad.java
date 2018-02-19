package test1;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.junit.AfterClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class TestNotepad {
	
	WiniumDriver driver = null;
	@BeforeClass
	public void setupEnvironmnet() throws IOException 
	{
		DesktopOptions options = new DesktopOptions();
		options.setApplicationPath("C:\\WINDOWS\\system32\\notepad.exe");
		try
		{
			driver=new WiniumDriver(new URL("http://localhost:9999"),options);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void testNotepadapp()
	{
		driver.findElementByClassName("Edit").sendKeys("This is Windows app test");
		System.out.println("Hello");
	}
	@AfterClass
	public void tearDown() throws IOException
	{
		driver.close();
	}

}
