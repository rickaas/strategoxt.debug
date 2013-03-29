package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

/**
 * Called when a Where or With clause in a rule fails.
 * 
 * @author rlindeman
 *
 */
public class java_s_fail_0_3 extends DebugCallStrategy {
	
	public static java_s_fail_0_3 instance = new java_s_fail_0_3();

	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm current, IStrategoTerm filename, IStrategoTerm location, IStrategoTerm given) {
		super.recordDebugInformation(context, current, filename, location, given);
		//DebugEvents.enter(this.filenameString, this.locationString, this.strategyNameString);
		return this.current; // return this.current, its value could be changed while suspended.
	}
	
	public static String getFullClassName()
	{
		return instance.getClass().getName();
	}	
}
