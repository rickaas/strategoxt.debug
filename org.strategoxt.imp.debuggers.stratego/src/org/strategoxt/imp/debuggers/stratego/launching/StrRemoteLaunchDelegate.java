package org.strategoxt.imp.debuggers.stratego.launching;

import org.spoofax.debug.core.language.LIConstants;
import org.spoofax.debug.core.launching.LIRemoteLaunchDelegate;
import org.strategoxt.imp.debuggers.stratego.StrategoConstants;

public class StrRemoteLaunchDelegate extends LIRemoteLaunchDelegate {

	@Override
	public String getLanguageName() {
		return StrategoConstants.getLanguageID();
	}
	@Override
	public LIConstants getLIConstants() {
		return org.strategoxt.imp.debuggers.stratego.Activator.getDefault().getDebugServiceFactory().getLIConstants();
	}

}
