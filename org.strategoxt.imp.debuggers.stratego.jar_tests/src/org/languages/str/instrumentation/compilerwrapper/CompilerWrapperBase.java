package org.languages.str.instrumentation.compilerwrapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.Assert;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.strategoxt.HybridInterpreter;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerInputPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.IJavaCompiler;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ITaskProgress;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.JavaCompilerConfig;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.StrategoCompilerConfig;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.internal.CompilerPathsImpl;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.IHybridInterpreterProvider;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.StringUtil;

import util.HybridInterpreterHelper;

public abstract class CompilerWrapperBase {

	private IJavaCompiler compiler;
	private IHybridInterpreterProvider provider;
	private JavaCompilerConfig javaCompilerConfig;
	private StrategoCompilerConfig strategoCompilerConfig;
	
	private ICompilerInputPaths cip;
	private ICompilerPaths compilerPaths;
	
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
	
	public IHybridInterpreterProvider getHIP() {
		if (this.provider != null) return this.provider;
		
		provider = new IHybridInterpreterProvider() {
			
			private HybridInterpreter hi = null;
			
			@Override
			public HybridInterpreter get() {
				if (hi == null) {
					this.hi = HybridInterpreterHelper.createHybridInterpreter();
				}
				return hi;
			}
		};
		return provider;
	}
	
	public JavaCompilerConfig getJavaCompilerConfig() {
		if (this.javaCompilerConfig != null) return this.javaCompilerConfig;
		
		this.javaCompilerConfig = new JavaCompilerConfig();
		File strategoDebugRuntimeJar = new File("lib/runtime/org.strategoxt.imp.debuggers.stratego.runtime.jar");
		Assert.assertTrue(strategoDebugRuntimeJar.exists());
		javaCompilerConfig.setStrategoDebugRuntimeJar(strategoDebugRuntimeJar);
		
		File strategoxtJar = new File("lib/instrumentation/strategoxt.jar");
		Assert.assertTrue(strategoxtJar.exists());
		javaCompilerConfig.setStrategoxtJar(strategoxtJar);
		
		javaCompilerConfig.setJavaCompiler(createIJavaCompiler());
		
		return javaCompilerConfig;
	}
	
	public StrategoCompilerConfig getStrategoCompilerConfig(String packageName) {
		if (this.strategoCompilerConfig != null) return this.strategoCompilerConfig;
		
		this.strategoCompilerConfig = new StrategoCompilerConfig();
		File debugRuntimeRtreeLocation = new File("lib/runtime/org.strategoxt.imp.debuggers.stratego.runtime.rtree");
		Assert.assertTrue(debugRuntimeRtreeLocation.exists());
		strategoCompilerConfig.setDebugRuntimeRtreeLocation(debugRuntimeRtreeLocation);
		
		strategoCompilerConfig.setLibraryJavaPackage(packageName);
		
		return strategoCompilerConfig;
	}
	
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
		
		this.compilerPaths = new CompilerPathsImpl(cip, mode);
		return this.compilerPaths;
	}
	
	public String getJavaCommandline() throws IOException {
		
		File spoofaxDebugLibrary = new File("lib/runtime/org.spoofax.debug.interfaces.jar");
		Assert.assertTrue(spoofaxDebugLibrary.exists());
		
		// language independent runtime for java-based backends
		File spoofaxDebugJavaLibrary = new File("lib/runtime/org.spoofax.debug.interfaces.java.jar");
		Assert.assertTrue(spoofaxDebugJavaLibrary.exists());
		
		String[] cpArray  = new String[] {
				// strategoxt.jar
				javaCompilerConfig.getStrategoxtJar().getCanonicalPath(),
				// org.strategoxt.imp.debuggers.stratego.runtime.jar
				javaCompilerConfig.getStrategoDebugRuntimeJar().getCanonicalPath(),
				// org.spoofax.debug.interfaces.jar
				spoofaxDebugLibrary.getCanonicalPath(),
				// org.spoofax.debug.interfaces.java.jar
				spoofaxDebugJavaLibrary.getCanonicalPath(),
				// bin
				compilerPaths.getBinDirectory().getCanonicalPath(),
		};
		String classpath = StringUtil.join(cpArray, ":");
		// now we can run the program:
		String[] javaArgs = new String[] {
				"java",
				"-cp",
				classpath,
				// name.of.the.main.class
				strategoCompilerConfig.getLibraryJavaPackage() + ".Main"
		};
		
		
		return StringUtil.join(javaArgs, " ");
	}
}
