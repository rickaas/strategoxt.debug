package org.strategoxt.imp.debuggers.stratego.model.events;

import org.spoofax.debug.core.language.events.AbstractEventInfo;
import org.spoofax.debug.interfaces.info.IFunctionEventInfo;

public class StrExitEvent extends AbstractEventInfo implements IFunctionEventInfo {
	
	protected String functionName;
	
	public StrExitEvent(String threadName, String filename, String location, String functionName) {
		super(threadName, filename, location);
		this.functionName = functionName;
	}
	
	public String getFunctionName() {
		return functionName;
	}

	@Override
	public String getEventType() {
		return "exit";
	}
}
