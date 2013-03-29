package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExternalJavaRunner {

	
	public static int run(String commandline) throws InterruptedException {
	    int result = -1;

	    try {

	        System.out.println("command output:");
	        Process proc = Runtime.getRuntime().exec(commandline);

	        InputStream errin = proc.getErrorStream();
	        InputStream in = proc.getErrorStream(); 
	        BufferedReader errorOutput = new BufferedReader(new InputStreamReader(errin));
	        BufferedReader output = new BufferedReader(new InputStreamReader(in));
	        String line1 = null;
	        String line2 = null;
	        try {
	            while ((line1 = errorOutput.readLine()) != null || 
	                   (line2 = output.readLine()) != null) {
	                if(line1 != null) System.out.print(line1);
	                if(line2 != null) System.out.print(line2);
	            }
	        } catch (IOException e) {
	            e.printStackTrace(); 
	        } finally {
	        	safeClose(errorOutput);
	        	safeClose(output);
	        }
	        result = proc.waitFor();
	    } catch (IOException e) {
	        System.err.println("IOException raised: " + e.getMessage());
	    }
	    return result;
	}
	
	private static void safeClose(BufferedReader reader) {
		if (reader == null) return;
    	try {
			reader.close();
    	} catch(IOException e) {
    		// could not close reader
    	}
	}
}
