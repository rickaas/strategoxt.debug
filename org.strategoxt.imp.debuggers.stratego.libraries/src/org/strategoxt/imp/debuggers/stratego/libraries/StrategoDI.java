package org.strategoxt.imp.debuggers.stratego.libraries;

import java.net.URL;

public class StrategoDI {
	public final static String LIB_STRATEGO_DIR = "lib/stratego";
	
	public final static String STRATEGO_DI_JAR = LIB_STRATEGO_DIR + "/stratego-di.jar";
	
	public static URL getJarLocation() {
		URL url = LibraryLocation.getLocation(STRATEGO_DI_JAR);
		return url;
	}
}
