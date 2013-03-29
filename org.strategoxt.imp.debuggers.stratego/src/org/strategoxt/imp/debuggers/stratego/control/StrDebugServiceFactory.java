package org.strategoxt.imp.debuggers.stratego.control;

import org.eclipse.debug.core.ILaunch;
import org.spoofax.debug.core.control.java.VMContainer;
import org.spoofax.debug.core.eclipse.LIDebugTarget;
import org.spoofax.debug.core.language.IDebugServiceFactory;
import org.spoofax.debug.core.language.LIConstants;
import org.strategoxt.imp.debuggers.stratego.StrategoConstants;
import org.strategoxt.imp.debuggers.stratego.model.StrDebugTarget;

import com.sun.jdi.VirtualMachine;

public class StrDebugServiceFactory implements IDebugServiceFactory {

	@Override
	public VMContainer createVMContainer(VirtualMachine vm) {
		return new StrVMContainer(vm);
	}

	@Override
	public LIDebugTarget createDebugTarget(ILaunch launch, String port) {
		StrDebugTarget target = new StrDebugTarget(getLanguageID(), launch, port);
		return target;
	}

	@Override
	public LIConstants getLIConstants() {
		return new StrategoConstants.StrAttributes();
	}

	private static String getLanguageID() {
		String languageID = org.strategoxt.imp.editors.stratego.Activator.getInstance().getLanguageID();
		
		return languageID;
	}
}
