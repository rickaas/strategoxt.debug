package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

/**
 * Does nothing, use this to dynamically replace the java_r_enter_0_4 strategy.
 * 
 * @author rlindeman
 * 
 */
public class silent_s_step_0_3 extends java_s_step_0_3 {

	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm current, IStrategoTerm filename, IStrategoTerm location, IStrategoTerm given) {
		return current;
	}

}
