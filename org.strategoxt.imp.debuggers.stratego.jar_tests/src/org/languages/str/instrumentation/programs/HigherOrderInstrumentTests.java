package org.languages.str.instrumentation.programs;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class HigherOrderInstrumentTests extends ProgramInstrumentationBase {

	protected String getDsldiLocation() {
		return "str_scripts/dsldis/Stratego.dsldi";
		//return "str_scripts/dsldis/RuleDefEnterExit.dsldi";
		//return "str_scripts/dsldis/EnterRule.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/higherorder";
	}
	
	@Test
	public void instrumentHigherOrder() {
		
		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "--input-file", "higher-order.str"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		List<String> succeeded = getFilenamesWithSuccess();
		Assert.assertEquals(1, succeeded.size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
		
		Assert.fail("Running this program fails due to higher-order strategies");
	}
}
