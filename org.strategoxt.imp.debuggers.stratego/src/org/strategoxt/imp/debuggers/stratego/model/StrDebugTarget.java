package org.strategoxt.imp.debuggers.stratego.model;

import org.eclipse.debug.core.ILaunch;
import org.spoofax.debug.core.eclipse.LIDebugTarget;

import com.sun.jdi.VirtualMachine;

public class StrDebugTarget extends LIDebugTarget {

	public StrDebugTarget(String languageID, ILaunch launch, String port) {
		super(languageID, launch, port);
		// TODO Auto-generated constructor stub
	}
	
	public StrDebugTarget(String languageID, ILaunch launch, VirtualMachine vm) {
		super(languageID, launch, vm);
	}
}
