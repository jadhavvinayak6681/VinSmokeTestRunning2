package test1;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.AfterClass;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ezjems.SmokeTestWinium;
public class EzjemsTest {
	
	WiniumDriver driver = null;
	@BeforeClass
	public void setupEnvironmnet() throws IOException 
	{
		DesktopOptions options = new DesktopOptions();
		options.setApplicationPath("D:\\Vinayak_Data\\Shiva\\Selenium\\EZJems-SGN\\EZJems.exe");
		try
		{
			driver=new WiniumDriver(new URL("http://localhost:9999"),options);
			System.out.println("Hello");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void EzjemsTestapp() throws InterruptedException
	{
		Thread.sleep(3000);
		/*String s = driver.findElementById("txtPrice").getText();
		System.out.println("S = "+s);*/
		//driver.findElementById("cmbSource").sendKeys("SO");
		Thread.sleep(2000);
		driver.findElementById("btnModify").click();
		//driver.switchTo().window(null);
		Thread.sleep(2000);
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
			    //SmokeTestWinium.ReportTestCaseResult("Pass");
			 } catch (AWTException e) {
			    //Teleport penguins  
			 }
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
		/*//System.out.println("Hello");
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);
		//driver.findElementByClassName("WindowsForms10.EDIT.app.0.62e449_r13_ad1").sendKeys("sysadmin");
		driver.findElementById("txtuserName").click();
		Thread.sleep(2000);
		driver.findElementById("txtuserName").sendKeys("sysadmin");
		//System.out.println("Hello");
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(2000);
		driver.findElementById("txtPassword").sendKeys("12345");
		//System.out.println("Hello");
		driver.findElementById("btnLogin").click();
		Thread.sleep(8000);
		driver.findElementByName("Sales").click();
		Thread.sleep(8000);
		driver.findElementByName("257").click();
		Thread.sleep(8000);
		driver.findElementByName("Main");
		Thread.sleep(4000);
		driver.findElementById("cmbCustomer").sendKeys("1-800 LOOSE DIAMONDS");
		Thread.sleep(2000);
		driver.switchTo().frame("lcMain");
		System.out.println("Hello");
		driver.findElementByName("Row 1").click();
		Thread.sleep(2000);*/
		
		//System.out.println("Hello");
/*		Thread.sleep(10000);
		driver.findElementById("cmbCustomer").click();
		Thread.sleep(10000);
		driver.findElementById("cmbCustomer").click();
		//Thread.sleep(2000);driver.findElementByClassName("WindowsForms10.Window.b.app.0.62e449_r13_ad1").click();
		try {
		Robot r = new Robot();
	    //there are other methods such as positioning mouse and mouseclicks etc.
	    Thread.sleep(3000);
	    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
	    Thread.sleep(3000);
	    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
	    Thread.sleep(3000);
	    r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
	    System.out.println("test 3 down key press");
	    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
	    Thread.sleep(3000);
	    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
	    //Thread.sleep(3000);
	    //r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
	    Thread.sleep(3000);
	    System.out.println("test 3 down key release");
	    Thread.sleep(3000);
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
	    Thread.sleep(3000);
	    r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
	    System.out.println("test enter key release");
		System.out.println("Hello");
		 } catch (AWTException e) {
			    //Teleport penguins  
			 }*/
		//driver.findElementByXPath("//*[@class_name='WindowsForms10.EDIT.app.0.62e449_r13_ad1' and @Name='PasswordTextEdit'] ").click();
		//System.out.println("Hello");
		//parent = driver.findElementByClassName("WindowsForms10.EDIT.app.0.62e449_r13_ad1")[5];
		/*String s = driver.findElementById("PrintOption").getAttribute("Name");
		System.out.println(s);
		Thread.sleep(1000);
		driver.findElementById("btnOK").click();*/
		//Thread.sleep(3000);
		//driver.findElementByXPath("//pane[@id='nbcHeader']//button[Name='Additional Info']").click();
		/*[contains(@ControlType,'ControlType.Window')
		 */
		//driver.findElementByXPath("/*[contains(@ControlType,'ControlType.Button')and contains(@Name,'Additional Info')]").click();
		/*driver.findElementByXPath("/*[contains(@Name,'Additional Info')]").click();
		//driver.findElementByName("Additional Info").click();
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
		   //SmokeTestWinium.ReportTestCaseResult("Pass");
		 } catch (AWTException e) {
		    //Teleport penguins   
		 }
		*/
		/*Actions builder = new Actions(driver);   
		builder.moveToElement(null,666,460).click().build().perform();
		System.out.println("Hello click");
		Thread.sleep(2000);*/
		//driver.findElementById("btnAddSourceItem").click();
/*		try{
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
	    Thread.sleep(1000);
	   // driver.findElementById("pceSourceItem").setText
	    r.keyPress(java.awt.event.KeyEvent.VK_P);
	    r.keyPress(java.awt.event.KeyEvent.VK_O);
	    r.keyPress(java.awt.event.KeyEvent.VK_0);
	    r.keyPress(java.awt.event.KeyEvent.VK_0);
	    r.keyPress(java.awt.event.KeyEvent.VK_0);
	    r.keyPress(java.awt.event.KeyEvent.VK_3);
	    r.keyPress(java.awt.event.KeyEvent.VK_1);
	    r.keyPress(java.awt.event.KeyEvent.VK_3);
	    Thread.sleep(1000);
	    r.keyRelease(java.awt.event.KeyEvent.VK_P);
	    r.keyRelease(java.awt.event.KeyEvent.VK_O);
	    r.keyRelease(java.awt.event.KeyEvent.VK_0);
	    r.keyRelease(java.awt.event.KeyEvent.VK_0);
	    r.keyRelease(java.awt.event.KeyEvent.VK_0);
	    r.keyRelease(java.awt.event.KeyEvent.VK_3);
	    r.keyRelease(java.awt.event.KeyEvent.VK_1);
	    r.keyRelease(java.awt.event.KeyEvent.VK_3);
	    TextArea txa  = new TextArea(665,252);
	    Thread.sleep(2000);
        //txa.append("PO000313");
        txa.setText("PO000313");
        //Mouse event to pass string
	    MouseEvent e = null ;
		@SuppressWarnings("null")
		Point p = e.getPoint();
        System.out.printf("p = [%d, %d]%n", p.x, p.y);
        JTextField tf = (JTextField)e.getComponent();
        int pos = tf.viewToModel(p);
        tf.setCaretPosition(pos);
        try {
            String text = "PO000313";
            tf.getDocument().insertString(pos, text, null);
        } catch(BadLocationException ble) {
            System.out.println("insert error: " + ble.getMessage());
        }
		System.out.println("Hello");
		//WebElement.sendKeys(Keys.values();("Hello"));
	 } catch (AWTException e) {
		    //Teleport penguins  O000313
		 }*/
		/*Thread.sleep(2000);
		driver.findElementById("√ row 0").click();
		Thread.sleep(2000);
		driver.findElementByName("PO000313").click();
		Thread.sleep(2000);
		((WebElement) driver).sendKeys("PO000313");
		Thread.sleep(2000);*/
		
	}
	/*public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        System.out.printf("p = [%d, %d]%n", p.x, p.y);
        JTextField tf = (JTextField)e.getComponent();
        int pos = tf.viewToModel(p);
        tf.setCaretPosition(pos);
        try {
            String text = "PO000313";
            tf.getDocument().insertString(pos, text, null);
        } catch(BadLocationException ble) {
            System.out.println("insert error: " + ble.getMessage());
        }
    }*/
	//pane id = lcItemPanel , pane id = pnlItemPanel1, edit id = pceSourceItem;
	@AfterClass
	public void tearDown() throws IOException
	{
		driver.close();
	}
}