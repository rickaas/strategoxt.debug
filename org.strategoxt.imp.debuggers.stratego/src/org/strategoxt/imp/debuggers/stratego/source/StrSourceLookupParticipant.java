package org.strategoxt.imp.debuggers.stratego.source;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.spoofax.debug.core.eclipse.LIStackFrame;

public class StrSourceLookupParticipant extends AbstractSourceLookupParticipant {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceLookupParticipant#getSourceName(java.lang.Object)
	 */
	public String getSourceName(Object object) throws CoreException {
//		if (object instanceof EStrategoStackFrame) {
//			return ((EStrategoStackFrame)object).getSourceName(); // relative to the project dir
//		}
		if (object instanceof LIStackFrame) {
			return ((LIStackFrame) object).getSourceLocation();
		}
		return null;
	}
}
