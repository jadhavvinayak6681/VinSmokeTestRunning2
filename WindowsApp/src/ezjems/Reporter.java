package ezjems;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
//import java.nio.file.attribute.
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.thoughtworks.selenium.Selenium;

import javax.*;


public class Reporter {
	private static List<Result> details;
	private static final String resultPlaceholder = "<!-- INSERT_RESULTS -->";
	private static final String templatePath = "D:\\Selenium\\Data\\Robust\\CEC\\Reports\\report_template.html";
	private FileInputStream file = null;

	
	public static void initialize() {
		details = new ArrayList<Result>();
	}
	
	public static void startReporting() throws IOException {
		WebDriver driver = new HtmlUnitDriver();
		Reporter.initialize();
		driver.get("http://www.ontestautomation.com/files/report_test.html");
		
		for (int i = 1; i <=5; i++) {
			WebElement el = driver.findElement(By.id("textfield" + Integer.toString(i)));
			//Reporter.report(el.getAttribute("value"), "Text field " + Integer.toString(i));
		}
		Reporter.writeResults(templatePath,"Report_");
		driver.close();	
	}

	public static void report(String testCaseSteps, String expectedValue, String actualValue) {
		if(actualValue.equals(expectedValue)) {
			//Result r = new Result();
			Result r = new Result(testCaseSteps,"Actual value '" + actualValue + "' matched expected value '" + expectedValue + "'","Pass");
			details.add(r);
		} else {
			Result r = new Result(testCaseSteps,"Actual value '" + actualValue + "' does not match expected value '" + expectedValue + "'","Fail");
			details.add(r);
		}
	}
	
	public static void showResults() {
		FileOutputStream fop = null;
		File file;
		
		try {

			file = new File("D:/Selenium/Data/Robust/CEC/Reports/test.html");
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			for (int i = 1;i < details.size()-1;i++) {
				
				String content = "Result " + i + " | " + details.get(i-1).getTestSteps() + " | " + details.get(i-1).getResultText() + " | " + details.get(i-1).getResult();
				
				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				System.out.println("Result " + i + " | " + details.get(i-1).getTestSteps() + " | " + details.get(i-1).getResultText() + " | " + details.get(i-1).getResult());
			}

			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeResults(String sTemplatePath, String sReportPrefix) throws IOException {
			//String reportIn = new String(Files.readAllBytes(Paths.get(sTemplatePath)));
			String reportIn = null;
			for (int i = 0; i < details.size();i++) {
				reportIn = reportIn.replaceFirst(resultPlaceholder,"<tr><td>" + Integer.toString(i+1) + "</td><td>" + details.get(i).getTestSteps() + "</td><td>" + details.get(i).getResultText() + "</td><td>" + details.get(i).getResult() + "</td></tr>" + resultPlaceholder);
				
			}
			
			String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			
			String reportPath = sTemplatePath + sReportPrefix + currentDate + ".html";
			//------------
	        // The name of the file to open.
	        //String fileName = "temp.txt";

	        try {
	            // Assume default encoding.
	            FileWriter fileWriter =
	                new FileWriter(reportPath);

	            // Always wrap FileWriter in BufferedWriter.
	            BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);

	            // Note that write() does not automatically
	            // append a newline character.
	            bufferedWriter.write(reportIn);
	            //bufferedWriter.write(" here is some text.");
	            //bufferedWriter.newLine();
	            //bufferedWriter.write("We are writing");
	            //bufferedWriter.write(" the text to the file.");

	            // Always close files.
	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error writing to file '"
	                + reportPath + "'");
	            // Or we could just do this:
	            // ex.printStackTrace();
	        }
			//------------
			
	        //BufferedWriter output = null;
	        //try {
	        //    File file = new File(reportPath);
	        //    output = new BufferedWriter(new FileWriter(file));
	        //    output.write(reportIn);
	        //} catch ( IOException e ) {
			//	System.out.println("One: Error when writing report file:\n" + e.toString());
	            //e.printStackTrace();
	        //} finally {
	        //    if ( output != null ) output.close();
	       // }
			
			
			//Paths.get(sTemplatePath + "\\" + sReportPrefix + currentDate + ".html");
			//BufferedWriter out  = Files.newBufferedWriter(Paths.get(reportPath), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			//FileInputStream file = new FileInputStream(reportPath);
			//out = (BufferedWriter) Files.write(Paths.get(reportPath),reportIn.getBytes(),StandardOpenOption.CREATE);
			
	}

	public static void WriteToFileExample(String sTemplatePath, String sReportPrefix) {
		try {

			String content = "This is the content to write into file";

			String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String reportPath = sTemplatePath + sReportPrefix + currentDate + ".html";
			
			File file = new File(reportPath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	  public static String toHtml( String string )
	  {
	    if( StringUtils.isEmpty( string ) )
	      return "<html><body></body></html>";

	    BufferedReader st = new BufferedReader( new StringReader( string ) );
	    StringBuffer buf = new StringBuffer( "<html><body>" );

	    try
	    {
	      String str = st.readLine();

	      while( str != null )
	      {
	        if( str.equalsIgnoreCase( "<br/>" ) )
	        {
	          str = "<br>";
	        }

	        buf.append( str );

	        if( !str.equalsIgnoreCase( "<br>" ) )
	        {
	          buf.append( "<br>" );
	        }

	        str = st.readLine();
	      }
	    }
	    catch( IOException e )
	    {
	      e.printStackTrace();
	    }

	    buf.append( "</body></html>" );
	    string = buf.toString();
	    return string;
	  }	
	
	public String getSystemId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSystemId(String systemId) {
		// TODO Auto-generated method stub
		
	}
	

}

