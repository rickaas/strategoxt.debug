package org.languages.str.instrumentation.programs;

import junit.framework.Assert;

import org.junit.Test;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class AllPrograms extends ProgramInstrumentationBase {
	protected String getDsldiLocation() {
		return "str_scripts/dsldis/StrategyDefEnterExit.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs";
	}

	@Test
	public void debugInstrument() {
		long start = System.currentTimeMillis();
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		long end = System.currentTimeMillis();
		long duration = end - start;
		System.out.println(duration);

		// extract filenames, returns a list of strings
		//Assert.assertEquals(6, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
	}
}
