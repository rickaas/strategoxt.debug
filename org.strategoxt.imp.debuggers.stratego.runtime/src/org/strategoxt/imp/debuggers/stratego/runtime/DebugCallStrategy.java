package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.StringTermReader;
import org.spoofax.terms.TermFactory;
import org.strategoxt.lang.Context;

public abstract class DebugCallStrategy extends org.strategoxt.lang.Strategy {

	public static TermFactory factory = new TermFactory();
	public static StringTermReader termReader = new StringTermReader(factory);

	/**
	 * Name of the variable that contains the given Term. The type of the variable should be String.
	 */
	public static String CURRENTTERMSTRING = "givenTermString";
	
	/**
	 * String representation of the given IStrategoTerm.
	 * Fetching a String and parsing it in the debugger should be faster than using jdi reflection to generate the IStrategoTerm.
	 * 
	 * Should be protected so it can be accessed in any of its subclasses.
	 */
	protected String givenTermString = null;

	/**
	 * Every invoke should set the Context, the context is used when calling getDRKeySet() or getDRKeySetString().
	 */
	private Context context = null;
	
	/**
	 * Name of the method that returns an IStrategoTerm (IStrategoList actually) containing the active dynamic rule names.
	 */
	public static String GETDRKEYSET = "getDRKeySet";
	
	/**
	 * Name of the method that returns a String representation of the active dynamic rules names IStrategoList.
	 */
	public static String GETDRKEYSETSTRING = "getDRKeySetString";
	
	/**
	 * Returns the active dynamic rule names as an IStrategoTerm (IStrategoList)
	 * @return
	 */
	public IStrategoTerm getDRKeySet() {
		IStrategoTerm term = null; // the current term, can this be null?
		term = context.invokePrimitive("SSL_dynamic_rules_hashtable", term, org.strategoxt.lang.Term.NO_STRATEGIES, org.strategoxt.lang.Term.NO_TERMS);
		if (term == null) {
			return null;
		}
		term = context.invokePrimitive("SSL_hashtable_keys", null, org.strategoxt.lang.Term.NO_STRATEGIES, new IStrategoTerm[]{term});
		
		return term;
	}
	
	/**
	 * Returns the active dynamic rule names as a String representation of an IStrategoTerm (IStrategoList)
	 * @return
	 * 
	 */
	public String getDRKeySetString() {
		IStrategoTerm keySet = this.getDRKeySet();
		if (keySet != null) {
			return this.getDRKeySet().toString();
		} else {
			return null;
		}
	}
	
	/**
	 * The filename in which the event occured
	 */
	protected String filenameString;
	/**
	 * Location of the event encoded as String
	 */
	protected String locationString;

	public IStrategoTerm recordDebugInformation(Context context, IStrategoTerm current, IStrategoTerm filename, IStrategoTerm location, IStrategoTerm given) {
		this.context = context;
		this.filenameString = filename.toString();
		this.locationString = location.toString();
		//this.givenTermString = given.toString(); // very slowwwwww....
		this.current = current;
		//debug("TEST " + filename.toString() + " D:"+getDRKeySetString()); // adding this will prevent a TimeoutException (I think because it will trigger a ClassLoad...)
		return current;
	}
	
	/**
	 * Remember the current StrategoTerm.
	 * It can be changed by the debugger, there each invoke should return this.current.
	 */
	protected IStrategoTerm current = null;
	
	/**
	 * Name of the method to change the current term.
	 * The argument of setCurrentTerm is String.
	 */
	public static String SETCURRENTTERM = "setCurrentTerm";
	
	/**
	 * Call this method from the debugger (using reflection) to change the current term.
	 * @param termString
	 */
	public void setCurrentTerm(String termString)
	{
		IStrategoTerm newCurrent = termReader.parseFromString(termString);
		this.current = newCurrent;
		this.givenTermString = termString;
	}
	
	private void debug(String s) {
		//System.out.println(s);
	}
}
