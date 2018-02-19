package SmokeTesting;

import java.io.BufferedWriter;
import java.io.IOException;

public class ReportFunctions {

	public static void ReportHeader(BufferedWriter bw, String sStyleSheetPath, String sDataSheet, String sEnvironmentName, String baseURL, String sRunDate) throws Exception {
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

	public static void ReportTestCaseNameData(BufferedWriter bw, String sTestCaseName) throws Exception {
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

	public static void ReportTestCaseDetails(BufferedWriter bw, int iStepNo, String sStepDescription, String sStepExpection) throws Exception {
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

	public static void ReportTestCaseResult(BufferedWriter bw, String sResult) throws Exception {
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
	
	public static void ReportFooter(BufferedWriter bw) throws Exception {
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
}
