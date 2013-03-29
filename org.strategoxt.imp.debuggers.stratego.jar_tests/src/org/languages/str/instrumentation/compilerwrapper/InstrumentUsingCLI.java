package org.languages.str.instrumentation.compilerwrapper;

import org.junit.Test;
import org.strategoxt.imp.debuggers.stratego.instrumentation.CommandLineCompiler;

import util.StrTestConstants;

public class InstrumentUsingCLI {

	//@Test
	public void testName() throws Exception {
		String[] args = new String[] {
				"--input-dir", "str_scripts/testcases",
				"--output-dir","str_scripts/generated/cli",
				"--fake-run", "true",
				"--jar", StrTestConstants.LIBDSLDI_JAR,
				"--jar", StrTestConstants.DSLDI_JAVA_JAR,
				"--jar", StrTestConstants.STRATEGOSUGAR_JAR,
				"--jar", StrTestConstants.STRATEGO_DI_JAR,
		};
		CommandLineCompiler.main(args);
	}
	
	public static final String STRATEGOXT = "/home/rlindeman/Documents/TU/strategoxt/git-stuff/only-stratego-files";
	
	@Test
	public void testStrategoSource() throws Exception {
		String[] args = new String[] {
				"--input-dir", STRATEGOXT,
				"--output-dir","str_scripts/generated/cli/strategoxt",
//				"--fake-run", "true",
				"--jar", StrTestConstants.LIBDSLDI_JAR,
				"--jar", StrTestConstants.DSLDI_JAVA_JAR,
				"--jar", StrTestConstants.STRATEGOSUGAR_JAR,
				"--jar", StrTestConstants.STRATEGO_DI_JAR,
		};
		CommandLineCompiler.main(args);
	}
}
