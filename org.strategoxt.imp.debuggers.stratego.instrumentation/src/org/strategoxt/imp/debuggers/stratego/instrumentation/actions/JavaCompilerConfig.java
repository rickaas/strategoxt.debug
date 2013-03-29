package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.File;

public class JavaCompilerConfig {

	private File strategoxtJar;
	private File strategoDebugRuntimeJar;
	
	private IJavaCompiler javaCompiler;
	
	public File getStrategoxtJar() {
		return strategoxtJar;
	}
	public void setStrategoxtJar(File strategoxtJar) {
		this.strategoxtJar = strategoxtJar;
	}
	public File getStrategoDebugRuntimeJar() {
		return strategoDebugRuntimeJar;
	}
	public void setStrategoDebugRuntimeJar(File strategoDebugRuntimeJar) {
		this.strategoDebugRuntimeJar = strategoDebugRuntimeJar;
	}
	
	
	public IJavaCompiler getJavaCompiler() {
		return javaCompiler;
	}
	public void setJavaCompiler(IJavaCompiler javaCompiler) {
		this.javaCompiler = javaCompiler;
	}
	
	
}
