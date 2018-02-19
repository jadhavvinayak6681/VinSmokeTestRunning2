package SmokeTesting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.mail.MessagingException;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import SmokeTesting.Constants;
import java.sql.Connection;

public class SmokeTesting {
	public static String baseURL,sIsVideoOn,sRecorderFilePath,RecorderFilePrefix,sHTMLReportsPath, sHTMLReportsPrefix,currentDate,sRunDate, sReportPath, sTimeOuts,sEnvironmentName,sStyleSheetPath;
	public static String parentWindowHandle,sBrowserName,sControlToEnable,sAuthentication,sAuthenticateUser,sAuthenticatePassword,sScreenShotPath,sGetTextFromControl,sSplitDelimeter;
	public static String sDBConnectionString,sSQLUser,sSQLPassword, sSQLQueryString, sSQLResult, sConnectToDatabase, sIsEMailOn;
	public static String sDataSheet,sFunctionSheet, sUtilitiesSheet, sDriversSheet, sDatabaseSheet, sObjectRepositorySheet,sEnvironmentSetUpSheet,sEmailSetUpSheet,sDatabaseQueriesSheet,sExcelUpdateSheet, sRemoteEXESheet;
	public static String sRemoteMachine;
	public static HSSFWorkbook wb, wbgen, wbwrite ; static HSSFSheet sh, shgen ;
	public static Connection ConnDB;
	public static int iTimeoutValue;
	public static BufferedWriter bw;
	public static File file;
	public static WebDriver driver;
	public static boolean bVideoStarted=false, bExceptionOccurred=false, bParentWindow=true, bCompareValues=false;
	public static void main(String args[]) throws Exception 
	{
		try 
		{
			SmokeTesting.InitializeForTesting();
			
			SmokeTesting.ProceedWithTesting();
			
			SmokeTesting.CloseAfterTesting();

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
					bVideoStarted = false;
				}
				driver.quit();
		}
	}

	@BeforeTest
	public static void InitializeForTesting() throws BiffException, ATUTestRecorderException, IOException {
		// Work sheet having all the generic parameters
		wbgen = ExcelFunctions.OpenExcelWorkBook(Constants.FILE_NAME);
		Constants.FILE_NAME = ExcelFunctions.FindValueInExcelSheet(wbgen, Constants.APPLICATION_SHEET, 0, Constants.GET_THIS_ROW, 2); // Path for application datasheet
		sDataSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, Constants.APPLICATION_SHEET, 0, Constants.GET_THIS_ROW, 1); // Data to be used for this application
		wbwrite = ExcelFunctions.OpenExcelWorkBook(Constants.FILE_NAME); // Workbook to write data
		sFunctionSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, Constants.APPLICATION_SHEET, 0, Constants.GET_THIS_ROW, 11); // get the sheet name of all the functional data setups 
		sUtilitiesSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 1); // get the sheet name of Utilities
		sDriversSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 2); // get the sheet name of Driver setup
		sDatabaseSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 3);  // get the sheet name of Database setup
		sObjectRepositorySheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 4); // get the sheet name of Object Repository
		sEnvironmentSetUpSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 5); // get the sheet name of Environment Setup
		sEmailSetUpSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 6); // Get the sheet name of EMail setup
		sDatabaseQueriesSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 7);	// Get the sheet name of Database Queries
		sBrowserName = ExcelFunctions.FindValueInExcelSheet(wbgen, sDriversSheet, 0, Constants.GET_THIS_ROW, 5 ); // Browser Name (FireFox,IE,Chrome,etc)
		sExcelUpdateSheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 8);	// Get the sheet name of Excel Update data
		sRemoteEXESheet = ExcelFunctions.FindValueInExcelSheet(wbgen, sFunctionSheet, 0, Constants.GET_FUNCTION, 9);	// Get the sheet name of remot execution data
		sRemoteMachine = ExcelFunctions.FindValueInExcelSheet(wbgen, sRemoteEXESheet, 0, Constants.GET_THIS_ROW, 1 ); // Remote machine to be used
		System.out.println("Opening Browser: "+sBrowserName);
		driver = WebDriverFunctions.OpenWebDriver(wbgen, sDriversSheet, Constants.GET_THIS_ROW, sRemoteMachine); // Browser driver to be used (IE, Chrome). Default is Firefox
		bVideoStarted = false;
		bExceptionOccurred = false;
		sIsVideoOn = ExcelFunctions.FindValueInExcelSheet(wbgen, sUtilitiesSheet, 0, "VideoRecorder", 1 ); // Parameter to get the status of Video Recorder
		sHTMLReportsPath = ExcelFunctions.FindValueInExcelSheet(wbgen, Constants.APPLICATION_SHEET, 0, Constants.GET_THIS_ROW, 5 ); // HTML Report Path to be used
		sHTMLReportsPrefix = ExcelFunctions.FindValueInExcelSheet(wbgen, Constants.APPLICATION_SHEET, 0, Constants.GET_THIS_ROW, 6 ); // HTML Report Prefix to be used
		currentDate = new SimpleDateFormat(Constants.DATE_FORMAT_FOR_FILE).format(new Date());
		sRunDate = new SimpleDateFormat(Constants.DATE_FORMAT_FOR_REPORT).format(new Date());
		sReportPath = sHTMLReportsPath + sHTMLReportsPrefix + currentDate + ".html";
		sScreenShotPath = sHTMLReportsPath;
		UtilityFunctions.VerifyExistenceOfFileOrDirectory(sHTMLReportsPath,"D"); 	// Verify the existence of report folder and create 
		UtilityFunctions.VerifyExistenceOfFileOrDirectory(sReportPath,"F"); 		// Verify the existence of report file and create
		bw = new BufferedWriter(new FileWriter(sReportPath));
		if (sIsVideoOn.matches("On")) {
			//GenericCode.SetupRecorderFilePath(ExcelFunctions.FindValueInExcelSheet(wbgen, sUtilitiesSheet, 0, "VideoRecorder", 3 ), ExcelFunctions.FindValueInExcelSheet(wbgen, sUtilitiesSheet, 0, "VideoRecorder", 4 ));
			//GenericCode.StartVideoRecorder();
			bVideoStarted = true;
		}
		sTimeOuts = ExcelFunctions.FindValueInExcelSheet(wbgen, sUtilitiesSheet, 0, "TimeoutValue", 2 ); // Timeout (wait) to be used between controls
		sSplitDelimeter = ExcelFunctions.FindValueInExcelSheet(wbgen, sUtilitiesSheet, 0, "SplitDelimeter", 3 ); // Delimiter to split values
		if (sTimeOuts.indexOf(".") > 0) {
			sTimeOuts = sTimeOuts.substring(0, sTimeOuts.indexOf(".")); 
		}
		iTimeoutValue = Integer.parseInt(sTimeOuts);
	}
	
	@Test
	public static void ProceedWithTesting() throws Exception {
		try {
			// Work sheet having all the application specific parameters
			wb = ExcelFunctions.OpenExcelWorkBook(Constants.FILE_NAME);
			sh = wb.getSheet(sDataSheet);
			int iRows = sh.getLastRowNum();
			//String sGetLink = null;
			sEnvironmentName = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 1 ); // environment name to be used
			sStyleSheetPath = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 7 ); // Style sheet path to be used
			sConnectToDatabase = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 16 ); // Connect to database Yes / No 
			sDBConnectionString = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 17 ); // Connection string for the database 
			sSQLUser = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 18 ); // Database user
			sSQLPassword = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 19 ); // Database User password
			if(sConnectToDatabase.equals(Constants.CONDITION_YES)) {
				ConnDB = DBFunctions.OpenMSSQLConnection(sDBConnectionString, sSQLUser, sSQLPassword); // Connect to the configured database
			}
			sIsEMailOn = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 20 ); // Is Send Email flag is set
			System.out.println("Starting for Application: "+sDataSheet);
			sAuthentication = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 13 );
			if (sAuthentication.matches(Constants.CONDITION_YES)) {
				sAuthenticateUser = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 14 );
				sAuthenticatePassword = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 15 );
				baseURL = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 2 ); // environment to be used
				baseURL = baseURL + ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 3 );
			}
			else{
				baseURL = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 2 ); // environment to be used
				baseURL = baseURL + ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 3 );
			}
			baseURL = UtilityFunctions.AddParametersToTheURL(wb, baseURL, sEnvironmentSetUpSheet);
			ReportFunctions.ReportHeader(bw, sStyleSheetPath, sDataSheet, sEnvironmentName, baseURL, sRunDate);
			for (int i = 1; i <= iRows;i++) { // For each test case in the control sheet
				HSSFRow row = sh.getRow(i);
				String sTestCaseName = row.getCell(0).getStringCellValue();
				String sFunction = row.getCell(1).toString();
				if (!sFunction.isEmpty()) {
					if (row.getCell(4).getStringCellValue().matches(Constants.CONDITION_YES)) { // Should execute the test case or not
						System.out.println(sTestCaseName);
						ReportFunctions.ReportTestCaseNameData(bw, sTestCaseName);
						int iStartColumn = (int) row.getCell(1).getNumericCellValue(); // Start getting data from this column
						int iEndColumn = (int) row.getCell(2).getNumericCellValue(); // End getting data from this column
						String sParameterSheet = row.getCell(5).getStringCellValue(); // Get Data from this sheet
						int iTestCaseSteps = 1;
						sGetTextFromControl = null;
						for (int ii = iStartColumn; ii <= iEndColumn; ii++) { // For the range of Start and End columns
							String sControlName = ExcelFunctions.FindValueInExcelSheet(wb, sParameterSheet, 0, "Action", ii); // Column Name = Control Name
							String sControlValue = ExcelFunctions.FindValueInExcelSheet(wb, sParameterSheet, 0, Constants.GET_THIS_ROW, ii); // Control Value = Data 
							if (!sControlValue.isEmpty()) { // If the data is not empty then proceed
								System.out.println("Control Name: " +sControlName);
								String sTestCaseSteps = ExcelFunctions.FindValueInExcelSheet(wb, sParameterSheet, 0, "TestSteps", ii); // Test Case Steps
								String sExpectedValue = ExcelFunctions.FindValueInExcelSheet(wb, sParameterSheet, 0, "Expected", ii); // Expected  
								String sKeyword = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 1); // get the control type like (input, dropdown) etc
								String sLocatorType = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 2); // get the control identification type like (id, xpath, class) etc
								//String sCheckExistence = FindValueInExcelSheet(wb, "ColumnMapping", 0, sControlName, 8); // whether need to check the existence of this object
								String sIdentification = null;
								sIdentification = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 3); // get the control identification like the finding the element
								String sSequenceStr = null;
								sSequenceStr = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 4); // get the sequence of the control identification to find the element
								//@Vinayak : To get partial text as per position on string and update excel, position is to be on excel sheet Column mapping Sequence column
								int sSequenceint = 0;
								if (!sSequenceStr.isEmpty())
								{
									if (!sSequenceStr.equals("value"))
									{
									sSequenceint=Integer.parseInt(sSequenceStr);
									}
								}
								String sEncrypt = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 8); // check whether the data should be encrypted in the report
								String sEncryptChr = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 9); // get the encrypt character
								driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
								switch (sKeyword) {
								case "BrowserReLaunch": // Closing the browser
									driver.manage().deleteAllCookies();
									driver.close();
									break;
								case "GetTextFrom":
								case "GetTextToUpdate":
									sGetTextFromControl = LocatorFunctions.ControlToGetTextFrom(driver,sLocatorType,sIdentification);
									sExpectedValue = UtilityFunctions.ToShowEncryptValueOrNot(sEncrypt, sExpectedValue, sEncryptChr, sGetTextFromControl, sControlValue);
									break;
								case "GetFromAttribute": // Get the text value from attributes
									sGetTextFromControl = LocatorFunctions.ControlToGetTextFromAttributes(driver,sLocatorType,sIdentification, sSequenceStr);
									sExpectedValue = UtilityFunctions.ToShowEncryptValueOrNot(sEncrypt, sExpectedValue, sEncryptChr, sGetTextFromControl, sControlValue);
									break;
								//@Vinayak : To get partial text as per position on string and update excel, position is to be on excel sheet Column mapping Sequence column
								case "GetPartialText": // Get the Partial text from string as per location in string
									sGetTextFromControl = LocatorFunctions.ControlToGetPartialTextFrom(driver,sLocatorType,sIdentification,sSequenceint);
									sExpectedValue = UtilityFunctions.ToShowEncryptValueOrNot(sEncrypt, sExpectedValue, sEncryptChr, sGetTextFromControl, sControlValue);
									break;
								case "CompareValues":
									String sFirstValue = ExcelFunctions.GetValuesForComparing(wb,sObjectRepositorySheet,sControlName, 5, 6, 7);
									String sSecondValue = ExcelFunctions.GetValuesForComparing(wb,sObjectRepositorySheet,sControlName, 10, 11, 12);
									sExpectedValue = sExpectedValue + " = Value " + sFirstValue + " and Value " + sSecondValue;
									if(sFirstValue == sSecondValue) {
										bCompareValues = true;
									} else {
										bCompareValues = false;
									}
									break;
								case "WaitFor":
									sExpectedValue = sExpectedValue + " = " + sControlValue + " milliseconds";
									break;
								default:
									if(sEncrypt.equals(Constants.CONDITION_YES)) {
										sExpectedValue = sExpectedValue + " = " + sEncryptChr;
									} else {
										sExpectedValue = sExpectedValue + " = " + sControlValue;
									}
									break;
								}
								ReportFunctions.ReportTestCaseDetails(bw, iTestCaseSteps, sTestCaseSteps, sExpectedValue);
								driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
								switch (sKeyword) {
								case "BrowserReLaunch": // re-launching the browser
								case "Application": // Opens Application 
									if(sKeyword.equals("BrowserReLaunch")) {
										driver = WebDriverFunctions.OpenWebDriver(wbgen, sDriversSheet, Constants.GET_THIS_ROW,sRemoteMachine); // Browser driver to be used (IE, Chrome). Default is Firefox
									}
									driver.get(baseURL);
									if (sAuthentication.matches(Constants.CONDITION_YES)) {
										if(sKeyword.equals("BrowserReLaunch")) {
											UtilityFunctions.WindowsAuthenticationlogin(sLocatorType,sIdentification);
										}else{
											UtilityFunctions.WindowsAuthenticationlogin(sAuthenticateUser,sAuthenticatePassword);
										}
									}
									driver.manage().window().maximize();
									ReportFunctions.ReportTestCaseResult(bw,Constants._PASS);
									break;
								case "URLLaunch": // URL launch after logging into the application specific to certain navigations
									driver.get(sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "GetEMailLink": // Gets which link to be searched in the emails
									String sEMailIMAP = ExcelFunctions.FindValueInExcelSheet(wb, sEmailSetUpSheet, 0, Constants._OUTLOOK, 3);
									String sEMailId = ExcelFunctions.FindValueInExcelSheet(wb, sEmailSetUpSheet, 0, Constants._OUTLOOK, 1);
									String sEMailPass = ExcelFunctions.FindValueInExcelSheet(wb, sEmailSetUpSheet, 0, Constants._OUTLOOK, 2);
									String sEmailSubject = ExcelFunctions.FindValueInExcelSheet(wb, sEmailSetUpSheet, 0, sIdentification, 9);
									String sText1ToSearch = ExcelFunctions.FindValueInExcelSheet(wb, sEmailSetUpSheet, 0, sIdentification, 11);
									String sText2ToSearch = ExcelFunctions.FindValueInExcelSheet(wb, sEmailSetUpSheet, 0, sIdentification, 12);
									
									String sGetLink = null;
									if (GetFromEmail.IsNewEmailFound(sEMailIMAP, sEMailId, sEMailPass)) {
										sGetLink = GetFromEmail.GetLinkFromMailBox(sEMailIMAP, sEMailId, sEMailPass, sEmailSubject, sText1ToSearch, sText2ToSearch);
										driver.get(sGetLink);
									} else {
										System.out.println(Constants.NO_EMAILS_FOUND);
									}
									break;
								case "sikuli": // executing sikuli code
							         sikuli.siluliMethod(driver, sLocatorType, sIdentification,sControlValue.trim());
							         ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
							         break;
								case "LaunchURL": // Launching a defined URL
									String sLaunchURL = ExcelFunctions.FindValueInExcelSheet(wb, sEmailSetUpSheet, 0, sIdentification, 6);
									driver.get(sLaunchURL);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "Loop": // Gets which functions needs to be looped 
									break;
								case "UploadFile":
									//open upload window
									LocatorFunctions.ControlToUploadFile(driver, sLocatorType, sIdentification, sControlValue);
									if (UtilityFunctions.IsFileOrDirectoryExist(sControlValue, Constants._FOLDER)) {
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									} else {
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "Input": // Inputting data into text fields 
								case "ClearData": // Clearing data from a field 
								case "FileOpen": // populating data into Input fields like upload files and read only fields 
									LocatorFunctions.ControlToUse(driver, sLocatorType,sIdentification,sControlValue,sKeyword);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "InputRandom": // @Vianayak : Generate Random number through code and Inputting generated Random number in text filed
									LocatorFunctions.ControlforRandom(driver, sLocatorType,sIdentification,sControlValue,sKeyword);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "InputInFrame":
									LocatorFunctions.ControlToSwitchToElementInFrame(driver, sLocatorType, sIdentification, sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "DropDown": // Selecting options from drop down fields
									LocatorFunctions.ControlDropdown(driver, sLocatorType,sIdentification,sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "Click": // clicking the appropriate buttons
									LocatorFunctions.ControlToUse(driver, sLocatorType, sIdentification, true);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "DoubleClick": // double clicking the appropriate buttons
									LocatorFunctions.ControlToUse(driver, sLocatorType, sIdentification, false);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "MultiSelect": // Selecting Multiple options from the list
									String[] aValuesToSplit = UtilityFunctions.SplitValuesIntoArray(sControlValue, sSplitDelimeter);
									for (int pi = 0;pi <= aValuesToSplit.length-1; pi++) {
										String sValue = aValuesToSplit[pi];
										LocatorFunctions.ControlToUseMultiSelect(driver, sLocatorType, sIdentification,sValue);
									}
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "MultiSelectX": // Selecting Multiple options from the list with XPATH only
									String[] xValuesToSplit = UtilityFunctions.SplitValuesIntoArray(sControlValue, sSplitDelimeter);
									for (int pi = 0;pi <= xValuesToSplit.length-1; pi++) {
										String sValue = xValuesToSplit[pi];
										new Select(driver.findElement(By.xpath(sIdentification))).selectByVisibleText(sValue);
									}
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "SequenceClick": // clicking the appropriate option with available text
									sIdentification = sIdentification + sControlValue + sSequenceStr;
									LocatorFunctions.ControlToUseSequence(driver, sLocatorType, sIdentification);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "MoveAndClick": // Click after moving down using down arrow in the list
									LocatorFunctions.ControlToMoveDownAndClick(driver, sLocatorType, sIdentification, sControlValue);									
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "SelectDate": //
									LocatorFunctions.ControlToSelectADate(driver, sLocatorType, sIdentification, sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "KEYTAB": // Key press Tab 
								case "KEYENTER": // Key press Enter
								case "KEYF1": // Key press F1 
								case "KEYF2": // Key press F2 
								case "KEYF3": // Key press F3 
								case "KEYF4": // Key press F4
								case "KEYF5": // Key press F5 
								case "KEYF6": // Key press F6 
								case "KEYF7": // Key press F7 
								case "KEYF8": // Key press F8 
								case "KEYF9": // Key press F9 
								case "KEYF10": // Key press F10 
								case "KEYF11": // Key press F11 
								case "KEYF12": // Key press F12 
									LocatorFunctions.ControlKeyPress(driver, sLocatorType,sIdentification,sControlValue,sKeyword);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "WaitFor": // Wait for seconds
									Thread.sleep(Integer.parseInt(sControlValue));
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "DisplayValue": // Wait for seconds
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "CtrlKeyDown": // Control Key
									Actions actionDN = new Actions(driver);
									actionDN.keyDown(Keys.LEFT_CONTROL);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "CtrlKeyUp": // Control Key
									Actions actionUP = new Actions(driver);
									actionUP.keyDown(Keys.LEFT_CONTROL);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "WaitForElement": // Wait for element
									LocatorFunctions.ControlToWaitForElement(driver, sLocatorType, sIdentification);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "AlertOk": // Clicking OK on alerts if exists
									Thread.sleep(5000);
									UtilityFunctions.checkAlert(driver);
									Thread.sleep(2000);
									break;
								case "AlertCancel": // Clicking CANCEL on alerts if exists
									Thread.sleep(5000);
									UtilityFunctions.checkAlertToCancel(driver);
									Thread.sleep(2000);
									break;
								case "VerifyTitle": // Verifying windows Title
									String sTitle = driver.getTitle().trim();
									//if (driver.getTitle().equals(sExpectedValue.trim()) == false) {
									if (sTitle.equals(sControlValue.trim())) {
										System.out.println(Constants._EXPECTED + sExpectedValue + Constants._FOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									} else {
										String sActVerifyTitle = UtilityFunctions.captureScreen(driver,sScreenShotPath);
										System.out.println(Constants._EXPECTED + sExpectedValue + Constants._NOTFOUND);
										System.out.println(sActVerifyTitle);
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "Verify": // Verifying a particular text value which is present
									if (LocatorFunctions.CheckTextPresent(driver, sLocatorType,sIdentification,sControlValue.trim()) == false) {
										String sActVerify = UtilityFunctions.captureScreen(driver,sScreenShotPath);
										System.out.println(Constants._EXPECTED + sControlValue + Constants._NOTFOUND);
										System.out.println(sActVerify);
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									} else {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._FOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}
									break;
								case "Verifyvalue": // Verifying a value
									driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
									//Thread.sleep(5000);
									if (LocatorFunctions.CheckValuePresent(driver, sLocatorType,sIdentification,sControlValue.trim()) == false)
									{
										System.out.println("Expected Text '" + sControlValue + "' Not Found...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									} else {
										System.out.println("Expected Text '" + sControlValue + "' Found...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
											}
									break;
								case "VerifyFail": // Verifying a particular text value which is not present
									if (LocatorFunctions.CheckTextPresent(driver, sLocatorType,sIdentification,sControlValue.trim()) == false) {
										String sActVerify = UtilityFunctions.captureScreen(driver,sScreenShotPath);
										System.out.println(Constants._EXPECTED + sControlValue + Constants._NOTFOUND);
										System.out.println(sActVerify);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									} else {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._FOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "VerifyPartial": // Verifying a Partial value
									if (LocatorFunctions.CheckPartialTextPresent(driver, sLocatorType,sIdentification,sControlValue.trim()) == false) {
										String sActVerify = UtilityFunctions.captureScreen(driver,sScreenShotPath);
										System.out.println(Constants._EXPECTED + sControlValue + Constants._NOTFOUND);
										System.out.println(sActVerify);
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									} else {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._FOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}
									break;
								case "VerifyAndClick": // Verifying a particular text value which is present
									if (LocatorFunctions.CheckTextPresent(driver, sLocatorType,sIdentification,sControlValue) == true) {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._FOUND);
										LocatorFunctions.ControlToUse(driver, sLocatorType, sIdentification, true);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}
									break;
								case "Assert": // Assert a value, if found stop testing
									if (LocatorFunctions.CheckTextPresent(driver, sLocatorType,sIdentification,sControlValue.trim()) == false) {
										String sActVerify = UtilityFunctions.captureScreen(driver,sScreenShotPath);
										System.out.println(Constants._EXPECTED + sControlValue + Constants._NOTFOUND);
										System.out.println(sActVerify);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									} else {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._FOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
										SmokeTesting.CloseAfterTesting();
									}
									break;
								case "VerifyText": // Verify a text anywhere on a web page
									if (UtilityFunctions.VerifyTextOnPage(driver, sControlValue)) {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._FOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}else {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._NOTFOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									};
									break;
								case "VerifyEnabled": // Verify that an element is enabled or not on a web page
									if (LocatorFunctions.ControlToVerifyIsEnable(driver, sLocatorType,sIdentification) == true) {
										System.out.println("Expected Element '" + sControlName + "' is Enabled...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}else {
										System.out.println("Expected Element '" + sControlName + "' is Disabled...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "VerifyDisabled": // Verify that an element is disabled or not on a web page
									if (!LocatorFunctions.ControlToVerifyIsEnable(driver, sLocatorType,sIdentification) == true) {
										System.out.println("Expected Element '" + sControlName + "' is Disabled...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}else {
										System.out.println("Expected Element '" + sControlName + "' is Enabled...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "AssertText": // Assert a text anywhere on a web page and stop testing if fails
									if (UtilityFunctions.VerifyTextOnPage(driver, sControlValue)) {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._FOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
										SmokeTesting.CloseAfterTesting();
									}else {
										System.out.println(Constants._EXPECTED + sControlValue + Constants._NOTFOUND);
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}
									break;
								case "VerifyCurrentURL": // Verify the contents of current URL
									String sCurrentURL = driver.getCurrentUrl();
									if(sCurrentURL.contains(sControlValue)) {
										System.out.println("Expected URL contains word '" + sControlValue + "'...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									}else {
										System.out.println("Expected URL does not contain word '" + sControlValue + "'...: ");
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "SwitchToWindow": // Switching to a window
									if( bParentWindow == true)  {
								        parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									driver = UtilityFunctions.getHandleToWindow(driver,sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "SwitchToFrame": // Switching to a Frame
									if( bParentWindow == true)  {
								        parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									LocatorFunctions.ControlToSwitchFrame(driver,sLocatorType,sIdentification);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "SwitchToIFrame": // Switching to the first iFrame
									if( bParentWindow == true)  {
								        parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									List<WebElement> iframeElements = driver.findElements(By.tagName("iframe"));
									System.out.println("The total number of iframes are " + iframeElements.size());
									driver.switchTo().frame(0);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "SwitchToIFrameN": // Switching to an iFrame
									if( bParentWindow == true)  {
								        parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									List<WebElement> iframesElements = driver.findElements(By.tagName("iframe"));
									System.out.println("The total number of iframes are " + iframesElements.size());
									int iFrameNos = iframesElements.size();
									if( iFrameNos==1) {
										driver.switchTo().frame(0);
										System.out.println("Switched to Frame 0");										
									} else {
										driver.switchTo().frame(iFrameNos-1);
										System.out.println("Switched to Frame: " + iFrameNos);										
									}
									//LocatorFunctions.ControlToSwitchIFrame(driver,sLocatorType,sIdentification);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "SwitchToTAB": // Switching to a Tab
									if( bParentWindow == true)  {
								        parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
									LocatorFunctions.ControlToSwitchTAB(driver,sLocatorType,sIdentification);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "SwitchBack": // Switching Back to Parent window
									driver.switchTo().defaultContent();
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "CloseWindow": // Closing a window
									if( bParentWindow == true)  {
								        parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
									    bParentWindow = false;
								    }
								    driver.close();
								    driver = UtilityFunctions.getHandleToWindow(driver,sControlValue);
								    ReportFunctions.ReportTestCaseResult(bw, " ");
								    break;
								case "ScrollDown": // Scroll Down the page to access the element
									UtilityFunctions.ExecuteJavaScriptsForPageScroll(driver, Constants._PAGE_DOWN);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "ScrollUp": // Scroll up the page to access the element
									UtilityFunctions.ExecuteJavaScriptsForPageScroll(driver, Constants._PAGE_UP);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "ScrollRight": // Scroll to the right to access the element
									JavascriptExecutor js = (JavascriptExecutor)driver; 
									//WebElement problematicElement= driver.findElement(By.xpath(".//*[@id='DivGrid']/div[2]"));
									//js.executeScript("arguments[0].scrollIntoView()", problematicElement);
									//a[starts-with(@href,'/ExistingFixture/Edit?ID_ExistingFixtureMapping')]
									//WebElement scroll = driver.findElement(By.xpath(".//*[@id='DivGrid']/div[2]"));
									WebElement scroll = driver.findElement(By.xpath("//a[starts-with(@href,'/ExistingFixture/Edit?ID_ExistingFixtureMapping')]"));
									js.executeScript("document.getElementByXpath('/ExistingFixture/Edit?ID_ExistingFixtureMapping').scrollLeft += 500", scroll);
									//.//*[@id='DivGrid']/div[2]
									//UtilityFunctions.ExecuteJavaScriptsForPageScroll(driver, Constants._PAGE_RIGHT);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "ScrollLeft": // Scroll to the left to access the element 
									UtilityFunctions.ExecuteJavaScriptsForPageScroll(driver, Constants._PAGE_LEFT);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "EnableElement": // Enabling an disabled element through JavaSript to fetch value
									WebElement textbox = LocatorFunctions.ControlToEnableByScript(driver,sLocatorType,sIdentification);
									((JavascriptExecutor) driver).executeScript("arguments[0].enabled = true", textbox);
									sControlToEnable = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", textbox);
									ReportFunctions.ReportTestCaseResult(bw, " ");
									break;
								case "GetFromAttribute": // Get the value from attributes
								case "GetTextFrom": // Getting text from a control
								case "GetTextToUpdate": // Getting text from a control to update excel
								//@Vinayak : To get partial text as per position on string and update excel, position is to be on excel sheet Column mapping Sequence column
								case "GetPartialText" : // Get partial text as per position from string and update excel
									if( sGetTextFromControl.isEmpty()) {
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									else {
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
										if (sKeyword.equals("GetTextToUpdate")|sKeyword.equals("GetFromAttribute")|sKeyword.equals("GetPartialText")) {
											String sUpdateSheet = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 5); // get the sheet name to update
											if ( !sUpdateSheet.isEmpty()) {
												ExcelFunctions.UpdateTextIntoExcelFile(wb,sObjectRepositorySheet,sControlName,sGetTextFromControl,sUpdateSheet);
												try {
													wb = ExcelFunctions.OpenExcelWorkBook(Constants.FILE_NAME);
												} catch (BiffException e) {
										      	  	System.out.println(Constants._ERROR_IN_OPENING_WB + Constants.FILE_NAME);
													e.printStackTrace();
												}
											}
										}
									}
									break;
								case "GetFromElementList": // Get the values from a list
									LocatorFunctions.ControlToGetFromList(driver, sLocatorType, sIdentification, sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "InputOnFocus": // Input a text when a control gets focus
									UtilityFunctions.EnterStringOnFocus(sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "EntercurrentDateonfocus": //@vinayak : code for entering current date on focus
									UtilityFunctions.EnterCurrentdateOnFocus();
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "EntercurrentDateplusonfocus": //@vinayak : code for entering current date on focus
									UtilityFunctions.EnterCurrentdateplusOnFocus(sControlValue);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
									//>>Vinayak : Tab on Focus
								case "TabonFocus": //>>Keyword is added for Tab on Focus
									UtilityFunctions.ClickTabonFocus();
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
									//>>Vinayak : Enter on Focus
								case "ClicKEnteronFocus": //>>Keyword is added for clicking Enter on Focus
									UtilityFunctions.ClickEntonFocus();
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "UpdateExcel": // Update excel file
									//EnterStringOnFocus(sControlValue);
									//UpdateExcelFile(Constants.sFileName, sParameterSheet, sGetTextFromControl, 6, 13);
									String sUpdateSheet = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 5); // get the sheet name to update
									//int iNoOfExcelUpdates = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sExcelUpdateSheet, 0, sUpdateSheet, 1)); // get the sheet name to update
									//int iFoundInRow = ExcelFunctions.FindRowNoOfValueInExcelSheet(wb, sExcelUpdateSheet, 0, sUpdateSheet, 1 );
									
									int iUpdateRow = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 6)); // get the Row Number to update
									int iUpdateColumn = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 7)); // get the Column Number to update
									ExcelFunctions.UpdateExcelFile(Constants.FILE_NAME, sUpdateSheet, sGetTextFromControl, iUpdateRow, iUpdateColumn);
									if( !ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 10).isEmpty()) {
										String sDBSheet02 = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 10); // get the second sheet name to update
										int iDBRow02 = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 11)); // get the second Row Number to update
										int iDBColumn02 = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 12)); // get the second Column Number to update
										ExcelFunctions.UpdateExcelFile(Constants.FILE_NAME, sDBSheet02, sGetTextFromControl, iDBRow02, iDBColumn02);
									}
									try {
										wb = ExcelFunctions.OpenExcelWorkBook(Constants.FILE_NAME);
									} catch (BiffException e) {
							      	  	System.out.println(Constants._ERROR_IN_OPENING_WB + Constants.FILE_NAME);
										e.printStackTrace();
									}
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "MVCPOC": // Update excel file
									//DBFunctions.CreateQueryStringAuthors(ConnDB);
									//DBFunctions.CreateQueryStringBooks(ConnDB);
									//DBFunctions.CreateQueryStringOrders(ConnDB);
									//DBFunctions.CreateQueryStringOrderDetails(ConnDB);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "DBUpdateExcel": // Update excel file from Database query
									String sDBUpdateSheet = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 5); // get the sheet name to update
									int iDBUpdateRow = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 6)); // get the Row Number to update
									int iDBUpdateColumn = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 7)); // get the Column Number to update
									int iDBCount = 0;
									boolean bIsPass = false; 
									if(sConnectToDatabase.equals(Constants.CONDITION_YES)) {
										iDBCount = DBFunctions.ExecuteSpecificDatabaseQuery(ConnDB, wb, sDatabaseQueriesSheet,sControlValue,"NO","SELECT" );
										if(ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 1).equals(Constants.CONDITION_YES)) {
											iDBCount = DBFunctions.ExecuteQueryCount(ConnDB,sSQLQueryString,ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 3));
											Integer.toString(iDBCount);
											ExcelFunctions.UpdateExcelFile(Constants.FILE_NAME, sDBUpdateSheet, Integer.toString(iDBCount), iDBUpdateRow, iDBUpdateColumn);
											bIsPass = true;
										} else {
											System.out.println(sControlValue + Constants._QUERY_NOT_CONFIGURED);
										}
									} else {
										System.out.println(Constants.DB_NOT_CONFIFURED_MSG);
									}
									if( bIsPass) {
										try {
											wb = ExcelFunctions.OpenExcelWorkBook(Constants.FILE_NAME);
											ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
										} catch (BiffException e) {
								      	  	System.out.println("Error Opening Workbook : "+ Constants.FILE_NAME);
											e.printStackTrace();
										}
									} else {
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "SelectTableRow": // Select a row from table
									// Pick the locator from ColumnMapping Sheet Column 4 for the control to locate the element of the value to be searched
									String sLocateString = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 4);
									// Pick the locator from ColumnMapping Sheet Column 5 for the control to locate the element of the table row to be selected
									String sLocateToSelect = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 5);
									
									List<WebElement> Table_Rows = LocatorFunctions.ControlToUseTableRowColumn(driver, sLocatorType, sIdentification);
									int iTable_Rows = Table_Rows.size();
									if(iTable_Rows == 0 ) {
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									} else {
										boolean bIsFound = false;
									    for (int i0 = 1; i <= iTable_Rows; i++) 
									    {
									    	// Get the value on each table row on the specified column
									    	String sCellValue = LocatorFunctions.ControlToGetTextFrom(driver,sLocatorType,sLocateString);
									    	// if the column value matches with the searched string
									    	if (sCellValue.equalsIgnoreCase(sControlValue)) 
									    	{
									    		// Select the row and exit the for loop
									    		LocatorFunctions.ControlToUse(driver, sLocatorType, sLocateToSelect, true);
												ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
												bIsFound = true;
									    		break;
									    	}
									    }
									    if( !bIsFound) {
											ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									    }
									}
									//WebElement Wbtable = ControlToUseTable(driver, sIdentificationType, sIdentification);
									
									break;
								case "VerifyTableRows": // Count rows in a table
									List<WebElement> TableRows = LocatorFunctions.ControlToUseTableRowColumn(driver, sLocatorType, sIdentification);
									System.out.println("Table rows :"+ TableRows.size());
									int iValuetoVerify = Integer.parseInt(sControlValue);
									int iTableRows = TableRows.size();
									if(iTableRows == iValuetoVerify ) {
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									} else {
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;
								case "HoverOn":
									LocatorFunctions.ControlHover(driver, sLocatorType,sIdentification);
									ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									break;
								case "ExecuteDBQuery": //ExecuteQueryCount
									if(sConnectToDatabase.equals(Constants.CONDITION_YES)) {
										DBFunctions.ExecuteSpecificDatabaseQuery(ConnDB, wb, sDatabaseQueriesSheet,sControlValue,"YES","SELECT" );
									} else {
										System.out.println(Constants.DB_NOT_CONFIFURED_MSG);
									}
									break;
								case "ExecuteDBUpdateQuery": //Execute Update Query
									if(sConnectToDatabase.equals(Constants.CONDITION_YES)) {
										DBFunctions.ExecuteSpecificDatabaseQuery(ConnDB, wb, sDatabaseQueriesSheet,sControlValue,"YES","UPDATE" );
									} else {
										System.out.println(Constants.DB_NOT_CONFIFURED_MSG);
									}
									break;
								case "CompareValues": // Compare 2 Values
									if(bCompareValues) {
										ReportFunctions.ReportTestCaseResult(bw, Constants._PASS);
									} else {
										ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
									}
									break;									
								case "CompareAppDB": //ExecuteQueryCount
									if(sConnectToDatabase.equals(Constants.CONDITION_YES)) {
										DBFunctions.ExecuteSpecificDatabaseQuery(ConnDB, wb, sDatabaseQueriesSheet,sControlValue,"YES","SELECT" );
									} else {
										System.out.println(Constants.DB_NOT_CONFIFURED_MSG);
									}
									break;
								case "DXLPOC": // Update excel file
									driver.findElement(By.xpath("//button[contains(text(),'Continue Shopping')]")).click();
									Thread.sleep(3000);
									driver.findElement(By.xpath(".//*[@id='fp-root']/div[1]/div/h1")).click();
									Thread.sleep(3000);
									driver.findElement(By.xpath("//li[@class='fp-tr-ca']/a")).click();
									break;
								} // Case
								iTestCaseSteps ++;
							} // if
							//*********************************
						} // Second Loop
						driver.manage().timeouts().implicitlyWait(iTimeoutValue, TimeUnit.SECONDS);
						Thread.sleep(3000);
					} // Second If
				}	// First If
			} // First Loop
		} catch (FileNotFoundException e) {
			ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
			bExceptionOccurred = true;
			e.printStackTrace();
		} catch (MessagingException e) {
			ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
			bExceptionOccurred = true;
			e.printStackTrace();
		} catch (InterruptedException e) {
			ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
			bExceptionOccurred = true;
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
			e.printStackTrace();
		} catch (ElementNotVisibleException e) {
			ReportFunctions.ReportTestCaseResult(bw, Constants._FAIL);
			bExceptionOccurred = true;
			e.printStackTrace();
		}
		if(bExceptionOccurred) {
			driver.close();
		}
	}
	
	@AfterTest
	public static void CloseAfterTesting() throws Exception {
		try {
			ReportFunctions.ReportFooter(bw);
			driver.quit();
			try {
				if(sIsEMailOn.equals(Constants.CONDITION_YES)) {
					SendMail.execute(sEmailSetUpSheet, wb, sHTMLReportsPath,sHTMLReportsPrefix + currentDate + ".html",sSplitDelimeter);
				}
			} catch (Exception e) {
	      	  	System.out.println(Constants._ERROR_SENDING_EMAIL);
				e.printStackTrace();
			}
			if( bVideoStarted) {
				//GenericCode.StopVideoRecorder();
				bVideoStarted = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}