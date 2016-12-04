// Cartwright, Stephen D
// 10/26/16 208pm


import java.io.*;
import java.io.OutputStream.*;

public class FileOperations {

	public FileOperations() {
		

		
	}
	
	public String makeFile(String name, String args) {
		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter = null;
		String output = "makeFile...\n";
		
		try {
			
			file = new File(name);
			
			if(!file.exists()) {
				file.createNewFile();
			} else {
				return "Error";
			}
			
			fileWriter = new FileWriter(file);
			buffWriter = new BufferedWriter(fileWriter);
			
			
				buffWriter.write(args);
			
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if(buffWriter != null) {
					buffWriter.close();
				}
				
				output = "File Created successfully.\n";
				
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
		return output;
	}
	
	public String[] readFile(String name) {
		String[] ret = new String[2];
		BufferedReader buffReader = null;
		StringBuffer buffString = null;
		String output = "readFile...\n";
		String lineOut = "";
		
		try {
			
			buffReader = new BufferedReader(new FileReader(name));
			buffString = new StringBuffer();
			
			output += "Reading from file...\n";
			
			lineOut = "";
			
			while( (lineOut = buffReader.readLine()) != null) {
				buffString.append(lineOut + "\n");
			}
			
			ret[0] = buffString.toString();
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				
				if(buffReader != null) {
					buffReader.close();
				}
				
				output += "File Created successfully.\n";
				
				ret[1] = output;
				
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
	
		return ret;
		
	}


}
