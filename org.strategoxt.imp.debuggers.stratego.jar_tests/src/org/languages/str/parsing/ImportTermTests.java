package org.languages.str.parsing;

import junit.framework.Assert;

import org.junit.Test;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;

public class ImportTermTests {

	@Test
	public void testImportTermWithDSLDI() {
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		boolean b = HybridInterpreterHelper.safeInvoke(i, "init-dsldi");
		Assert.assertTrue(b);
	}
}
