package util.str_scripts;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.SELUtil;
import util.StrTestConstants;

public class TinyScript extends ParsedProgramBase {

	@Test
	public void testLocalvarScriptParsing()
	{
		this.init();
		Assert.assertNotNull(this.getModule());
		Assert.assertEquals("Module", SELUtil.getConstructor(this.getModule()));
		Assert.assertEquals("Module", SELUtil.getSort(this.getModule()));
		
		Assert.assertNotNull(this.getImports());
		Assert.assertEquals("Imports", SELUtil.getConstructor(this.getImports()));
		Assert.assertEquals("Decl", SELUtil.getSort(this.getImports()));
		
		Assert.assertNotNull(this.getStrategies());
		Assert.assertEquals("Strategies", SELUtil.getConstructor(this.getStrategies())); // it is a list
		Assert.assertEquals("Decl", SELUtil.getSort(this.getStrategies()));
		
		Assert.assertNotNull(this.getMain());
		Assert.assertEquals("SDefNoArgs", SELUtil.getConstructor(this.getMain()));
		Assert.assertEquals("StrategyDef", SELUtil.getSort(this.getMain()));
		
		Assert.assertNotNull(this.getMainBody());
		Assert.assertEquals("Seq", SELUtil.getConstructor(this.getMainBody()));
		Assert.assertEquals("Strategy", SELUtil.getSort(this.getMainBody()));
	}
	
	public IStrategoTerm getMainBody() {
		check();
		IStrategoTerm mainSDefT = getMain(); // SDefNoArgs("main", body )
		IStrategoTerm body = mainSDefT.getSubterm(1);
		return body;
	}

	public IStrategoTerm getMain() {
		check();
		IStrategoTerm strategies = getStrategies(); // Strategies( [ SDefNoArgs("main", body ) ] )
		IStrategoTerm strategiesList = strategies.getSubterm(0);
		IStrategoTerm mainSDefT = strategiesList.getSubterm(0);
		return mainSDefT;
	}

	public IStrategoTerm getStrategies() {
		check();
		IStrategoTerm moduleContent = dslProgramAterm.getSubterm(1); // [ Imports([Import("libstrategolib")]), Strategies() ]
		IStrategoTerm strategies = moduleContent.getSubterm(1); // Strategies()
		return strategies;
	}

	public IStrategoTerm getImports() {
		check();
		IStrategoTerm moduleContent = dslProgramAterm.getSubterm(1); // [ Imports([Import("libstrategolib")]), Strategies() ]
		IStrategoTerm imports = moduleContent.getSubterm(0); // Imports
		return imports;
	}

	public IStrategoTerm getModule() {
		check();
		return dslProgramAterm; // Module("tiny", [ * ] )
	}

	public void init()
	{
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny/tiny.str";
		File file = new File(program_location);
		Assert.assertTrue(file.exists());
		
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
