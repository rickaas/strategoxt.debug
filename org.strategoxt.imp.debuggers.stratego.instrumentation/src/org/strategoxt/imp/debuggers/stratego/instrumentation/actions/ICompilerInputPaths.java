package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.File;

public interface ICompilerInputPaths {

	/**
	 * The root source directory of the stratego files.
	 * This can be viewed as a 'project' directory.
	 * @return
	 */
	public File getStrategoProgramBaseDirectory();
	
	/**
	 * The main stratego source file, relative to the StrategoProgramBaseDirectory.
	 * @return
	 */
	public File getStrategoSourceFile();
	
	/**
	 * A temp directory to store the generated files.
	 * @return
	 */
	public File getTempDirectory();
	
}
