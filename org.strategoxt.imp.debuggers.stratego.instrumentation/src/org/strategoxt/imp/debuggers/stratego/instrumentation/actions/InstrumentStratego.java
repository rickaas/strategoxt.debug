package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.spoofax.interpreter.core.InterpreterErrorExit;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.InterpreterExit;
import org.spoofax.interpreter.core.UndefinedStrategyException;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.IHybridInterpreterProvider;

public class InstrumentStratego {

	public final static int STATISTICS_LEVEL = 1;
	public final static int VERBOSITY_LEVEL = 4;
	
	private IHybridInterpreterProvider provider;
	
	private String dslProgramBasePath;
	private String outputBasePath;
	
	private int statisticsLevel = STATISTICS_LEVEL;
	private int verbosityLevel = VERBOSITY_LEVEL;
	
	private HybridInterpreter lazyHI = null;
	private HybridInterpreter getHI() {
		if (this.lazyHI == null) {
			this.lazyHI = provider.get();
		}
		return this.lazyHI;
	}
	public InstrumentStratego(IHybridInterpreterProvider provider, String dslProgramBasePath, String outputBasePath)
	{
		this.provider = provider;
		this.dslProgramBasePath = dslProgramBasePath;
		this.outputBasePath = outputBasePath;
	}
	
	public void init() throws IOException {
		this.setInputDirectory(dslProgramBasePath);
		this.setOutputBasePath(outputBasePath);
		
		getHI().setCurrent(getHI().getFactory().makeInt(verbosityLevel));
		safeInvoke(getHI(), "set-verbosity");
		
		getHI().setCurrent(makeConfigTuple(getHI(),"--statistics", statisticsLevel));
		safeInvoke(getHI(), "set-config");
		
		for (String key : extraArguments.keySet()) {
			String value = extraArguments.get(key);
			
			getHI().setCurrent(makeConfigTuple(getHI(),key, value));
			safeInvoke(getHI(), "set-config");
		}
	}
	
	public Map<String, String> extraArguments = new HashMap<String, String>();
	

	protected void setOutputBasePath(String outputBasePath) {
		getHI().setCurrent(makeConfigTuple(getHI(), "--output-dir", outputBasePath));

		boolean b = false;
		b = safeInvoke(getHI(), "set-config");
		if (!b) {
			getHI().getCompiledContext().printStackTrace();
		}
	}
	
	/**
	 * Sets the --input-dir argument
	 * @param inputDirectory
	 */
	protected void setInputDirectory(String inputDirectory) {
		getHI().setCurrent(makeConfigTuple(getHI(), "--input-dir", inputDirectory));

		boolean b = false;
		b = safeInvoke(getHI(), "set-config");
		if (!b) {
			getHI().getCompiledContext().printStackTrace();
		}
	}
	
	public void setTempDirectory(String tempDirectory) {
		boolean b = false;
		getHI().setCurrent(getHI().getFactory().makeString(tempDirectory));
		b = safeInvoke(getHI(), "SET-TEMP-DIRECTORY");
		if (!b) {
			getHI().getCompiledContext().printStackTrace();
		}
	}

	public void execute() {
		safeInvoke(getHI(), "init-config");
		safeInvoke(getHI(), "execute");
	}
	
	
	public static boolean safeInvoke(HybridInterpreter i, String strategy)
	{
		try {
			boolean b = i.invoke(strategy);
			if (!b) {
				i.getCompiledContext().printStackTrace();
			}
			return b;
		} catch (InterpreterErrorExit e) {
			e.printStackTrace();
		} catch (InterpreterExit e) {
			e.printStackTrace();
		} catch (UndefinedStrategyException e) {
			e.printStackTrace();
		} catch (InterpreterException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected IStrategoTerm makeConfigTuple(HybridInterpreter i, String key, String value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeString(value));
	}
	protected IStrategoTerm makeConfigTuple(HybridInterpreter i, String key, int value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeInt(value));
	}
	public int getStatisticsLevel() {
		return statisticsLevel;
	}
	public void setStatisticsLevel(int statisticsLevel) {
		this.statisticsLevel = statisticsLevel;
	}
	public int getVerbosityLevel() {
		return verbosityLevel;
	}
	public void setVerbosityLevel(int verbosityLevel) {
		this.verbosityLevel = verbosityLevel;
	}


}
