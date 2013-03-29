package org.strategoxt.imp.debuggers.stratego.instrumentation.actions.internal;

import java.io.File;

import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerInputPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerPaths;

public class CompilerPathsImpl implements ICompilerPaths {

	private File sd;
	
	private File jd;
	
	private File bd;
	
	private File logs;
	
	public CompilerPathsImpl(ICompilerInputPaths cip, String mode) {
		// get the basedir of programPath, 
		// module-name is the filename of programPath
		
		// create dir: .working/$module-name/$mode
		// in each $mode we have the following directories:
		// .working/$module-name/$mode/stratego
		// .working/$module-name/$mode/java
		// .working/$module-name/$mode/bin
		
		//String moduleName = cip.getStrategoSourceFile().getName();
		//String childMode = moduleName + "/" + mode;
		String childMode = mode;
		File modeDirectory = new File(cip.getTempDirectory(), childMode);
		this.sd = new File(modeDirectory, "stratego");
		this.jd = new File(modeDirectory, "java");
		this.bd = new File(modeDirectory, "bin");
		this.logs = new File(modeDirectory, "logs");
		
	}
	
	public CompilerPathsImpl(File sd, File jd, File bd, File logs) {
		super();
		this.sd = sd;
		this.jd = jd;
		this.bd = bd;
		this.logs = logs;
	}

	/**
	 * .working/$module-name/$mode/stratego
	 */
	@Override
	public File getStrategoDirectory() {
		return sd;
	}

	@Override
	public File getJavaDirectory() {
		return jd;
	}

	@Override
	public File getBinDirectory() {
		return bd;
	}
	
	@Override
	public File getLogsDirectory() {
		return logs;
	}

}
