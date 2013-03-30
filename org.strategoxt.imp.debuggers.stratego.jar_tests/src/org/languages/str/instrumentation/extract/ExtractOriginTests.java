package org.languages.str.instrumentation.extract;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

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
		Assert.assertEquals("(0,0,8,14)", i.current().toString()); // FIXME: result should indeed be (0,0,8,14)
		
		i.setCurrent(tinyScript.getModule());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("(1,0,7,33)", i.current().toString());
		
		i.setCurrent(tinyScript.getImports());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
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
}
