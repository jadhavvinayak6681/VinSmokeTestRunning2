package SmokeTesting;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class WebDriverFunctions {
	@SuppressWarnings("unused")
	public static WebDriver OpenWebDriver(HSSFWorkbook DataBook, String ShDriver, String sStringToSearch, String sRemoteIP) {
		WebDriver driver = null; int DrvRow = 0 ; File fileIE = null; 
		//ProfilesIni profile = new ProfilesIni();
		HSSFSheet DataSheet = null;
		DesiredCapabilities capabilities = null;
		URL url = null;
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
/*		try {
			url = new URL( "http", sRemoteIP, 4444, "/wd/hub" );
		} catch (MalformedURLException e) {
			System.out.println("Error in Remoting...");		
			e.printStackTrace();
		}		
*/		switch (DrvRow) {
		case 2:
			sDriverName = "FireFox";
			//FirefoxProfile myprofile = profile.getProfile("AutomationTester");
			capabilities = DesiredCapabilities.firefox();

			driver = new FirefoxDriver(capabilities);
			//driver = new RemoteWebDriver(url,capabilities);
			driver = new RemoteWebDriver(url,capabilities);
			break;
		case 3:
			sDriverName = "InternetExplorer";
			capabilities = DesiredCapabilities.internetExplorer();
			driver = new InternetExplorerDriver(capabilities);
			//driver = new RemoteWebDriver(url,capabilities);
			break;
		case 4:
			sDriverName = "GoogleChrome";
			capabilities = DesiredCapabilities.chrome();
			driver = new ChromeDriver(capabilities);
			//driver = new RemoteWebDriver(url,capabilities);
			break;
		case 5:
			sDriverName = "IEEdge";
			capabilities = DesiredCapabilities.edge();
			driver = new EdgeDriver(capabilities);
			//driver = new RemoteWebDriver(url,capabilities);
			break;
		}
		//capabilities.setJavascriptEnabled(true);			
		//capabilities.setCapability("marionette", true);
		
		//driver = new RemoteWebDriver(url,capabilities);
		
		return driver;
	}
	
}
