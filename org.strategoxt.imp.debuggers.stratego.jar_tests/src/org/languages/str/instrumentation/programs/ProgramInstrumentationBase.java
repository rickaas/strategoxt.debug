package org.languages.str.instrumentation.programs;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;
import util.StrTestConstants;

public abstract class ProgramInstrumentationBase {

	protected HybridInterpreter i = null;
	
	protected abstract String getDsldiLocation();
	protected abstract String getDslProgramBasePath();
	
	protected String getGeneratedLocation() {
		return StrTestConstants.STR_SCRIPTS_GENERATED_DIR +"/" + getDslProgramBasePath();
	}
	
	protected String getInstrumentedLocation() {
		return StrTestConstants.STR_SCRIPTS_INSTRUMENTED_DIR +"/" + getDslProgramBasePath();
	}
	
	protected void initHI()
	{
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
	}
	
	protected List<String> getFilenamesWithSuccess() {
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "getFilenamesWithSuccess"));
		List<String> instrumentedFiles = new ArrayList<String>();
		
		if (i.current() instanceof IStrategoList) {
			IStrategoList list = (IStrategoList)i.current();
			for(int i = 0; i < list.size(); i++) {
				IStrategoTerm t = list.getSubterm(i);
				if (t.getTermType() == IStrategoTerm.STRING) {
					IStrategoString s = (IStrategoString) t;
					instrumentedFiles.add(s.stringValue());
				}
			}
		}
		return instrumentedFiles;
	}
	protected List<String> getFilenamesWithFailure() {
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "getFilenamesWithFailure"));
		List<String> instrumentedFiles = new ArrayList<String>();
		
		if (i.current() instanceof IStrategoList) {
			IStrategoList list = (IStrategoList)i.current();
			for(int i = 0; i < list.size(); i++) {
				IStrategoTerm t = list.getSubterm(i);
				if (t.getTermType() == IStrategoTerm.STRING) {
					IStrategoString s = (IStrategoString) t;
					instrumentedFiles.add(s.stringValue());
				}
			}
		}
		return instrumentedFiles;
	}
	
//	protected void initialize() {
//		boolean b = HybridInterpreterHelper.safeInvoke(i, "init-instrumentation");
//		Assert.assertTrue(b);
//	}
	
	/**
	 * Initialize the debug-instrumentation with DSL specific constants.
	 */
	@Before
	public void setup() {
		initHI();

		i.setCurrent(i.getFactory().makeString("foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "init-config"));
		
		// <set-config> ("--sel",)
		//String dsldi = StrTestConstants.STR_SCRIPTS_DIR + "/dsldis/StepEnterExit.dsldi";
		String dsldi = getDsldiLocation();
		i.setCurrent(makeConfigTuple("--sel", dsldi));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// <set-config> ("--file-extension",)
		//i.setCurrent(makeConfigTuple("--file-extension", "str"));
		//Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// <set-config> ("--input-dir",)
		String dslProgramBasepath = getDslProgramBasePath();
		i.setCurrent(makeConfigTuple("--input-dir", dslProgramBasepath));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// <set-config> ("--output-dir",)
		String output = getGeneratedLocation() + ".str";
		i.setCurrent(makeConfigTuple("--output-dir", output));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		// <set-config> ("--fake-run",)
		//i.setCurrent(makeConfigTuple("--fake-run", "true"));
		//Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		// set-verbosity
		
		// Vomit
		//i.setCurrent(i.getFactory().makeInt(10));
		// verbose-level : Debug()     -> 4
		// Debug
		i.setCurrent(i.getFactory().makeInt(10));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-verbosity"));

		i.setCurrent(makeConfigTuple("--statistics", 1));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
	}
	
	protected IStrategoTerm makeConfigTuple(String key, String[] values) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeList(makeStringList(values)));
	}
	protected IStrategoTerm makeConfigTuple(String key, String value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeString(value));
	}
	protected IStrategoTerm makeConfigTuple(String key, int value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeInt(value));
	}
	private IStrategoTerm[] makeStringList(String[] values) {
		IStrategoTerm[] terms = new IStrategoTerm[values.length];
		for(int index = 0; index < values.length; index++) {
			terms[index] = i.getFactory().makeString(values[index]);
		}
		return terms;
	}

}
