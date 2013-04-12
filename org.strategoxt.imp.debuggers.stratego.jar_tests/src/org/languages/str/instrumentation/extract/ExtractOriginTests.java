package org.languages.str.instrumentation.extract;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.Strategy;
import org.strategoxt.lang.compat.override.jsglr_parser_compat.jsglr_parse_string_1_3;
import org.strategoxt.lang.compat.override.jsglr_parser_compat.jsglr_parse_string_pt_1_3;

import util.HybridInterpreterHelper;
import util.StrTestConstants;
import util.str_scripts.TinyScript;

public class ExtractOriginTests {

	@Test
	public void extractOriginFromLocalvar()
	{
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny/tiny.str";
		File file = new File(program_location);
		Assert.assertTrue(file.exists());
		
		i.setCurrent(i.getFactory().makeString(program_location));
		boolean b = HybridInterpreterHelper.safeInvoke(i, StrTestConstants.PARSE_STRATEG_SUGAR_FILE);
		Assert.assertTrue(b);
		IStrategoTerm dslProgramAterm = i.current();
		
		// origin-line
		i.setCurrent(dslProgramAterm);
		b = HybridInterpreterHelper.safeInvoke(i, "origin-line");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("0", i.current().toString()); // FIXME: should be 0
		
		i.setCurrent(dslProgramAterm);
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(0,0,8,14)", i.current().toString()); // FIXME: should be (0,0,8,14)
		
		TinyScript tinyScript = new TinyScript();
		tinyScript.init(dslProgramAterm);
		
		i.setCurrent(tinyScript.getModule());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(1,0,8,14)", i.current().toString());// FIXME: should be (0,0,8,14)
		
		i.setCurrent(tinyScript.getModule().getSubterm(0));
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(0,7,0,10)", i.current().toString());
		
		i.setCurrent(tinyScript.getImports());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(2,0,3,17)", i.current().toString());
		
		i.setCurrent(tinyScript.getStrategies());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(5,0,8,14)", i.current().toString());
		
		i.setCurrent(tinyScript.getMain());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(6,4,6,32)", i.current().toString());
		
		i.setCurrent(tinyScript.getMainBody());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(6,11,6,32)", i.current().toString());
	}
	
	@Test
	public void extractOriginFromTermLoadedByOtherHybridInterpreter()
	{
		TinyScript tinyScript = new TinyScript();
		tinyScript.init();
		
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		i.setCurrent(tinyScript.getModule());
		boolean b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		//Assert.assertEquals("(0,0,8,14)", i.current().toString()); // FIXME: result should indeed be (0,0,8,14)
		
		i.setCurrent(tinyScript.getModule());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		//Assert.assertEquals("(1,0,7,33)", i.current().toString()); // FIXME
		
		i.setCurrent(tinyScript.getImports());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location"); // zero-based
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(3,0,4,17)", i.current().toString());
		
		i.setCurrent(tinyScript.getStrategies());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(6,0,7,32)", i.current().toString());
		
		i.setCurrent(tinyScript.getMain());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(7,4,7,32)", i.current().toString());
		
		i.setCurrent(tinyScript.getMainBody());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(7,11,7,32)", i.current().toString());
	}
	
	protected String getDsldiLocation() {
		return "str_scripts/dsldis/Stratego.dsldi";
		//return "str_scripts/dsldis/RuleDefEnterExit.dsldi";
		//return "str_scripts/dsldis/EnterRule.dsldi";
	}
	protected String getDslProgramBasePath() {
		return StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/cs-syntax";
	}
	@Test
	public void extractOriginFromConcreteSyntax() {
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		
		String syntax[] = new String[] {
				"/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt/strategoxt/syntax/stratego-front/syn",
				"/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt/strategoxt/syntax/java-front/syntax-embedding",
		};
		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "-I", syntax));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		String program_location = getDslProgramBasePath() + "/split-large-strategies.str";

		i.setCurrent(i.getFactory().makeString(program_location));
		Assert.assertTrue("Failed to parse stratego file with concrete syntax",HybridInterpreterHelper.safeInvoke(i, "parse-stratego-as-dsl"));
		IStrategoTerm current = i.current();
		System.out.println(current);
		boolean b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(0,0,8,14)", i.current().toString()); // FIXME: result should indeed be (0,0,8,14)

	}

	@Test
	public void extractOrigin() {
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		boolean b = false;
		IStrategoTerm current = null;

		i.setCurrent(i.getFactory().makeString("foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "init-enable-tracking"));
		
		String syntax[] = new String[] {
				"/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt/strategoxt/syntax/stratego-front/syn",
				"/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt/strategoxt/syntax/java-front/syntax-embedding",
		};
		i.setCurrent(HybridInterpreterHelper.makeConfigTuple(i, "-I", syntax));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny/tiny.str";
		
		IStrategoTerm moduleName = null;
		
		i.setCurrent(i.getFactory().makeString(program_location));
		// uses STRSGLR_parse_string_pt
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "parse-stratego-as-dsl"));
		current = i.current();
		moduleName = current.getAllSubterms()[0];
		i.setCurrent(moduleName);
		//Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "origin-location"));
		//Assert.assertEquals("(0,0,8,14)", i.current().toString()); // FIXME: result should indeed be (0,0,8,14)

		System.out.println(current);
		// the toplevel term doesn't have an origin
		
		i.setCurrent(i.getFactory().makeString(program_location));
		// uses STRSGLR_parse_string
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, StrTestConstants.PARSE_STRATEG_SUGAR_FILE));
		current = i.current();
		moduleName = current.getAllSubterms()[0];
		i.setCurrent(moduleName);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "origin-location"));

		Assert.assertEquals("(0,7,0,10)", i.current().toString()); // 0-based line numbers

		System.out.println(current);

		// replace jsglr-parse-string-pt with jsglr-parse-string
		// The jsglr-parse-string-pt would call the primitive STRSGLR_parse_string_pt which only has asfix-location annotations
		// while jsglr-parse-string calls STRSGLR_parse_string which adds ImploderAttachments
		jsglr_parse_string_pt_1_3 old = jsglr_parse_string_pt_1_3.instance;
		//jsglr_parse_string_pt_1_3.instance = jsglr_parse_string_1_3.instance;
		jsglr_parse_string_pt_1_3.instance = new jsglr_parse_string_pt_1_3() {
			@Override public IStrategoTerm invoke(Context context, IStrategoTerm current, Strategy s1, IStrategoTerm t1, IStrategoTerm t2, IStrategoTerm t3)
			{
				//IStrategoTerm ret = jsglr_parse_string_1_3.instance.invoke(context, current, s1, t1, t2, t3);
				IStrategoTerm ret = super.invoke(context, current, s1, t1, t2, t3);
				// should return parse tree
				System.out.println("RET: " + ret.toString());
				return ret;
			}
		};
		i.setCurrent(i.getFactory().makeString(program_location));
		HybridInterpreterHelper.safeInvoke(i, "parse-stratego-as-dsl");
		i.getCompiledContext().printStackTrace();
		current = i.current();
		System.out.println(current);
		jsglr_parse_string_pt_1_3.instance = old;

		current = i.current();
		moduleName = current.getAllSubterms()[0];
		i.setCurrent(moduleName);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "origin-location"));
		Assert.assertEquals("(0,7,0,10)", i.current().toString()); // 0-based line numbers

	}
	
	@Test
	public void extractFromString() {
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		boolean b = false;
		IStrategoTerm parsed = null;

		i.setCurrent(i.getFactory().makeString("foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "init-enable-tracking"));
		
		String program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny/tiny.str";
		
		IStrategoTerm moduleName = null;
		
		i.setCurrent(i.getFactory().makeString(program_location));
		
		i.setCurrent(i.getFactory().makeString(program_location));
		// uses STRSGLR_parse_string
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, StrTestConstants.PARSE_STRATEG_SUGAR_FILE));
		parsed = i.current();
		moduleName = parsed.getAllSubterms()[0];
		i.setCurrent(moduleName);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "origin-location"));
		Assert.assertEquals("(0,7,0,10)", i.current().toString()); // 0-based line numbers

		System.out.println(parsed);
		
		// pp-stratego_sugar-string
		i.setCurrent(parsed);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "my-pp-stratego-string"));
		System.out.println(i.current().toString());

		// parse-stratego_sugar-string
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "parse-stratego_sugar-string"));
		System.out.println(i.current().toString());
		
		parsed = i.current();
		moduleName = parsed.getAllSubterms()[0];
		i.setCurrent(moduleName);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "origin-location"));
		Assert.assertEquals("(0,7,0,10)", i.current().toString()); // 0-based line numbers
	}

}
