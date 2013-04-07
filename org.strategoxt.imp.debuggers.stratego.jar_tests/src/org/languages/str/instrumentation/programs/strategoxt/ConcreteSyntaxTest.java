package org.languages.str.instrumentation.programs.strategoxt;

import junit.framework.Assert;

import org.junit.Test;
import org.languages.str.instrumentation.programs.ProgramInstrumentationBase;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class ConcreteSyntaxTest extends ProgramInstrumentationBase {

	protected String getDsldiLocation() {
		return "str_scripts/dsldis/Stratego.dsldi";
		//return "str_scripts/dsldis/RuleDefEnterExit.dsldi";
		//return "str_scripts/dsldis/EnterRule.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/cs-syntax";
	}
	
	@Test
	public void instrumentSplitLargeStrategies() {
		
		i.setCurrent(this.makeConfigTuple("--input-file", "split-large-strategies.str"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		Assert.assertEquals(1, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
		
	}
}
