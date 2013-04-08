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
		
		i.setCurrent(this.makeConfigTuple("--verbosity", "4"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		String syntax[] = new String[] {
				"/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt/strategoxt/syntax/stratego-front/syn",
				
		};
		i.setCurrent(this.makeConfigTuple("-I", syntax));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		Assert.assertEquals(1, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
		Assert.fail("File is parsed, but inserting events still fails");
	}
	
	@Test
	public void instrumentS2j() {
		i.setCurrent(this.makeConfigTuple("--input-file", "s2j.str"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		i.setCurrent(this.makeConfigTuple("--verbosity", "4"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		String syntax[] = new String[] {
				"/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt/strategoxt/syntax/stratego-front/syn",
				"/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt/strategoxt/syntax/java-front/syntax-embedding",
		};
		i.setCurrent(this.makeConfigTuple("-I", syntax));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		boolean b = false;
		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);

		// extract filenames, returns a list of strings
		Assert.assertEquals(1, getFilenamesWithSuccess().size());
		Assert.assertEquals(0, getFilenamesWithFailure().size());
		
		Assert.fail("File is parsed, but inserting events still fails");
	}
}
