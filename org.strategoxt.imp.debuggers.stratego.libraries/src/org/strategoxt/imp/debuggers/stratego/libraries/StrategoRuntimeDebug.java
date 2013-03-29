package org.strategoxt.imp.debuggers.stratego.libraries;

import java.net.URL;

public class StrategoRuntimeDebug {

	public final static String LIB_RUNTIME_DIR = "lib/runtime";
	
	public final static String STRATEGO_DEBUG_RUNTIME_JAR = LIB_RUNTIME_DIR + "/org.strategoxt.imp.debuggers.stratego.runtime.jar";
	public final static String STRATEGO_DEBUG_RUNTIME_RTREE = LIB_RUNTIME_DIR + "/org.strategoxt.imp.debuggers.stratego.runtime.rtree";
	
	public static URL getJarLocation() {
		URL url = LibraryLocation.getLocation(STRATEGO_DEBUG_RUNTIME_JAR);
		return url;
	}
	public static URL getRtreeLocation() {
		URL url = LibraryLocation.getLocation(STRATEGO_DEBUG_RUNTIME_RTREE);
		return url;
	}
}
