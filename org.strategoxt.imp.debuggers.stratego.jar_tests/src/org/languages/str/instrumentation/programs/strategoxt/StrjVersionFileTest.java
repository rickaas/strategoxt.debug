package org.languages.str.instrumentation.programs.strategoxt;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class StrjVersionFileTest {
	
	protected HybridInterpreter i = null;
	protected String singleDSLProgram = null;
	protected String baseDir = null;
	protected void initHI() {
		i = HybridInterpreterHelper.createHybridInterpreter();
	}
	protected String getDslProgramBasePath() {
		return baseDir;
	}
	protected String getDslProgramPath() {
		return singleDSLProgram;
	}
	protected String getGeneratedLocation() {
		return StrTestConstants.STR_SCRIPTS_GENERATED_DIR +"/" + getDslProgramBasePath();
	}
	
	protected String getInstrumentedLocation() {
		return StrTestConstants.STR_SCRIPTS_INSTRUMENTED_DIR +"/" + getDslProgramBasePath();
	}
	
	/**
	 * Initialize the debug-instrumentation with DSL specific constants.
	 */
	public void setup() {
		initHI();

		i.setCurrent(i.getFactory().makeString("foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "init-config"));
		
		// <set-config> ("--input-dir",)
		String dslProgramBasePath = getDslProgramBasePath();
		i.setCurrent(makeConfigTuple("--input-dir", dslProgramBasePath));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		String dslProgramPath = getDslProgramPath();
		i.setCurrent(makeConfigTuple("--input-file", dslProgramPath));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// <set-config> ("--output-dir",)
		String output = getGeneratedLocation() + ".str";
		i.setCurrent(makeConfigTuple("--output-dir", output));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		// <set-config> ("--fake-run",)
		//i.setCurrent(makeConfigTuple("--fake-run", "true"));
		//Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		// <set-config> ("--pipe",)
		i.setCurrent(makeConfigTuple("--pipe", "true"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		i.setCurrent(i.getFactory().makeInt(10)); // vomit
		//i.setCurrent(i.getFactory().makeInt(4)); // debug
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-verbosity"));

		i.setCurrent(makeConfigTuple("--statistics", 1));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		i.setCurrent(makeConfigTuple("--report-failed-files", getGeneratedLocation()+"/failed.txt"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		i.setCurrent(makeConfigTuple("--report-succeeded-files", getGeneratedLocation()+"/succeeded.txt"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
	}
	
	protected IStrategoTerm makeConfigTuple(String key, String value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeString(value));
	}
	protected IStrategoTerm makeConfigTuple(String key, int value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeInt(value));
	}
	@Test
	public void testStrjVersion() throws Exception {
		String base = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments";
		String dir = "strategoxt/strategoxt-java-backend/trans";
		baseDir = base;
		singleDSLProgram = dir + "/strj-version.str";
		setup();
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
	}
	@Test
	public void testQconsTest03() {
		String base = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments";
		String dir = "strategoxt/strategoxt-java-backend/test/strc1";
		baseDir = base;
		singleDSLProgram = dir + "/qcons_test03.str";
		setup();
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
	}
}
