package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.StringWriter;
import java.io.Writer;

import org.spoofax.interpreter.library.IOAgent;

/**
 * Q: Why do we need a custom IOAgent?
 * A: So we can get the stdout and stderr.
 * @author rlindeman
 *
 */
public class CustomIOAgent extends IOAgent {

	private Writer stdoutStringWriter = new StringWriter();
	
	private Writer stderrStringWriter = new StringWriter();
	
	@Override
	public Writer getWriter(int fd) {
        if (fd == CONST_STDOUT) {
            return stdoutStringWriter;
        } else if (fd == CONST_STDERR) {
            return stderrStringWriter;
        } else {
        	return super.getWriter(fd);
        }
	}
	
	public String getStdout()
	{
		return this.stdoutStringWriter.toString();
	}
	
	public String getStderr()
	{
		return this.stderrStringWriter.toString();
	}
}
