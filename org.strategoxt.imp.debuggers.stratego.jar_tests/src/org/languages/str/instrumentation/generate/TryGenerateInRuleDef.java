package org.languages.str.instrumentation.generate;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import util.DebugInfoBuilder;
import util.HybridInterpreterHelper;
import util.StrTestConstants;
import util.str_scripts.RuledefScript;

public class TryGenerateInRuleDef extends TryGenerateBase {

	private String sourceLocation = null;
	
	protected String getInstrumented(String postfix) {
		return StrTestConstants.STR_SCRIPTS_INSTRUMENTED_DIR + "/" + sourceLocation + postfix;
	}
	
	@Before
	public void setupProgram() {
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();

		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/ruledef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterRule.dsldi";
		TryGenerateBase.setupHI(i, program_location, dsldi_location);
	}
	
	@Test
	public void testRDefNoArgs() {
		
		sourceLocation = "str_scripts/testcases/fragments/fargs/ruledef.str";
		
		boolean b = false;
		
		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename(sourceLocation);
		builder.setLocation(5, 2, 8, 20);
		builder.setData("main");
		this.initCurrentDebugInformation(builder);
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		RuledefScript program = new RuledefScript();
		program.init(i.current());
		
		callTryGenerate(StrTestConstants.GEN_RULE_ENTER, program.getRuleRDefNoArgs());
		
		// instrumented version
		RuledefScript instrumentedProgram = new RuledefScript();
		String instrumented_program_location = getInstrumented(".enter.str");
		instrumentedProgram.initFromFile(instrumented_program_location, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);

		Assert.assertEquals(instrumentedProgram.getRuleRDefNoArgs().toString(), i.current().toString());
	}
	
	@Test
	public void testRDefS() {

		sourceLocation = "str_scripts/testcases/fragments/fargs/ruledef.str";

		boolean b = false;

		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := (filename, a, b, c, d, name)
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename(sourceLocation);
		builder.setLocation(10, 2, 13, 20);
		builder.setData("main");
		this.initCurrentDebugInformation(builder);

		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		RuledefScript program = new RuledefScript();
		program.init(i.current());
		
		callTryGenerate(StrTestConstants.GEN_RULE_ENTER, program.getRuleRDefS());
		
		// instrumented version
		RuledefScript instrumentedProgram = new RuledefScript();
		String instrumented_program_location = getInstrumented(".enter.str");
		instrumentedProgram.initFromFile(instrumented_program_location, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);

		Assert.assertEquals(instrumentedProgram.getRuleRDefS().toString(), i.current().toString());

	}
	
	@Test
	public void testSDefT() {

		sourceLocation = "str_scripts/testcases/fragments/fargs/ruledef.str";
		
		boolean b = false;

		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := (filename, a, b, c, d, name)
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename(sourceLocation);
		builder.setLocation(15, 2, 18, 20);
		builder.setData("main");
		this.initCurrentDebugInformation(builder);

		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		RuledefScript program = new RuledefScript();
		program.init(i.current());
		
		callTryGenerate(StrTestConstants.GEN_RULE_ENTER, program.getRuleRDefT());
		
		// instrumented version
		RuledefScript instrumentedProgram = new RuledefScript();
		String instrumented_program_location = getInstrumented(".enter.str");
		instrumentedProgram.initFromFile(instrumented_program_location, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);

		Assert.assertEquals(instrumentedProgram.getRuleRDefT().toString(), i.current().toString());

	}

}
