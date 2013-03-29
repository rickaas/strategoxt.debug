package org.languages.str.instrumentation.extract;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import util.DebugInfoBuilder;
import util.HybridInterpreterHelper;
import util.StrTestConstants;
import util.str_scripts.RuledefScript;
import util.str_scripts.StrategydefScript;

public class TryExtractFromStrategyDef extends TryExtractBase {

	@Before
	public void setupProgram() {
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
	}
	
	@Test
	public void testSDefNoArgs() {
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterStrategy.dsldi";
		TryExtractBase.setupHI(i, program_location, dsldi_location);
		
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		StrategydefScript program = new StrategydefScript();
		program.init(i.current());
		
		callTryExtract(StrTestConstants.EXTRACT_STRATEGY_INFO, program.getStrategySDefNoArgs());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/strategydef.str");
		builder.setLocation(4, 4, 4, 12);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
	}
	
	@Test
	public void testSDefS() {
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterStrategy.dsldi";
		TryExtractBase.setupHI(i, program_location, dsldi_location);
		
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		StrategydefScript program = new StrategydefScript();
		program.init(i.current());
		
		callTryExtract(StrTestConstants.EXTRACT_STRATEGY_INFO, program.getStrategySDefS());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/strategydef.str");
		builder.setLocation(6, 4, 6, 15);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
	}
	
	@Test
	public void testSDefT() {
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterStrategy.dsldi";
		TryExtractBase.setupHI(i, program_location, dsldi_location);
		
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		StrategydefScript program = new StrategydefScript();
		program.init(i.current());
		
		callTryExtract(StrTestConstants.EXTRACT_STRATEGY_INFO, program.getStrategySDefT());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/strategydef.str");
		builder.setLocation(8, 4, 8, 17);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
	}
}
