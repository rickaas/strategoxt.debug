package org.languages.str.instrumentation.generate;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import util.DebugInfoBuilder;
import util.HybridInterpreterHelper;
import util.StrTestConstants;
import util.str_scripts.StrategydefScript;

public class TryGenerateInStrategyDef extends TryGenerateBase {

	@Before
	public void setupProgram() {
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();

		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str";
		String dsldi_location = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterStrategy.dsldi";
		TryGenerateBase.setupHI(i, program_location, dsldi_location);
	}
	
	@Test
	public void testSDefNoArgs() {
		
		boolean b = false;
		
		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/strategydef.str");
		builder.setLocation(4, 4, 4, 12);
		builder.setData("main");
		this.initCurrentDebugInformation(builder);
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		StrategydefScript program = new StrategydefScript();
		program.init(i.current());
		
		callTryGenerate(StrTestConstants.GEN_STRATEGY_ENTER, program.getStrategySDefNoArgs());
		
		// instrumented version
		StrategydefScript instrumentedProgram = new StrategydefScript();
		String instrumented_program_location = StrTestConstants.STR_SCRIPTS_INSTRUMENTED_DIR + "/" + StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str.enter.str";
		instrumentedProgram.initFromFile(instrumented_program_location, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);

		Assert.assertEquals(instrumentedProgram.getStrategySDefNoArgs().toString(), i.current().toString());
	}
	
	@Test
	public void testSDefS() {

		boolean b = false;

		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := (filename, a, b, c, d, name)
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/strategydef.str");
		builder.setLocation(6, 4, 6, 15);
		builder.setData("main");
		this.initCurrentDebugInformation(builder);

		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		StrategydefScript program = new StrategydefScript();
		program.init(i.current());
		
		callTryGenerate(StrTestConstants.GEN_STRATEGY_ENTER, program.getStrategySDefS());
		
		// instrumented version
		StrategydefScript instrumentedProgram = new StrategydefScript();
		String instrumented_program_location = StrTestConstants.STR_SCRIPTS_INSTRUMENTED_DIR + "/" + StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str.enter.str";
		instrumentedProgram.initFromFile(instrumented_program_location, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);

		Assert.assertEquals(instrumentedProgram.getStrategySDefS().toString(), i.current().toString());

	}
	
	@Test
	public void testSDefT() {

		boolean b = false;

		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := (filename, a, b, c, d, name)
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/fragments/fargs/strategydef.str");
		builder.setLocation(8, 4, 8, 17);
		builder.setData("main");
		this.initCurrentDebugInformation(builder);

		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(b);
		
		StrategydefScript program = new StrategydefScript();
		program.init(i.current());
		
		callTryGenerate(StrTestConstants.GEN_STRATEGY_ENTER, program.getStrategySDefT());
		
		// instrumented version
		StrategydefScript instrumentedProgram = new StrategydefScript();
		String instrumented_program_location = StrTestConstants.STR_SCRIPTS_INSTRUMENTED_DIR + "/" + StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/strategydef.str.enter.str";
		instrumentedProgram.initFromFile(instrumented_program_location, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);

		Assert.assertEquals(instrumentedProgram.getStrategySDefT().toString(), i.current().toString());

	}
}
