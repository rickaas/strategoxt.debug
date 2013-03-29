package org.languages.str.instrumentation.extract;

import java.io.File;

import junit.framework.Assert;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class TryExtractBase {

	protected HybridInterpreter i = null;
	
	protected void callTryExtract(String extractStrategyName, IStrategoTerm current) {
		boolean b;
		
		// call ext-register-extract-strategy(extract-strategy|strategy-name)
		// the strategy ignores the current term
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(extractStrategyName));
		i.getContext().getVarScope().addSVar("s0", i.lookupUncifiedSVar(extractStrategyName));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_register_extract_strategy_1_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		i.getContext().getVarScope().removeSVar("s0");
		
		// it should now exist
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(extractStrategyName));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_exists_extract_strategy_0_1");
		Assert.assertTrue(b); // Found extract transformation: extract-function-info
		i.getContext().getVarScope().removeVar("t0");
		
		// call try-extract(|extract-name) = ?aterm;
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(extractStrategyName));
		i.setCurrent(current);
		b = HybridInterpreterHelper.safeInvoke(i, "try_extract_0_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		Assert.assertEquals(current.toString(), i.current().toString());
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
