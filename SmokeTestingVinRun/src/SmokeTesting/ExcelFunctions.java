package SmokeTesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jxl.read.biff.BiffException;

public class ExcelFunctions {

	public static HSSFWorkbook OpenExcelWorkBook(String sFile) throws FileNotFoundException, BiffException {
		HSSFWorkbook Wb = null;
		try {
			FileInputStream file = new FileInputStream(sFile);
			Wb = new HSSFWorkbook(file);
		} catch (IOException e) {
      	  	System.out.println("Error Opening Workbook : "+ sFile);
			e.printStackTrace();
		}
		return Wb;
	}

	@SuppressWarnings("static-access")
	public static String FindValueInExcelSheet(HSSFWorkbook DataBook, String SheetName, int iCols, String sToSearch, int iColsToReturn ) {

		HSSFSheet DataSheet = null;
		int FoundinRow = 0;		
		DataSheet = DataBook.getSheet(SheetName);
		HSSFRow row2 = DataSheet.getRow(0);

		for(int row=0; row <=DataSheet.getLastRowNum();row++)
		{
			// Store the value of Column iCols for comparing
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
		return sReturnValue;
	}

	
	@SuppressWarnings("static-access")
	public static int FindRowNoOfValueInExcelSheet(HSSFWorkbook DataBook, String SheetName, int iCols, String sToSearch, int iColsToReturn ) {

		HSSFSheet DataSheet = null;
		int FoundinRow = 0;		
		DataSheet = DataBook.getSheet(SheetName);
		HSSFRow row2 = DataSheet.getRow(0);

		for(int row=0; row <=DataSheet.getLastRowNum();row++)
		{
			// Store the value of Column iCols for comparing
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
		int iReturnValue = 0;
		HSSFCell cell = null;
		cell = DataSheet.getRow(FoundinRow).getCell(iColsToReturn) ;
		if (cell == null) {
			cell =  row2.getCell(iColsToReturn,row2.CREATE_NULL_AS_BLANK);
		}
		iReturnValue = cell.getRowIndex();
		return iReturnValue;
	}
	
	
    public static void UpdateExcelFile(String sInputOutputFile, String sSheetName, String sTargetValue, int iRowNum, int iCellNum) throws IOException {
    	  
          FileInputStream fsIP= new FileInputStream(new File(sInputOutputFile)); //Read the spreadsheet that needs to be updated
          HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook

          for (int i = 0; i < wb.getNumberOfSheets(); i++) // Iterate to go to the sheet needs to be updated
          {
        	  HSSFSheet worksheet = wb.getSheetAt(i);
        	  if( worksheet.getSheetName().equals(sSheetName)) {  // If the current sheet matches with sSheetName
            	  System.out.println("Updating Sheet Name : "+ worksheet.getSheetName());
                  // Process your sheet here.
                  HSSFCell cell = null; // declare a Cell object
                  cell = worksheet.getRow(iRowNum).getCell(iCellNum); // Access the cell in row to update the value
                  cell.setCellValue(sTargetValue);  // Get current cell value value and overwrite the value
        	  }
          }          
            
          fsIP.close(); //Close the InputStream
           
          FileOutputStream output_file =new FileOutputStream(new File(sInputOutputFile));  //Open FileOutputStream to write updates
          wb.write(output_file); //write changes
          
          output_file.close();  //close the stream
    	  System.out.println("Done with Updating...");
    }
    
    public static void UpdateTextIntoExcelFile(HSSFWorkbook wb, String sObjectRepositorySheet, String sControlName, String sGetTextFromControl, String sUpdateSheet) {
		if ( !sUpdateSheet.isEmpty()) {
			String sRemoveChar = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 13);
			int iUpdateRow = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 6)); // get the Row Number to update
			int iUpdateColumn = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 7)); // get the Column Number to update
			if(sRemoveChar.equals(Constants.CONDITION_YES)) {
				sGetTextFromControl = UtilityFunctions.deleteAllNonDigit(sGetTextFromControl);
			}
			try {
				UpdateExcelFile(Constants.FILE_NAME, sUpdateSheet, sGetTextFromControl, iUpdateRow, iUpdateColumn);
			} catch (IOException e1) {
          	  	System.out.println("Error while updating Excel Sheet : "+ sUpdateSheet);
				e1.printStackTrace();
			}
			String sDBSheet02 = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 10); // get the second sheet name to update
			if( !sDBSheet02.isEmpty()) {
				int iDBRow02 = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 11)); // get the second Row Number to update
				int iDBColumn02 = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, 12)); // get the second Column Number to update
				if(sRemoveChar.equals(Constants.CONDITION_YES)) {
					sGetTextFromControl = UtilityFunctions.deleteAllNonDigit(sGetTextFromControl);
				}
				try {
					UpdateExcelFile(Constants.FILE_NAME, sDBSheet02, sGetTextFromControl, iDBRow02, iDBColumn02);
				} catch (IOException e) {
	          	  	System.out.println("Error while updating Excel Sheet : "+ sDBSheet02);
					e.printStackTrace();
				}
			}
		}
    }
    
    public static String GetValuesForComparing(HSSFWorkbook wb, String sObjectRepositorySheet, String sControlName, int iDBSheet, int iDBRow, int iDBColumn) {
		String sDBSheet = ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, iDBSheet); // get the sheet name to compare
		int iRow = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, iDBRow)); // get the Row Number to compare
		int iColumn = Integer.parseInt(ExcelFunctions.FindValueInExcelSheet(wb, sObjectRepositorySheet, 0, sControlName, iDBColumn)); // get the Column Number to compare
		HSSFSheet sSheet = null;
		sSheet = wb.getSheet(sDBSheet);
		HSSFRow sRow = sSheet.getRow(iRow);
		String sValue = sRow.getCell(iColumn).getStringCellValue();
    	return sValue;
    }
}
