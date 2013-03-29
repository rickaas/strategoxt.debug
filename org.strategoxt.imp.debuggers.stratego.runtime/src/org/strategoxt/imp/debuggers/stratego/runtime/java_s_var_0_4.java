package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

public class java_s_var_0_4 extends DebugCallStrategy {
	
	public static java_s_var_0_4 instance = new java_s_var_0_4();

	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm current, IStrategoTerm filename, IStrategoTerm varname, IStrategoTerm location, IStrategoTerm given) {
		super.recordDebugInformation(context, current, filename, location, given);
		String varnameString = varname.toString();
		DebugEvents.var(this.filenameString, this.locationString, varnameString, this.givenTermString);
		return this.current; // return this.current, its value could be changed while suspended.
	}
	
	public static String getFullClassName()
	{
		return instance.getClass().getName();
	}
}
