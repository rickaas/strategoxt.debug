package org.languages.str.instrumentation.programs;

import junit.framework.Assert;

import org.junit.Test;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class ProgramInstrument006 extends ProgramInstrumentationBase  {
	protected String getDsldiLocation() {
		return "str_scripts/dsldis/Stratego.dsldi";
		//return "str_scripts/dsldis/StrategyDefEnterExit.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/006_manybigfiles";
	}

	@Test
	public void debugInstrument() {
//		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "--exclude-dir", new String[] {"foo"}));
//		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// --report-runtime-statistics
		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "--report-runtime-statistics", "foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// --skip-file-instrumentation
		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "--skip-file-instrumentation", "on"));
		//Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		Assert.assertEquals(2, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
	}
}
