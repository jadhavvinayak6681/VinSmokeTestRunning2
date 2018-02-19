package SmokeTesting;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class UtilityFunctions {

	static ATUTestRecorder recorder;
	
    public static String captureScreen(WebDriver Driver, String sFilepath) {
        try {
            WebDriver augmentedDriver = new Augmenter().augment(Driver);
            File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
            sFilepath = sFilepath + source.getName();
            FileUtils.copyFile(source, new File(sFilepath)); 
        }
        catch(IOException e) {
            sFilepath = "Failed to capture screenshot: " + e.getMessage();
        }
        return sFilepath;
    }    
    
	public static void checkAlert(WebDriver driver) {
	    try {
	        //WebDriverWait wait = new WebDriverWait(driver, 2);
	       // wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        alert.accept();
	        
	        String parentWindow = driver.getWindowHandle();
	        Set<String> windowHandles = driver.getWindowHandles();
	        Iterator<String> iterator = windowHandles.iterator();
	        while (iterator.hasNext()) {
	           String handle = iterator.next();
	           if (!handle.contains(parentWindow)) {
	                // Switch to popup and close it
	                driver.switchTo().window(handle);
	                // Perform required action in popup
	           }
	        }
	        // Switching back to parent window
	        driver.switchTo().window(parentWindow);
	        
	    } catch (Exception e) {
	        //exception handling
	    	System.out.println("No Alerts were found.");
	    }
	}	

	public static void checkAlertToCancel(WebDriver driver) {
	    try {
	        //WebDriverWait wait = new WebDriverWait(driver, 2);
	       // wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        alert.dismiss();
	    } catch (Exception e) {
	        //exception handling
	    	System.out.println("No Alerts were found.");
	    }
	}

	public static WebDriver getHandleToWindow(WebDriver driver, String title)
	{
		String parent=driver.getWindowHandle();
		Set<String>s1=driver.getWindowHandles();
		Iterator<String> I1= s1.iterator();
		while(I1.hasNext())
		{
		   String child_window=I1.next();
		   if(!parent.equals(child_window))
		   {
			   	driver.switchTo().window("http://172.31.0.33/ - CDA - Internet Explorer");
			   	System.out.println(driver.switchTo().window(child_window).getTitle());
			   	driver.close();
		   }
		}

        WebDriver popup = null;
        //Set<String> windowIterator = WebDriverInitialize.getDriver().getWindowHandles();
        String windowIterator = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        System.err.println("No of windows :  " + handles.size());
        
        for(String s : driver.getWindowHandles()) 
        {
        	if(!s.equals(windowIterator))
        	{
        	String windowHandle = s; 
        	//popup = WebDriverInitialize.getDriver().switchTo().window(windowHandle);
        	driver.switchTo().window(windowHandle);
        	System.out.println("Window Title : " + driver.getTitle());
        	System.out.println("Window Url : " + driver.getCurrentUrl());
        	if (driver.getTitle().equals(title) ){
        		System.out.println("Selected Window Title : " + driver.getTitle());
        		return popup;
        	}
        	}
        }
        System.out.println("Window Title :" + driver.getTitle());
        System.out.println();
        return popup;
    }	
	
    public static void WindowsAuthenticationlogin(String sUserName,String sPassword) throws Exception {

        try {
            //wait - increase this wait period if required
            Thread.sleep(5000);
           
            //create robot for keyboard operations
            Robot rb = new Robot();

            //Enter user name by CTRL-V
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

            //Enter password by CTRL-V
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

    public static void EnterStringOnFocus(String sText) {
        try {
            //wait - increase this wait period if required
            Thread.sleep(1000);
           
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
            Thread.sleep(1000);
        } catch (Exception ex) {
            System.out.println("Error in Focus Thread: " + ex.getMessage());
        }
    }
  //>>Vinayak : Tab on Focus : Keyword is added for entering current date on Focus
    public static void EnterCurrentdateOnFocus() {
        try {
            //wait - increase this wait period if required
            Thread.sleep(1000);
            
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // Now use today date.
			c.add(Calendar.DATE, 0); // Adding 0 days
			String output = sdf.format(c.getTime());
			System.out.println(output);
			Robot rb = new Robot();
            StringSelection currentdate = new StringSelection(output);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(currentdate, null);            
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            
        } catch (Exception ex) {
            System.out.println("Error in getting current date: " + ex.getMessage());
        }
    }
    //>>Vinayak : Tab on Focus : Keyword is added for entering current date on Focus
    public static void EnterCurrentdateplusOnFocus(String sText) {
        try {
            //wait - increase this wait period if required
            Thread.sleep(1000);
            int d = Integer.parseInt(sText);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // Now use today date.
			c.add(Calendar.DATE, d); // Adding days from excel
			String output = sdf.format(c.getTime());
			System.out.println(output);
			Robot rb = new Robot();
            StringSelection currentdate = new StringSelection(output);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(currentdate, null);            
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            
        } catch (Exception ex) {
            System.out.println("Error in getting current date: " + ex.getMessage());
        }
    }
    
  //>>Vinayak : Tab on Focus : Keyword is added for Tab on Focus
    public static void ClickTabonFocus() {
        try {
            //wait - increase this wait period if required
            Thread.sleep(1000);
           
            //create robot for keyboard operations
            Robot rb = new Robot();
  
            rb.keyPress(KeyEvent.VK_TAB);
            rb.keyRelease(KeyEvent.VK_TAB);
               
            //wait
            Thread.sleep(1000);
        } catch (Exception ex) {
            System.out.println("Error in Focus Thread: " + ex.getMessage());
        }
    }
    //>>Vinayak : Enter on Focus : Keyword is added for click Enter on Focus
    public static void ClickEntonFocus() {
        try {
            //wait - increase this wait period if required
            Thread.sleep(5000);
           
            //create robot for keyboard operations
            Robot rb = new Robot();
  
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_TAB);
           
            Thread.sleep(5000);
        } catch (Exception ex) {
            System.out.println("Error in Focus Thread: " + ex.getMessage());
        }
    }
    
    public static String readField(WebElement dropdown, String sSearchText) {
    	List<WebElement> options = dropdown.findElements(By.tagName("option"));
    	for (WebElement option : options) {
    	   if (option.getText().equals(sSearchText)) {
    		   option.click();
    	       return option.getText();
    	   }
    	}
    	return null;  
    }

    public static String[] SplitValuesIntoArray(String sStringToSplit, String sStringDelimeter) {
		String sTheString = sStringToSplit ;
		//String aTheArray[] = new String[sTheString.length()] ;
		int iNoOfDollars = 0;
		// Finding the number of delimited String available in String  
		for (int i=0;i <= sTheString.length()-1; i++) {
			String ss = sTheString.substring(i, i+1);
			if (ss.contains(sStringDelimeter)) {
				iNoOfDollars++;
			}
		}
		iNoOfDollars++; // increasing the $ count by 1
		String aTheArray[] = new String[iNoOfDollars] ;
		// Start Splitting the values
		for (int i = 0;i <= iNoOfDollars; i++) {
			int iFirstSplit = sTheString.indexOf(sStringDelimeter);
			if (iFirstSplit == -1) {
				aTheArray[i] = sTheString;
				break;
			} else {
				aTheArray[i] = sTheString.substring(0, iFirstSplit);
		        sTheString = sTheString.substring(iFirstSplit+1);
			}
		}
		return aTheArray;  
	}
    
    public static boolean VerifyTextOnPage(WebDriver driver,String sValueToSearch) {
    	if(driver.getPageSource().contains(sValueToSearch)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public static String deleteAllNonDigit(String s) {
        String temp = s.replaceAll("\\D", "");
        return temp;
    }

	public static void SetupRecorderFilePath(String sFilePathForVideos, String sFilePrefix) throws ATUTestRecorderException {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		  Date date = new Date();
		  //Created object of ATUTestRecorder
		  //Provide path to store videos and file name format.
		  recorder = new ATUTestRecorder(sFilePathForVideos,sFilePrefix+dateFormat.format(date),false);
	}
	
	public static void StartVideoRecorder() throws ATUTestRecorderException {
		// Start Video Recording
		recorder.start();
	}
	
	public static void StopVideoRecorder() throws ATUTestRecorderException {
		// Stop Video Recording
		recorder.stop();
	}
    
	public static String AddParametersToTheURL(HSSFWorkbook wb, String baseURL, String sEnvironmentSetUpSheet) {
		String sIsLinkHasParameters = "No";
		String LinkParametersCount = null;
		String sLinkParameter01, sLinkParameter02, sLinkParameter03, sLinkParameter04, sLinkParameter05 = null;
		sIsLinkHasParameters = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 6 );
		if (sIsLinkHasParameters.matches(Constants.CONDITION_YES)) {
			LinkParametersCount = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 7 );
			sLinkParameter01 = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 8 );
			sLinkParameter02 = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 9 );
			sLinkParameter03 = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 10 );
			sLinkParameter04 = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 11);
			sLinkParameter05 = ExcelFunctions.FindValueInExcelSheet(wb, sEnvironmentSetUpSheet, 0, Constants.GET_THIS_ROW, 12 );
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

		return baseURL;
	}
	
	public static String ToShowEncryptValueOrNot(String sEncrypt, String sExpectedValue, String sEncryptChr, String sGetTextFromControl, String sControlValue) {
		if(sEncrypt.equals(Constants.CONDITION_YES)) {
			sExpectedValue = sExpectedValue + " = " + sEncryptChr + sGetTextFromControl;
		} else {
			sExpectedValue = sExpectedValue + " = " + sControlValue + sGetTextFromControl;
		}
		
		return sExpectedValue;
	}
	
	public static void ExecuteJavaScriptsForPageScroll(WebDriver driver, String sDirection) {
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		switch (sDirection) {
		case "DOWN": // Scroll Down the page
			((JavascriptExecutor)driver).executeScript("javascript:window.scrollBy(0,450)");
			break;
		case "UP": // Scroll Up the page
			((JavascriptExecutor)driver).executeScript("javascript:window.scrollBy(0,-450)");
			break;
		case "RIGHT": // Scroll Right the page
			((JavascriptExecutor)driver).executeScript("javascript:window.scrollBy(2000,0)");
			break;
		case "LEFT": // Scroll Left the page
			((JavascriptExecutor)driver).executeScript("javascript:window.scrollBy(-2000,0)");
			break;
		}

	}
	
	public static void VerifyExistenceOfFileOrDirectory(String sPath,String sDirectoryOrFile) {
		File file = new File(sPath);
		if (!file.exists()) {
			switch (sDirectoryOrFile) {
			case "D":
	            file.mkdir();
	            System.out.print("Folder created");
				break;
			case "F":
				try {
					file.createNewFile();
		            System.out.print("File created");
				} catch (IOException e) {
		            System.out.print("Error while creating file");
					e.printStackTrace();
				}
				break;
			}
		}		
	}

	public static boolean IsFileOrDirectoryExist(String sPath,String sDirectoryOrFile) {
		boolean bFound = true;
		File file = new File(sPath);
		if (!file.exists()) {
			switch (sDirectoryOrFile) {
			case "D":
	            System.out.print("Folder not found...");
	            bFound = false;
				break;
			case "F":
	            System.out.print("File not found...");
	            bFound = false;
				break;
			}
		}
		return bFound;
	}
	
}
