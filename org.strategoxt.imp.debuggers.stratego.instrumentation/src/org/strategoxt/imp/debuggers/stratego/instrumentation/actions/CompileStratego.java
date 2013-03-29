package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.StringUtil;
import org.strategoxt.lang.StrategoExit;

public class CompileStratego {

	private ICompilerInputPaths cip;
	private ICompilerPaths icp;
	private StrategoCompilerConfig strategoCompilerConfig;
	
	public CompileStratego(ICompilerInputPaths cip, ICompilerPaths icp, StrategoCompilerConfig config) {
		this.cip = cip;
		this.icp = icp;
		this.strategoCompilerConfig = config;
	}
	
	public String getMainStrategoFileLocation() throws IOException {
		// $temp/$module-name/$mode/stratego
		File s = new File(icp.getStrategoDirectory(), cip.getStrategoSourceFile().getPath());
		return s.getCanonicalPath();
	}
	
	public String getStrjOutputFileLocation() throws IOException {
		// ${java-src.gen.dir}/${package-root-dir}/Main.java
		File packagePath = strategoCompilerConfig.getPackageDirectory();
		File dir = new File(icp.getJavaDirectory(), packagePath.getPath());
		File main = new File(dir, "Main.java");
		return main.getCanonicalPath();
	}
	
	public String getLibraryName() {
		return strategoCompilerConfig.getLibraryJavaPackage();
	}

	
	public String[] getDefaultIncludes() throws IOException {
		String[] includes = new String[] {
				icp.getStrategoDirectory().getCanonicalPath(),
				// include the rtree of the stratego debug runtime
				strategoCompilerConfig.getDebugRuntimeRtreeLocation().getParent()
//				getStrategoDebugRuntimeRtreeLocation() // TODO: must this be a directory or file location?
		};
		return includes;
	}
	
	/**
	 * Get user specified includes
	 * @return
	 */
	public String[] getUserIncludes() {
		String[] includes = new String[] {
		};
		return includes;
	}

	public String[] getDefaultLibraries() {
		String[] libraries = new String[] {
				"stratego-lib",
				"stratego-sglr", 
				"stratego-gpp",
				"stratego-xtc",
				"stratego-aterm",
				"java-front",
				// include the stratego debug runtime packages as imports in the generated Java
				"org.strategoxt.imp.debuggers.stratego.runtime",
				"org.strategoxt.imp.debuggers.stratego.runtime.strategies"
		};
		return libraries;
	}
	
		
	/**
	 * Get user specified libraries
	 * @return
	 */
	public String[] getUserLibraries() {
		String[] libraries = new String[] {
		};
		return libraries;
	}
	
	public boolean execute() throws IOException {
		System.out.println("Generated file at " + getMainStrategoFileLocation());
		System.out.println("Compile str to java...");
//		// compile the stratego file at $outputFilename
//		String strategodebuglib_rtree_dir = debugSessionSettings.getStrategoDebugLibraryDirectory().toOSString();
//		String javaImportName = "org.strategoxt.imp.debug.stratego.runtime.trans"; // was: "org.strategoxt.libstrategodebuglib"
//		// TODO: when compiling we may need extra arguments
//		String[] extra_args = debugSessionSettings.getCompileTimeExtraArguments();
//		
////        <java classname="org.strategoxt.strj.Main" failonerror="true">
////    	<classpath refid="strategoxt.jar.libraryclasspath" />
////        <arg value="-i"/>
////        <arg value="${str-src.dir}/${strmodule-name}.str"/>
////        <arg value="-o"/>
////    	<arg value="${build.stratego.outputfile}"/>
////    	<!-- argument p is used to specify the name of the java package -->
////        <arg value="-p"/>
////        <arg value="${package-root-name}"/>
////        <arg value="--library"/>
////        <arg value="--clean"/>
////        <arg line="${build.stratego.args}"/>
////        <arg line="${build.stratego.extraargs}"/>
////<!--            <arg line="${externaljarflags}"/>-->
////<!--            <arg line="${externaldefimport}"/>-->
////        <arg line="-I &quot;${str-lib.dir}&quot; -I &quot;${str-include.dir}&quot; --cache-dir &quot;${basedir}/.cache&quot;"/>
////    </java>
//
//		//		"-I", "/home/rlindeman/Documents/TU/strategoxt/spoofax-imp/source/org.strategoxt.imp.debug.stratego.transformer/"
//
		String[] basic_strj_args = new String[] {
			"-i", 	getMainStrategoFileLocation()
			, "-o", getStrjOutputFileLocation() // output will be java, so folders should match the library name
//			, "-I", strategodebuglib_rtree_dir // location of rtree files
			, "-p", getLibraryName() // will be the package name
//			//, "--silent"
			, "--clean" // remove previous java
//			, "-la", javaImportName // used as java import
		};
//		// Combine the basic_strj_args and the extra_I_args
		String[] includes = createIncludeArguments(); // -I
		String[] lib_args = createLibraryArguments(); // -la
		String[] extra_args = this.strategoCompilerConfig.getExtraArgs();
		
		String[] strj_args = basic_strj_args;
		strj_args = StringUtil.concat(strj_args, includes);
		strj_args = StringUtil.concat(strj_args, lib_args);
		strj_args = StringUtil.concat(strj_args, extra_args);
		
		
		boolean success = false;
		org.strategoxt.lang.Context c = org.strategoxt.strj.Main.init();
		CustomIOAgent ioAgent = new CustomIOAgent();
		c.setIOAgent(ioAgent);
		long startTime = System.currentTimeMillis();
		long finishTime = -1;
		try {
			// TODO: can we forward the error log messages?
			
			this.used_strj_args = Arrays.copyOf(strj_args, strj_args.length);
			IStrategoTerm term = org.strategoxt.strj.Main.mainNoExit(c, strj_args);
			System.out.println("result: " + term.toString());
			success = true; // also success, the program did not call the exit strategy
			// TODO: also true when term==null?
		}
		catch(StrategoExit e)
		{
			if (e.getValue() == StrategoExit.SUCCESS)
			{
				success = true;
			}
			else
			{
				System.out.println("Exception: " + e.getMessage());
				String message = "Failed to compile stratego program to java. \n" + ioAgent.getStderr().trim();
				System.out.println(message);
				System.out.println("Command line arguments");
				String line = "";
				for (String string : strj_args) {
					line += string + " ";
				}
				System.out.println(line);
//				DebugCompileException de = new DebugCompileException(message, e);
//				de.setStdErrContents(ioAgent.getStderr());
//				throw de;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
			String message = "Failed to compile stratego program to java. \n" + ioAgent.getStderr().trim();
			System.out.println(message);
//			DebugCompileException de = new DebugCompileException(message, e);
//			de.setStdErrContents(ioAgent.getStderr());
//			throw de;
			
		}
		finally
		{
			finishTime = System.currentTimeMillis();
			long duration = finishTime - startTime;
//			this.debugCompileProgress.setCompileStrategoDuration(duration);
			System.out.println("Stratego to Java compiler finished in " + duration +" ms.");
		}
		
		
		System.out.println("Strj compiler finished.");
		String s = ioAgent.getStderr();
		System.out.println("ERR:");
		System.out.println(s);
		
		String s2 = ioAgent.getStdout();
		System.out.println("OUT:");
		System.out.println(s2);
		
		return success;

	}

	private String[] createDefaultIncludeArguments() throws IOException {
		String[] defaultIncludes = getDefaultIncludes();
		// args size should be twice as big, every include requires a "-I"
		String[] args = new String[2*defaultIncludes.length];
		for(int i = 0; i < defaultIncludes.length; i++){
			int indexOfMinusI = 2*i;
			args[indexOfMinusI] = "-I";
			args[indexOfMinusI+1] = defaultIncludes[i];
		}
		return args;
	}
	
	private String[] createUserIncludeArguments() {
//		String[] includes = getUserIncludes();
//		String[] args = new String[2*includes.length];
//		for(int i = 0; i < includes.length; i++){
//			int indexOfMinusI = 2*i;
//			args[indexOfMinusI] = "-I";
//			args[indexOfMinusI+1] = includes[i];
//		}
//		return args;
		return StringUtil.insertBeforeEach(getUserIncludes(), "-I");
}
	
	private String[] createIncludeArguments() throws IOException {
		String[] defaultIncludes = createDefaultIncludeArguments();
		String[] userIncludes = createUserIncludeArguments();
		
		String[] includes = StringUtil.concat(defaultIncludes, userIncludes);
		return includes;
	}
	
	private String[] createLibraryArguments() {
		String[] libs = StringUtil.concat(getDefaultLibraries(), getUserLibraries());
		return StringUtil.insertBeforeEach(libs, "-la");
	}
	
	private String[] used_strj_args = null;
	/**
	 * Returns the arguments that were used to call strj.
	 * @return
	 */
	public String[] getArguments() {
		return used_strj_args;
	}
}
