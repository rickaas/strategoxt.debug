package org.strategoxt.imp.debuggers.stratego.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.spoofax.debug.core.eclipse.LILineBreakpoint;
import org.strategoxt.imp.debuggers.stratego.StrategoConstants;

public class StrLineBreakpoint extends LILineBreakpoint {

	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public StrLineBreakpoint() {
		// TODO Auto-generated constructor stub
	}

	public StrLineBreakpoint(final IResource resource, final int lineNumber) throws CoreException {
		super(resource, lineNumber);
	}
	
	@Override
	public String getModelIdentifier() {
		return StrategoConstants.ID_STR_DEBUG_MODEL;
	}

	@Override
	public String getLineBreakpointMarkerID() {
		return StrategoConstants.STR_LINEBREAKPOINT_MARKER;
	}

}
