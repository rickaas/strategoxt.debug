package util.str_scripts;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.SELUtil;
import util.StrTestConstants;

public class StrategydefScript extends ParsedProgramBase {

	@Test
	public void testParsing()
	{
		this.init();
		Assert.assertNotNull(this.getModule());
		Assert.assertEquals("Module", SELUtil.getConstructor(this.getModule()));
		Assert.assertEquals("Module", SELUtil.getSort(this.getModule()));
		
		Assert.assertNotNull(this.getRules());
		Assert.assertEquals("Strategies", SELUtil.getConstructor(this.getRules()));
		Assert.assertEquals("Decl", SELUtil.getSort(this.getRules()));
		
		Assert.assertNotNull(this.getStrategySDefNoArgs());
		Assert.assertEquals("SDefNoArgs", SELUtil.getConstructor(this.getStrategySDefNoArgs()));
		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getStrategySDefNoArgs()));
		
		Assert.assertNotNull(this.getStrategySDefS());
		Assert.assertEquals("SDef", SELUtil.getConstructor(this.getStrategySDefS()));
		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getStrategySDefS()));
		
		Assert.assertNotNull(this.getStrategySDefT());
		Assert.assertEquals("SDefT", SELUtil.getConstructor(this.getStrategySDefT()));
		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getStrategySDefT()));
	}
	
	public IStrategoTerm getStrategySDefNoArgs() {
		check();
		IStrategoTerm strategies = getRules(); // Rules( [ SDefNoArgs, SDef, SDefT ] )
		IStrategoTerm strategiesList = strategies.getSubterm(0);
		IStrategoTerm mainSDefT = strategiesList.getSubterm(0);
		return mainSDefT;
	}
	
	public IStrategoTerm getStrategySDefS() {
		check();
		IStrategoTerm strategies = getRules(); // Rules( [ SDefNoArgs, SDef, SDefT ] )
		IStrategoTerm strategiesList = strategies.getSubterm(0);
		IStrategoTerm mainSDefT = strategiesList.getSubterm(1);
		return mainSDefT;
	}

	public IStrategoTerm getStrategySDefT() {
		check();
		IStrategoTerm strategies = getRules(); // Rules( [ SDefNoArgs, SDef, SDefT ] )
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
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str";
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
