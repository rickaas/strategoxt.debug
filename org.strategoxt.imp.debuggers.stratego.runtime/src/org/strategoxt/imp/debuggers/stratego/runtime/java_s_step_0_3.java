package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.debug.interfaces.java.GlobalVarEnvironmentScope;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

/**
 * 
 * a step event is fired before each strategy.
 * e.g.
 * foo;bar; baz
 * becomes
 * (s-step; foo); (s-step; bar); (s-step; baz)
 * 
 * @author rlindeman
 *
 */
public class java_s_step_0_3 extends DebugCallStrategy {
	
	public static java_s_step_0_3 instance = new java_s_step_0_3();

	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm current, IStrategoTerm filename, IStrategoTerm location, IStrategoTerm given) {
		super.recordDebugInformation(context, current, filename, location, given);
		GlobalVarEnvironmentScope.get().setVar("*current*", given); // save current term
		
		DebugEvents.step(this.filenameString, this.locationString);
		return this.current; // return this.current, its value could be changed while suspended.
	}
	
	public static String getFullClassName()
	{
		return instance.getClass().getName();
	}
}
