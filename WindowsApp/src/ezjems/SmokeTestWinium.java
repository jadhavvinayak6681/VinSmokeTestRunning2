package ezjems;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
//import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.mail.MessagingException;

//import jxl.*;
//import jxl.read.biff.BiffException;

import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.*;
import org.openqa.selenium.Alert;
//import org.openqa.jetty.html.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import atu.testrecorder.exceptions.ATUTestRecorderException;
import ezjems.Constants;

public class SmokeTestWinium {
	
	public static String baseURL,sIsVideoOn,sRecorderFilePath,RecorderFilePrefix,sHTMLReportsPath, sHTMLReportsPrefix,sDataSheet,currentDate,sRunDate, sReportPath, sTimeOuts,sEnvironmentName,sStyleSheetPath;
	public static String parentWindowHandle,sBrowserName,sControlToEnable,sAuthentication,sAuthenticateUser,sAuthenticatePassword,sGetTextFromControl,trannumber;
	public static HSSFWorkbook wb, wbgen ; static HSSFSheet sh, shgen ;
	public static int iTimeoutValue;
	public static BufferedWriter bw;
	public static File file;
	//public static WebDriver driver;
	public static WiniumDriver wdriver = null;
	public static boolean bVideoStarted=false, bExceptionOccurred=false, bParentWindow=true;

	public static void main() throws Exception 
	{
		try 
		{
			SmokeTestWinium.InitializeForTesting();
			
			SmokeTestWinium.ProceedWithTesting();
			
			SmokeTestWinium.CloseAfterTesting();

		} 
		catch (FileNotFoundException e) 
		{
				bExceptionOccurred = true;
				e.printStackTrace();
		} 
		catch (MessagingException e) 
		{
				bExceptionOccurred = true;
				e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
				bExceptionOccurred = true;
				e.printStackTrace();
		} 
		catch (NoSuchElementException e) 
		{
				bExceptionOccurred = true;
				e.printStackTrace();
		}
		if(bExceptionOccurred) 
		{
				if( bVideoStarted) 
				{
					//GenericCode.StopVideoRecorder();
					bVideoStarted = false;
				}
				wdriver.close();
				wdriver.quit();
		}
	}

	@BeforeTest
	public static void InitializeForTesting() throws BiffException, ATUTestRecorderException, IOException {
		// Worksheet having all the generic parameters
		wbgen = OpenExcelWorkBook(Constants.sFileName);
		Constants.sFileName = FindValueInExcelSheet(wbgen, "Application", 0, "Start", 2); // Path for application datasheet
		sDataSheet = FindValueInExcelSheet(wbgen, "Application", 0, "Start", 1); // Data to be used for this application
		wdriver = (WiniumDriver) OpenWebDriver(wbgen, "Drivers", "Start"); // Browser driver to be used (IE, Chrome). Default is Firefox
		sBrowserName = FindValueInExcelSheet(wbgen, "Drivers", 0, "Start", 5 ); // Browser Name (FireFox,IE,Chrome,etc)
		bVideoStarted = false;
		bExceptionOccurred = false;
		sIsVideoOn = FindValueInExcelSheet(wbgen, "Utilities", 0, "VideoRecorder", 1 ); // Parameter to get the status of Video Recorder
		sHTMLReportsPath = FindValueInExcelSheet(wbgen, "Application", 0, "Start", 5 ); // HTML Report Path to be used
		sHTMLReportsPrefix = FindValueInExcelSheet(wbgen, "Application", 0, "Start", 6 ); // HTML Report Prefix to be used
		currentDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		sRunDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
		sReportPath = sHTMLReportsPath + sHTMLReportsPrefix + currentDate + ".html";
		file = new File(sReportPath);
		if (!file.exists()) {
			file.createNewFile();
		}
		bw = new BufferedWriter(new FileWriter(sReportPath));

		if (sIsVideoOn.matches("On")) {
			//GenericCode.SetupRecorderFilePath(GenericCode.FindValueInExcelSheet(wbgen, "Utilities", 0, "VideoRecorder", 3 ), GenericCode.FindValueInExcelSheet(wbgen, "Utilities", 0, "VideoRecorder", 4 ));
			//GenericCode.StartVideoRecorder();
			bVideoStarted = true;
		}
		sTimeOuts = FindValueInExcelSheet(wbgen, "Utilities", 0, "TimeoutValue", 2 ); // Timeout (wait) to be used between controls
		if (sTimeOuts.indexOf(".") > 0) {
			sTimeOuts = sTimeOuts.substring(0, sTimeOuts.indexOf(".")); 
		}
		iTimeoutValue = Integer.parseInt(sTimeOuts);
	}
	
	@Test
	public static void ProceedWithTesting() throws Exception {
		try {
			// Worksheet having all the application specific parameters
			wb = OpenExcelWorkBook(Constants.sFileName);
			sh = wb.getSheet(sDataSheet);
			int iRows = sh.getLastRowNum();
			String sGetLink = null;
			String sIsLinkHasParameters = null;
			String LinkParametersCount = null;
			String sLinkParameter01, sLinkParameter02, sLinkParameter03, sLinkParameter04, sLinkParameter05 = null;
			sEnvironmentName = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 1 ); // environment name to be used
			sStyleSheetPath = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 7 ); // Stylesheet path to be used
			System.out.println("Starting for Application: "+sDataSheet);
			sAuthentication = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 13 );
			if (sAuthentication.matches("Yes")) {

				sAuthenticateUser = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 14 );
				sAuthenticatePassword = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 15 );
				//baseURL = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 5 ) + sAuthenticateUser + ":" + sAuthenticatePassword + "@" + FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 4 );  
				baseURL = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 2 ); // environment to be used
				baseURL = baseURL + FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 3 );
			}
			else{
				baseURL = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 2 ); // environment to be used
				baseURL = baseURL + FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 3 );
			}

			sIsLinkHasParameters = "No";
			sIsLinkHasParameters = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 6 );
			if (sIsLinkHasParameters.matches("Yes")) {
				LinkParametersCount = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 7 );
				sLinkParameter01 = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 8 );
				sLinkParameter02 = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 9 );
				sLinkParameter03 = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 10 );
				sLinkParameter04 = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 11);
				sLinkParameter05 = FindValueInExcelSheet(wb, "EnvURLs", 0, "Start", 12 );
				switch (LinkParametersCount) {
				case "1": // Adding parameter01 to the URL
					baseURL = baseURL + sLinkParameter01;
					break;
				case "2": // Adding parameter02 to the URL
					baseURL = baseURL + sLinkParameter01 + sLinkParameter02;
					break;
				case "3": // Adding parameter03 to the URL
					baseURL = baseURL + sLinkParameter01 + sLinkParameter02 + sLinkParameter03;
					break;
				case "4": // Adding parameter04 to the URL
					baseURL = baseURL + sLinkParameter01 + sLinkParameter02 + sLinkParameter03 + sLinkParameter04;
					break;
				case "5": // Adding parameter05 to the URL
					baseURL = baseURL + sLinkParameter01 + sLinkParameter02 + sLinkParameter03 + sLinkParameter04 + sLinkParameter05;
					break;
				}
			}

			SmokeTestWinium.ReportHeader();
			for (int i = 1; i <= iRows;i++) {
				HSSFRow row = sh.getRow(i);
				String sStrToCheck = row.getCell(0).getStringCellValue();
				System.out.println(sStrToCheck);
				String sFunction = row.getCell(1).toString();
			
				if (!sFunction.isEmpty()) {
					if (row.getCell(4).getStringCellValue().matches("Yes")) { // Should execute the row or not
						SmokeTestWinium.ReportTestCaseNameData(sStrToCheck);
						int iStartColumn = (int) row.getCell(1).getNumericCellValue(); // Start getting data from this column
						int iEndColumn = (int) row.getCell(2).getNumericCellValue(); // End getting data from this column
						String sParameterSheet = row.getCell(5).getStringCellValue(); // Get Data from this sheet
						int iTestCaseSteps = 1;
						for (int ii = iStartColumn; ii <= iEndColumn; ii++) { // For the range of Start and End columns
							String sControlName = FindValueInExcelSheet(wb, sParameterSheet, 0, "Action", ii); // Column Name = Control Name
							String sControlValue = FindValueInExcelSheet(wb, sParameterSheet, 0, "Start", ii); // Control Value = Data 
							if (!sControlValue.isEmpty()) { // If the data is not empty then proceed
								System.out.println("Control Name: " +sControlName);
								String sTestCaseSteps = FindValueInExcelSheet(wb, sParameterSheet, 0, "TestSteps", ii); // Test Case Steps
								String sExpectedValue = FindValueInExcelSheet(wb, sParameterSheet, 0, "Expected", ii); // Expected  
								String sControlType = FindValueInExcelSheet(wb, "ColumnMapping", 0, sControlName, 1); // get the control type like (input, dropdown) etc
								String sIdentificationType = FindValueInExcelSheet(wb, "ColumnMapping", 0, sControlName, 2); // get the control identification type like (id, xpath, class) etc
								String sCheckExistence = FindValueInExcelSheet(wb, "ColumnMapping", 0, sControlName, 8); // whether need to check the existence of this object
								String sIdentification = null;
								sIdentification = FindValueInExcelSheet(wb, "ColumnMapping", 0, sControlName, 3); // get the control identification like the finding the element
								String sSequenceStr = null;
								sSequenceStr = FindValueInExcelSheet(wb, "ColumnMapping", 0, sControlName, 4); // get the sequence of the control identification to find the element
								//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
								//Vinayak : commented above line for winium
								if(sControlType.equals("GetTextFrom")) {
									sGetTextFromControl = ControlToGetTextFrom(wdriver,sIdentificationType,sIdentification);
									//String sGetTextFromControl = wdriver.findElementById(sIdentification).getAttribute("Name");
									System.out.println(sGetTextFromControl);
									//System.out.println("Booking No: " +sGetTextFromControl);
									sExpectedValue = sExpectedValue + " = " + sControlValue + sGetTextFromControl;
									trannumber = sGetTextFromControl;
								} 
								
								else {
									sExpectedValue = sExpectedValue + " = " + sControlValue;
								}

								SmokeTestWinium.ReportTestCaseDetails(iTestCaseSteps, sTestCaseSteps, sExpectedValue);
								
								switch (sControlType) {
								case "Application": // Opens Application 
									//switch (sBrowserName) {
									//case "FireFox": // FireFox browser
									//	driver.get(baseURL);
									//	break;
									//case "IE": // IE browser
									//	driver.get(baseURL);
									//	break;
									//case "Chrome": // GoogleChrome browser
									//	driver.get(baseURL);
									//	break;
									//}
									DesktopOptions options = new DesktopOptions();
									options.setApplicationPath("D:\\Vinayak_Data\\Shiva\\Selenium\\EZJems-SGN\\EZJems.exe");
									try
									{
										wdriver=new WiniumDriver(new URL("http://localhost:9999"),options);
										System.out.println("Hello");
									}
									catch (MalformedURLException e)
									{
										e.printStackTrace();
									}
									//SmokeTestWinium.ReportTestCaseResult("Pass");
									
									/*driver.get(baseURL);
									if (sAuthentication.matches("Yes")) {
										SmokeTestWinium.WindowsAuthenticationlogin(sAuthenticateUser,sAuthenticatePassword);
									}
									driver.manage().window().maximize();
									//driver.manage().deleteAllCookies();
									SmokeTestWinium.ReportTestCaseResult("Pass");*/
									break;
								case "Get": // Gets which link to be searched in the emails
									//String sFindEmail = null;
									//int iGetColumn = 0;
									//switch (sIdentificationType) {
									//case "GETREG1LINK": // getting link from ESMS invite email 
									//	sFindEmail = "Stage1Invitation"; iGetColumn = 2;
									//	break;
									//case "GETREG2LINK": // getting link from CSMS invite email
									//	sFindEmail = "Stage2Invitation"; iGetColumn = 2;
									//	break;
									//case "GETAPPLINK": // getting link from account created email
									//	sFindEmail = "AccountCreated"; iGetColumn = 4;
									//	break;
									//case "GETWELCOMEESMSLINK": // getting link from ESMS welcome email
									//	sFindEmail = "Stage1Welcome"; iGetColumn = 2;
									//	break;
									//case "GETWELCOMECSMSLINK": // getting link from CSMS welcome email
									//	sFindEmail = "Stage2Welcome"; iGetColumn = 2;
									//break;
									//}
									//Thread.sleep(30000);
									// 	The variable that receives the link from email
									//sGetLink = GenericCode.GetLinkFromMailBox(GenericCode.FindValueInExcelSheet(wb, "EnvURLs", 0, "Outlook", 3), 
									//	GenericCode.FindValueInExcelSheet(wbgen, "LoginData", 0, "Outlook", 1),
									//	GenericCode.FindValueInExcelSheet(wbgen, "LoginData", 0, "Outlook", 2),
									//	GenericCode.FindValueInExcelSheet(wb, "EmailData", 0, sFindEmail, 1),
									//	GenericCode.FindValueInExcelSheet(wbgen, "EnvURLs", 0, "Start",iGetColumn ),
									//	GenericCode.FindValueInExcelSheet(wb, "EmailData", 0, sFindEmail, 2));
									//break;
								case "Loop": // Gets which functions needs to be looped 
									//String aStringToSplit[] = GenericCode.SplitValuesIntoArray(sControlValue,"$");
									//for (int pi = 0;pi <= aStringToSplit.length-1; pi++) {
									//	String sValue = aStringToSplit[pi];
									//	switch (sIdentificationType) {
									//	case "PRODSELECT": // Product Selection
									//		driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//		driver.findElement(By.id(GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ApplicationProductSearchText", 3))).clear();
									//		driver.findElement(By.id(GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ApplicationProductSearchText", 3))).sendKeys(sValue);
									//		driver.findElement(By.id(GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ApplicationProductSearchButton", 3))).click();
									//		Thread.sleep(30000);
									//		driver.findElement(By.xpath(GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ApplicationProductClickToSelect", 3))).click();
									//		Thread.sleep(5000);
									//		break;
									//	case "ROSSELECT": // Region of Supply Selection
									//	case "ROMSELECT": // Region of Manufacture Selection
									//		driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//		if (sIdentificationType.matches("ROMSELECT")) {
									//			sValue = "M_" + sValue;
									//		}
									//		driver.findElement(By.id(GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, sValue, 3))).click();
									//		driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//		Thread.sleep(3000);
									//		break;
									//	case "QDPRODSELECT": // Product Selection in Questionnaire
									//		driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//		String sClickProduct = GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataSelectProduct", 3);
									//		sClickProduct = sClickProduct + sValue + "')]";
									//		System.out.println(sClickProduct);
									//		driver.findElement(By.xpath(sClickProduct)).click();
									//		Thread.sleep(59000);
									//		String sProdRelationNotApplicable = GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 3);
									//		if (!sProdRelationNotApplicable.isEmpty()) {
									//			GenericCode.ControlToUse(driver, "ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataProductRelationNotApplicable", 3), sProdRelationNotApplicable);
									//		}
									//		String sProdRelationAgent = GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 4);
									//		if (!sProdRelationAgent.isEmpty()) {
									//			GenericCode.ControlToUse(driver, "ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataProductRelationAgent", 3), sProdRelationAgent);
									//			GenericCode.ControlToUse(driver, "Input","ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataPrimaryManufacturerName", 3), GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 6));
									//			GenericCode.ControlToUse(driver, "ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataCountryOfPrimaryManufacturer", 3), GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 7));
									//		}
									//		String sProdRelationStockist = GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 5);
									//		if (!sProdRelationStockist.isEmpty()) {
									//			GenericCode.ControlToUse(driver, "ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataProductRelationStockist", 3), sProdRelationStockist);
									//			GenericCode.ControlToUse(driver, "Input","ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataPrimaryManufacturerName", 3), GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 6));
									//			GenericCode.ControlToUse(driver, "ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataCountryOfPrimaryManufacturer", 3), GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 7));
									//		}
									//		String sProdRelationManufacturer = GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 8);
									//		if (!sProdRelationManufacturer.isEmpty()) {
									//			GenericCode.ControlToUse(driver, "ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataProductRelationManufacturer", 3), sProdRelationManufacturer);
									//			GenericCode.ControlToUse(driver, "XPATH",GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataSelectFactory", 3),true);
									//		}
									//		String sProdSelectRegionOfSupply = GenericCode.FindValueInExcelSheet(wb, "DataSheet3", 0, "Start", 10);
									//		GenericCode.ControlToUse(driver, "ID", GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ProductNServicesFillDataSelectRegionOfSupply", 3), sProdSelectRegionOfSupply);
									//		GenericCode.ControlToUse(driver, "ID",GenericCode.FindValueInExcelSheet(wb, "ColumnMapping", 0, "ContinueButton", 3),true);
									//		driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//		Thread.sleep(10000);
									//		break;
									//	} // End Case
									//}	// For Loop
									//break;
								case "Input": // populating data into Input fields 
								case "ClearData": // Clearing data from a field 
								case "FileOpen": // populating data into Input fields like upload files and read only fields 
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(3000);
									ControlToUse(wdriver, sIdentificationType,sIdentification,sControlValue,sControlType);
									SmokeTestWinium.ReportTestCaseResult("Pass");
									//Thread.sleep(2000);
									break;
								case "DropDown": // populating data into drop down fields
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									ControlDropdown(wdriver, sIdentificationType,sIdentification,sControlValue);
									SmokeTestWinium.ReportTestCaseResult("Pass");
									//Thread.sleep(2000);
									break;
								case "SearchTransac":
									System.out.println("Transaction number "+sGetTextFromControl);
									Thread.sleep(2000);
									ControlToUse(wdriver, sIdentificationType,sIdentification,sGetTextFromControl,sControlType);
									SmokeTestWinium.ReportTestCaseResult("Pass");
									break;
								case "Click": // clicking the appropriate buttons
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(10000);
									System.out.println("Locator "+sIdentification);
									ControlToUse(wdriver, sIdentificationType, sIdentification, true);
									SmokeTestWinium.ReportTestCaseResult("Pass");
									//Thread.sleep(2000);
									break;
								case "DoubleClick": // clicking the appropriate buttons
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									ControlToUse(wdriver, sIdentificationType, sIdentification, false);
									SmokeTestWinium.ReportTestCaseResult("Pass");
									//Thread.sleep(2000);
									break;
								case "SequenceClick": // clicking the appropriate buttons with available text
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									sIdentification = sIdentification + sControlValue + sSequenceStr;
									ControlToUse(wdriver, sIdentificationType, sIdentification, true);
									SmokeTestWinium.ReportTestCaseResult("Pass");
									break;
								case "KEYTAB": // Tab Keypress Tab 
								case "KEYDown_Arrow":// Down Arrow Key Press
								case "KEYENTER": // Tab Keypress Enter
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									ControlKeyPress(wdriver, sIdentificationType,sIdentification,sControlValue,sControlType);
									SmokeTestWinium.ReportTestCaseResult("Pass");
									//Thread.sleep(2000);
									break;
									//** Vinayak
								case "WKeys_Down_Enter": //Windows Key down
									 try {
										    Robot r = new Robot();
										    //there are other methods such as positioning mouse and mouseclicks etc.
										    Thread.sleep(3000);
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    System.out.println("test down key press");
										    Thread.sleep(3000);
										    r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
										    System.out.println("test Enter key press");
										    r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
										    System.out.println("test enter key release");
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    System.out.println("test down key release");
										    Thread.sleep(3000);
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									break;
								case "click_addSourceitem": // clicking on Add button of source item
									try {
									    Robot r = new Robot();
									    Thread.sleep(2000);
									    r.mouseMove(666, 460); // move mouse point to specific location	
								        r.delay(1500);        // delay is to make code wait for mentioned milliseconds before executing next step	
								        r.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click	
								        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click	
								        r.delay(1500);	
									 } catch (AWTException e) {
									    //Teleport penguins  
									 }
									break;
									/*Actions builder = new Actions(driver);   
									builder.moveToElement(null, -460, -665).click().build().perform();
									System.out.println("Hello click");
									Thread.sleep(2000);
									break;*/
								case "SI_addSourceitem": // clicking on Add button of source item
									try {
									    Robot r = new Robot();
									    Thread.sleep(2000);
									    r.mouseMove(583, 569); // move mouse point to specific location	
								        r.delay(1500);        // delay is to make code wait for mentioned milliseconds before executing next step	
								        r.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click	
								        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click	
								        r.delay(1500);	
									 } catch (AWTException e) {
									    //Teleport penguins  
									 }
									break;	
								case "Down_Check": //Checking on Source transaction number
									 try {
										    Robot r = new Robot();
										    //there are other methods such as positioning mouse and mouseclicks etc.
										    Thread.sleep(2000);
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    System.out.println("Hello DOWN");
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(2000);
										    r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
										    r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
										    Thread.sleep(2000);
										    r.keyPress(java.awt.event.KeyEvent.VK_SPACE);
										    System.out.println("Hello SPACE");
										    r.keyRelease(java.awt.event.KeyEvent.VK_SPACE);										
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									break;
								case "DownChkEnter": //Checking on Source transaction number
									 try {
										    Robot r = new Robot();
										    //there are other methods such as positioning mouse and mouseclicks etc.
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    System.out.println("Hello DOWN");
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_SPACE);
										    System.out.println("Hello SPACE");
										    r.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
										    System.out.println("Hello Enter");
										    r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									break;
								case "ControlF": //Windows Key 
									try{
										Robot r = new Robot();
									    //there are other methods such as positioning mouse and mouseclicks etc.
									    ;
									    r.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
									    Thread.sleep(1000);
									    r.keyPress(java.awt.event.KeyEvent.VK_F);
									    Thread.sleep(1000);
									    r.keyRelease(java.awt.event.KeyEvent.VK_F);
									    Thread.sleep(1000);
									    r.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									break;
								case "WKeys_Enter": //Windows Key 
									 try {
										    Robot r = new Robot();
										    //there are other methods such as positioning mouse and mouseclicks etc.
										    Thread.sleep(3000);
										    r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
										    System.out.println("test Enter key press");
										    r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
										    System.out.println("test enter key release");
										    Thread.sleep(3000);
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									break;
							/*	case "WENTER" : // Enter key press
									 try {
										    Robot r = new Robot();
										    //there are other methods such as positioning mouse and mouseclicks etc.
										    //r.keyPress(13);
										   
										    
										   ;
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									 break;*/
								case "Assetordsel": //Windows Key for order type selection 
									 try {
										    Robot r = new Robot();
										    //there are other methods such as positioning mouse and mouseclicks etc.
										    Thread.sleep(3000);
										   /* r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(3000);
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(3000);
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(3000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(3000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(3000);*/
										    r.keyPress(java.awt.event.KeyEvent.VK_ALT);
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(3000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    r.keyRelease(java.awt.event.KeyEvent.VK_ALT);
										    Thread.sleep(3000);
										    System.out.println("ALT Down key press");
										    r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
										    Thread.sleep(3000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
										    System.out.println("test enter key release");
										    Thread.sleep(3000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									break;
								case "SPOneSelection": //Windows Key for Sales person Selection 
									 try {
										    Robot r = new Robot();
										    //there are other methods such as positioning mouse and mouseclicks etc.
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_TAB);
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_ALT);
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(1000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    r.keyRelease(java.awt.event.KeyEvent.VK_ALT);
										    Thread.sleep(1000);
										    System.out.println("ALT Down key press");
										    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
										    Thread.sleep(1000);
										    r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
										    Thread.sleep(1000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
										    System.out.println("test enter key release");
										    Thread.sleep(1000);
										    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
										    SmokeTestWinium.ReportTestCaseResult("Pass");
										 } catch (AWTException e) {
										    //Teleport penguins  
										 }
									break;
									//** Vinayak
								case "WaitFor": // Wait for seconds
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//New code to try wait till element present
									//WebDriverWait wait = new WebDriverWait(wdriver, 15);
									//wait.until(ExpectedConditions.presenceOfElementLocated(By.(sIdentificationType,sIdentification));
									Thread.sleep(5000);
									Thread.sleep(Integer.parseInt(sControlValue));
									///SmokeTestWinium.ReportTestCaseResult(" ");
									break;
								 case "InputOnFocus": // Input a text when a control gets focus
							         //wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
							         EnterStringOnFocus(sControlValue);
							         SmokeTestWinium.ReportTestCaseResult("Pass");
							         break;
								case "AlertOk": // Clicking OK on alerts if exists
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									checkAlert(wdriver);
				
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(2000);
									break;
								case "AlertCancel": // Clicking CANCEL on alerts if exists
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									checkAlertToCancel(wdriver);
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									break;
								case "VerifyTitle": // Verifying Title
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									Thread.sleep(5000);
									String sTitle = wdriver.getTitle().trim();
									//if (driver.getTitle().equals(sExpectedValue.trim()) == false) {
									if (sTitle.equals(sControlValue.trim())) {
										System.out.println("Expected Text '" + sExpectedValue + "' Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Pass");
									} else {
										System.out.println("Expected Text '" + sExpectedValue + "' Not Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Fail");
									}
									break;
								case "Verifyvalue": // Verifying a value
									//driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									if (CheckValuePresent(wdriver, sIdentificationType,sIdentification,sControlValue.trim()) == false) {
										System.out.println("Expected Text '" + sControlValue + "' Not Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Fail");
									} else {
										System.out.println("Expected Text '" + sControlValue + "' Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Pass");
									}
									break;
								case "VerifyName": // Verifying Title
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									Thread.sleep(5000);
									if (CheckNamePresent(wdriver, sIdentificationType,sIdentification,sControlValue.trim()) == false) {
										System.out.println("Expected Text '" + sControlValue + "' Not Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Fail");
									} else {
										System.out.println("Expected Text '" + sControlValue + "' Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Pass");
									}
									break;

								case "Verify": // Verifying a value1
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									if (CheckTextPresent(wdriver, sIdentificationType,sIdentification,sControlValue.trim()) == false) {
										System.out.println("Expected Text '" + sControlValue + "' Not Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Fail");
									} else {
										System.out.println("Expected Text '" + sControlValue + "' Found...: ");
										SmokeTestWinium.ReportTestCaseResult("Pass");
									}
									break;
								case "SwitchToWindow": // Switching to a window
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									if( bParentWindow == true)  {
								        parentWindowHandle = wdriver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									wdriver = (WiniumDriver) getHandleToWindow(wdriver,sControlValue);
									//Thread.sleep(5000);
									SmokeTestWinium.ReportTestCaseResult(" ");
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									break;
								case "SwitchToFrame": // Switching to a Frame
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									if( bParentWindow == true)  {
								        parentWindowHandle = wdriver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									ControlToSwitchFrame(wdriver,sIdentificationType,sIdentification);
									SmokeTestWinium.ReportTestCaseResult(" ");
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									break;
								case "SwitchToIFrame": // Switching to a Frame
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									Thread.sleep(5000);
									if( bParentWindow == true)  {
								        parentWindowHandle = wdriver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									ControlToSwitchFrame(wdriver,sIdentificationType,sIdentification);
									SmokeTestWinium.ReportTestCaseResult(" ");
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									break;
								case "SwitchToTAB": // Switching to a Tab
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									Thread.sleep(5000);
									if( bParentWindow == true)  {
								        parentWindowHandle = wdriver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									ControlToSwitchTAB(wdriver,sIdentificationType,sIdentification);
									SmokeTestWinium.ReportTestCaseResult(" ");
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									break;
								case "SwitchBack": // Switching Back to Parent window
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									wdriver.switchTo().window(parentWindowHandle); // Switch to parent window
									SmokeTestWinium.ReportTestCaseResult(" ");
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									break;
								case "CloseWindow":
									if( bParentWindow == true)  {
								        parentWindowHandle = wdriver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
								    wdriver.close();
								    wdriver = (WiniumDriver) getHandleToWindow(wdriver,sControlValue);
								    SmokeTestWinium.ReportTestCaseResult(" ");
								    //wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
								    //Thread.sleep(5000);
								    break;
								case "ScrollDown": // Scroll Down the page to access the element
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									JavascriptExecutor js = (JavascriptExecutor) wdriver;
									js.executeScript("javascript:window.scrollBy(250,350)");
									SmokeTestWinium.ReportTestCaseResult(" ");
									break;
								case "EnableElement": // Enabling an disabled element through JS to fetch value
									//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									WebElement textbox = ControlToEnableByScript(wdriver,sIdentificationType,sIdentification);
									((JavascriptExecutor) wdriver).executeScript("arguments[0].enabled = true", textbox);
									sControlToEnable = (String) ((JavascriptExecutor) wdriver).executeScript("return arguments[0].value;", textbox);
									SmokeTestWinium.ReportTestCaseResult(" ");
									break;
								case "WaitForElement": // Wait for element1
							         //wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
							         ControlToWaitForElement(wdriver, sIdentificationType, sIdentification);
							         break;
								case "GetTextFrom": // Getting text from a control
									//driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									if( sGetTextFromControl.isEmpty()) {
										SmokeTestWinium.ReportTestCaseResult("Fail");
									}
									else {
										SmokeTestWinium.ReportTestCaseResult("Pass");
									}
									break;
								} // Case
								iTestCaseSteps ++;
							} // if
							//*********************************
						} // Second Loop
						//wdriver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
						Thread.sleep(5000);
					} // Second If
				}	// First If
			} // First Loop
		} catch (FileNotFoundException e) {
			bExceptionOccurred = true;
			e.printStackTrace();
		} catch (MessagingException e) {
			bExceptionOccurred = true;
			e.printStackTrace();
		} catch (InterruptedException e) {
			bExceptionOccurred = true;
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		if(bExceptionOccurred) {
			wdriver.close();
		}
	}
	
	@AfterTest
	public static void CloseAfterTesting() throws Exception {
		try {

			SmokeTestWinium.ReportFooter();
			//GenericCode.OpenHTMLFile(driver, sReportPath);
			//wdriver.quit();
			if( bVideoStarted) {
				//GenericCode.StopVideoRecorder();
				bVideoStarted = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void ReportHeader() throws Exception {
		try {
			bw.write("<html><head><title>Automation Test Results</title>");
			bw.write("<link rel='stylesheet' type='text/css' href='" + sStyleSheetPath + "'style.css'>");
			bw.write("</head>");
			bw.write("<body>");
			bw.write("<center> <table width='800px' cellspacing='0' cellpadding='0' bordercolor='#ECE9D8' border='0'>");
			bw.write("<tbody>");
			bw.write("<tr>");
			bw.write("<td align='left' style='padding-left:20px;'>");
			bw.write("<span class='style1'>");
			bw.write("<u>" + sDataSheet + " : AUTOMATION TEST EXECUTION REPORT                                                  </u>");
			bw.write("<p></p>");
			bw.write("</span>");
			bw.write("</td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td align='left' style='padding-left:20px;'>");
			bw.write("<span class='style14'>");
			bw.write("<u>ENVIRONMENT : " + sEnvironmentName + "  </u>");
			bw.write("<p></p>");
			bw.write("</span>");
			bw.write("</td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td align='left' style='padding-left:20px;'>");
			bw.write("<span class='style14'>");
			bw.write("<u>ENVIRONMENT URL : " + baseURL + "  </u>");
			bw.write("<p></p>");
			bw.write("</span>");
			bw.write("</td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td align='left' style='padding-left:20px;'>");
			bw.write("<span class='style14'>");
			bw.write("<u>Run Date : " + sRunDate + "  </u>");
			bw.write("<p></p>");
			bw.write("</span>");
			bw.write("</td>");
			bw.write("</tr>");
			bw.write("</tr>");
			bw.write("</tbody>");
			bw.write("</table>");
			bw.write("<center> <table width='800px' cellspacing='0' cellpadding='0' bordercolor='#ECE9D8' border='0'>");
			bw.write("<tbody>");
			bw.write("<tr>");
			bw.write("<td align='center' style='background-color:#3399CC; border:1px; border-color:#23698B; border-style:solid; width:50px;'>");
			bw.write("<span style='font-family: Calibri'>STEP # </span>");
			bw.write("</td>");
			bw.write("<td align='center' style='background-color:#3399CC; border:1px; border-color:#23698B; border-style:solid; width:300px;'>");
			bw.write("<span style='font-family: Calibri'>STEP DESCRIPTION </span>");
			bw.write("</td>");
			bw.write("<td align='center' style='background-color:#3399CC; border:1px; border-color:#23698B; border-style:solid; width:300px;'>");
			bw.write("<span style='font-family: Calibri'>EXPECTED RESULT </span>");
			bw.write("</td>");
			bw.write("<td align='center' style='background-color:#3399CC; border:1px; border-color:#23698B; border-style:solid; width:50px;'>");
			bw.write("<span style='font-family: Calibri'>RESULT </span>");
			bw.write("</td>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

public static void ReportTestCaseNameData(String sTestCaseName) throws Exception {
		try {
			bw.write("<left> <table width='800px' cellspacing='0' cellpadding='0' bordercolor='#ECE9D8' border='0'>");
			bw.write("<tbody>");
			bw.write("<tr bgcolor='#E0FFFF'>");
			bw.write("<td align='left' style='border:1px; border:1px; border-color:#23698B; border-style:solid; width:800px;'>");
			bw.write("<span style='font-family: Calibri'> Test Case Name: " + sTestCaseName + "</span>");
			bw.write("</td></tr>");
			bw.write("</tbody>");
			bw.write("</table></left>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ReportTestCaseDetails(int iStepNo, String sStepDescription, String sStepExpection) throws Exception {
		try {
			bw.write("<left> <table width='800px' cellspacing='0' cellpadding='0' bordercolor='#ECE9D8' border='0'>");
			bw.write("<tbody>");
			bw.write("<tr>");

			bw.write("<td align='center' style='border:1px; border:1px; border-color:#23698B; border-style:solid; width:50px;'>");
			bw.write("<span style='font-family: Calibri'>" + iStepNo + "</span>");
			bw.write("</td>");

			bw.write("<td align='center' style='border:1px; border:1px; border-color:#23698B; border-style:solid; width:300px;'>");
			bw.write("<span style='font-family: Calibri'>" + sStepDescription + " </span>");
			bw.write("</td>");

			bw.write("<td align='center' style='border:1px; border:1px; border-color:#23698B; border-style:solid; width:300px;'>");
			bw.write("<span style='font-family: Calibri'>" + sStepExpection + " </span>");
			bw.write("</td>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ReportTestCaseResult(String sResult) throws Exception {
		try {
			bw.write("<td align='center' style='border:1px; border-color:#23698B; border-style:solid; width:50px;'>");
			if (sResult.equals("Pass")) {
				bw.write("<span style='font-family: Calibri;color:#009933'>" + sResult + "</span>");
			} else {
				bw.write("<span style='font-family: Calibri;color:#ff0000'>" + sResult + "</span>");
			}
			bw.write("</td>");
			bw.write("</tr>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void ReportFooter() throws Exception {
		try {
			bw.write("</table></center>");
			bw.write("<left> <table width='800px' cellspacing='0' cellpadding='0' bordercolor='#ECE9D8' border='0'>");
			bw.write("<tbody>");
			bw.write("<tr>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td height='33' align='left' style='padding-left:20px; font-family:Times New Roman; font-size:15px; color:#000000;'>");
			bw.write("<p class='style19'>Regards,</p>");
			bw.write("<p></p>");
			bw.write("</td>");
			bw.write("</tr>");
			bw.write("<tr>");
			bw.write("<td class='style20' align='left' style='padding-left:20px;'>QA Team</td>");
			bw.write("</tr>");
			bw.write("</table></left>");
			bw.write("</body></html>");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver OpenWebDriver(HSSFWorkbook DataBook, String ShDriver, String sStringToSearch) {
		WebDriver driver = null; int DrvRow = 0 ; File fileIE = null; 
		ProfilesIni profile = new ProfilesIni();
		HSSFSheet DataSheet = null;
		DataSheet = DataBook.getSheet(ShDriver);
		HSSFRow row2 = DataSheet.getRow(0);
		String sDriverName;

		for(int row=0; row <=DataSheet.getLastRowNum();row++)
		{
			// Store the value of Column A for comparing
			HSSFRow row1 = DataSheet.getRow(row);
			row2 = DataSheet.getRow(row);
			String value = row1.getCell(0).toString();
			
            // If Column A value is equal to variable
	        if (value.equals(sStringToSearch)){
	             DrvRow = row;
	             break;
	        }
		}		
		HSSFCell cDriverStatus,cDriverPath,cDriverProperty = null;
		cDriverStatus = row2.getCell(0) ;
		cDriverPath = row2.getCell(1) ;
		cDriverProperty = row2.getCell(2) ;
		
		System.out.println(DrvRow);		
		System.out.println(cDriverStatus.getStringCellValue());
		
		if (DrvRow > 2) {
			fileIE = new File(cDriverPath.getStringCellValue().toString());
			System.setProperty(cDriverProperty.getStringCellValue().toString(), fileIE.getAbsolutePath());
		}

		switch (DrvRow) {
		case 2:
			sDriverName = "FireFox";
			//FirefoxProfile myprofile = profile.getProfile("AutomationTester");
			driver = new FirefoxDriver();
			break;
		case 3:
			sDriverName = "InternetExplorer";
			driver = new InternetExplorerDriver();
			break;
		case 4:
			sDriverName = "GoogleChrome";
			driver = new ChromeDriver();
			break;
		}
		return driver;
	}

	public static HSSFWorkbook OpenExcelWorkBook(String sFile) throws FileNotFoundException, BiffException {
		HSSFWorkbook Wb = null;
		try {
			FileInputStream file = new FileInputStream(sFile);
			Wb = new HSSFWorkbook(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Wb;
	}

	public static String FindValueInExcelSheet(HSSFWorkbook DataBook, String SheetName, int iCols, String sToSearch, int iColsToReturn ) {

		HSSFSheet DataSheet = null;
		int FoundinRow = 0;		
		DataSheet = DataBook.getSheet(SheetName);
		HSSFRow row2 = DataSheet.getRow(0);

		for(int row=0; row <=DataSheet.getLastRowNum();row++)
		{
			// Store the value of Column iCols for comparing
			//String value = DataSheet.getCell(iCols, row).getContents();
			HSSFRow row1 = DataSheet.getRow(row);
			row2 = DataSheet.getRow(row);
			String value = row1.getCell(iCols).toString();
            // If Column A value is equal to variable
	        if (value.equals(sToSearch)){
	             FoundinRow = row;
	             row2 = DataSheet.getRow(row);
	             break;
	        }
		}
		String sReturnValue = null;
		HSSFCell cell = null;
		cell = DataSheet.getRow(FoundinRow).getCell(iColsToReturn) ;
		if (cell == null) {
			cell =  row2.getCell(iColsToReturn,row2.CREATE_NULL_AS_BLANK);
		}
		sReturnValue = cell.toString();
		//System.out.println(sReturnValue);		

		return sReturnValue;
		//return DataSheet.getCell(iColsToReturn,FoundinRow).getContents();
	}
	
	public static void ControlToUse(WebDriver driver, String sIdentificationType, String sIdentification, boolean bIsClick) {
		// Click
		Actions action = new Actions(wdriver);
		if (bIsClick) {
			switch (sIdentificationType) {
			case "ID":
				action.moveToElement(wdriver.findElement(By.id(sIdentification))).click().perform();
				break;
			case "NAME":
				action.moveToElement(wdriver.findElement(By.name(sIdentification))).click().perform();
				break;
			case "XPATH":
				action.moveToElement(wdriver.findElement(By.xpath(sIdentification))).click().perform();
				break;
			case "LINKTEXT":
				action.moveToElement(wdriver.findElement(By.linkText(sIdentification))).click().perform();
				break;
			case "CSSPATH":
				action.moveToElement(wdriver.findElement(By.cssSelector(sIdentification))).click().perform();
				break;
			case "CLASS":
				action.moveToElement(wdriver.findElement(By.className(sIdentification))).click().perform();
				break;
			}
		} else {
		// DoubleClick
			switch (sIdentificationType) {
			case "ID":
				action.moveToElement(wdriver.findElement(By.id(sIdentification))).doubleClick().perform();
				break;
			case "NAME":
				action.moveToElement(wdriver.findElement(By.name(sIdentification))).doubleClick().perform();
				break;
			case "XPATH":
				action.moveToElement(wdriver.findElement(By.xpath(sIdentification))).doubleClick().perform();
				break;
			case "LINKTEXT":
				action.moveToElement(wdriver.findElement(By.linkText(sIdentification))).doubleClick().perform();
				break;
			case "CSSPATH":
				action.moveToElement(wdriver.findElement(By.cssSelector(sIdentification))).doubleClick().perform();
				break;
			case "CLASS":
				action.moveToElement(wdriver.findElement(By.className(sIdentification))).doubleClick().perform();
				break;
			}
		}
	}
	
	public static void ControlDropdown(RemoteWebDriver driver, String sIdentificationType, String sIdentification, String sGetInputValue) {
		// Dropdown
		switch (sIdentificationType) {
		case "ID":
			new Select(wdriver.findElement(By.id(sIdentification))).selectByVisibleText(sGetInputValue);
			break;
		case "NAME":
			new Select(wdriver.findElement(By.name(sIdentification))).selectByVisibleText(sGetInputValue);
			break;
		case "XPATH":
			new Select(wdriver.findElement(By.xpath(sIdentification))).selectByVisibleText(sGetInputValue);
			break;
		case "LINKTEXT":
			new Select(wdriver.findElement(By.linkText(sIdentification))).selectByVisibleText(sGetInputValue);
			break;
		case "CSSPATH":
			new Select(wdriver.findElement(By.cssSelector(sIdentification))).selectByVisibleText(sGetInputValue);
			break;
		case "CLASS":
			new Select(wdriver.findElement(By.className(sIdentification))).selectByVisibleText(sGetInputValue);
			break;
		}
	}

	//driver.findElement(By.id(sIdentification)).sendKeys(Keys.TAB);
	public static void ControlToUse(WebDriver driver, String sIdentificationType,String sIdentification,String sGetInputValue,String sControlType) {
		// Input, FileOpen, ClearData
		if (sControlType.matches("Input") | sControlType.matches("ClearData") ) {
			driver.findElement(By.id(sIdentification)).clear();
		}
		if (!sControlType.matches("ClearData")) {
		
			switch (sIdentificationType) {
			case "ID":
				wdriver.findElement(By.id(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "NAME":
				wdriver.findElement(By.name(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "XPATH":
				wdriver.findElement(By.xpath(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "LINKTEXT":
				wdriver.findElement(By.linkText(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CSSPATH":
				wdriver.findElement(By.cssSelector(sIdentification)).sendKeys(sGetInputValue);
				break;
			case "CLASS":
				wdriver.findElement(By.className(sIdentification)).sendKeys(sGetInputValue);
				break;
			}
		}
	}

	public static void ControlKeyPress(RemoteWebDriver driver, String sIdentificationType,String sIdentification,String sGetInputValue, String sControlType) {
		// For Key Press
		Actions action = new Actions(driver);
		String Keypress = null;
		switch (sControlType) {
		case "KEYTAB":
			Keypress = "Keys.TAB";
			break;
		case "KEYALT":
			Keypress = "Keys.ALT";
			break;
		case "KEYALTSA":
			Keypress = "Keys.ALT+SA";
			break;
		case "KEYENTER":
			Keypress = "Keys.ENTER";
			break;
		case "KEYDown_Arrow":
			Keypress = "Keys.ARROW_DOWN";
			break;
		}
		switch (sIdentificationType) {
		case "ID":
			action.sendKeys(wdriver.findElement(By.id(sIdentification)),Keypress).build().perform();
			break;
		case "NAME":
			action.sendKeys(wdriver.findElement(By.name(sIdentification)),Keypress).build().perform();
			break;
		case "XPATH":
			action.sendKeys(wdriver.findElement(By.xpath(sIdentification)),Keypress).build().perform();
			break;
		case "LINKTEXT":
			action.sendKeys(wdriver.findElement(By.linkText(sIdentification)),Keypress).build().perform();
			break;
		case "CSSPATH":
			action.sendKeys(wdriver.findElement(By.cssSelector(sIdentification)),Keypress).build().perform();
			break;
		case "CLASS":
			action.sendKeys(wdriver.findElement(By.className(sIdentification)),Keypress).build().perform();
			break;
		}
	}

	public static void ControlToSwitchFrame(RemoteWebDriver driver, String sIdentificationType, String sIdentification) {
		// Switch To Frame
		switch (sIdentificationType) {
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
		case "CSSPATH":
			driver.switchTo().frame(driver.findElement(By.cssSelector(sIdentification)));
			break;
		case "CLASS":
			driver.switchTo().frame(driver.findElement(By.className(sIdentification)));
			break;
		}
	}

	public static void ControlToSwitchIFrame(RemoteWebDriver driver, String sIdentificationType, String sIdentification) {
		// Switch To IFrame
		switch (sIdentificationType) {
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
		case "CSSPATH":
			driver.switchTo().frame(driver.findElement(By.cssSelector(sIdentification)));
			break;
		case "CLASS":
			driver.switchTo().frame(driver.findElement(By.className(sIdentification)));
			break;
		}
		driver.switchTo().frame(0);
	}

	public static void ControlToSwitchTAB(RemoteWebDriver driver, String sIdentificationType, String sIdentification) {
		// Switch To TAB
		switch (sIdentificationType) {
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
		case "CSSPATH":
			driver.findElement(By.cssSelector(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		case "CLASS":
			driver.findElement(By.className(sIdentification)).sendKeys(Keys.CONTROL +"\t");
			break;
		}
	}
	public static boolean CheckTextPresent(RemoteWebDriver driver, String sIdentificationType,String sIdentification,String sTextToSearch) {
		boolean bValueFound = false;
		String s;
		try {
			switch (sIdentificationType) 
			{
			case "ID":
				bValueFound  = driver.findElement(By.id(sIdentification)).getText().trim().matches(sTextToSearch);
				s = driver.findElement(By.id(sIdentification)).getText().trim();
				System.out.println("Actual Vaue '" + s);
				break;
			case "NAME":
				bValueFound  = driver.findElement(By.name(sIdentification)).getText().trim().matches(sTextToSearch);
				s = driver.findElement(By.name(sIdentification)).getText().trim();
				System.out.println("Actual Vaue '" + s);
				break;
			case "XPATH":
				bValueFound  = driver.findElement(By.xpath(sIdentification)).getText().trim().matches(sTextToSearch);
				s = driver.findElement(By.xpath(sIdentification)).getText().trim();
				System.out.println("Actual Vaue '" + s);
				break;
			case "LINKTEXT":
				bValueFound  = driver.findElement(By.linkText(sIdentification)).getText().trim().matches(sTextToSearch);
				s = driver.findElement(By.linkText(sIdentification)).getText().trim();
				System.out.println("Actual Vaue '" + s);
				break;
			case "CSSPATH":
				bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getText().trim().matches(sTextToSearch);
				s = driver.findElement(By.cssSelector(sIdentification)).getText().trim();
				System.out.println("Actual Vaue '" + s);
				break;
			case "CLASS":
				bValueFound  = driver.findElement(By.className(sIdentification)).getText().trim().matches(sTextToSearch);
				s = driver.findElement(By.className(sIdentification)).getText().trim();
				System.out.println("Actual Vaue '" + s);
				break;
			}
		} catch (Exception e) 
		{
		  return bValueFound;
		}
		return bValueFound;
	}

	public static WebElement ControlToEnableByScript(RemoteWebDriver driver,String sIdentificationType,String sIdentification) {
		// Enable a particular Control through script
		WebElement textbox = null;

		switch (sIdentificationType) {
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
		case "CSSPATH":
			textbox = driver.findElement(By.cssSelector(sIdentification));
			break;
		case "CLASS":
			textbox = driver.findElement(By.className(sIdentification));
			break;
		}
		return textbox;
	}
	
	 public static void EnterStringOnFocus(String sText) {
	        try {
	            //wait - increase this wait period if required
	            Thread.sleep(5000);
	            //create robot for keyboard operations
	            Robot rb = new Robot();
	            //Enter user name by CTRL-V
	            StringSelection sInputText = new StringSelection(sText);
	            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sInputText, null);            
	            rb.keyPress(KeyEvent.VK_CONTROL);
	            rb.keyPress(KeyEvent.VK_V);
	            rb.keyRelease(KeyEvent.VK_V);
	            rb.keyRelease(KeyEvent.VK_CONTROL);
	            //wait
	            Thread.sleep(5000);
	        } catch (Exception ex) {
	            System.out.println("Error in Focus Thread: " + ex.getMessage());
	        }
	    }
	 public static boolean CheckValuePresent(WiniumDriver driver, String sIdentificationType,String sIdentification,String sTextToSearch) {
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
	 public static boolean CheckNamePresent(WiniumDriver driver, String sIdentificationType,String sIdentification,String sTextToSearch) {
			boolean bValueFound = false;
			String verifytxt ;
			try {
				switch (sIdentificationType) 
				{
				case "ID":
					bValueFound  = driver.findElement(By.id(sIdentification)).getAttribute("Name").trim().matches(sTextToSearch);
					Thread.sleep(5000);
					verifytxt = driver.findElement(By.id(sIdentification)).getAttribute("Name");
					System.out.println("Text is ="+verifytxt);
					break;
				case "NAME":
					bValueFound  = driver.findElement(By.name(sIdentification)).getAttribute("Name").trim().matches(sTextToSearch);
					Thread.sleep(2000);
					verifytxt = driver.findElement(By.name(sIdentification)).getAttribute("Name");
					System.out.println("Text is ="+verifytxt);
					break;
				case "XPATH":
					bValueFound  = driver.findElement(By.xpath(sIdentification)).getAttribute("Name").trim().matches(sTextToSearch);
					Thread.sleep(2000);
					verifytxt = driver.findElement(By.xpath(sIdentification)).getAttribute("Name");
					System.out.println("Text is ="+verifytxt);
					break;
				case "LINKTEXT":
					bValueFound  = driver.findElement(By.linkText(sIdentification)).getAttribute("Name").trim().matches(sTextToSearch);
					Thread.sleep(2000);
					verifytxt = driver.findElement(By.linkText(sIdentification)).getAttribute("Name");
					System.out.println("Text is ="+verifytxt);
					break;
				case "CSSPATH":
					bValueFound  = driver.findElement(By.cssSelector(sIdentification)).getAttribute("Name").trim().matches(sTextToSearch);
					Thread.sleep(2000);
					verifytxt = driver.findElement(By.cssSelector(sIdentification)).getAttribute("Name");
					System.out.println("Text is ="+verifytxt);
					break;
				case "CLASS":
					bValueFound  = driver.findElement(By.className(sIdentification)).getAttribute("Name").trim().matches(sTextToSearch);
					Thread.sleep(2000);
					verifytxt = driver.findElement(By.className(sIdentification)).getAttribute("Name");
					System.out.println("Text is ="+verifytxt);
					break;
				}
			} catch (Exception e) 
			{
			  return bValueFound;
			}
			return bValueFound;
		}
	public static String ControlToGetTextFrom(RemoteWebDriver driver,String sIdentificationType,String sIdentification) {
		// To Get Text from a control
		String textbox = null;

		switch (sIdentificationType) {
		case "ID":
			textbox = driver.findElement(By.id(sIdentification)).getAttribute("Name");
			//textbox = ((RemoteWebDriver) driver).findElementById(sIdentification).getAttribute("Name");
			System.out.println(textbox);
			break;
		case "NAME":
			textbox = ((RemoteWebDriver) driver).findElement(By.name(sIdentification)).getAttribute("Name");
			//textbox = ((RemoteWebDriver) driver).findElementByName(sIdentification).getAttribute("Name");
			break;
		case "XPATH":
			textbox = driver.findElement(By.xpath(sIdentification)).getAttribute("Name");
			//textbox = ((RemoteWebDriver) driver).findElementByXPath(sIdentification).getAttribute("Name");
			break;
		case "LINKTEXT":
			textbox = driver.findElement(By.linkText(sIdentification)).getAttribute("Name");
			break;
		case "CSSPATH":
			textbox = driver.findElement(By.cssSelector(sIdentification)).getAttribute("Name");
			break;
		case "CLASS":
			textbox = driver.findElement(By.className(sIdentification)).getAttribute("Name");
			//textbox = ((RemoteWebDriver) driver).findElementByClassName(sIdentification).getAttribute("Name");
			break;
		}
		return textbox;
	}
	
	public static void checkAlert(WebDriver driver) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 2);
	       // wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        alert.accept();
	    } catch (Exception e) {
	        //exception handling
	    	System.out.println("No Alerts were found.");
	    }
	}	

	public static void checkAlertToCancel(WebDriver driver) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 2);
	       // wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        alert.dismiss();
	    } catch (Exception e) {
	        //exception handling
	    	System.out.println("No Alerts were found.");
	    }
	}

	public static WebDriver getHandleToWindow(WiniumDriver driver, String title){

		WiniumDriver popup = null;
        //Set<String> windowIterator = WebDriverInitialize.getDriver().getWindowHandles();
        Set<String> windowIterator = driver.getWindowHandles();
        System.err.println("No of windows :  " + windowIterator.size());
        for (String s : windowIterator) {
        	String windowHandle = s; 
        	//popup = WebDriverInitialize.getDriver().switchTo().window(windowHandle);
        	popup = (WiniumDriver) driver.switchTo().window(windowHandle);
        	System.out.println("Window Title : " + popup.getTitle());
        	System.out.println("Window Url : " + popup.getCurrentUrl());
        	if (popup.getTitle().equals(title) ){
        		System.out.println("Selected Window Title : " + popup.getTitle());
        		return popup;
        	}
        }
        System.out.println("Window Title :" + popup.getTitle());
        System.out.println();
        return popup;
    }	

	 public static void ControlToWaitForElement(WiniumDriver driver, String sIdentificationType, String sIdentification) {
		  // Wait For Element
		  WebDriverWait wait = new WebDriverWait(driver, 15);
		  switch (sIdentificationType) {
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
		  case "CSSPATH":
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(sIdentification)));
		   break;
		  case "CLASS":
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(sIdentification)));
		   break;
		  }
		 }
	
    public static void WindowsAuthenticationlogin(String sUserName,String sPassword) throws Exception {

        try {
            //wait - increase this wait period if required
            Thread.sleep(5000);
           
            //create robot for keyboard operations
            Robot rb = new Robot();

            //Enter user name by ctrl-v
            StringSelection username = new StringSelection(sUserName);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);            
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);

            //tab to password entry field
            rb.keyPress(KeyEvent.VK_TAB);
            rb.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(2000);

            //Enter password by ctrl-v
            StringSelection pwd = new StringSelection(sPassword);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);
           
            //press enter
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
           
            //wait
            Thread.sleep(5000);
        } catch (Exception ex) {
            System.out.println("Error in Login Thread: " + ex.getMessage());
        }
    }                        
}
