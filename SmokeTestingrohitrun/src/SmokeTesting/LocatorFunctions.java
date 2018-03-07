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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LocatorFunctions {

	static WebDriverWait wait;
	static int waitinseconds = 5;

	public static void ControlToUse(WebDriver driver, String sLocatorType, String sIdentification, boolean bIsClick) 
	{
		// Click
		Actions action = new Actions(driver);
		WebElement ElementSeq = null;
		if (bIsClick) {
			try 
			{
				switch (sLocatorType) {
				case "ID":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					action.moveToElement(driver.findElement(By.id(sIdentification))).click().perform();
					break;
				case "NAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					action.moveToElement(driver.findElement(By.name(sIdentification))).click().perform();
					break;
				case "XPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					action.moveToElement(driver.findElement(By.xpath(sIdentification))).click().perform();
					break;
				case "LINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					action.moveToElement(driver.findElement(By.linkText(sIdentification))).click().perform();
					break;
				case "PARTLINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					action.moveToElement(driver.findElement(By.partialLinkText(sIdentification))).click().perform();
					break;
				case "CSSPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					action.moveToElement(driver.findElement(By.cssSelector(sIdentification))).click().perform();
					break;
				case "CLASS":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					break;
				case "TAGNAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					break;
				}
				Constants._PASSFAIL = "Pass";
			}
			catch(NoSuchElementException ex)
			{
				Constants._PASSFAIL = "Fail";
				System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
			}
		} else {
			// DoubleClick
			try
			{
				switch (sLocatorType) {
				case "ID":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.id(sIdentification));
					break;
				case "NAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.name(sIdentification));
					break;
				case "XPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.xpath(sIdentification));
					break;
				case "LINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.linkText(sIdentification));
					break;
				case "PARTLINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.partialLinkText(sIdentification));
					break;
				case "CSSPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.cssSelector(sIdentification));
					break;
				case "CLASS":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.className(sIdentification));
					break;
				case "TAGNAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					ElementSeq = driver.findElement(By.tagName(sIdentification));
					break;
				}
				action.moveToElement(ElementSeq).doubleClick().perform();
				Constants._PASSFAIL = "Pass";
			}
			catch(NoSuchElementException ex)
			{
				Constants._PASSFAIL = "Fail";
				System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
			}
		}
	}

	public static void ControlToUploadFile(WebDriver driver, String sLocatorType, String sIdentification, String sGetFilePath) throws AWTException {
		// Click
		Actions action = new Actions(driver);
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				action.moveToElement(driver.findElement(By.id(sIdentification))).click().perform();
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				action.moveToElement(driver.findElement(By.name(sIdentification))).click().perform();
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				action.moveToElement(driver.findElement(By.xpath(sIdentification))).click().perform();
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				action.moveToElement(driver.findElement(By.linkText(sIdentification))).click().perform();
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				action.moveToElement(driver.findElement(By.partialLinkText(sIdentification))).click().perform();
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				action.moveToElement(driver.findElement(By.cssSelector(sIdentification))).click().perform();
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				action.moveToElement(driver.findElement(By.className(sIdentification))).click().perform();
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
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
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlDropdown(WebDriver driver, String sLocatorType, String sIdentification, String sGetInputValue) {
		// Drop down
		Select dropdown = null;

		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.id(sIdentification)));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.name(sIdentification)));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.xpath(sIdentification)));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.linkText(sIdentification)));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.partialLinkText(sIdentification)));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.cssSelector(sIdentification)));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.className(sIdentification)));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				dropdown = new Select(driver.findElement(By.tagName(sIdentification)));
				break;
			}
			dropdown.selectByVisibleText(sGetInputValue);
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlToUse(WebDriver driver, String sLocatorType,String sIdentification,String sGetInputValue,String sKeyword) throws NoSuchElementException
	{
		// Input, FileOpen, ClearData
		if (sKeyword.matches("Input") | sKeyword.matches("ClearData") ) 
		{
			try {
				switch (sLocatorType) {
				case "ID":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.id(sIdentification)).clear();
					break;
				case "NAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.name(sIdentification)).clear();
					break;
				case "XPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.xpath(sIdentification)).clear();
					break;
				case "LINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.linkText(sIdentification)).clear();
					break;
				case "PARTLINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.partialLinkText(sIdentification)).clear();
					break;
				case "CSSPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.cssSelector(sIdentification)).clear();
					break;
				case "CLASS":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.className(sIdentification)).clear();
					break;
				case "TAGNAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.tagName(sIdentification)).clear();

					break;
				}
				Constants._PASSFAIL = "Pass";
			}
			catch(NoSuchElementException ex)
			{
				System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
			}
		}
		if (!sKeyword.matches("ClearData")) {
			try {
				switch (sLocatorType) {
				case "ID":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.id(sIdentification)).sendKeys(sGetInputValue);
					break;
				case "NAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.name(sIdentification)).sendKeys(sGetInputValue);
					break;
				case "XPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.xpath(sIdentification)).sendKeys(sGetInputValue);
					break;
				case "LINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.linkText(sIdentification)).sendKeys(sGetInputValue);
					break;
				case "PARTLINKTEXT":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.partialLinkText(sIdentification)).sendKeys(sGetInputValue);
					break;
				case "CSSPATH":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.cssSelector(sIdentification)).sendKeys(sGetInputValue);
					break;
				case "CLASS":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.className(sIdentification)).sendKeys(sGetInputValue);
					break;
				case "TAGNAME":
					ControlToWaitForElement(driver,sLocatorType,sIdentification);
					driver.findElement(By.tagName(sIdentification)).sendKeys(sGetInputValue);
					break;
				}
				Constants._PASSFAIL = "Pass";
			}
			catch(NoSuchElementException ex)
			{
				System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
			}
		}
	}


	// @Vianayak : Code to take current date and add number of days from excel and enter in date field ddmmyyyy
	//Note : for current date enter 0 in excel sheet
	public static void Enterdate(WebDriver driver, String sLocatorType,String sIdentification,String sControlValue){
		
		try {
	        int d = Integer.parseInt(sControlValue);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // Now use today date.
			c.add(Calendar.DATE, d); // Adding days from excel
			String output = sdf.format(c.getTime());
			System.out.println(output);  
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.id(sIdentification)).clear();
				driver.findElement(By.id(sIdentification)).sendKeys(output);
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.name(sIdentification)).clear();
				driver.findElement(By.name(sIdentification)).sendKeys(output);
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.xpath(sIdentification)).clear();
				driver.findElement(By.xpath(sIdentification)).sendKeys(output);
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.linkText(sIdentification)).clear();
				driver.findElement(By.linkText(sIdentification)).sendKeys(output);
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.partialLinkText(sIdentification)).clear();
				driver.findElement(By.partialLinkText(sIdentification)).sendKeys(output);
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.cssSelector(sIdentification)).clear();
				driver.findElement(By.cssSelector(sIdentification)).sendKeys(output);
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.className(sIdentification)).clear();
				driver.findElement(By.className(sIdentification)).sendKeys(output);
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.tagName(sIdentification)).sendKeys(output);
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}
	

	// @Vianayak : Generate Random number through code and Inputting generated Random number in text filed
	public static void ControlforRandom(WebDriver driver, String sLocatorType,String sIdentification,String sGetInputValue,String sKeyword) {
		// generate random number and enter in text field
		Random randomGenerator = new Random();
	    for (int idx = 1; idx <= 10; ++idx){
	      int randomInt = randomGenerator.nextInt(100);
	      //System.out.println("Generated : " + randomInt);
	      sGetInputValue = Integer.toString(randomInt);
	    }
	    try {
	   
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
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}
	
	
	public static void ControlKeyPress(WebDriver driver, String sLocatorType,String sIdentification,String sGetInputValue, String sKeyword) {
		// For Key Press
		Actions action = new Actions(driver);
		WebElement EKeyPress = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EKeyPress = driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EKeyPress = driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EKeyPress = driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EKeyPress = driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EKeyPress = driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EKeyPress = driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EKeyPress = driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
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
			case "KEYF2": // Key press F2 
				action.sendKeys(EKeyPress, Keys.F2).build().perform();
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlHover(WebDriver driver, String sLocatorType,String sIdentification) 
	{
		// Hover on
		WebElement EHoverOn = null;

		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				EHoverOn = driver.findElement(By.tagName(sIdentification));
				break;
			}
			Actions action = new Actions(driver);
			action.moveToElement(EHoverOn).perform();
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlToSwitchFrame(WebDriver driver, String sLocatorType, String sIdentification) {
		// Switch To Frame
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.id(sIdentification)));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.name(sIdentification)));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.xpath(sIdentification)));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.linkText(sIdentification)));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.partialLinkText(sIdentification)));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.cssSelector(sIdentification)));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.className(sIdentification)));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.tagName(sIdentification)));
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlToSwitchToElementInFrame(WebDriver driver, String sLocatorType, String sIdentification, String sGetInputValue) {
		// Switch To Frame

		try
		{

			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.id(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.id(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.name(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.name(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.xpath(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.xpath(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.linkText(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.linkText(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.partialLinkText(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.partialLinkText(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.cssSelector(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.cssSelector(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.className(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.className(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().activeElement().findElement(By.tagName(sIdentification)).clear();
				driver.switchTo().activeElement().findElement(By.tagName(sIdentification)).sendKeys(sGetInputValue);
				break;
			}
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static boolean ControlToVerifyIsEnable(WebDriver driver, String sLocatorType, String sIdentification) 
	{
		// Verify whether the element is Enabled or Not

		boolean bIsEnabled = true;

		try
		{
			switch (sLocatorType) 
			{
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.id(sIdentification)).isEnabled();
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.name(sIdentification)).isEnabled();
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.xpath(sIdentification)).isEnabled();
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.linkText(sIdentification)).isEnabled();
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.partialLinkText(sIdentification)).isEnabled();
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.cssSelector(sIdentification)).isEnabled();
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.className(sIdentification)).isEnabled();
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bIsEnabled=driver.findElement(By.tagName(sIdentification)).isEnabled();
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return bIsEnabled;
	}

	public static WebElement ControlToUseTable(WebDriver driver, String sLocatorType, String sIdentification) {
		// Table
		WebElement Webtable = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				Webtable=driver.findElement(By.tagName(sIdentification));
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return Webtable;
	}

	public static List<WebElement> ControlToUseTableRowColumn(WebDriver driver, String sLocatorType, String sIdentification) {
		// Table Row / Column Count
		List<WebElement> TotalRowColumnCount = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.id(sIdentification));			
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.name(sIdentification));			
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.xpath(sIdentification));			
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.linkText(sIdentification));			
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.partialLinkText(sIdentification));			
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.cssSelector(sIdentification));			
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.className(sIdentification));			
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				TotalRowColumnCount=driver.findElements(By.tagName(sIdentification));			
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return TotalRowColumnCount;
	}

	public static void ControlToSwitchIFrame(WebDriver driver, String sLocatorType, String sIdentification) {
		// Switch To IFrame
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.id(sIdentification)));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.name(sIdentification)));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.xpath(sIdentification)));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.linkText(sIdentification)));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.partialLinkText(sIdentification)));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.cssSelector(sIdentification)));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.className(sIdentification)));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.switchTo().frame(driver.findElement(By.tagName(sIdentification)));
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		//driver.switchTo().frame(0);
	}

	public static void ControlToWaitForElement(WebDriver driver, String sLocatorType, String sIdentification) {
		// Wait For Element
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try
		{
			switch (sLocatorType) 
			{

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
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		catch(TimeoutException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in TimeoutException exceptin **********");
		}
	}
	public static void ControlToSwitchTAB(WebDriver driver, String sLocatorType, String sIdentification) {
		// Switch To TAB
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.id(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.name(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.xpath(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.linkText(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.partialLinkText(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.cssSelector(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.className(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				driver.findElement(By.tagName(sIdentification)).sendKeys(Keys.CONTROL +"\t");
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static boolean CheckTextPresent(WebDriver driver, String sLocatorType,String sIdentification,String sTextToSearch) {
		boolean bValueFound = false;
		try {
			switch (sLocatorType) 
			{
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.id(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.name(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.xpath(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.linkText(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.partialLinkText(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.className(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.tagName(sIdentification)).getText().trim().matches(sTextToSearch);
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		} 
		catch (Exception e) 
		{
			Constants._PASSFAIL = "Fail";
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
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.id(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.name(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.xpath(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.linkText(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.partialLinkText(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.className(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.tagName(sIdentification)).getText().trim().contains(sTextToSearch);
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		catch (Exception e) 
		{
			Constants._PASSFAIL = "Fail";
			return bValueFound;
		}
		return bValueFound;
	}

	public static WebElement ControlToEnableByScript(WebDriver driver,String sLocatorType,String sIdentification) {
		// Enable a particular Control through script
		WebElement textbox = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.tagName(sIdentification));
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return textbox;
	}
//Vinayak : For Verifying value of any field (to get text in value attribute)
	public static boolean CheckValuePresent(WebDriver driver, String sLocatorType,String sIdentification,String sTextToSearch) {
		boolean bValueFound = false;
		String verifytxt ;
		try {
			switch (sLocatorType) 
			{
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.id(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				System.out.println("Text is ="+bValueFound);
				verifytxt = driver.findElement(By.id(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.name(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				verifytxt = driver.findElement(By.name(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.xpath(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				verifytxt = driver.findElement(By.xpath(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.linkText(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				verifytxt = driver.findElement(By.linkText(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				verifytxt = driver.findElement(By.cssSelector(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				bValueFound  = driver.findElement(By.className(sIdentification)).getAttribute("value").trim().matches(sTextToSearch);
				verifytxt = driver.findElement(By.className(sIdentification)).getAttribute("value");
				System.out.println("Text is ="+verifytxt);
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return bValueFound;
	}
	
	//@Vinayak : To get partial text as per position on string, position is to be on excel sheet Column mapping Sequence column
	public static  String ControlToGetPartialTextFrom(WebDriver driver,String sLocatorType,String sIdentification, int sSequenceint) {
		// To Get Text from a control
		String textbox = null;
		String[] split_text = null;
		String Leave_num =null;
		try{
			
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.id(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.name(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.xpath(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.linkText(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.partialLinkText(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.cssSelector(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.className(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.tagName(sIdentification)).getText();
				split_text = textbox.split(" ");
				Leave_num = split_text[sSequenceint];
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return Leave_num;
	}
	
	public static String ControlToGetTextFrom(WebDriver driver,String sLocatorType,String sIdentification) {
		// To Get Text from a control
		String textbox = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.id(sIdentification)).getText();
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.name(sIdentification)).getText();
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.xpath(sIdentification)).getText();
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.linkText(sIdentification)).getText();
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.partialLinkText(sIdentification)).getText();
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.cssSelector(sIdentification)).getText();
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.className(sIdentification)).getText();
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.tagName(sIdentification)).getText();
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return textbox;
	}

	public static String ControlToGetTextFromAttributes(WebDriver driver,String sLocatorType,String sIdentification, String sAttributeName) {
		// To Get the text value from an attribute of a control
		String textbox = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.id(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.name(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.xpath(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.linkText(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.partialLinkText(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.cssSelector(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.className(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				textbox = driver.findElement(By.tagName(sIdentification)).getAttribute(sAttributeName).toString();
				break;
			}
			System.out.println("The Atribute of " + sAttributeName + " =: " + textbox);
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
		return textbox;
	}

	public static void ControlToUseCtrlClick(WebDriver driver, String sLocatorType, String sIdentification) {
		// CTRL + Click
		Actions action = new Actions(driver);
		WebElement ElementSeq = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.tagName(sIdentification));
				break;
			}
			action.moveToElement(ElementSeq).contextClick().sendKeys(Keys.CONTROL).click().perform();
			action.sendKeys(Keys.CONTROL).release().perform();
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlToUseMultiSelect(WebDriver driver, String sLocatorType, String sIdentification, String sSelectValue) {
		// Multi Select 
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.id(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.name(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.xpath(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.linkText(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.partialLinkText(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.cssSelector(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.className(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				new Select(driver.findElement(By.tagName(sIdentification))).selectByVisibleText(sSelectValue);
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlToUseSequence(WebDriver driver, String sLocatorType, String sIdentification) {
		// Click
		Actions action = new Actions(driver);
		WebElement ElementSeq = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				ElementSeq = driver.findElement(By.tagName(sIdentification));
				break;
			}
			action.moveToElement(ElementSeq).click().perform();
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}
	}

	public static void ControlToGetFromList(WebDriver driver, String sLocatorType, String sIdentification, String sSelectValue) {
		// getting a specific value from a list which does not have any unique attributes
		List<WebElement> list = null;
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				list = driver.findElements(By.tagName(sIdentification));
				break;
			}
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getText().equalsIgnoreCase(sSelectValue)){
					list.get(i).click();
					break;
				}
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}

	}

	//@SuppressWarnings("null")
	public static void ControlToMoveDownAndClick(WebDriver driver, String sLocatorType, String sIdentification, String sNoOfTimes) {
		// Move number of times in the list and press enter
		WebElement wbeValueToSelect = null; 
		try
		{
			switch (sLocatorType) {
			case "ID":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.id(sIdentification));
				break;
			case "NAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.name(sIdentification));
				break;
			case "XPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.xpath(sIdentification));
				break;
			case "LINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.linkText(sIdentification));
				break;
			case "PARTLINKTEXT":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.partialLinkText(sIdentification));
				break;
			case "CSSPATH":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.cssSelector(sIdentification));
				break;
			case "CLASS":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.className(sIdentification));
				break;
			case "TAGNAME":
				ControlToWaitForElement(driver,sLocatorType,sIdentification);
				wbeValueToSelect = driver.findElement(By.tagName(sIdentification));
				break;
			}
			Constants._PASSFAIL = "Pass";
		}
		catch(NoSuchElementException ex)
		{
			Constants._PASSFAIL = "Fail";
			System.out.println(ex.getMessage()+"******* in NoSuchElementException exceptin **********");
		}


		for (int i = 0; i < Integer.parseInt(sNoOfTimes); i++) {
			wbeValueToSelect.sendKeys(Keys.ARROW_DOWN);
		}
		wbeValueToSelect.sendKeys(Keys.ENTER);
	}

}
