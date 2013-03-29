package org.languages.str.instrumentation.extract;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import util.DebugInfoBuilder;
import util.HybridInterpreterHelper;
import util.StrTestConstants;
import util.str_scripts.RuledefScript;

public class TryExtractFromRuleDef extends TryExtractBase {

	@Before
	public void setupProgram() {
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
	}
	
	@Test
	public void testRDefNoArgs() {
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/ruledef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterRule.dsldi";
		TryExtractBase.setupHI(i, program_location, dsldi_location);
		
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		RuledefScript program = new RuledefScript();
		program.init(i.current());
		
		callTryExtract(StrTestConstants.EXTRACT_RULE_INFO, program.getRuleRDefNoArgs());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/ruledef.str");
		builder.setLocation(5, 2, 8, 20);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
	}
	
	@Test
	public void testRDefS() {
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/ruledef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterRule.dsldi";
		TryExtractBase.setupHI(i, program_location, dsldi_location);
		
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		RuledefScript program = new RuledefScript();
		program.init(i.current());
		
		callTryExtract(StrTestConstants.EXTRACT_RULE_INFO, program.getRuleRDefS());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/ruledef.str");
		builder.setLocation(10, 2, 13, 20);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
	}
	
	@Test
	public void testRDefT() {
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/ruledef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterRule.dsldi";
		TryExtractBase.setupHI(i, program_location, dsldi_location);
		
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		RuledefScript program = new RuledefScript();
		program.init(i.current());
		
		callTryExtract(StrTestConstants.EXTRACT_RULE_INFO, program.getRuleRDefT());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/ruledef.str");
		builder.setLocation(15, 2, 18, 20);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
	}
}
