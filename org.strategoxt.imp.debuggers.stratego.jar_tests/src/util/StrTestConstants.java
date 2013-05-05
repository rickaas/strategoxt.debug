package util;

public class StrTestConstants {

	public static String STR_SCRIPTS_DIR = "str_scripts";
	public static String STR_SCRIPTS_GENERATED_DIR = STR_SCRIPTS_DIR+"/generated";
	public static String STR_SCRIPTS_INSTRUMENTED_DIR = STR_SCRIPTS_DIR+"/instrumented";
	public static String STR_SCRIPTS_TESTCASES_DIR = STR_SCRIPTS_DIR+"/testcases";
	
	public static String LIB_DIR = "lib";
	public static String LIB_INSTRUMENTATION_DIR = LIB_DIR + "/instrumentation";

	//public static String DSLDI_JAVA_JAR = LIB_INSTRUMENTATION_DIR + "/dsldi-java.jar";
	public static String DSLDI_JAR = LIB_INSTRUMENTATION_DIR + "/dsldi.jar";
	public static String LIBDSLDI_JAR = LIB_INSTRUMENTATION_DIR + "/libdsldi.jar";
	
	public static String LIB_STRATEGO_DIR = LIB_DIR + "/stratego";
	public static String STRATEGOSUGAR_JAR = LIB_STRATEGO_DIR + "/stratego_sugar.jar";
	public static String STRATEGOSUGAR_JAVA_JAR = LIB_STRATEGO_DIR + "/stratego_sugar-java.jar";
	
	public static String STRATEGO_DI_JAR =  LIB_STRATEGO_DIR + "/stratego-di.jar";
	
	
	public static String PARSE_STRATEG_SUGAR_FILE = "parse-stratego_sugar-file";
	
	
	public static String EXTRACT_STRATEGY_INFO = "extract-strategy-info";
	public static String EXTRACT_RULE_INFO = "extract-rule-info";
	
	public static String GEN_STRATEGY_ENTER = "gen-strategy-enter";
	public static String GEN_RULE_ENTER = "gen-rule-enter";
}
