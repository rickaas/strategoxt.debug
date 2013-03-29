package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.PrintWriter;

public interface IJavaCompiler {

	void setArguments(String[] arguments);
	
	void setOutWriter(PrintWriter outWriter);
	void setErrWriter(PrintWriter errWriter);
	
	void setTaskProgress(ITaskProgress taskProgress);
	
	void execute();
}
