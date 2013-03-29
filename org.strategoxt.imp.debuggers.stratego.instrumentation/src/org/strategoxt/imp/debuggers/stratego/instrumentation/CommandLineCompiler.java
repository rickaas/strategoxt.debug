package org.strategoxt.imp.debuggers.stratego.instrumentation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strategoxt.HybridInterpreter;
import org.strategoxt.IncompatibleJarException;
import org.strategoxt.NoInteropRegistererJarException;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.InstrumentStratego;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.IHybridInterpreterProvider;

public class CommandLineCompiler {

	public static void main(String[] args) throws Exception {
		System.out.println("Instrumenting...");

		// requires --input-dir
		// requires --output-dir
		String dslProgramBasePath = getArgumentValue("--input-dir", args);
		if (dslProgramBasePath == null) throw new Exception("--input-dir is required.");
		String outputBasePath = getArgumentValue("--output-dir", args);
		if (outputBasePath == null) throw new Exception("--output-dir is required.");
		
		final List<String> jars = getArgumentValueList("--jar", args);
		List<String> remaining = remaining(args);
		
		
		IHybridInterpreterProvider provider = new IHybridInterpreterProvider() {
			
			@Override
			public HybridInterpreter get() {
				return createHybridInterpreter(jars);
			}
		}; 
		
		//String fakeRun = getArgumentValue("--fake-run", args);
		//i.setCurrent(makeConfigTuple("--fake-run", "true"));
		//Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		InstrumentStratego i = new InstrumentStratego(provider, dslProgramBasePath, outputBasePath);
		
		//i.setVerbosityLevel(10);
		//i.setStatisticsLevel(10);
		
		i.extraArguments = extraArguments(remaining);
		i.init();
		i.setTempDirectory(outputBasePath);
		i.execute();
	}
	
	private static List<String> remaining(String[] args) {
		// get the remaining arguments
		List<String> remaining = new ArrayList<String>();
		
		int i = 0;
		while(i < args.length) {
			if (args[i].equals("--input-dir")) {
				i = i+2;
				continue;
			}
			if (args[i].equals("--output-dir")) {
				i = i+2;
				continue;
			}
			if (args[i].equals("--jar")) {
				i = i+2;
				continue;
			}
			remaining.add(args[i]);
			i++;
		}
		return remaining;
	}
	
	public static HybridInterpreter createHybridInterpreter(List<String> jars) {
		// init the Context
		//org.strategoxt.lang.Context context = trans.Main.init(); // not needed
		HybridInterpreter i = new HybridInterpreter();
		i.init();
		// Load the Jars.

		if (jars != null) {
			for(String jar : jars) {
				safeLoadJar(i, jar);
			}
		}
		// language independent instrumentation
		//safeLoadJar(i, StrTestConstants.LIBDSLDI_JAR);
		// also load dsldi-java because it contains strategies implemented in java
		// TODO: move the java strategies from dsldi-java.jar to lidsldi-java.jar
		//safeLoadJar(i, StrTestConstants.DSLDI_JAVA_JAR);
		// language specific strategies
		//safeLoadJar(i, StrTestConstants.STRATEGOSUGAR_JAR);
		//safeLoadJar(i, StrTestConstants.STRATEGO_DI_JAR);
		return i;
	}
	
	private static void safeLoadJar(HybridInterpreter i, String location) {
		File jarLocation = new File(location);
		try {
			safeLoadJar(i, jarLocation.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static void safeLoadJar(HybridInterpreter i, URL jar) {
		try {
			i.loadJars(jar);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoInteropRegistererJarException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IncompatibleJarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getArgumentValue(String key, String[] arguments) {
		for(int i = 0; i < arguments.length; i++) {
			if (arguments[i].equals(key) && i < arguments.length - 1) {
				// next token is the desired argument value
				return arguments[i+1];
			}
		}
		return null;
	}
	
	public static List<String> getArgumentValueList(String key, String[] arguments) {
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < arguments.length; i++) {
			if (arguments[i].equals(key) && i < arguments.length - 1) {
				// next token is the desired argument value
				list.add(arguments[i+1]);
			}
		}
		return list;
		
	}
	
	public static Map<String,String> extraArguments(List<String> remaining) {
		Map<String, String> extraArguments = new HashMap<String, String>();
		
		for(int i = 0; i < remaining.size(); i++) {
			if (remaining.get(i).startsWith("--") && i < remaining.size() - 1) {
				// next token is the desired argument value
				String key = remaining.get(i);
				String value = remaining.get(i+1);
				if (!extraArguments.containsKey(key)) {
					extraArguments.put(key, value);
				}
			}
		}
		return extraArguments;
	}
}