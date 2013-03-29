package util.str_scripts;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.SELUtil;
import util.StrTestConstants;

public class StrategycombinatorsScript extends ParsedProgramBase {

	@Test
	public void testParsing()
	{
		this.init();
		Assert.assertNotNull(this.getModule());
		Assert.assertEquals("Module", SELUtil.getConstructor(this.getModule()));
		Assert.assertEquals("Module", SELUtil.getSort(this.getModule()));
		
		Assert.assertNotNull(this.getStrategies());
		Assert.assertEquals("Strategies", SELUtil.getConstructor(this.getStrategies()));
		Assert.assertEquals("Decl", SELUtil.getSort(this.getStrategies()));

		Assert.assertNotNull(this.getSequential());
		Assert.assertEquals("SDefNoArgs", SELUtil.getConstructor(this.getSequential()));
		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getSequential()));

		Assert.assertEquals("Seq", SELUtil.getConstructor(this.getSequential().getSubterm(1)));
		Assert.assertEquals("Strategy", SELUtil.getSort(this.getSequential().getSubterm(1)));

		Assert.assertEquals("CallNoArgs", SELUtil.getConstructor(this.getSequential().getSubterm(1).getSubterm(0)));
		Assert.assertEquals("Strategy", SELUtil.getSort(this.getSequential().getSubterm(1).getSubterm(0)));

		Assert.assertEquals("Seq", SELUtil.getConstructor(this.getSequential().getSubterm(1).getSubterm(1)));
		Assert.assertEquals("Strategy", SELUtil.getSort(this.getSequential().getSubterm(1).getSubterm(1)));

		
//		Assert.assertNotNull(this.getStrategySDefNoArgs());
//		Assert.assertEquals("SDefNoArgs", SELUtil.getConstructor(this.getStrategySDefNoArgs()));
//		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getStrategySDefNoArgs()));
//		
//		Assert.assertNotNull(this.getStrategySDefS());
//		Assert.assertEquals("SDef", SELUtil.getConstructor(this.getStrategySDefS()));
//		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getStrategySDefS()));
//		
//		Assert.assertNotNull(this.getStrategySDefT());
//		Assert.assertEquals("SDefT", SELUtil.getConstructor(this.getStrategySDefT()));
//		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getStrategySDefT()));
	}
	
	public IStrategoTerm getModule() {
		check();
		return dslProgramAterm; // Module("tiny", [ * ] )
	}
	
	public IStrategoTerm getStrategies() {
		check();
		IStrategoTerm moduleContent = dslProgramAterm.getSubterm(1); // [ Strategies() ]
		IStrategoTerm strategies = moduleContent.getSubterm(0); // Strategies()
		return strategies;
	}
	
	public IStrategoTerm getSequential() {
		check();
		IStrategoTerm strategies = this.getStrategies();
		IStrategoTerm list = strategies.getSubterm(0); // Strategies([...])
		IStrategoTerm sdef = list.getSubterm(0);
		return sdef;
	}

	public void init()
	{
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/statements/strategycombinators.str";
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
