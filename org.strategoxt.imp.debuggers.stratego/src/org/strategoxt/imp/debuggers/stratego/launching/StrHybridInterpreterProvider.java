package org.strategoxt.imp.debuggers.stratego.launching;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.spoofax.interpreter.core.InterpreterErrorExit;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.InterpreterExit;
import org.spoofax.interpreter.core.UndefinedStrategyException;
import org.strategoxt.HybridInterpreter;
import org.strategoxt.IncompatibleJarException;
import org.strategoxt.NoInteropRegistererJarException;
import org.strategoxt.imp.debuggers.stratego.instrumentation.util.IHybridInterpreterProvider;
import org.strategoxt.imp.debuggers.stratego.libraries.StrategoDI;
import org.strategoxt.imp.editors.stratego.StrategoSugarParseController;
import org.strategoxt.imp.runtime.Environment;
import org.strategoxt.imp.runtime.dynamicloading.BadDescriptorException;
import org.strategoxt.imp.runtime.dynamicloading.Descriptor;
import org.strategoxt.imp.runtime.parser.SGLRParseController;
import org.strategoxt.imp.runtime.services.StrategoObserver;
import org.strategoxt.imp.runtime.stratego.EditorIOAgent;

public class StrHybridInterpreterProvider implements IHybridInterpreterProvider {

	@Override
	public HybridInterpreter get() {
		return getHybridInterpreter();
	}
	
	private static HybridInterpreter getHybridInterpreter()
	{
		Descriptor d = StrategoSugarParseController.getDescriptor();
		SGLRParseController controller = null;

		StrategoObserver observer = null;
		try {
			observer = d.createService(StrategoObserver.class, controller);
		} catch (BadDescriptorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (observer == null) return null;
		
		//observer.configureRuntime(project, projectPath)
		HybridInterpreter runtime = observer.getRuntime();
		
		HybridInterpreter i = Environment.createInterpreterFromPrototype(runtime);
		
		// Set the language descriptor so the strategy "plugin-path" works.
		// "plugin-path" returns the path to the ActionLanguage editor plugin
		EditorIOAgent ioDerived = (EditorIOAgent) i.getIOAgent();
		ioDerived.setDescriptor(d);
		// PluginPathPrimitive should now work because it uses:
		// Descriptor descriptor = ((EditorIOAgent) agent).getDescriptor();
		// descriptor.getBasePath().toPortableString();

		URL dsldijava = org.spoofax.debug.instrumentation.util.LibDsldiJarLocations.getDsldiJava();
		//safeLoadJar(i, dsldijava); // this one has to be in the current classpath
		URL libdsldi = org.spoofax.debug.instrumentation.util.LibDsldiJarLocations.getLibdsldi();
		//safeLoadJar(i, libdsldi);
		URL strategodi = StrategoDI.getJarLocation();
		safeLoadJar(i, dsldijava, libdsldi, strategodi);
		
		return i;
	}
	
	
//	/**
//	 * Create a new HybridInterpreter with actionlanguage.jar and libdsldi.jar loaded.
//	 * @return
//	 * @throws IOException 
//	 */
//	public static HybridInterpreter createHybridInterpreter() throws IOException
//	{
//		// init the Context
//		//org.strategoxt.lang.Context context = trans.Main.init(); // not needed
//		HybridInterpreter i = new HybridInterpreter();
//		i.init();
//		// Load the Jars.
//
//		// language independent instrumentation
//		//safeLoadJar(i, StrTestConstants.LIBDSLDI_JAR);
//		URL libdsldi = org.spoofax.debug.instrumentation.util.LibDsldiJarLocations.getLibdsldi();
//		//safeLoadJar(i, libdsldi);
//		// also load dsldi-java because it contains strategies implemented in java
//		// TODO: move the java strategies from dsldi-java.jar to lidsldi-java.jar
//		//safeLoadJar(i, StrTestConstants.DSLDI_JAVA_JAR);
//		URL dsldijava = org.spoofax.debug.instrumentation.util.LibDsldiJarLocations.getDsldiJava();
//		//safeLoadJar(i, dsldijava);
//		
//		safeLoadJar(i, dsldijava, libdsldi);
//		
//		// language specific strategies
//		//safeLoadJar(i, StrTestConstants.STRATEGOSUGAR_JAR);// these should already be loaded because we use the HybridInterpeter from StrategoSugar
//		//safeLoadJar(i, StrTestConstants.STRATEGO_DI_JAR);// these should already be loaded because we use the HybridInterpeter from StrategoSugar
//		return i;
//	}
	
	public static void safeLoadJar(HybridInterpreter i, String... locations)
	{
		try {
			URL[] urls = new URL[locations.length];
			for(int index = 0; index < locations.length; index++){
				File jarLocation = new File(locations[index]);
				URL url = jarLocation.toURI().toURL();
				urls[index] = url;
			}
			safeLoadJar(i, urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
//			Assert.fail();
		}
	}
	
	public static void safeLoadJar(HybridInterpreter i, URL... jars)
	{
		ClassLoader l= null;
		try {
			l = HybridInterpreter.class.getClassLoader();

			//l = org.strategoxt.imp.debug.instrumentation.strategies.Main.class.getClassLoader();
			//i.loadJars(jar);
			i.loadJars(l, jars);
			//i.loadJars(StrHybridInterpreterProvider.class.getClassLoader(), jar);
		} catch (SecurityException e) {
			e.printStackTrace();
//			Assert.fail();
		} catch (NoInteropRegistererJarException e) {
			e.printStackTrace();
//			Assert.fail();
		} catch (MalformedURLException e) {
			e.printStackTrace();
//			Assert.fail();
		} catch (IncompatibleJarException e) {
			e.printStackTrace();
//			Assert.fail();
		} catch (IOException e) {
			e.printStackTrace();
//			Assert.fail();
		}
	}
	
	/**
	 * Invoke the strategy in the HybridInterpreter.
	 * The strategy name does not need escaping.
	 * 
	 * When invoking fails, do a Assert.fail().
	 * @param i
	 * @param strategy
	 * @return
	 */
	public static boolean safeInvoke(HybridInterpreter i, String strategy)
	{
		try {
			boolean b = i.invoke(strategy);
			return b;
		} catch (InterpreterErrorExit e) {
			e.printStackTrace();
//			Assert.fail();
		} catch (InterpreterExit e) {
			e.printStackTrace();
//			Assert.fail();
		} catch (UndefinedStrategyException e) {
			e.printStackTrace();
//			Assert.fail();
		} catch (InterpreterException e) {
			e.printStackTrace();
//			Assert.fail();
		}
		return false;
	}

}
