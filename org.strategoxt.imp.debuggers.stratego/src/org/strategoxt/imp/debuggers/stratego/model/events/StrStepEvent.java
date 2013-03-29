package org.strategoxt.imp.debuggers.stratego.model.events;

import org.spoofax.debug.core.language.events.AbstractEventInfo;

public class StrStepEvent extends AbstractEventInfo {

	public StrStepEvent(String threadName, String filename, String location) {
		super(threadName, filename, location);
	}

	@Override
	public String getEventType() {
		return "step";
	}
}
