package org.strategoxt.imp.debuggers.stratego.runtime;

/**
 * Global Stratego event handling class.
 * All events are passed on to the DebugEventsForwarder which will forward the events 
 * to the generic debug event handling library.
 * @author rlindeman
 *
 */
public class DebugEvents {

	public static DebugEventsForwarder INSTANCE;
	static {
		INSTANCE = new DebugEventsForwarder();
	}
	
	public static void enter(String filename, String location, String functionName) {
		INSTANCE.enter(filename, location, functionName);
	}
	public static void exit(String filename, String location, String functionName) {
		INSTANCE.exit(filename, location, functionName);
	}
	public static void var(String filename, String location, String varName, Object value) {
		INSTANCE.var(filename, location, varName, value);
	}
	public static void step(String filename, String location) {
		INSTANCE.step(filename, location);
	}
	public static void disableEvents() {
		INSTANCE.disableEvents();
	}
	public static void enableEvents() {
		INSTANCE.enableEvents();
	}
}
