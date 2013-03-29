package org.strategoxt.imp.debuggers.stratego.instrumentation.actions.internal;

import java.io.File;

import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerInputPaths;

public class CompilerInputPathsImpl implements ICompilerInputPaths {

	private File spbd;
	private File ssf;
	private File td;
	
	public CompilerInputPathsImpl(File spbd, File ssf, File td) {
		super();
		this.spbd = spbd;
		this.ssf = ssf;
		this.td = td;
	}

	@Override
	public File getStrategoProgramBaseDirectory() {
		return spbd;
	}

	@Override
	public File getStrategoSourceFile() {
		return ssf;
	}

	@Override
	public File getTempDirectory() {
		return td;
	}

}
