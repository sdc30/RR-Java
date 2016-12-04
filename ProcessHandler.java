// Cartwright, Stephen D
// 12/3/16 658pm

import java.io.IOException;
import java.util.*;

public class ProcessHandler {

	private List<String> commandArgs;
	public String stdErrF, stdOutF;

	public ProcessHandler(List<String> commandArgs) throws IOException, InterruptedException {
		try {
			this.commandArgs = commandArgs;
			System.out.println("ProcessHandler\n");
			SystemCommandHandler systemCommandHandler = new SystemCommandHandler(commandArgs);
			int result = systemCommandHandler.execute();
			
			System.out.println("The numeric result of the command was: " + result);
			stdOutF = "StdOut: " + systemCommandHandler.getStdOut().toString();
			stdErrF = "StdErr: " + systemCommandHandler.getStdErr().toString();

		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}


}
