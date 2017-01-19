// Cartwright, Stephen D
// 12/3/16 658pm

package com;

import java.io.IOException;
import java.util.*;

public class ProcessHandler {

	private List<String> commandArgs;
	public String stdErrF, stdOutF, exited;
	public String completeLog;
	public ProcessHandler(List<String> commandArgs) throws IOException, InterruptedException {
		try {
			this.commandArgs = commandArgs;
			SystemCommandHandler systemCommandHandler = new SystemCommandHandler(commandArgs);
			int result = systemCommandHandler.execute();
			
			exited = "System Command exited with " + result + "\n";;
			
			stdOutF = "StdOut: " + systemCommandHandler.getStdOut() + "\n";
			stdErrF = "StdErr: " + systemCommandHandler.getStdErr() + "\n";
			
			completeLog = exited + stdOutF + stdErrF;
		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}


}
