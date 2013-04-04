package org.languages.str.instrumentation.programs;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class RuleInstrumentTests extends ProgramInstrumentationBase {

	protected String getDsldiLocation() {
		//return "str_scripts/dsldis/Stratego.dsldi";
		return "str_scripts/dsldis/RuleDefEnterExit.dsldi";
		//return "str_scripts/dsldis/EnterRule.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/rules";
	}
	
	@Test
	public void instrumentRuleBasic() {
		
		i.setCurrent(this.makeConfigTuple("--input-file", "rule-basic.str"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		Assert.assertEquals(1, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
	}
	
	@Test
	public void instrumentRuleComplex() {
		
		i.setCurrent(this.makeConfigTuple("--input-file", "rule-complex.str"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		Assert.assertEquals(1, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
	}
	@After
	public void uninit() {
		if (this.i != null) this.i.uninit();
		if (this.i != null) this.i.reset();
		this.i = null;
	}
}
