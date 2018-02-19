package test1;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.Test;
//import java.util.concurrent.TimeUnit;

public class NotepadTest {
	
		 @Test
		 
		 public void test() throws IOException{
		  DesktopOptions options= new DesktopOptions();
		  options.setApplicationPath("C:\\WINDOWS\\system32\\calc.exe");
		  System.out.println("Hello");
		  try{
		   WiniumDriver driver=new WiniumDriver(new URL("http://localhost:9999"),options);
		   System.out.println("Hello");
		   driver.findElementByName("7").click();
		   System.out.println("Hello");
		   driver.findElementByName("7").click();
		   System.out.println("Hello");
		   driver.findElementByName("7").click();
		   System.out.println("Hello");
		   driver.findElementByName("Subtract").click();
		   System.out.println("Hello");
		   driver.findElementByName("9").click();
		   System.out.println("Hello");
/*		   WebDriver augmentedDriver = new Augmenter().augment(driver);
	       File screenshot = ((TakesScreenshot)augmentedDriver).
	                            getScreenshotAs(OutputType.FILE);*/
		   driver.close();
		  }
		  catch(Exception e){
		   System.out.println(e.getMessage());
		  }
		 }
	}


			
