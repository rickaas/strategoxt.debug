package org.languages.str.instrumentation.compilerwrapper;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.strategoxt.imp.debuggers.stratego.instrumentation.StrategoCompilerWrapper;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ExternalJavaRunner;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerInputPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ICompilerPaths;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.JavaCompilerConfig;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.StrategoCompilerConfig;

import util.StrTestConstants;

public class ProgramInstrument001 extends CompilerWrapperBase {

	private String outputBasePath = StrTestConstants.STR_SCRIPTS_GENERATED_DIR + "/programs/001_tiny.work";
	private String dslProgramBasepath = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny";
	
	@Test
	public void testStrategoCompilerWrapper() throws IOException {

		ICompilerInputPaths cip = super.getICompilerInputPaths(dslProgramBasepath, "tiny.str", outputBasePath);
		ICompilerPaths compilerPaths = super.getICompilerPaths("debug");
		
		
		JavaCompilerConfig javaCompilerConfig = super.getJavaCompilerConfig();
		StrategoCompilerConfig strategoCompilerConfig = super.getStrategoCompilerConfig("foopackage");
		
		StrategoCompilerWrapper compiler = new StrategoCompilerWrapper(cip, compilerPaths, strategoCompilerConfig, javaCompilerConfig, super.getHIP());
		compiler.compileForDebug();
		
		String instStratego = compiler.getICompilerPaths().getStrategoDirectory().getPath();
		Assert.assertEquals("str_scripts/generated/programs/001_tiny.work/debug/stratego", instStratego);
		
		String commandline = getJavaCommandline();
		System.out.println(commandline);
		try {
			ExternalJavaRunner.run(commandline);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		/*
java 
-cp 
/home/rlindeman/Applications/development/eclipse/helios_sdk/workspace/org.languages.str.jar_tests/lib/instrumentation/strategoxt.jar
 * :/home/rlindeman/Applications/development/eclipse/helios_sdk/workspace/org.languages.str.jar_tests/lib/stratego/org.strategoxt.imp.debuggers.stratego.runtime.jar
 * :/home/rlindeman/Applications/development/eclipse/helios_sdk/workspace/org.languages.str.jar_tests/lib/runtime/org.spoofax.debug.java.library.jar
 * :/home/rlindeman/Applications/development/eclipse/helios_sdk/workspace/org.languages.str.jar_tests/str_scripts/generated/programs/001_tiny/tiny.str/debug/bin

foopackage.Main
		*/
		
	}
	

}
