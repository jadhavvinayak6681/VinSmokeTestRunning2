package SmokeTesting;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DBFunctions {
	@SuppressWarnings("unused")
	public static Connection OpenMSSQLConnection(String sDBConnectionString, String sSQLUser, String sSQLPassword) throws ClassNotFoundException, AWTException, InterruptedException {
		Connection Conn = null;
        try {
        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        	 
            Conn = DriverManager.getConnection(sDBConnectionString, sSQLUser, sSQLPassword);
            if (Conn != null) {
            	System.out.println("Connected to the database...");
            	
                DatabaseMetaData dm = (DatabaseMetaData) Conn.getMetaData();
            }
 
        } catch (SQLException ex) {
        	System.out.println("Failed to connect to database");
            ex.printStackTrace();
        }
		return Conn;
	}

	public static void ExecuteQuery(Connection Conn,String sSQLQueryString,String sReturnField) {
		//String sReturnValue=null;
		Statement st;
		try {
			st = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					  ResultSet.CONCUR_READ_ONLY);
			ResultSet rs;
			rs = st.executeQuery(sSQLQueryString);
			System.out.println("Fired Query : " + sSQLQueryString);
			if(!rs.wasNull()) {
				System.out.println("Results of the Query as Below : ");
				while (rs.next()) {
					System.out.println(rs.getString(sReturnField));
					System.out.println(rs.getRow());
				}
			} else {
				System.out.println("The Query fetched zero records...");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void ExecuteUpdateQuery(Connection Conn,String sSQLQueryString) {
		//String sReturnValue=null;
		boolean bResults = true;
		Statement st;
		ResultSet rs;
		try {
			st = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					  ResultSet.CONCUR_READ_ONLY);
			bResults = st.execute(sSQLQueryString);
			if( !bResults) {
				System.out.println("Fired Query : " + sSQLQueryString + " No resultset generated...");
			} else {
				rs = st.getResultSet();
				System.out.println("Fired Query : " + sSQLQueryString + " Resultset generated...");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Query which returns the count (single field) fetched through the query
	public static int ExecuteQueryCount(Connection Conn,String sSQLQueryString,String sReturnField) {
		Statement st;
		int iReturnValue = 0;
		try {
			st = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
					  ResultSet.CONCUR_READ_ONLY);
			ResultSet rs;
			rs = st.executeQuery(sSQLQueryString);
			System.out.println("Fired Query : " + sSQLQueryString);
			if (rs == null) {
				System.out.println("The Query fetched zero records...");
				return 0;
			}
			rs.last();
			if(rs.getRow() > 1 ) {
				System.out.println("The Query fetched more than one record(s)...");
				return 0;
			}
			System.out.println("Results of the Query as Below : ");
			rs.beforeFirst();
			while (rs.next()) {
				System.out.println(rs.getString(sReturnField));
				iReturnValue = rs.getInt(sReturnField);
			}
			return iReturnValue;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return iReturnValue;
		}
	}

	// Query which returns the number of rows fetched through the query
	public static int ExecuteQueryRows(Connection Conn,String sSQLQueryString) {
		Statement st;
		try {
			//st = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st = Conn.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs;
			rs = st.executeQuery(sSQLQueryString);
			System.out.println("Fired Query : " + sSQLQueryString);
			if (rs == null) {
				System.out.println("The Query fetched zero records...");
				return 0;
			}
			rs.last();
			System.out.println("Count : " + rs.getRow());
			return rs.getRow();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static int ExecuteSpecificDatabaseQuery(Connection ConnDB, HSSFWorkbook wb, String sDatabaseQueriesSheet, String sControlValue, String sYesNo, String sSelectUpdate ) {
		int iDBCount = 0;
		String sSQLQueryString = ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 2);
		if(ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 4).equals(Constants.CONDITION_YES)) {
			sSQLQueryString = sSQLQueryString + ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 5);
		}
		if(!ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 6).isEmpty()) {
			sSQLQueryString = sSQLQueryString + ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 6);
		}										
		if(!ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 7).isEmpty()) {
			sSQLQueryString = sSQLQueryString + ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 7);
		}										
		if(!ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 8).isEmpty()) {
			sSQLQueryString = sSQLQueryString + ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 8);
		}
		if(ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 1).equals(Constants.CONDITION_YES)) {
			if( sYesNo.equals("YES")) {
				if(sSelectUpdate.equals("SELECT")) {
					DBFunctions.ExecuteQuery(ConnDB,sSQLQueryString,ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 3));
				} else {
					DBFunctions.ExecuteUpdateQuery(ConnDB,sSQLQueryString);
				}
			} else {
				iDBCount = DBFunctions.ExecuteQueryCount(ConnDB,sSQLQueryString,ExcelFunctions.FindValueInExcelSheet(wb, sDatabaseQueriesSheet, 0, sControlValue, 3));
			}
		} else {
			System.out.println("Query "+ sControlValue +" is not configured to run...");
		}
		return iDBCount;
	}
	
	public static void CreateQueryStringOrders(Connection Conn) {
		String sQueryString = "";
		int imin = 14001;
		int imax = 15000;
		sQueryString = "INSERT into Orders (OrderNumber,Comments,OrderDate,AddedDate,ModifiedDate) values (";
		for(int row=imin; row <=imax;row++) {
			if (row < imax) {
				sQueryString = sQueryString + "'ORD-" + String.valueOf(row) + "','QET Testing Order Number - " + String.valueOf(row) + "',SYSDATETIME(),SYSDATETIME(),SYSDATETIME()),(";
			} else {	
				sQueryString = sQueryString + "'ORD-" + String.valueOf(row) + "','QET Testing Order Number - " + String.valueOf(row) + "',SYSDATETIME(),SYSDATETIME(),SYSDATETIME())";
			}
		}
		System.out.println("Query String : " + sQueryString);
	}

	public static void CreateQueryStringOrderDetails(Connection Conn) {
		String sQueryString = "";
		int imin = 14501;
		int imax = 15000;
		sQueryString = "INSERT into OrderDetails (ProductName,Quantity,Price,OrderId,AddedDate,ModifiedDate) values (";
		for(int row=imin; row <=imax;row++) {
			if (row < imax) {
				sQueryString = sQueryString + "'PRODUCT-MVC-" + String.valueOf(row) + "',3,499," + row + ",SYSDATETIME(),SYSDATETIME()),(";
				sQueryString = sQueryString + "'PRODUCT-POC-" + String.valueOf(row) + "',2,699," + row + ",SYSDATETIME(),SYSDATETIME()),(";
			} else {	
				sQueryString = sQueryString + "'PRODUCT-MVC-" + String.valueOf(row) + "',3,499," + row + ",SYSDATETIME(),SYSDATETIME()),(";
				sQueryString = sQueryString + "'PRODUCT-POC-" + String.valueOf(row) + "',2,699," + row + ",SYSDATETIME(),SYSDATETIME())";
			}
		}
		System.out.println("Query String : " + sQueryString);
	}
	
	public static void CreateQueryStringAuthors(Connection Conn) {
		String sQueryString = "";
		int imin = 9000;
		int imax = 9999;
		sQueryString = "INSERT into Authors (FirstName,LastName,AddedDate,ModifiedDate) values (";
		for(int row=imin; row <=imax;row++) {
			if (row < imax) {
				sQueryString = sQueryString + "'QET" + String.valueOf(row) + "','Tester" + String.valueOf(row) + "',SYSDATETIME(),SYSDATETIME()),(";
			} else {	
				sQueryString = sQueryString + "'QET" + String.valueOf(row) + "','Tester" + String.valueOf(row) + "',SYSDATETIME(),SYSDATETIME())";
			}
		}
		System.out.println("Query String : " + sQueryString);
	}

	public static void CreateQueryStringBooks(Connection Conn) {
		String sQueryString = "";
		int imin = 9001;
		int imax = 10000;
		sQueryString = "INSERT into Books (Title,Author,ISBN,Published,AddedDate,ModifiedDate,Price) values (";
		for(int row=imin; row <=imax;row++) {
			int iPrice = 399;
			switch (row) {
			case 50:
			case 100:
			case 150:
			case 200:
			case 250:
			case 300:
			case 350:
			case 400:
			case 450:
			case 500:
			case 550:
			case 600:
			case 650:
			case 700:
			case 750:
			case 800:
			case 850:
			case 900:
			case 950:
			case 1000:
			case 1050:
				iPrice = 499;
				break;
			case 1100:
			case 1200:
			case 1300:
			case 1400:
			case 1500:
			case 1600:
			case 1700:
			case 1800:
			case 1900:
			case 2000:
				iPrice = 599;
				break;
			case 2100:
			case 2200:
			case 2300:
			case 2400:
			case 2500:
			case 2600:
			case 2700:
			case 2800:
			case 2900:
			case 3000:
				iPrice = 699;
				break;
			}
			if (row < imax) {
				sQueryString = sQueryString + "'Book-QET-MVC-"+ String.valueOf(row) + "','QET" + String.valueOf(row) + "','Tester." + String.valueOf(row) + ".17.01.25',SYSDATETIME(),SYSDATETIME(),SYSDATETIME()," + iPrice + "),(";
			} else {	
				sQueryString = sQueryString + "'Book-QET-MVC-"+ String.valueOf(row) + "','QET" + String.valueOf(row) + "','Tester." + String.valueOf(row) + ".17.01.25',SYSDATETIME(),SYSDATETIME(),SYSDATETIME()," + iPrice+ ")";
			}
		}
		System.out.println("Query String : " + sQueryString);
	}

}

