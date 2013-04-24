package org.strategoxt.imp.debuggers.stratego;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.spoofax.debug.core.language.LIConstants;

public class StrategoDebuggerPlugin {

	
	public static org.strategoxt.imp.debuggers.stratego.Activator getDefault() {
		return org.strategoxt.imp.debuggers.stratego.Activator.getDefault();
	}
	private static String getUniqueIdentifier() {
		return org.strategoxt.imp.debuggers.stratego.Activator.PLUGIN_ID;
	}
	
	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}
	
	/**
	 * Logs an internal error with the specified message.
	 * 
	 * @param message the error message to log
	 */
	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, getUniqueIdentifier(), LIConstants.INTERNAL_ERROR, message, null));
	}



	/**
	 * Logs an internal error with the specified throwable
	 * 
	 * @param e the exception to be logged
	 */	
	public static void log(Throwable e) {
		if (e instanceof CoreException) {
			log(new Status(IStatus.ERROR, getUniqueIdentifier(), IStatus.ERROR, e.getMessage(), e.getCause()));
		} else {
			log(new Status(IStatus.ERROR, getUniqueIdentifier(), LIConstants.INTERNAL_ERROR, "Internal Error", e));   //$NON-NLS-1$
		}
	}
	
	/**
	 * Returns the active workbench window
	 * 
	 * @return the active workbench window
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}	
	
	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow w = getActiveWorkbenchWindow();
		if (w != null) {
			return w.getActivePage();
		}
		return null;
	}
	
	
	/**
	 * Returns the active workbench shell or <code>null</code> if none
	 * 
	 * @return the active workbench shell or <code>null</code> if none
	 */
	public static Shell getActiveWorkbenchShell() {
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		if (window != null) {
			return window.getShell();
		}
		return null;
	}
	
	public static void statusDialog(IStatus status) {
		switch (status.getSeverity()) {
		case IStatus.ERROR:
			statusDialog("Error", status);
			break;
		case IStatus.WARNING:
			statusDialog("Warning", status);
			break;
		case IStatus.INFO:
			statusDialog("Info", status);
			break;
		}		
	}
	public static void statusDialog(String title, IStatus status) {
		Shell shell = getActiveWorkbenchShell();
		if (shell != null) {
			switch (status.getSeverity()) {
			case IStatus.ERROR:
				ErrorDialog.openError(shell, title, null, status);
				break;
			case IStatus.WARNING:
				MessageDialog.openWarning(shell, title, status.getMessage());
				break;
			case IStatus.INFO:
				MessageDialog.openInformation(shell, title, status.getMessage());
				break;
			}
		}		
	}
		
	/**
	 * Creates a new internal error status with the specified message and throwable, then displays
	 * it in a status dialog.
	 * 
	 * @param message error message
	 * @param t throwable cause or <code>null</code>
	 */
	public static void errorDialog(String message, Throwable t) {
		IStatus status= new Status(IStatus.ERROR, getUniqueIdentifier(), LIConstants.INTERNAL_ERROR, message, t);
		statusDialog(status);
	}
	
	/**
	 * Opens an error dialog with the given title and message.
	 */
	public static void errorDialog(Shell shell, String title, String message, Throwable t) {
		IStatus status;
		if (t instanceof CoreException) {
			status= ((CoreException)t).getStatus();
			// if the 'message' resource string and the IStatus' message are the same,
			// don't show both in the dialog
			if (status != null && message.equals(status.getMessage())) {
				message= null;
			}
		} else {
			status= new Status(IStatus.ERROR, getUniqueIdentifier(), LIConstants.INTERNAL_ERROR, "Error within Debug UI: ", t); //$NON-NLS-1$
			log(status);
		}
		ErrorDialog.openError(shell, title, message, status);
	}
}
