package org.strategoxt.imp.debuggers.stratego.source;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.WorkspaceSourceContainer;

public class StrSourcePathComputerDelegate implements ISourcePathComputerDelegate {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourcePathComputerDelegate#computeSourceContainers(org.eclipse.debug.core.ILaunchConfiguration, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException {
//		// TODO: does this work?
//		//String project = configuration.getAttribute(IStrategoConstants.ATTR_PROJECT_DIRECTORY, (String) null);
//		String project = null;
//		//String path = configuration.getAttribute(IStrategoConstants.ATTR_STRATEGO_PROGRAM, (String)null);
//		ISourceContainer sourceContainer = null;
//		
//		if (project != null) {
//			org.eclipse.core.resources.IResource resource = org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().findMember(new org.eclipse.core.runtime.Path(project).lastSegment());
//			if (resource != null) {
//				//org.eclipse.core.resources.IContainer container = resource.getParent();
//				if (resource.getType() == org.eclipse.core.resources.IResource.PROJECT) {
//					sourceContainer = new org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer((org.eclipse.core.resources.IProject)resource, false);
//				}
//				/*
//				else if (container.getType() == org.eclipse.core.resources.IResource.FOLDER) {
//					sourceContainer = new org.eclipse.debug.core.sourcelookup.containers.FolderSourceContainer(container, false);
//				}
//				*/
//			}
//		}
		
		ISourceContainer sourceContainer = null;
		if (sourceContainer == null) {
			sourceContainer = new WorkspaceSourceContainer();
		}
		return new ISourceContainer[]{sourceContainer};
	}
}
