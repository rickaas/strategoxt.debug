package util;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;
import org.spoofax.jsglr.client.imploder.ImploderAttachment;

public class SELUtil {

	public static String getConstructor(IStrategoTerm term)
	{
		if (term instanceof IStrategoAppl)
		{
			return ((IStrategoAppl)term).getConstructor().getName();
		} else {
			return null;
		}
	}
	
	public static String getConstructor(IStrategoAppl appl)
	{
		return appl.getConstructor().getName();
	}
	
	public static String getSort(IStrategoTerm term)
	{
		String sortName = null;
		
        ImploderAttachment attachment = term.getAttachment(ImploderAttachment.TYPE);
        
        if (attachment != null)
        {
        	try {
        		sortName = attachment.getSort();
        		//this.log(context, "AFTER GET SORT");
        	} catch(Exception e)
        	{
        		//this.log(context, "EXPEXPEXPEPXPEXP::: " + e.getMessage());
        	}
        } else {
        	// TODO: could not get attachment
        }
        
        return sortName;
	}
	

	/**
	 * Returns (program-fragment, pattern)
	 * @param programFragment
	 * @param sort
	 * @param cons
	 * @param factory
	 * @return
	 */
	public static IStrategoTerm createATermPatternTuple(IStrategoTerm programFragment, String sort, String cons, ITermFactory factory) {
		IStrategoTerm pattern = createPattern(sort, cons, factory);
		IStrategoTerm tuple = factory.makeTuple(programFragment, pattern);
		return tuple;
	}
	
	// syntax pattern can be one of the following structures
	//	SortAndConstructor(Empty(), Constructor("Fail"))
	//	SortAndConstructor(Sort("Strategy"), Constructor("Fail"))
	//	SortAndConstructor(Sort("StrategyDef"),Empty())
	
	public static IStrategoTerm createSortPattern(String sort, ITermFactory factory)
	{
		IStrategoConstructor sc_c = factory.makeConstructor("SortAndConstructor", 2);
		return factory.makeAppl(sc_c, makeSortTerm(sort, factory), makeEmptyTerm(factory));
	}
	
	public static IStrategoTerm createConstructorPattern(String cons, ITermFactory factory)
	{
		IStrategoConstructor sc_c = factory.makeConstructor("SortAndConstructor", 2);
		return factory.makeAppl(sc_c, makeEmptyTerm(factory),makeConstructorTerm(cons, factory));
	}
	
	public static IStrategoTerm createPattern(String sort, String cons, ITermFactory factory)
	{
		IStrategoConstructor sc_c = factory.makeConstructor("SortAndConstructor", 2);
		return factory.makeAppl(sc_c, makeSortTerm(sort, factory),makeConstructorTerm(cons, factory));
	}
	
	private static IStrategoAppl makeConstructorTerm(String value, ITermFactory factory)
	{
		IStrategoConstructor c = factory.makeConstructor("Constructor", 1);
		return factory.makeAppl(c, factory.makeString(value));
	}
	
	private static IStrategoAppl makeEmptyTerm(ITermFactory factory)
	{
		IStrategoConstructor c = factory.makeConstructor("Empty", 0);
		return factory.makeAppl(c);
	}
	
	private static IStrategoAppl makeSortTerm(String value, ITermFactory factory)
	{
		IStrategoConstructor c = factory.makeConstructor("Sort", 1);
		return factory.makeAppl(c, factory.makeString(value));
	}
}
