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

public class ProgramInstrument005 extends CompilerWrapperBase {
	private String outputBasePath = StrTestConstants.STR_SCRIPTS_GENERATED_DIR + "/programs/005_multiplefiles.work";
	private String dslProgramBasepath = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/005_multiplefiles";
	
	@Test
	public void testStrategoCompilerWrapper() throws IOException {

		ICompilerInputPaths cip = super.getICompilerInputPaths(dslProgramBasepath, "start.str", outputBasePath);
		ICompilerPaths compilerPaths = super.getICompilerPaths("debug");
		
		JavaCompilerConfig javaCompilerConfig = super.getJavaCompilerConfig();
		StrategoCompilerConfig strategoCompilerConfig = super.getStrategoCompilerConfig("foopackage");
		strategoCompilerConfig.setExtraArgs(new String[] {"--main", "start-it"});
		
		StrategoCompilerWrapper compiler = new StrategoCompilerWrapper(cip, compilerPaths, strategoCompilerConfig, javaCompilerConfig, super.getHIP());
		compiler.compileForDebug();
		
		String instStratego = compiler.getICompilerPaths().getStrategoDirectory().getPath();
		Assert.assertEquals("str_scripts/generated/programs/005_multiplefiles.work/debug/stratego", instStratego);
		
		String commandline = getJavaCommandline();
		System.out.println(commandline);
		try {
			ExternalJavaRunner.run(commandline);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
