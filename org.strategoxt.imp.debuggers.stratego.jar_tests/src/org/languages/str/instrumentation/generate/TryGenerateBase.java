package org.languages.str.instrumentation.generate;

import java.io.File;

import junit.framework.Assert;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.DebugInfoBuilder;
import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class TryGenerateBase {

	protected HybridInterpreter i = null;
	
	protected void callTryGenerate(String generateStrategyName, IStrategoTerm current) {
		boolean b;
		
		// call ext-register-gen-strategy(gen-strategy|strategy-name)
		// the strategy ignores the current term
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(generateStrategyName));
		i.getContext().getVarScope().addSVar("s0", i.lookupUncifiedSVar(generateStrategyName));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_register_gen_strategy_1_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		i.getContext().getVarScope().removeSVar("s0");
		
		// it should now exist
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(generateStrategyName));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_exists_gen_strategy_0_1");
		Assert.assertTrue(b); // Found extract transformation: extract-function-info
		i.getContext().getVarScope().removeVar("t0");
		
		// call try-generate(|generate-name) = ?aterm;
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(generateStrategyName));
		i.setCurrent(current); // FunctionDeclVoid should be current term
		b = HybridInterpreterHelper.safeInvoke(i, "try_generate_0_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
	}
	
	protected void initCurrentDebugInformation(DebugInfoBuilder builder) {
		// debug-information := (filename, a, b, c, d, name)
		boolean b = false;
//		DebugInfoBuilder builder = new DebugInfoBuilder();
//		builder.setFilename("str_scripts/testcases/fragments/fargs/strategydef.str");
//		builder.setLocation(4, 4, 4, 12);
//		builder.setData("main");
		IStrategoTerm currentDebugInformation = builder.makeEnter(i.getFactory());
		i.setCurrent(currentDebugInformation);
		b = HybridInterpreterHelper.safeInvoke(i, "SET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);

	}
	
	protected static void setupHI(HybridInterpreter i, String program_location, String dsldi_location) {
		//String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/tiny/tiny.str";
		File file = new File(program_location);
		Assert.assertTrue(file.exists());
		
		//String dsldiLocation = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterStrategy.dsldi";
		String dsldiLocation = dsldi_location;
		File selFile = new File(dsldiLocation);
		Assert.assertTrue(selFile.exists());

//		// init the HybridInterpreter
//		i = HybridInterpreterHelper.createHybridInterpreter();
		
		// load the dlsdi
		// read the SEL-definition and make sure it is available via GET-SEL-DEFINITION
		i.setCurrent(i.getFactory().makeString(dsldiLocation));
		boolean b = HybridInterpreterHelper.safeInvoke(i, "read-dsldi-file");
		Assert.assertTrue(b);
		// is SEL the current term?
		Assert.assertEquals(IStrategoTerm.APPL, i.current().getTermType());
		Assert.assertEquals("SEL", ((IStrategoAppl)i.current()).getName());
		b = HybridInterpreterHelper.safeInvoke(i, "SET-SEL-DEFINITION");
		
		// SET-DSL-PROGRAM-FILENAME
		i.setCurrent(i.getFactory().makeString(program_location));
		b = HybridInterpreterHelper.safeInvoke(i, "SET-DSL-PROGRAM-FILENAME");
		Assert.assertTrue(b);
		
		// load justprint.alng
		i.setCurrent(i.getFactory().makeString(program_location));
		b = HybridInterpreterHelper.safeInvoke(i, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);
		Assert.assertTrue(b);

		// SET-DSL-PROGRAM-SOURCE
		b = HybridInterpreterHelper.safeInvoke(i, "SET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
	}
}
