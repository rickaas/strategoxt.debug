package org.strategoxt.imp.debuggers.stratego.runtime;

import org.spoofax.debug.interfaces.java.FireEvent;
import org.spoofax.debug.interfaces.java.GlobalVarEnvironmentScope;

public class DebugEventsForwarder {

	public void enter(String filename, String location, String functionName) {
		String eventInfo = join(filename, location, functionName);
		GlobalVarEnvironmentScope.get().enterFrame();
		FireEvent.enter(eventInfo);
	}
	
	public void exit(String filename, String location, String functionName) {
		String eventInfo = join(filename, location, functionName);
		GlobalVarEnvironmentScope.get().exitFrame(); // Or should this be after exit?
		FireEvent.exit(eventInfo);
	}
	
	public void var(String filename, String location, String varName, Object value) {
		String eventInfo = join(filename, location, varName);
		GlobalVarEnvironmentScope.get().setVar(varName, value);
		FireEvent.var(eventInfo);
	}
	
	/**
	 * After each step the current term can be changed.
	 * Save the current term in the Variable Environment but do not include it in the debug event.
	 * The value is retrieved when the program is suspended. 
	 * @param filename
	 * @param location
	 */
	public void step(String filename, String location) {
		String eventInfo = join(filename, location);
		FireEvent.step(eventInfo);
	}
	
	private String join(String s1, String s2, String s3) {
		//String s = s1 + "\t" + s2 + "\t" + s3;
		String s = s1.concat("\t").concat(s2).concat("\t").concat(s3);
		//StringBuilder b = new StringBuilder();
		//b.append(s1);
		//b.append("\t");
		//b.append(s2);
		//b.append("\t");
		//b.append(s3);
		//String s = b.toString();
		return s;
	}
	
	private String join(String s1, String s2) {
		//String s = s1 + "\t" + s2;
		String s = s1.concat("\t").concat(s2);
		//StringBuilder b = new StringBuilder();
		//b.append(s1);
		//b.append("\t");
		//b.append(s2);
		//String s = b.toString();
		return s;
	}
	
	/**
	 * Replace all java_*-strategies with their silent_*-counterpart.
	 */
	public void disableEvents() {
		// RL: Is it safe to replace the instances at runtime?
		java_s_var_0_4.instance = new silent_s_var_0_4();
		java_s_step_0_3.instance = new silent_s_step_0_3();
		java_s_fail_0_3.instance = new silent_s_fail_0_3();
		java_s_exit_0_4.instance = new silent_s_exit_0_4();
		java_s_enter_0_4.instance = new silent_s_enter_0_4();
		java_r_exit_0_4.instance = new silent_r_exit_0_4();
		java_r_enter_0_4.instance = new java_r_enter_0_4();
	}
	
	/**
	 * Re-create the java_*-strategy instances.
	 */
	public void enableEvents() {
		// RL: Is it safe to replace the instances at runtime?
		java_s_var_0_4.instance = new java_s_var_0_4();
		java_s_step_0_3.instance = new java_s_step_0_3();
		java_s_fail_0_3.instance = new java_s_fail_0_3();
		java_s_exit_0_4.instance = new java_s_exit_0_4();
		java_s_enter_0_4.instance = new java_s_enter_0_4();
		java_r_exit_0_4.instance = new java_r_exit_0_4();
		java_r_enter_0_4.instance = new java_r_enter_0_4();
	}
}
