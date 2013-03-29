package util;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class DebugInfoBuilder {

	private String filename;
	private int a;
	private int b;
	private int c;
	private int d;
	
	private String data;
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setLocation(int a, int b, int c, int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	/**
	 * Can be the function-name, the variable name, ...
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	public IStrategoTerm makeEnter(ITermFactory termFactory) {
//		String aterm_filename = "\"" + filename + "\"";
//		String aterm_location = ""+a+","+b+","+c+","+d;
//		String aterm_data = "\"" + data + "\"";
		IStrategoTerm currentDebugInformation = termFactory.makeTuple(
				termFactory.makeString(filename)
				,termFactory.makeInt(a)
				,termFactory.makeInt(b)
				,termFactory.makeInt(c)
				,termFactory.makeInt(d)
				,termFactory.makeString(data)
				);
		return currentDebugInformation;
	}
}
