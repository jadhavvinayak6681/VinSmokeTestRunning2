package SmokeTesting;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;



public class PowershellClass 
{

	public void powershellmethod(String CreateFilePath, String MovetoLocationPath )
	{
		PowerShell powerShell = null;
		 CreateFilePath = "D:\\powershell\\testfileone.txt";
		 MovetoLocationPath = "d:\\powershell\\testfileone.txt";
		
		try 
		{
			//Creates PowerShell session (we can execute several commands in the same session)
			powerShell = PowerShell.openSession();
			
			//Execute a command in PowerShell session
			PowerShellResponse response = powerShell.executeCommand("New-Item " + CreateFilePath);
			response = powerShell.executeCommand("move-item " + MovetoLocationPath);
			response = powerShell.executeCommand("test-path" + MovetoLocationPath);
			if (response.toString() == "True")
			{
				// successfull messange in to LogforJ file
			}
		}
		catch(PowerShellNotAvailableException ps)
		{
			
		}
		finally
		{
			powerShell.close();
		}
	}
	
}
