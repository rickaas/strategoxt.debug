package org.strategoxt.imp.debuggers.stratego.launching;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.osgi.framework.Bundle;
import org.spoofax.debug.core.control.launching.IJavaProgramLaunchPreparation;
import org.spoofax.debug.core.control.launching.IJavaProgramPrepareResult;
import org.spoofax.debug.core.control.launching.LaunchPreparationException;
import org.strategoxt.imp.debuggers.stratego.instrumentation.StrategoCompilerWrapper;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerInputPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.IJavaCompiler;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ITaskProgress;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.JavaCompilerConfig;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.StrategoCompilerConfig;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.internal.CompilerPathsImpl;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.IHybridInterpreterProvider;
import org.strategoxt.imp.debuggers.stratego.libraries.StrategoRuntimeDebug;

public class StrLaunchPreparation implements IJavaProgramLaunchPreparation {

	@Override
	public IJavaProgramPrepareResult prepare(String projectName, IPath programPath, String mode)
			throws LaunchPreparationException {
		//String dslProgramBasepath = programPath.removeLastSegments(1).toOSString();
		// the DSL-PROGRAM_BASEPATH is the project directory
		// The paths in the debug event info are relative to this directory.
		// This makes sense because the filelocation set in a breakpoint is relative to the project directory.
		IPath projectLocation = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocation();
		
		String dslProgramBasepath = projectLocation.toOSString();
		//String strategoSource = programPath.lastSegment();
		String strategoSource = programPath.toOSString(); // path is relative to the project directory
		String tempDirName = ".stratego-debug-compiler";
		String packageName = "foopackage";
		
		String outputBasePath = projectLocation.append(tempDirName).toOSString();
		//dslProgramBasepath = projectLocation.append(dslProgramBasepath).toOSString();; // prefix with project location
		
		IJavaProgramPrepareResult result = null;
		
		try {
			// init classes that contain the paths required for the toolchain Stratego->Instrumented-Stratego->Java->Binaries
			ICompilerInputPaths cip = this.getICompilerInputPaths(dslProgramBasepath, strategoSource, outputBasePath);
			ICompilerPaths compilerPaths = this.getICompilerPaths(mode);
			StrategoCompilerConfig strategoCompilerConfig = this.getStrategoCompilerConfig(packageName);
			JavaCompilerConfig javaCompilerConfig = this.getJavaCompilerConfig();
			IHybridInterpreterProvider provider = new StrHybridInterpreterProvider();
	
			// init the compiler
			StrategoCompilerWrapper compiler = new StrategoCompilerWrapper(cip, compilerPaths, strategoCompilerConfig, javaCompilerConfig, provider);
			System.out.println("PWD: " + new File(".").getCanonicalPath());
			compiler.compileForDebug();
			
			
			final String binDirectory = compilerPaths.getBinDirectory().getCanonicalPath();
			final String mainClassname = strategoCompilerConfig.getLibraryJavaPackage() + ".Main";
			result = new IJavaProgramPrepareResult() {
				
				@Override
				public String getClassname() {
					return mainClassname;
				}
				
				@Override
				public String getBinDirectory() {
					return binDirectory;
				}
			};
		} catch (IOException e) {
			throw new LaunchPreparationException(e);
		}

		
		// get the basedir of programPath, 
		// module-name is the filename of programPath
		
		// create dir: .working/$module-name/$mode
		// in each $mode we have the following directories:
		// .working/$module-name/$mode/stratego
		// .working/$module-name/$mode/java
		// .working/$module-name/$mode/bin
		
		
		// instrument all .str-files in this $basedir
		// the output base path is .working/$module-name/$mode/stratego
		
		// generate Java from Stratego
		
		// compile Java
		

		
		return result;
	}

	
	private ICompilerInputPaths cip;
	private ICompilerPaths compilerPaths;

	private StrategoCompilerConfig strategoCompilerConfig;
	
	private JavaCompilerConfig javaCompilerConfig;

	private IJavaCompiler compiler;

	
	public ICompilerInputPaths getICompilerInputPaths(final String dslProgramBasepath, final String strategoSource, final String outputBasePath) {
		if (this.cip != null) return this.cip;
		
		this.cip = new ICompilerInputPaths() {
			
			@Override
			public File getTempDirectory() {
				return new File(outputBasePath);
			}
			
			@Override
			public File getStrategoSourceFile() {
				return new File(strategoSource);
			}
			
			@Override
			public File getStrategoProgramBaseDirectory() {
				return new File(dslProgramBasepath);
			}
		};
		
		return cip;
	}
	
	public ICompilerPaths getICompilerPaths(String mode) {
		if (this.compilerPaths != null) return this.compilerPaths;
		
		this.compilerPaths = new CompilerPathsImpl(this.cip, mode);
		return this.compilerPaths;
	}
	
	public StrategoCompilerConfig getStrategoCompilerConfig(String packageName) throws IOException {
		if (this.strategoCompilerConfig != null) return this.strategoCompilerConfig;
		
		this.strategoCompilerConfig = new StrategoCompilerConfig();
		System.out.println("");
		// get the rtree from the plugin directory
		URL url_rtree = StrategoRuntimeDebug.getRtreeLocation();
		File file_rtree = org.strategoxt.imp.debuggers.stratego.Activator.URLToIPath(url_rtree).toFile();
		strategoCompilerConfig.setDebugRuntimeRtreeLocation(file_rtree);
		
		strategoCompilerConfig.setLibraryJavaPackage(packageName);
		
		return strategoCompilerConfig;
	}
	
	public JavaCompilerConfig getJavaCompilerConfig() throws IOException {
		if (this.javaCompilerConfig != null) return this.javaCompilerConfig;
		
		this.javaCompilerConfig = new JavaCompilerConfig();
		
		
		URL url_strategodebugruntime = StrategoRuntimeDebug.getJarLocation();
		File file_strategodebugruntime = org.strategoxt.imp.debuggers.stratego.Activator.URLToIPath(url_strategodebugruntime).toFile();
		javaCompilerConfig.setStrategoDebugRuntimeJar(file_strategodebugruntime);
		
		// strategoxt.jar is in org.strategoxt.strj/java/strategoxt.jar
		String strjPluginID = "org.strategoxt.strj";
		Bundle bundle = Platform.getBundle(strjPluginID);
		URL url_strategoxt = org.strategoxt.imp.debuggers.stratego.Activator.getLocation(bundle, "java/strategoxt.jar");
		File file_strategoxt = org.strategoxt.imp.debuggers.stratego.Activator.URLToIPath(url_strategoxt).toFile();
		javaCompilerConfig.setStrategoxtJar(file_strategoxt);
		
		javaCompilerConfig.setJavaCompiler(createIJavaCompiler());
		
		return javaCompilerConfig;
	}

	public IJavaCompiler createIJavaCompiler() {
		if (this.compiler != null) return this.compiler;
		
		this.compiler = new IJavaCompiler() {
			
			private ITaskProgress taskProgress;
			private PrintWriter outWriter;
			private PrintWriter errWriter;
			private String[] arguments;
			
			private CompilationProgress getCompilationProgress() {
				if (taskProgress instanceof CompilationProgress) {
					return (CompilationProgress) taskProgress;
				} else {
					return null;
				}
			}
			
			@Override
			public void setTaskProgress(ITaskProgress taskProgress) {
				this.taskProgress = taskProgress;
			}
			
			@Override
			public void setOutWriter(PrintWriter outWriter) {
				this.outWriter = outWriter;
			}
			
			@Override
			public void setErrWriter(PrintWriter errWriter) {
				this.errWriter = errWriter;
			}
			
			@Override
			public void setArguments(String[] arguments) {
				this.arguments = arguments;
			}
			
			@Override
			public void execute() {
				org.eclipse.jdt.core.compiler.batch.BatchCompiler.compile(arguments, outWriter, errWriter, getCompilationProgress());
				
			}
		};
		return compiler;
	}
}
