package org.strategoxt.imp.debuggers.stratego.model.events;

import org.spoofax.debug.core.language.events.AbstractEventInfo;
import org.spoofax.debug.interfaces.info.IVarEventInfo;

public class StrVarEvent extends AbstractEventInfo implements IVarEventInfo {

	protected String varName;
	
	public StrVarEvent(String threadName, String filename, String location, String varName) {
		super(threadName, filename, location);
		this.varName = varName;
	}

	@Override
	public String getEventType() {
		return "var";
	}

	@Override
	public String getVarname() {
		return varName;
	}
}
