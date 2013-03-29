package org.strategoxt.imp.debuggers.stratego.libraries;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

public class LibraryLocation {

	public static URL getLocation(Bundle bundle, String location) {
	    URL url = FileLocator.find(bundle, new Path(location), null);
	    try {
	        url = FileLocator.resolve(url);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return url;
	}
	
	public static URL getLocation(String location) {
		Bundle bundle = org.strategoxt.imp.debuggers.stratego.libraries.Activator.getDefault().getBundle();
	    URL url = FileLocator.find(bundle, new Path(location), null);
	    try {
	        url = FileLocator.resolve(url);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return url;
	}

	public static IPath URLToIPath(URL url) throws IOException {
		URL fileURL = FileLocator.toFileURL(url);
		
		String urlPath = fileURL.getPath();
		IPath path = new Path(urlPath);
		return path;
	}
}
