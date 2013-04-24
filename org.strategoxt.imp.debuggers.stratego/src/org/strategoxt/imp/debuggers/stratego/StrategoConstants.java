package org.strategoxt.imp.debuggers.stratego;

import org.spoofax.debug.core.language.LIConstants;

public class StrategoConstants {

	/**
	 * Unique identifier for the Stratego debug model (value 
	 * <code>org.strategoxt.imp.debuggers.stratego</code>).
	 */
	public static final String ID_STR_DEBUG_MODEL = "org.strategoxt.imp.debuggers.stratego";
	
	/**
	 * Launch configuration key. Value is a path to a Stratego
	 * program. The path is a string representing a full path
	 * to a ActionLanguage program in the workspace. 
	 */
	public static final String ATTR_STR_PROGRAM = ID_STR_DEBUG_MODEL + ".ATTR_STR_PROGRAM";
	
	// org.strategoxt.imp.debuggers.stratego.launchConfigurationType
	public static final String STR_LAUNCH_CONFIG_TYPE = ID_STR_DEBUG_MODEL + ".launchConfigurationType";
	
	public static final String STR_LINEBREAKPOINT_MARKER = ID_STR_DEBUG_MODEL + ".lineBreakpoint.marker";

	public static final String ATTR_LANGUAGE = ID_STR_DEBUG_MODEL + ".ATTR_LANGUAGE";
	
	public static String getLanguageID() {
		return "Stratego";
	}
	
	public static class StrAttributes implements LIConstants {

		@Override
		public String getProgram() {
			return ATTR_STR_PROGRAM;
		}
		
		@Override
		public String getDebugModel() {
			return ID_STR_DEBUG_MODEL;
		}
		
		@Override
		public String getLineBreakpointMarker() {
			return STR_LINEBREAKPOINT_MARKER;
		}
		
		@Override
		public String getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
