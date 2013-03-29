package org.strategoxt.imp.debuggers.stratego.launching;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.osgi.framework.Bundle;
import org.spoofax.debug.core.control.launching.IJavaProgramLaunchPreparation;
import org.spoofax.debug.core.control.launching.IJavaProgramPrepareResult;
import org.spoofax.debug.core.eclipse.LIDebugTarget;
import org.spoofax.debug.core.language.LIConstants;
import org.spoofax.debug.core.launching.LILaunchDelegate;
import org.spoofax.debug.core.launching.LaunchUtil;
import org.strategoxt.imp.debuggers.stratego.Activator;
import org.strategoxt.imp.debuggers.stratego.StrategoConstants;
import org.strategoxt.imp.debuggers.stratego.libraries.StrategoRuntimeDebug;

public class StrLaunchDelegate extends LILaunchDelegate {
	@Override
	public String getLanguageName() {
		return StrategoConstants.getLanguageID();
	}
	@Override
	public LIConstants getLIConstants() {
		return org.strategoxt.imp.debuggers.stratego.Activator.getDefault().getDebugServiceFactory().getLIConstants();
	}
	@Override
	public IJavaProgramLaunchPreparation getLaunchPreparation() {
		return new StrLaunchPreparation();
	}
	
	public String[] getClasspaths(IJavaProgramPrepareResult result) {
		String[] cp = null;
		try {
			
			
			// program bin
			String binDirectory = result.getBinDirectory();

			// stratego.debug.runtime
			URL url_strategodebugruntime = StrategoRuntimeDebug.getJarLocation();
			File file_strategodebugruntime = org.strategoxt.imp.debuggers.stratego.Activator.URLToIPath(url_strategodebugruntime).toFile();
		
			// spoofax.debug.runtime
			URL url_spoofaxdebug = null;
			url_spoofaxdebug = org.spoofax.debug.instrumentation.util.JavaDebugLibraryJarLocation.getInterfacesPath();
			File file_spoofaxdebug = org.strategoxt.imp.debuggers.stratego.Activator.URLToIPath(url_spoofaxdebug).toFile();
			
			URL url_spoofaxjavadebug = null;
			url_spoofaxjavadebug = org.spoofax.debug.instrumentation.util.JavaDebugLibraryJarLocation.getJavaInterfacesPath();
			File file_spoofaxjavadebug = org.strategoxt.imp.debuggers.stratego.Activator.URLToIPath(url_spoofaxjavadebug).toFile();

			// strategoxt.jar is in org.strategoxt.strj/java/strategoxt.jar
			String strjPluginID = "org.strategoxt.strj";
			Bundle bundle = Platform.getBundle(strjPluginID);
			URL url_strategoxt = org.strategoxt.imp.debuggers.stratego.Activator.getLocation(bundle, "java/strategoxt.jar");
			File file_strategoxt = org.strategoxt.imp.debuggers.stratego.Activator.URLToIPath(url_strategoxt).toFile();
		
			cp = new String[] {
					binDirectory,
					file_strategodebugruntime.getCanonicalPath(),
					file_spoofaxjavadebug.getCanonicalPath(),
					file_spoofaxdebug.getCanonicalPath(),
					file_strategoxt.getCanonicalPath()
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		

		return cp;
	}
	
	public void launchVM(IProgressMonitor monitor, String classname, 
			String[] classpath, 
			ILaunch launch, 
			String mode) throws CoreException {
		
        if (monitor == null){
            monitor = new NullProgressMonitor();
        }
        monitor.beginTask("Starting debugger for "+this.getLanguageName()+" program", IProgressMonitor.UNKNOWN);
        
		String[] alngProgramArguments = new String[] {};
		
		// Initialize the VMRunner
		IVMInstall defaultInstall = JavaRuntime.getDefaultVMInstall();
		IVMRunner vmRunner = defaultInstall.getVMRunner(ILaunchManager.RUN_MODE); // always use RUN, so we can control the debug parameters of the VM
		
		VMRunnerConfiguration vmRunnerConfiguration = new VMRunnerConfiguration(classname, classpath);
		vmRunnerConfiguration.setProgramArguments(alngProgramArguments);
		// working directory should be the project directory
		vmRunnerConfiguration.setWorkingDirectory(this.getProject().getLocation().toPortableString());
		
		// the started wm will wait for a debugger to connect to this port
		String port = ""+LaunchUtil.findFreePort();
		
		// if we are in DEBUG_MODE also set the debugging parameters for the VM as we previously created an IVMRunner in RUN_MODE
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			// socket attach
			//String[] realVMargs = new String[] { "-Xdebug", "-Xrunjdwp:transport=dt_socket,address="+port+",server=y,suspend=y" };
			// socket listen, vm will wait untill a debugger is attached
			String[] realVMargs = new String[] { "-Xdebug", "-Xrunjdwp:transport=dt_socket,address="+port+",suspend=y", "-Xss8m" };
		//String[] realVMargs = new String[] { "-Xrunjdwp:transport=dt_socket,address=9000,server=y,suspend=y" };
		//String[] realVMargs = new String[] { "-Xdebug" };
			vmRunnerConfiguration.setVMArguments(realVMargs);
		}
		
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			monitor.subTask("Attaching to the Stratego VM");
			LIDebugTarget target = Activator.getDefault().getDebugServiceFactory().createDebugTarget(launch, port);
			//AlngDebugTarget target = new AlngDebugTarget(launch, port);
			//(launch,p,requestPort,eventPort );
			launch.addDebugTarget(target);
			monitor.worked(1);
		}
		
		// start the VM with the alng program
		// using attach, run before the StrategoDebugTarget is initialized
		// using listen, run after the StrategoDebugTarget is initialized
		System.out.println("RUN");
		vmRunner.run(vmRunnerConfiguration, launch, monitor);
		monitor.worked(1);
	}


}
