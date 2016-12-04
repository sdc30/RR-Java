// Cartwright, Stephen D
// 12/3/16 640pm


import java.io.*;


public class ThreadHandler extends Thread {
	private InputStream inputStream;
	private String adminPass;
	private OutputStream outputStream;
	private PrintWriter printWriter;
	private StringBuilder outputBuffer;
	private boolean sudoRequested;
	
	public ThreadHandler(InputStream inputStream) {
		this.outputBuffer = new StringBuilder();
		this.sudoRequested = false;
		this.inputStream = inputStream;
	}
	
	public ThreadHandler(InputStream inputStream, OutputStream outputStream, String adminPass) {
		this.outputBuffer = new StringBuilder();
		this.sudoRequested = false;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.printWriter = new PrintWriter(outputStream);
		this.adminPass = adminPass;
		this.sudoRequested = true;
	}
	
	public void run() {
		if(sudoRequested) {
			printWriter.println(adminPass);
			printWriter.flush();
		}
		
		BufferedReader bufferedReader = null;
		
		try {
			System.out.println("run()\n");

			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			
			while( (line = bufferedReader.readLine()) != null) {
				System.out.println(line + "\n");

				outputBuffer.append(line + "\n");
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			
			try {
				System.out.println("close\n");

				bufferedReader.close();
			} catch (IOException ioe) {
				
			}
		
		}
	}
	
	public StringBuilder getOutputBuffer() {
		return outputBuffer;
	}
}
