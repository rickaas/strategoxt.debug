package org.languages.str.instrumentation.programs;

import junit.framework.Assert;

import org.junit.Test;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class ProgramInstrument005 extends ProgramInstrumentationBase {

	protected String getDsldiLocation() {
		return "str_scripts/dsldis/Stratego.dsldi";
		//return "str_scripts/dsldis/StrategyDefEnterExit.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/005_multiplefiles";
	}

	@Test
	public void debugInstrument() {
		
		// --report-runtime-statistics
		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "--report-runtime-statistics", "foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "--exclude-dir", new String[] {"foo"}));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		Assert.assertEquals(2, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
	}
}
