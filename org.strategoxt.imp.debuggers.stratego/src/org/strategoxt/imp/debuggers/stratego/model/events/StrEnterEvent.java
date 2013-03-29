package org.strategoxt.imp.debuggers.stratego.model.events;

import org.spoofax.debug.core.language.events.AbstractEventInfo;
import org.spoofax.debug.interfaces.info.IFunctionEventInfo;

public class StrEnterEvent extends AbstractEventInfo implements IFunctionEventInfo {
	
	protected String functionName;
	
	public StrEnterEvent(String threadName, String filename, String location, String functionName) {
		super(threadName, filename, location);
		this.functionName = functionName;
	}
	
	public String getFunctionName() {
		return functionName;
	}

	@Override
	public String getEventType() {
		return "enter";
	}
}
