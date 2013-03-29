package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.File;

import org.strategoxt.imp.debuggers.stratego.instrumentation.util.StringUtil;

public class StrategoCompilerConfig {
	
	/**
	 * points to the org.strategoxt.imp.debuggers.stratego.runtime.rtree
	 */
	private File debugRuntimeRtreeLocation;
	
	/**
	 * The fully qualified name of the java package
	 */
	private String libraryJavaPackage;
	
	public File getDebugRuntimeRtreeLocation() {
		return this.debugRuntimeRtreeLocation;
	}

	public void setDebugRuntimeRtreeLocation(File debugRuntimeRtreeLocation) {
		this.debugRuntimeRtreeLocation = debugRuntimeRtreeLocation;
	}

	public String getLibraryJavaPackage() {
		return libraryJavaPackage;
	}

	public void setLibraryJavaPackage(String libraryJavaPackage) {
		this.libraryJavaPackage = libraryJavaPackage;
	}

	/**
	 * Returns the file-system representation of libraryJavaPackage.
	 * (All '.' are replaced with '/')
	 * @return
	 */
	public File getPackageDirectory() {
		String[] parts = this.libraryJavaPackage.split("\\."); // matches a single dot
		
		String path = StringUtil.join(parts, "/");
		File f = new File(path);
		return f;
	}

	private String[] extraArgs;
	
	public void setExtraArgs(String[] extraArgs) {
		this.extraArgs = extraArgs;
	}
	
	public String[] getExtraArgs() {
		return extraArgs;
	}
	
}
