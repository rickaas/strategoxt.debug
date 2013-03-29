package org.strategoxt.imp.debuggers.stratego.instrumentation;

import java.io.File;
import java.io.IOException;

import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.CompileJava;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.CompileStratego;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerInputPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.InstrumentStratego;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.JavaCompilerConfig;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.StrategoCompilerConfig;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.internal.CompilerPathsImpl;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.FileUtil;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.IHybridInterpreterProvider;

public class StrategoCompilerWrapper {

	/**
	 * Specified by the user.
	 */
	private ICompilerInputPaths cip;
	
	/**
	 * Specified by the user.
	 */
	private IHybridInterpreterProvider provider;
	
	/**
	 * Contains location of stratego debug runtime rtree.
	 */
	private StrategoCompilerConfig strategoCompilerConfig;
	
	private JavaCompilerConfig javaCompilerConfig;
	
	private ICompilerPaths compilerPaths;
	
	public ICompilerInputPaths getICompilerInputPaths(){
		return this.cip;
	}
	
	public ICompilerPaths getICompilerPaths() {
		return this.compilerPaths;
	}
	
	public StrategoCompilerWrapper(ICompilerInputPaths cip, ICompilerPaths compilerPaths, StrategoCompilerConfig strategoCompilerConfig, JavaCompilerConfig javaCompilerConfig, IHybridInterpreterProvider provider) {
		this.cip = cip;
		this.compilerPaths = compilerPaths;
		this.strategoCompilerConfig = strategoCompilerConfig;
		this.javaCompilerConfig = javaCompilerConfig;
		this.provider = provider;
	}
	
	public void compileForRelease() throws IOException {

		ICompilerPaths compilerPaths = new CompilerPathsImpl(cip, "run");
		this.clean(compilerPaths);

		// generate Java from Stratego
		CompileStratego s = new CompileStratego(this.cip, compilerPaths, this.strategoCompilerConfig);
		s.execute();
		
		// compile Java
		CompileJava j = new CompileJava(compilerPaths, strategoCompilerConfig, javaCompilerConfig);
		j.execute();
	}
	
	private void clean(ICompilerPaths compilerPaths) throws IOException {
		String projectStrategoDir = compilerPaths.getStrategoDirectory().getCanonicalPath();
		String projectJavaDir = compilerPaths.getJavaDirectory().getCanonicalPath();
		String projectClassDir = compilerPaths.getBinDirectory().getCanonicalPath();
		String logsDir = compilerPaths.getLogsDirectory().getCanonicalPath();

		//FileUtil.deleteDirectory(projectStrategoDir); // delete the 'stratego' dir (do not delete to enable caching of instrumented files)
		FileUtil.deleteDirectory(projectJavaDir); // delete the 'java' dir
		FileUtil.deleteDirectory(projectClassDir); // delete the 'class' dir
		FileUtil.deleteDirectory(logsDir);
		
		new File(projectStrategoDir).mkdirs();
		new File(projectJavaDir).mkdirs();
		new File(projectClassDir).mkdirs();
		new File(logsDir).mkdirs();
	}
	
	public void compileForDebug() throws IOException {
		// get the basedir of programPath, 
		// module-name is the filename of programPath
		
		// create dir: .working/$module-name/$mode
		// in each $mode we have the following directories:
		// .working/$module-name/$mode/stratego
		// .working/$module-name/$mode/java
		// .working/$module-name/$mode/bin
		
		this.clean(compilerPaths);
		
		// instrument all .str-files in this $basedir
		// the output base path is .working/$module-name/$mode/stratego
		String dslProgramBasePath = this.cip.getStrategoProgramBaseDirectory().getCanonicalPath();
		String outputBasePath = compilerPaths.getStrategoDirectory().getCanonicalPath();
		InstrumentStratego i = new InstrumentStratego(provider, dslProgramBasePath, outputBasePath);
		i.init();
		i.setTempDirectory(this.cip.getTempDirectory().getCanonicalPath());
		i.execute();
		
		// generate Java from Stratego
		CompileStratego s = new CompileStratego(this.cip, compilerPaths, this.strategoCompilerConfig);
		s.execute();
		this.strjArguments = s.getArguments();
		
		// compile Java
		CompileJava j = new CompileJava(compilerPaths, strategoCompilerConfig, javaCompilerConfig);
		j.execute();
		this.javacArguments = j.getJavacArgs();
		
	}
	
	private String[] strjArguments = null;
	
	public String[] getStrjArguments() {
		return strjArguments;
	}
	
	private String[] javacArguments = null;
	public String[] getJavacArguments() {
		return javacArguments;
	}
}
