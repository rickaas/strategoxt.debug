package org.languages.str.parsing;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class StrParsingTests {

	@Test
	public void parseFile_justprint() throws IOException
	{
		String str_program_location = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/002_test-prop/test-prop.str";
		File file = new File(str_program_location);
		Assert.assertTrue(file.exists());
		
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		
		i.setCurrent(i.getFactory().makeString(str_program_location));
		
		boolean b = HybridInterpreterHelper.safeInvoke(i, "parse-stratego_sugar-file");
		Assert.assertTrue(b);
		
		org.spoofax.interpreter.terms.IStrategoTerm termOutput = i.current();
		Assert.assertEquals(2, termOutput.getAllSubterms().length); // FIXME: test parsing
		
		String stringRepresentation = termOutput.toString();
		//Assert.assertEquals(FileUtil.readFile(ALTestConstants.ALNG_SCRIPTS_DIR + "/aterm/justprint/justprint.aterm"), stringRepresentation);
		
		//termOutput.
	}
}
