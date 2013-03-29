package org.languages.str.parsing;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class GetDslProgramFilesTests {

	@Test
	public void multipleFiles() {
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		// get-dsl-program-files
		// (basepath, extensions*)
		String basepath = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/multiplefiles";
		String extension = "str";
		IStrategoString bp = i.getFactory().makeString(basepath);
		IStrategoList list = i.getFactory().makeList(i.getFactory().makeString(extension));
		IStrategoTerm current = i.getFactory().makeTuple(bp, list);
		i.setCurrent(current);
		
		boolean b = HybridInterpreterHelper.safeInvoke(i, "get-dsl-program-files");
		Assert.assertTrue(b);
		Assert.assertTrue(i.current() instanceof IStrategoList);
		System.out.println(i.current().toString());
	}
	
	@Test
	public void testWithRelativeDirectory() {
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		// get-dsl-program-files
		// (basepath, extensions*)
		String basepath = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny";
		String extension = "str";
		IStrategoString bp = i.getFactory().makeString(basepath);
		IStrategoList list = i.getFactory().makeList(i.getFactory().makeString(extension));
		IStrategoTerm current = i.getFactory().makeTuple(bp, list);
		i.setCurrent(current);
		
		boolean b = HybridInterpreterHelper.safeInvoke(i, "get-dsl-program-files");
		Assert.assertTrue(b);
		Assert.assertTrue(i.current() instanceof IStrategoList);
		System.out.println(i.current().toString());
	}
	
	@Test
	public void testWithAbsoluteDirectory() {
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		// get-dsl-program-files
		// (basepath, extensions*)
		String basepath = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/programs/001_tiny";
		basepath = makeAbsolute(basepath);
		String extension = "str";
		IStrategoString bp = i.getFactory().makeString(basepath);
		IStrategoList list = i.getFactory().makeList(i.getFactory().makeString(extension));
		IStrategoTerm current = i.getFactory().makeTuple(bp, list);
		i.setCurrent(current);
		
		boolean b = HybridInterpreterHelper.safeInvoke(i, "get-dsl-program-files");
		Assert.assertTrue(b);
		Assert.assertTrue(i.current() instanceof IStrategoList);
		System.out.println(i.current().toString());
	}
	
	private String makeAbsolute(String relativePath) {
		File f = new File(relativePath);
		try {
			return f.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
