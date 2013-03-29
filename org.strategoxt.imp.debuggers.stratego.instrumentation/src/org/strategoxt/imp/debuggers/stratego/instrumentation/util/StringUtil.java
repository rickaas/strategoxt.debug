package org.strategoxt.imp.debuggers.stratego.instrumentation.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class StringUtil {

	/**
	 * Concat the contents of String[] A and String[] B and return the result.
	 * 
	 * Method also accepts null as method parameter.
	 * @param A
	 * @param B
	 * @return
	 */
	public static String[] concat(String[] A, String[] B) {
		// If A and B are null it will enter the first if-block in the first call 
		// and in the second call it will enter the second if-block. 
		if (A == null)
		{
			return concat(new String[0], B);
		}
		else if (B == null)
		{
			return concat(A, new String[0]);
		}
		else
		{
			String[] C = new String[A.length + B.length];
			System.arraycopy(A, 0, C, 0, A.length);
			System.arraycopy(B, 0, C, A.length, B.length);
			return C;
		}
	}
	
	public static String join(String[] array, String delimiter) {
	    return join(Arrays.asList(array), delimiter);
	}
	
	@SuppressWarnings("rawtypes")
	public static String join(Collection s, String delimiter) {
	    StringBuffer buffer = new StringBuffer();
	    Iterator iter = s.iterator();
	    while (iter.hasNext()) {
	        buffer.append(iter.next());
	        if (iter.hasNext()) {
	            buffer.append(delimiter);
	        }
	    }
	    return buffer.toString();
	}
	
	public static String[] insertBeforeEach(String[] array, String item) {
		String[] args = new String[2*array.length];
		for(int i = 0; i < array.length; i++){
			int indexOfMinusI = 2*i;
			args[indexOfMinusI] = item;
			args[indexOfMinusI+1] = array[i];
		}
		return args;
	}
}
