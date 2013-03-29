package org.strategoxt.imp.debuggers.stratego.libraries;

import java.net.URL;

import org.eclipse.ant.core.IAntPropertyValueProvider;

public class StrategoDebugAntPropertyProvider implements IAntPropertyValueProvider {
	
    public static final String STRATEGO_DI_JAR = "eclipse.stratego.di.jar";
    public static final String STRATEGO_DEBUG_RUNTIME_JAR = "eclipse.stratego.debug.runtime.jar";
    public static final String STRATEGO_DEBUG_RUNTIME_RTREE = "eclipse.stratego.debug.runtime.rtree";
    
	@Override
	public String getAntPropertyValue(String antPropertyName) {

		if (STRATEGO_DI_JAR.equals(antPropertyName)) {
			URL url = StrategoDI.getJarLocation();
			return url.getFile();
		} else if (STRATEGO_DEBUG_RUNTIME_JAR.equals(antPropertyName)) {
			URL url = StrategoRuntimeDebug.getJarLocation();
			return url.getFile();
		} else if (STRATEGO_DEBUG_RUNTIME_RTREE.equals(antPropertyName)) {
			URL url = StrategoRuntimeDebug.getRtreeLocation();
			return url.getFile();
		}
		return null;
	}
}
