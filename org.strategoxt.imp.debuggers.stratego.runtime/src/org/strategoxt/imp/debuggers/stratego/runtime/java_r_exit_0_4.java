package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

/**
 * Called right before the rule returns a new term.
 * 
 * @author rlindeman
 * 
 */
public class java_r_exit_0_4 extends DebugCallStrategy {
	
	public static java_r_exit_0_4 instance = new java_r_exit_0_4();

	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm current,	IStrategoTerm filename,	IStrategoTerm name, IStrategoTerm location, IStrategoTerm given) {
		super.recordDebugInformation(context, current, filename, location, given);
		String functionName = name.toString();
		DebugEvents.exit(this.filenameString, this.locationString, functionName);
		return this.current; // return this.current, its value could be changed while suspended.
	}
	
	public static String getFullClassName()
	{
		return instance.getClass().getName();
	}
}
