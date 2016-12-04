// Cartwright, Stephen D
// 12/3/16 640pm


import java.io.*;


public class ThreadHandler extends Thread {
	public InputStream inputStream;
	public String adminPass;
	public OutputStream outputStream;
	public PrintWriter printWriter;
	public StringBuilder outputBuffer;
	private boolean sudoRequested;
	
	public ThreadHandler(InputStream inputStream) {
		this.outputBuffer = new StringBuilder();
		this.sudoRequested = false;
		this.inputStream = inputStream;
	}
	
	public ThreadHandler(InputStream inputStream, OutputStream outputStream, String adminPass) {
		this.outputBuffer = new StringBuilder();
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

			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			
			while( (line = bufferedReader.readLine()) != null) {

				outputBuffer.append(line + "\n");
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			
			try {

				bufferedReader.close();
			} catch (IOException ioe) {
				
			}
		
		}
	}
	
	public String getOutputBuffer() {
		return outputBuffer.toString();
	}
}
