// Cartwright, Stephen D
// 12/3/16 630pm

import java.io.*;
import java.util.List;


public class SystemCommandHandler {

	private List<String> commandArgs;
	private String adminPass;
	private ThreadHandler inputHandler;
	private ThreadHandler outputHandler;
	
	public SystemCommandHandler(final List<String> commandArgs) {
		System.out.println("SystemCommandHandler\n");
		if(commandArgs == null) throw new NullPointerException("Argument list cannot be null");
		
		this.commandArgs = commandArgs;
		this.adminPass = null;
	}
	
	public int execute() throws IOException, InterruptedException {
		int exit = -1;
		
		try {
			System.out.println("execute()\n");
			ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
			final Process process = processBuilder.start();
			
			OutputStream stdOut = process.getOutputStream();
			
			InputStream stdIn = process.getInputStream();
			InputStream stdErr = process.getErrorStream();
			
			ThreadHandler inputHandler = new ThreadHandler(stdIn, stdOut, adminPass);
			ThreadHandler outputHandler = new ThreadHandler(stdErr);
			
			inputHandler.start();
			outputHandler.start();
			
			exit = process.waitFor();
			
			
			inputHandler.interrupt();
			outputHandler.interrupt();
			
			inputHandler.join();
			outputHandler.join();
			
			
		} catch (IOException ioe) {
			throw ioe;
		} catch (InterruptedException ie) {
			throw ie;
		} finally {
			return exit;
		}
	}
	
	public StringBuilder getStdOut() {
		return inputHandler.getOutputBuffer();
	}
	
	public StringBuilder getStdErr() {
		return outputHandler.getOutputBuffer();
	}

}
