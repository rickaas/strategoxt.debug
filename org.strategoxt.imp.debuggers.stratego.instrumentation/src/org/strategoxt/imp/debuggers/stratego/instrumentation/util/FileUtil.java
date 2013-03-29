package org.strategoxt.imp.debuggers.stratego.instrumentation.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtil {

	/**
	 * Given a directory, delete the directory and all its contents in one go.
	 * @param sFilePath
	 * @return
	 * @throws IOException 
	 */
	public static boolean deleteDirectory(String directory) throws IOException
	{
	  File oFile = new File(directory);
	  if(oFile.isDirectory())
	  {
	    File[] aFiles = oFile.listFiles();
	    for(File oFileCur: aFiles)
	    {
	    	deleteDirectory(oFileCur.getCanonicalPath());
	    }
	  }
	  return oFile.delete();
	}
	
	public static String convertIPathToClasspath(List<File> list)
	{
		boolean first = true;
		StringBuilder builder = new StringBuilder();
		for(File path : list)
		{
			if (!first)
			{
				builder.append(java.io.File.pathSeparatorChar);
			}
			else
			{
				first = false;
			}
			builder.append(path);
		}
		return builder.toString();
	}

}
