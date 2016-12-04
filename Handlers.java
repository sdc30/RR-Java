// Cartwright, Stephen D
// 10/18/16 1128pm
// 


import java.io.*;
import java.util.List;
import java.io.InputStream.*;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.awt.GraphicsEnvironment;
import java.net.URISyntaxException;

public class Handlers {
	
	public String output;
	
	public Handlers(List<String> args) throws Exception {
		output = new String("Handlers called...\n");
		Console console = System.console();
		
		if(!GraphicsEnvironment.isHeadless()) {
			System.out.println("Handler\n");
			ProcessHandler processHandler = new ProcessHandler(args);
			output += processHandler.completeLog;
	
		} else {
			//System.out.println("Nada\n");
		}
		
		
	}



}

	
	












































