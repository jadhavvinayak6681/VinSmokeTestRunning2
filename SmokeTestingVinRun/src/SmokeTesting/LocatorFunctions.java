package SmokeTesting;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LocatorFunctions {

	public static void ControlToUse(WebDriver driver, String sLocatorType, String sIdentification, boolean bIsClick) {
		// Click
		Actions action = new Actions(driver);
		WebElement ElementSeq = null;
		if (bIsClick) {
			switch (sLocatorType) {
			case "ID":
				action.moveToElement(driver.findElement(By.id(sIdentification))).click().perform();
				break;
			case "NAME":
				action.moveToElement(driver.findElement(By.name(sIdentification))).click().perform();
				break;
			case "XPATH":
				action.moveToElement(driver.findElement(By.xpath(sIdentification))).click().perform();
				break;
			case "LINKTEXT":
				action.moveToElement(driver.findElement(By.linkText(sIdentification))).click().perform();
				break;
			case "PARTLINKTEXT":
				action.moveToElement(driver.findElement(By.partialLinkText(sIdentification))).click().perform();
				break;
			case "CSSPATH":
				action.moveToElement(driver.findElement(By.cssSelector(sIdentification))).click().perform();
				break;
			case "CLASS":
				action.moveToElement(driver.findElement(By.className(sIdentification))).click().perform();
				break;
			case "TAGNAME":
				action.moveToElement(driver.findElement(By.tagName(sIdentification))).click().perform();
				break;
			}
		} else {
		// DoubleClick
			switch (sLocatorType) {
			case "ID":
				ElementSeq = driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ElementSeq = driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ElementSeq = driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ElementSeq = driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ElementSeq = driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ElementSeq = driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ElementSeq = driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ElementSeq = driver.findElement(By.tagName(sIdentification));
				break;
			}
			action.moveToElement(ElementSeq).doubleClick().perform();
		}
	}
	
	public static void ControlToUploadFile(WebDriver driver, String sLocatorType, String sIdentification, String sGetFilePath) throws AWTException {
		// Click
		Actions action = new Actions(driver);
		switch (sLocatorType) {
		case "ID":
			action.moveToElement(driver.findElement(By.id(sIdentification))).click().perform();
			break;
		case "NAME":
			action.moveToElement(driver.findElement(By.name(sIdentification))).click().perform();
			break;
		case "XPATH":
			action.moveToElement(driver.findElement(By.xpath(sIdentification))).click().perform();
			break;
		case "LINKTEXT":
			action.moveToElement(driver.findElement(By.linkText(sIdentification))).click().perform();
			break;
		case "PARTLINKTEXT":
			action.moveToElement(driver.findElement(By.partialLinkText(sIdentification))).click().perform();
			break;
		case "CSSPATH":
			action.moveToElement(driver.findElement(By.cssSelector(sIdentification))).click().perform();
			break;
		case "CLASS":
			action.moveToElement(driver.findElement(By.className(sIdentification))).click().perform();
			break;
		case "TAGNAME":
			action.moveToElement(driver.findElement(By.tagName(sIdentification))).click().perform();
			break;
		}
	    //put path to your image in a clipboard
	    StringSelection sFileToUpload = new StringSelection(sGetFilePath);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sFileToUpload, null);

	    //imitate mouse events like ENTER, CTRL+C, CTRL+V
	    Robot robot = new Robot();
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public static void ControlDropdown(WebDriver driver, String sLocatorType, String sIdentification, String sGetInputValue) {
		// Drop down
		Select dropdown = null;
		switch (sLocatorType) {
		case "ID":
			dropdown = new Select(driver.findElement(By.id(sIdentification)));
			break;
		case "NAME":
			dropdown = new Select(driver.findElement(By.name(sIdentification)));
			break;
		case "XPATH":
			dropdown = new Select(driver.findElement(By.xpath(sIdentification)));
			break;
		case "LINKTEXT":
			dropdown = new Select(driver.findElement(By.linkText(sIdentification)));
			break;
		case "PARTLINKTEXT":
			dropdown = new Select(driver.findElement(By.partialLinkText(sIdentification)));
			break;
		case "CSSPATH":
			dropdown = new Select(driver.findElement(By.cssSelector(sIdentification)));
			break;
		case "CLASS":
			dropdown = new Select(driver.findElement(By.className(sIdentification)));
			break;
		case "TAGNAME":
			dropdown = new Select(driver.findElement(By.tagName(sIdentification)));
			break;
		}
		dropdown.selectByVisibleText(sGetInputValue);
	}

	public static void ControlToUse(WebDriver driver, String sLocatorType,String sIdentification,String sGetInputValue,String sKeyword) {
		// Input, FileOpen, ClearData
		if (sKeyword.matches("Input") | sKeyword.matches("ClearData") ) {
			switch (sLocatorType) {
			case "ID":
				driver.findElement(By.id(sIdentification)).clear();
				break;
			case "NAME":
				driver.findElement(By.name(sIdentification)).clear();
				break;
			case "XPATH":
				driver.findElement(By.xpath(sIdentification)).clear();
				break;
			case "LINKTEXT":
				driver.findElement(By.linkText(sIdentification)).clear();
				break;
			case "PARTLINKTEXT":
				driver.findElement(By.partialLinkText(sIdentification)).clear();
				break;
			case "CSSPATH":
				driver.findElement(By.cssSelector(sIdentification)).clear();
				break;
			case "CLASS":
				driver.findElement(By.className(sIdentification)).clear();
				break;
			case "TAGNAME":
				driver.findElement(By.tagName(sIdentification)).clear();
				break;
			}
		}
		if (!sKeyword.matches("ClearData")) {
		
			switch (sLocatorType) {
			case "ID":
				driver.findElement(By.id(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "NAME":
				driver.findElement(By.name(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "XPATH":
				driver.findElement(By.xpath(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "LINKTEXT":
				driver.findElement(By.linkText(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "PARTLINKTEXT":
				driver.findElement(By.partialLinkText(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CSSPATH":
				driver.findElement(By.cssSelector(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CLASS":
				driver.findElement(By.className(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "TAGNAME":
				driver.findElement(By.tagName(sIdentification)).sendKeys(sGetInputValue);
				break;
			}
		}
	}
	
	// @Vianayak : Generate Random number through code and Inputting generated Random number in text filed
	public static void ControlforRandom(WebDriver driver, String sLocatorType,String sIdentification,String sGetInputValue,String sKeyword) {
		// generate random number and enter in text field
		Random randomGenerator = new Random();
	    for (int idx = 1; idx <= 10; ++idx){
	      int randomInt = randomGenerator.nextInt(100);
	      System.out.println("Generated : " + randomInt);
	      sGetInputValue = Integer.toString(randomInt);
	    }
			switch (sLocatorType) {
			case "ID":
				driver.findElement(By.id(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "NAME":
				driver.findElement(By.name(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "XPATH":
				driver.findElement(By.xpath(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "LINKTEXT":
				driver.findElement(By.linkText(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "PARTLINKTEXT":
				driver.findElement(By.partialLinkText(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CSSPATH":
				driver.findElement(By.cssSelector(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CLASS":
				driver.findElement(By.className(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "TAGNAME":
				driver.findElement(By.tagName(sIdentification)).sendKeys(sGetInputValue);
				break;
			}
		}
	
	public static void ControlKeyPress(WebDriver driver, String sLocatorType,String sIdentification,String sGetInputValue, String sKeyword) {
		// For Key Press
		Actions action = new Actions(driver);
		WebElement EKeyPress = null;
		switch (sLocatorType) {
		case "ID":
			EKeyPress = driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			EKeyPress = driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			EKeyPress = driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			EKeyPress = driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			EKeyPress = driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			EKeyPress = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			EKeyPress = driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			EKeyPress = driver.findElement(By.tagName(sIdentification));
			break;
		}

		switch (sKeyword) {
		case "KEYTAB":
			action.sendKeys(EKeyPress, Keys.TAB).build().perform();
			break;
		case "KEYENTER":
			action.sendKeys(EKeyPress, Keys.ENTER).build().perform();
			break;
		case "KEYF1": // Key press F1 
			action.sendKeys(EKeyPress, Keys.F1).build().perform();
			break;
		case "KEYF2": // Key press F2 
			action.sendKeys(EKeyPress, Keys.F2).build().perform();
			break;
		case "KEYF3": // Key press F3 
			action.sendKeys(EKeyPress, Keys.F3).build().perform();
			break;
		case "KEYF4": // Key press F4
			action.sendKeys(EKeyPress, Keys.F4).build().perform();
			break;
		case "KEYF5": // Key press F5 
			action.sendKeys(EKeyPress, Keys.F5).build().perform();
			break;
		case "KEYF6": // Key press F6 
			action.sendKeys(EKeyPress, Keys.F6).build().perform();
			break;
		case "KEYF7": // Key press F7 
			action.sendKeys(EKeyPress, Keys.F7).build().perform();
			break;
		case "KEYF8": // Key press F8 
			action.sendKeys(EKeyPress, Keys.F8).build().perform();
			break;
		case "KEYF9": // Key press F9 
			action.sendKeys(EKeyPress, Keys.F9).build().perform();
			break;
		case "KEYF10": // Key press F10 
			action.sendKeys(EKeyPress, Keys.F10).build().perform();
			break;
		case "KEYF11": // Key press F11 
			action.sendKeys(EKeyPress, Keys.F11).build().perform();
			break;
		case "KEYF12": // Key press F12 
			action.sendKeys(EKeyPress, Keys.F12).build().perform();
			break;
		}
	}

	public static void ControlHover(WebDriver driver, String sLocatorType,String sIdentification) {
		// Hover on
		WebElement EHoverOn = null;
		switch (sLocatorType) {
		case "ID":
			EHoverOn = driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			EHoverOn = driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			EHoverOn = driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			EHoverOn = driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			EHoverOn = driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			EHoverOn = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			EHoverOn = driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			EHoverOn = driver.findElement(By.tagName(sIdentification));
			break;
		}
		Actions action = new Actions(driver);
		action.moveToElement(EHoverOn).perform();
	}
	
	public static void ControlToSwitchFrame(WebDriver driver, String sLocatorType, String sIdentification) {
		// Switch To Frame
		switch (sLocatorType) {
		case "ID":
			driver.switchTo().frame(driver.findElement(By.id(sIdentification)));
			break;
		case "NAME":
			driver.switchTo().frame(driver.findElement(By.name(sIdentification)));
			break;
		case "XPATH":
			driver.switchTo().frame(driver.findElement(By.xpath(sIdentification)));
			break;
		case "LINKTEXT":
			driver.switchTo().frame(driver.findElement(By.linkText(sIdentification)));
			break;
		case "PARTLINKTEXT":
			driver.switchTo().frame(driver.findElement(By.partialLinkText(sIdentification)));
			break;
		case "CSSPATH":
			driver.switchTo().frame(driver.findElement(By.cssSelector(sIdentification)));
			break;
		case "CLASS":
			driver.switchTo().frame(driver.findElement(By.className(sIdentification)));
			break;
		case "TAGNAME":
			driver.switchTo().frame(driver.findElement(By.tagName(sIdentification)));
			break;
		}
	}

	public static void ControlToSwitchToElementInFrame(WebDriver driver, String sLocatorType, String sIdentification, String sGetInputValue) {
		// Switch To Frame
		switch (sLocatorType) {
		case "ID":
			driver.switchTo().activeElement().findElement(By.id(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.id(sIdentification)).sendKeys(sGetInputValue);
			break;
		case "NAME":
			driver.switchTo().activeElement().findElement(By.name(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.name(sIdentification)).sendKeys(sGetInputValue);
			break;
		case "XPATH":
			driver.switchTo().activeElement().findElement(By.xpath(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.xpath(sIdentification)).sendKeys(sGetInputValue);
			break;
		case "LINKTEXT":
			driver.switchTo().activeElement().findElement(By.linkText(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.linkText(sIdentification)).sendKeys(sGetInputValue);
			break;
		case "PARTLINKTEXT":
			driver.switchTo().activeElement().findElement(By.partialLinkText(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.partialLinkText(sIdentification)).sendKeys(sGetInputValue);
			break;
		case "CSSPATH":
			driver.switchTo().activeElement().findElement(By.cssSelector(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.cssSelector(sIdentification)).sendKeys(sGetInputValue);
			break;
		case "CLASS":
			driver.switchTo().activeElement().findElement(By.className(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.className(sIdentification)).sendKeys(sGetInputValue);
			break;
		case "TAGNAME":
			driver.switchTo().activeElement().findElement(By.tagName(sIdentification)).clear();
			driver.switchTo().activeElement().findElement(By.tagName(sIdentification)).sendKeys(sGetInputValue);
			break;
		}
	}

	public static boolean ControlToVerifyIsEnable(WebDriver driver, String sLocatorType, String sIdentification) {
		// Verify whether the element is Enabled or Not
		boolean bIsEnabled = true;
		switch (sLocatorType) {
		case "ID":
			bIsEnabled=driver.findElement(By.id(sIdentification)).isEnabled();
			break;
		case "NAME":
			bIsEnabled=driver.findElement(By.name(sIdentification)).isEnabled();
			break;
		case "XPATH":
			bIsEnabled=driver.findElement(By.xpath(sIdentification)).isEnabled();
			break;
		case "LINKTEXT":
			bIsEnabled=driver.findElement(By.linkText(sIdentification)).isEnabled();
			break;
		case "PARTLINKTEXT":
			bIsEnabled=driver.findElement(By.partialLinkText(sIdentification)).isEnabled();
			break;
		case "CSSPATH":
			bIsEnabled=driver.findElement(By.cssSelector(sIdentification)).isEnabled();
			break;
		case "CLASS":
			bIsEnabled=driver.findElement(By.className(sIdentification)).isEnabled();
			break;
		case "TAGNAME":
			bIsEnabled=driver.findElement(By.tagName(sIdentification)).isEnabled();
			break;
		}
		return bIsEnabled;
	}

	public static WebElement ControlToUseTable(WebDriver driver, String sLocatorType, String sIdentification) {
		// Table
		WebElement Webtable = null;
		switch (sLocatorType) {
		case "ID":
			Webtable=driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			Webtable=driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			Webtable=driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			Webtable=driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			Webtable=driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			Webtable=driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			Webtable=driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			Webtable=driver.findElement(By.tagName(sIdentification));
			break;
		}
		return Webtable;
	}

	public static List<WebElement> ControlToUseTableRowColumn(WebDriver driver, String sLocatorType, String sIdentification) {
		// Table Row / Column Count
		List<WebElement> TotalRowColumnCount = null;
		switch (sLocatorType) {
		case "ID":
			TotalRowColumnCount=driver.findElements(By.id(sIdentification));			
			break;
		case "NAME":
			TotalRowColumnCount=driver.findElements(By.name(sIdentification));			
			break;
		case "XPATH":
			TotalRowColumnCount=driver.findElements(By.xpath(sIdentification));			
			break;
		case "LINKTEXT":
			TotalRowColumnCount=driver.findElements(By.linkText(sIdentification));			
			break;
		case "PARTLINKTEXT":
			TotalRowColumnCount=driver.findElements(By.partialLinkText(sIdentification));			
			break;
		case "CSSPATH":
			TotalRowColumnCount=driver.findElements(By.cssSelector(sIdentification));			
			break;
		case "CLASS":
			TotalRowColumnCount=driver.findElements(By.className(sIdentification));			
			break;
		case "TAGNAME":
			TotalRowColumnCount=driver.findElements(By.tagName(sIdentification));			
			break;
		}
		return TotalRowColumnCount;
	}
	
	public static void ControlToSwitchIFrame(WebDriver driver, String sLocatorType, String sIdentification) {
		// Switch To IFrame
		switch (sLocatorType) {
		case "ID":
			driver.switchTo().frame(driver.findElement(By.id(sIdentification)));
			break;
		case "NAME":
			driver.switchTo().frame(driver.findElement(By.name(sIdentification)));
			break;
		case "XPATH":
			driver.switchTo().frame(driver.findElement(By.xpath(sIdentification)));
			break;
		case "LINKTEXT":
			driver.switchTo().frame(driver.findElement(By.linkText(sIdentification)));
			break;
		case "PARTLINKTEXT":
			driver.switchTo().frame(driver.findElement(By.partialLinkText(sIdentification)));
			break;
		case "CSSPATH":
			driver.switchTo().frame(driver.findElement(By.cssSelector(sIdentification)));
			break;
		case "CLASS":
			driver.switchTo().frame(driver.findElement(By.className(sIdentification)));
			break;
		case "TAGNAME":
			driver.switchTo().frame(driver.findElement(By.tagName(sIdentification)));
			break;
		}
		//driver.switchTo().frame(0);
	}

	public static void ControlToWaitForElement(WebDriver driver, String sLocatorType, String sIdentification) {
		// Wait For Element
		WebDriverWait wait = new WebDriverWait(driver, 15);
		switch (sLocatorType) {
		case "ID":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(sIdentification)));
			break;
		case "NAME":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(sIdentification)));
			break;
		case "XPATH":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sIdentification)));
			break;
		case "LINKTEXT":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(sIdentification)));
			break;
		case "PARTLINKTEXT":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(sIdentification)));
			break;
		case "CSSPATH":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(sIdentification)));
			break;
		case "CLASS":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(sIdentification)));
			break;
		case "TAGNAME":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(sIdentification)));
			break;
		}
	}
	
	public static void ControlToSwitchTAB(WebDriver driver, String sLocatorType, String sIdentification) {
		// Switch To TAB
		switch (sLocatorType) {
		case "ID":
			driver.findElement(By.id(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "NAME":
			driver.findElement(By.name(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "XPATH":
			driver.findElement(By.xpath(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "LINKTEXT":
			driver.findElement(By.linkText(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "PARTLINKTEXT":
			driver.findElement(By.partialLinkText(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "CSSPATH":
			driver.findElement(By.cssSelector(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "CLASS":
			driver.findElement(By.className(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "TAGNAME":
			driver.findElement(By.tagName(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		}
	}
	
	public static boolean CheckTextPresent(WebDriver driver, String sLocatorType,String sIdentification,String sTextToSearch) {
		boolean bValueFound = false;
		try {
			switch (sLocatorType) 
			{
			case "ID":
				bValueFound  = driver.findElement(By.id(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "NAME":
				bValueFound  = driver.findElement(By.name(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "XPATH":
				bValueFound  = driver.findElement(By.xpath(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "LINKTEXT":
				bValueFound  = driver.findElement(By.linkText(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "PARTLINKTEXT":
				bValueFound  = driver.findElement(By.partialLinkText(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "CSSPATH":
				bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "CLASS":
				bValueFound  = driver.findElement(By.className(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "TAGNAME":
				bValueFound  = driver.findElement(By.tagName(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			}
		} catch (Exception e) 
		{
		  return bValueFound;
		}
		return bValueFound;
	}

	public static boolean CheckPartialTextPresent(WebDriver driver, String sLocatorType,String sIdentification,String sTextToSearch) {
		boolean bValueFound = false;
		try {
			switch (sLocatorType) 
			{
			case "ID":
				bValueFound  = driver.findElement(By.id(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "NAME":
				bValueFound  = driver.findElement(By.name(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "XPATH":
				bValueFound  = driver.findElement(By.xpath(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "LINKTEXT":
				bValueFound  = driver.findElement(By.linkText(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "PARTLINKTEXT":
				bValueFound  = driver.findElement(By.partialLinkText(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "CSSPATH":
				bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "CLASS":
				bValueFound  = driver.findElement(By.className(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "TAGNAME":
				bValueFound  = driver.findElement(By.tagName(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			}
		} catch (Exception e) 
		{
		  return bValueFound;
		}
		return bValueFound;
	}

	public static WebElement ControlToEnableByScript(WebDriver driver,String sLocatorType,String sIdentification) {
		// Enable a particular Control through script
		WebElement textbox = null;

		switch (sLocatorType) {
		case "ID":
			textbox = driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			textbox = driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			textbox = driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			textbox = driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			textbox = driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			textbox = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			textbox = driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			textbox = driver.findElement(By.tagName(sIdentification));
			break;
		}
		return textbox;
	}

	public static boolean CheckValuePresent(WebDriver driver, String sIdentificationType,String sIdentification,String sTextToSearch) {
		boolean bValueFound = false;
		String verifytxt ;
		try {
			switch (sIdentificationType) 
			{
			case "ID":
				bValueFound  = driver.findElement(By.id(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				Thread.sleep(5000);
				verifytxt = driver.findElement(By.id(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "NAME":
				bValueFound  = driver.findElement(By.name(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				Thread.sleep(2000);
				verifytxt = driver.findElement(By.name(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "XPATH":
				bValueFound  = driver.findElement(By.xpath(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				Thread.sleep(2000);
				verifytxt = driver.findElement(By.xpath(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "LINKTEXT":
				bValueFound  = driver.findElement(By.linkText(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				Thread.sleep(2000);
				verifytxt = driver.findElement(By.linkText(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "CSSPATH":
				bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				Thread.sleep(2000);
				verifytxt = driver.findElement(By.cssSelector(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "CLASS":
				bValueFound  = driver.findElement(By.className(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				Thread.sleep(2000);
				verifytxt = driver.findElement(By.className(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			}
		} catch (Exception e) 
		{
		  return bValueFound;
		}
		return bValueFound;
	}
	//@Vinayak : To get partial text as per position on string, position is to be on excel sheet Column mapping Sequence column
	public static  String ControlToGetPartialTextFrom(WebDriver driver,String sLocatorType,String sIdentification, int sSequenceint) {
		// To Get Text from a control
		String textbox = null;
		String[] split_text = null;
		String Leave_num =null;
		switch (sLocatorType) {
		case "ID":
			textbox = driver.findElement(By.id(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		case "NAME":
			textbox = driver.findElement(By.name(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		case "XPATH":
			textbox = driver.findElement(By.xpath(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		case "LINKTEXT":
			textbox = driver.findElement(By.linkText(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		case "PARTLINKTEXT":
			textbox = driver.findElement(By.partialLinkText(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		case "CSSPATH":
			textbox = driver.findElement(By.cssSelector(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		case "CLASS":
			textbox = driver.findElement(By.className(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		case "TAGNAME":
			textbox = driver.findElement(By.tagName(sIdentification)).getText();
			split_text = textbox.split(" ");
			Leave_num = split_text[sSequenceint];
			break;
		}
		return Leave_num;
	}
	public static String ControlToGetTextFrom(WebDriver driver,String sLocatorType,String sIdentification) {
		// To Get Text from a control
		String textbox = null;

		switch (sLocatorType) {
		case "ID":
			textbox = driver.findElement(By.id(sIdentification)).getText();
			break;
		case "NAME":
			textbox = driver.findElement(By.name(sIdentification)).getText();
			break;
		case "XPATH":
			textbox = driver.findElement(By.xpath(sIdentification)).getText();
			break;
		case "LINKTEXT":
			textbox = driver.findElement(By.linkText(sIdentification)).getText();
			break;
		case "PARTLINKTEXT":
			textbox = driver.findElement(By.partialLinkText(sIdentification)).getText();
			break;
		case "CSSPATH":
			textbox = driver.findElement(By.cssSelector(sIdentification)).getText();
			break;
		case "CLASS":
			textbox = driver.findElement(By.className(sIdentification)).getText();
			break;
		case "TAGNAME":
			textbox = driver.findElement(By.tagName(sIdentification)).getText();
			break;
		}
		return textbox;
	}

	public static String ControlToGetTextFromAttributes(WebDriver driver,String sLocatorType,String sIdentification, String sAttributeName) {
		// To Get the text value from an attribute of a control
		String textbox = null;

		switch (sLocatorType) {
		case "ID":
			textbox = driver.findElement(By.id(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		case "NAME":
			textbox = driver.findElement(By.name(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		case "XPATH":
			textbox = driver.findElement(By.xpath(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		case "LINKTEXT":
			textbox = driver.findElement(By.linkText(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		case "PARTLINKTEXT":
			textbox = driver.findElement(By.partialLinkText(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		case "CSSPATH":
			textbox = driver.findElement(By.cssSelector(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		case "CLASS":
			textbox = driver.findElement(By.className(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		case "TAGNAME":
			textbox = driver.findElement(By.tagName(sIdentification)).getAttribute(sAttributeName).toString();
			break;
		}
		System.out.println("The Atribute of " + sAttributeName + " =: " + textbox);
		return textbox;
	}
	
	public static void ControlToUseCtrlClick(WebDriver driver, String sLocatorType, String sIdentification) {
		// CTRL + Click
		Actions action = new Actions(driver);
		WebElement ElementSeq = null;
		switch (sLocatorType) {
		case "ID":
			ElementSeq = driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			ElementSeq = driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			ElementSeq = driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			ElementSeq = driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			ElementSeq = driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			ElementSeq = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			ElementSeq = driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			ElementSeq = driver.findElement(By.tagName(sIdentification));
			break;
		}
		action.moveToElement(ElementSeq).contextClick().sendKeys(Keys.CONTROL).click().perform();
		action.sendKeys(Keys.CONTROL).release().perform();
	}
	
	public static void ControlToUseMultiSelect(WebDriver driver, String sLocatorType, String sIdentification, String sSelectValue) {
		// Multi Select 
		switch (sLocatorType) {
		case "ID":
			new Select(driver.findElement(By.id(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		case "NAME":
			new Select(driver.findElement(By.name(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		case "XPATH":
			new Select(driver.findElement(By.xpath(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		case "LINKTEXT":
			new Select(driver.findElement(By.linkText(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		case "PARTLINKTEXT":
			new Select(driver.findElement(By.partialLinkText(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		case "CSSPATH":
			new Select(driver.findElement(By.cssSelector(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		case "CLASS":
			new Select(driver.findElement(By.className(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		case "TAGNAME":
			new Select(driver.findElement(By.tagName(sIdentification))).selectByVisibleText(sSelectValue);
			break;
		}
	}

	public static void ControlToUseSequence(WebDriver driver, String sLocatorType, String sIdentification) {
		// Click
		Actions action = new Actions(driver);
		WebElement ElementSeq = null;
		switch (sLocatorType) {
		case "ID":
			ElementSeq = driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			ElementSeq = driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			ElementSeq = driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			ElementSeq = driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			ElementSeq = driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			ElementSeq = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			ElementSeq = driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			ElementSeq = driver.findElement(By.tagName(sIdentification));
			break;
		}
		action.moveToElement(ElementSeq).click().perform();
	}
	
	public static void ControlToGetFromList(WebDriver driver, String sLocatorType, String sIdentification, String sSelectValue) {
		// getting a specific value from a list which does not have any unique attributes
		List<WebElement> list = null;

		switch (sLocatorType) {
		case "ID":
			list = driver.findElements(By.id(sIdentification));
			break;
		case "NAME":
			list = driver.findElements(By.name(sIdentification));
			break;
		case "XPATH":
			list = driver.findElements(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			list = driver.findElements(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			list = driver.findElements(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			list = driver.findElements(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			list = driver.findElements(By.className(sIdentification));
			break;
		case "TAGNAME":
			list = driver.findElements(By.tagName(sIdentification));
			break;
		}
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getText().equalsIgnoreCase(sSelectValue)){
				list.get(i).click();
				break;
			}
		}

	}

	@SuppressWarnings("null")
	public static void ControlToMoveDownAndClick(WebDriver driver, String sLocatorType, String sIdentification, String sNoOfTimes) {
		// Move number of times in the list and press enter
		 WebElement wbeValueToSelect = null; 
		switch (sLocatorType) {
		case "ID":
			wbeValueToSelect = driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			wbeValueToSelect = driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			wbeValueToSelect = driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			wbeValueToSelect = driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			wbeValueToSelect = driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			wbeValueToSelect = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			wbeValueToSelect = driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			wbeValueToSelect = driver.findElement(By.tagName(sIdentification));
			break;
		}
		for (int i = 0; i < Integer.parseInt(sNoOfTimes); i++) {
			wbeValueToSelect.sendKeys(Keys.ARROW_DOWN);
		}
		wbeValueToSelect.sendKeys(Keys.ENTER);
	}

	public static void ControlToSelectADate(WebDriver driver, String sLocatorType, String sIdentification, String sSelectValue) {
		// Select a date from the calendar control
		WebElement datepicker = null;
		switch (sLocatorType) {
		case "ID":
			datepicker = driver.findElement(By.id(sIdentification));
			break;
		case "NAME":
			datepicker = driver.findElement(By.name(sIdentification));
			break;
		case "XPATH":
			datepicker = driver.findElement(By.xpath(sIdentification));
			break;
		case "LINKTEXT":
			datepicker = driver.findElement(By.linkText(sIdentification));
			break;
		case "PARTLINKTEXT":
			datepicker = driver.findElement(By.partialLinkText(sIdentification));
			break;
		case "CSSPATH":
			datepicker = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			datepicker = driver.findElement(By.className(sIdentification));
			break;
		case "TAGNAME":
			datepicker = driver.findElement(By.tagName(sIdentification));
			break;
		}
		List<WebElement> rows=datepicker.findElements(By.tagName("tr"));  
		List<WebElement> columns=datepicker.findElements(By.tagName("span"));
		
		for (WebElement cell: columns)
		{  
			//Select the Date from parameter   
			if (cell.getText().equals(sSelectValue)) {  
				cell.click();
				break;
			}
		}

	}
	
}
