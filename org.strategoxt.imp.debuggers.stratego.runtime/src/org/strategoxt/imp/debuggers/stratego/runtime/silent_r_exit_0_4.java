package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

/**
 * Does nothing, use this to dynamically replace the java_r_enter_0_4 strategy.
 * 
 * @author rlindeman
 * 
 */
public class silent_r_exit_0_4 extends java_r_exit_0_4 {

	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm current, IStrategoTerm filename, IStrategoTerm name, IStrategoTerm location, IStrategoTerm given) {
		return current;
	}

}
