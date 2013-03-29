package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.strategoxt.imp.debuggers.stratego.instrumentation.util.StringUtil;

public class CompileJava {
//
//	private ICompilerPaths cp;
//	
//	public CompileJava(ICompilerPaths cp) {
//		this.cp = cp;
//	}
//	
	
	private ICompilerPaths icp;
	private StrategoCompilerConfig strategoCompilerConfig;
	private JavaCompilerConfig javaCompilerConfig;
	
	public CompileJava(ICompilerPaths icp, StrategoCompilerConfig strConfig, JavaCompilerConfig javaConfig){
		this.icp = icp;
		this.strategoCompilerConfig = strConfig;
		this.javaCompilerConfig = javaConfig;
	}
	
	public String getStrjOutputFileLocation() throws IOException {
		// ${java-src.gen.dir}/${package-root-dir}/Main.java
		File packagePath = strategoCompilerConfig.getPackageDirectory();
		File dir = new File(icp.getJavaDirectory(), packagePath.getPath());
		File main = new File(dir, "Main.java");
		return main.getCanonicalPath();
	}
	
	public String[] getJavacArgs() throws IOException {
		String binBaseDir = icp.getBinDirectory().getCanonicalPath();
		String classpath = getClasspath();
		String filename = getStrjOutputFileLocation();
		String[] args = new String[] {
		        "-d", binBaseDir,
		         filename,
		         "-cp", classpath,
		         "-source", "1.5"
		    };
		return args;
	}
	
	public String getClasspath() throws IOException {
		String javaSourceDirectory = icp.getJavaDirectory().getCanonicalPath();
		// strategoxt.jar
		String strategoxt = javaCompilerConfig.getStrategoxtJar().getCanonicalPath();
		// org.strategoxt.imp.debuggers.stratego.runtime.jar
		String strategoDebugRuntime = javaCompilerConfig.getStrategoDebugRuntimeJar().getCanonicalPath();
		String[] cps = new String[] {
				javaSourceDirectory,
				strategoxt,
				strategoDebugRuntime
		};
		String classpath = StringUtil.join(cps, ":");
		return classpath;
	}
	
	public void execute() throws IOException {
		IJavaCompiler compiler = this.javaCompilerConfig.getJavaCompiler();
		// TODO: get extra compiler arguments from debugSessionSettings

		FileOutputStream outStream = null;
		FileOutputStream errorStream = null;
		String logFileLocation = "";
		try {
			File outFile = new File(this.icp.getLogsDirectory(), "out.log");
			File errorFile = new File(this.icp.getLogsDirectory(), "error.log");
			outStream = new FileOutputStream(outFile, false);
			errorStream = new FileOutputStream(errorFile, false);
			logFileLocation =  outFile.getAbsolutePath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log("Logfile: " + logFileLocation);

		
		PrintWriter outWriter = null;
		PrintWriter errWriter = null;
		
		// if output stream could not be created write to stdout and stderr
		if (outStream != null) {
			outWriter = new PrintWriter(outStream);
		} else {
			outWriter = new PrintWriter(System.out);
		}
		
		if (errorStream != null) {
			errWriter = new PrintWriter(errorStream);
		} else {
			errWriter = new PrintWriter(System.err);
		}
		
		compiler.setArguments(getJavacArgs());
		compiler.setOutWriter(outWriter);
		compiler.setErrWriter(errWriter);
		
		compiler.execute();
//		
//		//boolean systemExitWhenFinished = false;
//		//Map customDefaultOptions = new HashMap();
//		CompilationProgress compilationProgress = this.debugCompileProgress.getJavaCompileProgress();
//
//		
//		//org.eclipse.jdt.internal.compiler.batch.Main main = new Main(outWriter, errWriter, systemExitWhenFinished, customDefaultOptions, compilationProgress);
//		//boolean result = main.compile(args);
//		boolean result = org.eclipse.jdt.core.compiler.batch.BatchCompiler.compile(args, outWriter, errWriter, compilationProgress);
//		log("Compile result: " + result);
//		if (!result)
//		{
//			
//		}
//
	}
	
	private void log(String message) {
		System.out.println(message);
	}
}
