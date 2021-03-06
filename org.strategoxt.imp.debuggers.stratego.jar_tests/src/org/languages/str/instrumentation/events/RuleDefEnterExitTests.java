package org.languages.str.instrumentation.events;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.ParseError;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public class RuleDefEnterExitTests extends EventInstrumentationBase {

	private String sourceDSLLocation = null;
	
	private String getGeneratedLocation()
	{
		return StrTestConstants.STR_SCRIPTS_GENERATED_DIR +"/" + sourceDSLLocation;
	}
	private String getInstrumentedLocation()
	{
		return StrTestConstants.STR_SCRIPTS_INSTRUMENTED_DIR +"/" + sourceDSLLocation;
	}
	
	private void cleanupGenerated()
	{
		File f = new File(getGeneratedLocation());
		if (f.exists())
		{
			Assert.assertTrue(f.delete());
		}
		Assert.assertFalse(f.exists());
	}
	
	
	@Before
	public void setupProgram() throws ParseError, IOException
	{
		initHI();
		loadDSLDI(StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/RuleDefEnterExit.dsldi");

		// register extract and generate strategies:
		registerExtractFunction("extract-rule-info");
		registerGenerateFunction("gen-rule-enter");
		registerGenerateFunction("gen-rule-exit");
		
		this.setOutputBasePath(StrTestConstants.STR_SCRIPTS_GENERATED_DIR);
		this.setLanguageID("Stratego");
		
		this.setPostInstrumentation("stratego-post-instrumentation");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-to-file");
		this.addWriteFile("Stratego", "write-stratego-as-dsl"); // RL: this is the correct one
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-as-ast");

	}
	
	@Test
	public void instrumentRuledef()
	{
		sourceDSLLocation = StrTestConstants.STR_SCRIPTS_TESTCASES_DIR + "/fragments/fargs/ruledef.str";
		cleanupGenerated();
		
		loadDSLProgram(sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		i.getCompiledContext().printStackTrace();
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		System.out.println(i.current().toString());
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation() + ".enter-exit.str");
	}

}
