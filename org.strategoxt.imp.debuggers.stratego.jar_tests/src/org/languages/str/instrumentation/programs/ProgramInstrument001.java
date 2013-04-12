package org.languages.str.instrumentation.programs;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class ProgramInstrument001 extends ProgramInstrumentationBase {

	protected String getDsldiLocation() {
		return StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/StrategyDefEnterExit.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny";
	}
	
	@Test
	public void debugInstrument() {
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		// extract filenames, returns a list of strings
		List<String> succeeded = getFilenamesWithSuccess();
		Assert.assertEquals(1, succeeded.size());
		System.out.println(succeeded.get(0));
		Assert.assertEquals(0, getFilenamesWithFailure().size());
	}
}
