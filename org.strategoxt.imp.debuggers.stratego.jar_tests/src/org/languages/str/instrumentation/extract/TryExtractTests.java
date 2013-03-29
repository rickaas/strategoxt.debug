package org.languages.str.instrumentation.extract;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.stratego.SDefT;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.DebugInfoBuilder;
import util.HybridInterpreterHelper;
import util.StrTestConstants;
import util.str_scripts.TinyScript;

public class TryExtractTests extends TryExtractBase {

	
	/**
	 * Load the FunctionDeclVoid.dsldi.
	 * Load justprint.alng, this is the current term.
	 * The filename is available via GET-DSL-PROGRAM-FILENAME.
	 * The original program aterm is available via GET-DSL-PROGRAM-SOURCE.
	 * The SEL-definition is available via GET-SEL-DEFINITION.
	 */
	@Before
	public void setupProgram()
	{
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny/tiny.str";
		File file = new File(program_location);
		Assert.assertTrue(file.exists());
		
		String dsldiLocation = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/EnterStrategy.dsldi";
		File selFile = new File(dsldiLocation);
		Assert.assertTrue(selFile.exists());

		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
		
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
	
	/**
	 * Call the extract-function-info directly
	 * @throws InterpreterException
	 */
	@Test
	public void callAlngExtractFunctionInfo() throws InterpreterException
	{
		boolean b = false;

		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		TinyScript program = new TinyScript();
		program.init(i.current());

		
		// call extract-function-info to get debug-information
		//     extract-function-info: f -> debug-information
		Assert.assertNotNull(i.lookupUncifiedSVar(StrTestConstants.EXTRACT_STRATEGY_INFO));
		// current term should be a FunctionDeclVoid 
		i.setCurrent(program.getMain());
		b = HybridInterpreterHelper.safeInvoke(i, StrTestConstants.EXTRACT_STRATEGY_INFO);
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/programs/001_tiny/tiny.str");
		builder.setLocation(7, 4, 7, 32);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
		// debug-information := [filename, name, a, b, c, d]
		
		
	}
	
	@Test
	public void callTryExtractFails() throws InterpreterException {
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		TinyScript program = new TinyScript();
		program.init(i.current());
		
		// now try to call the extract function via it's name using: try-extract(|extract-name) = ?aterm;
		Assert.assertNotNull(i.getContext().getVarScope().lookupSVar("try_extract_0_1"));
		SDefT sdeft = i.getContext().getVarScope().lookupSVar("try_extract_0_1");
		Assert.assertTrue(sdeft.isCompiledStrategy());
		Assert.assertNotNull(sdeft.getTermParams());
		Assert.assertEquals(1, sdeft.getTermParams().length);
		Assert.assertEquals("t0", sdeft.getTermParams()[0]);
		Assert.assertNull(i.getContext().lookupVar("t0"));
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(StrTestConstants.EXTRACT_STRATEGY_INFO));
		Assert.assertNotNull(i.getContext().lookupVar("t0"));

		
		i.setCurrent(program.getMain()); // FunctionDeclVoid should be current term
		b = HybridInterpreterHelper.safeInvoke(i, "try_extract_0_1");
		Assert.assertFalse(b); // Could not find extract transformation: extract-function-info, we need to register it
		i.getContext().getVarScope().removeVar("t0");
	}
	
	@Test
	public void callTryExtractOnRegisteredStrategy() {
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		TinyScript program = new TinyScript();
		program.init(i.current());
		

		// call ext-register-extract-strategy(extract-strategy|strategy-name)
		// the strategy ignores the current term
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(StrTestConstants.EXTRACT_STRATEGY_INFO));
		i.getContext().getVarScope().addSVar("s0", i.lookupUncifiedSVar(StrTestConstants.EXTRACT_STRATEGY_INFO));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_register_extract_strategy_1_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		i.getContext().getVarScope().removeSVar("s0");
		
		// it should now exist
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(StrTestConstants.EXTRACT_STRATEGY_INFO));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_exists_extract_strategy_0_1");
		Assert.assertTrue(b); // Found extract transformation: extract-function-info
		i.getContext().getVarScope().removeVar("t0");
		
		// call try-extract(|extract-name) = ?aterm;
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(StrTestConstants.EXTRACT_STRATEGY_INFO));
		i.setCurrent(program.getMain()); // FunctionDeclVoid should be current term
		b = HybridInterpreterHelper.safeInvoke(i, "try_extract_0_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		Assert.assertEquals(program.getMain().toString(), i.current().toString());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/programs/001_tiny/tiny.str");
		builder.setLocation(7, 4, 7, 32);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());

	}
	
	@Test
	public void callTryExtractOnRegisteredStrategy2() {
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		TinyScript program = new TinyScript();
		program.init(i.current());
		
		callTryExtract(StrTestConstants.EXTRACT_STRATEGY_INFO, program.getMain());
		
		// GET-CURRENT-DEBUG-INFORMATION should contain the debug information
		b = HybridInterpreterHelper.safeInvoke(i, "GET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("str_scripts/testcases/programs/001_tiny/tiny.str");
		builder.setLocation(7, 4, 7, 32);
		builder.setData("main");
		Assert.assertEquals(builder.makeEnter(i.getFactory()).toString(), i.current().toString());
	}
}
