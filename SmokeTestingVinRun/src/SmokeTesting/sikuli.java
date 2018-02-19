package SmokeTesting;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class sikuli 
{
	public static void siluliMethod(WebDriver driver, String sLocatorType, String sIdentification, String sTextToSearch) throws FindFailed
		{
		Screen screen = new Screen();
		Pattern pattern = new Pattern(sIdentification); 
		switch (sLocatorType)
		{
		case "Click" :
			screen.click(pattern);
			System.out.println("Click *");
			break;
		case "EnterPath" :
			System.out.println("Enter");
			driver.switchTo().alert();
			System.out.println(sIdentification );
			screen.find(sIdentification);
			System.out.println(sIdentification);
			System.out.println("sTextToSearch :" + sTextToSearch);
			screen.type(sIdentification,sTextToSearch); // click(pattern);
			//alert.accept();
			break;
		case "Find" :
			System.out.println("In Find");
			screen.find(sIdentification);
			break;
		case "close":
			System.out.println("In Close");
			Actions actions = new Actions(driver);
	        actions.sendKeys(Keys.F4);
			break;
		}// switch
		}
}
