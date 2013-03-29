package util.str_scripts;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.SELUtil;
import util.StrTestConstants;

public class RuledefScript extends ParsedProgramBase {

	@Test
	public void testParsing()
	{
		this.init();
		Assert.assertNotNull(this.getModule());
		Assert.assertEquals("Module", SELUtil.getConstructor(this.getModule()));
		Assert.assertEquals("Module", SELUtil.getSort(this.getModule()));
		
		Assert.assertNotNull(this.getRules());
		Assert.assertEquals("Rules", SELUtil.getConstructor(this.getRules()));
		Assert.assertEquals("Decl", SELUtil.getSort(this.getRules()));
		
		Assert.assertNotNull(this.getRuleRDefNoArgs());
		Assert.assertEquals("RDefNoArgs", SELUtil.getConstructor(this.getRuleRDefNoArgs()));
		Assert.assertEquals("RuleDef", SELUtil.getSort(this.getRuleRDefNoArgs()));
		
		Assert.assertNotNull(this.getRuleRDefS());
		Assert.assertEquals("RDef", SELUtil.getConstructor(this.getRuleRDefS()));
		Assert.assertEquals("RuleDef", SELUtil.getSort(this.getRuleRDefS()));
		
		Assert.assertNotNull(this.getRuleRDefT());
		Assert.assertEquals("RDefT", SELUtil.getConstructor(this.getRuleRDefT()));
		Assert.assertEquals("RuleDef", SELUtil.getSort(this.getRuleRDefT()));
	}
	
	public IStrategoTerm getRuleRDefNoArgs() {
		check();
		IStrategoTerm strategies = getRules(); // Rules( [ RDefNoArgs, RDef, RDefT ] )
		IStrategoTerm strategiesList = strategies.getSubterm(0);
		IStrategoTerm mainSDefT = strategiesList.getSubterm(0);
		return mainSDefT;
	}
	
	public IStrategoTerm getRuleRDefS() {
		check();
		IStrategoTerm strategies = getRules(); // Rules( [ RDefNoArgs, RDef, RDefT ] )
		IStrategoTerm strategiesList = strategies.getSubterm(0);
		IStrategoTerm mainSDefT = strategiesList.getSubterm(1);
		return mainSDefT;
	}

	public IStrategoTerm getRuleRDefT() {
		check();
		IStrategoTerm strategies = getRules(); // Rules( [ RDefNoArgs, RDef, RDefT ] )
		IStrategoTerm strategiesList = strategies.getSubterm(0);
		IStrategoTerm mainSDefT = strategiesList.getSubterm(2);
		return mainSDefT;
	}

	public IStrategoTerm getRules() {
		check();
		IStrategoTerm moduleContent = dslProgramAterm.getSubterm(1); // [ Rules() ]
		IStrategoTerm strategies = moduleContent.getSubterm(0); // Rules()
		return strategies;
	}

	public IStrategoTerm getModule() {
		check();
		return dslProgramAterm; // Module("tiny", [ * ] )
	}

	public void init()
	{
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/ruledef.str";
		File alngFile = new File(program_location);
		Assert.assertTrue(alngFile.exists());
		
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		
		i.setCurrent(i.getFactory().makeString(program_location));
		boolean b = HybridInterpreterHelper.safeInvoke(i, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);
		Assert.assertTrue(b);
		this.dslProgramAterm = i.current();
		
		i.uninit();
	}
	
	private void check()
	{
		if (this.dslProgramAterm == null) {
			this.init();
		}
	}
}
