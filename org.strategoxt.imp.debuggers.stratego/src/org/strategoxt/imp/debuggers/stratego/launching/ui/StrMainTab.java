package org.strategoxt.imp.debuggers.stratego.launching.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.strategoxt.imp.debuggers.stratego.StrategoConstants;

public class StrMainTab extends AbstractLaunchConfigurationTab {

	/**
	 * The location of the ActionLanguage program
	 */
	private Text programLocationText;
	
	@Override
	public void createControl(Composite parent) {
		Font font = parent.getFont();

		// the container for all the elements on this tab
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		// use a grid layout with three columns: label, text, button (which is optional)
		GridLayout topLayout = new GridLayout();
		topLayout.verticalSpacing = 0;
		topLayout.numColumns = 3;
		comp.setLayout(topLayout);
		comp.setFont(font);
		
		createProgramControl(font, comp);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO: do we have default values?
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			// program location
			String program = configuration.getAttribute(StrategoConstants.ATTR_STR_PROGRAM, (String)null);
			if (program != null) {
				programLocationText.setText(program);
			}
		} catch (CoreException e) {
			setErrorMessage(e.getMessage());
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {

		// program location
		String program = programLocationText.getText().trim();
		if (program.length() == 0) {
			program = null;
		}
		configuration.setAttribute(StrategoConstants.ATTR_STR_PROGRAM, program);
		
	}

	@Override
	public String getName() {
		return "Main";
	}
	
	
	private void createProgramControl(Font font, Composite comp) {
		// program location
		Label programLabel = new Label(comp, SWT.NONE);
		programLabel.setText("&Program:");
		GridData gdProg = new GridData(GridData.BEGINNING);
		programLabel.setLayoutData(gdProg);
		programLabel.setFont(font);
		
		programLocationText = new Text(comp, SWT.SINGLE | SWT.BORDER);
		
		gdProg = new GridData(GridData.FILL_HORIZONTAL);
		programLocationText.setLayoutData(gdProg);
		programLocationText.setFont(font);
		programLocationText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		
		// browse button
		Button fProgramButton = createPushButton(comp, "&Browse...", null); //$NON-NLS-1$
		fProgramButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				browseActionLanguageFiles();
			}
		});
	}
	
	
	/**
	 * Open a resource chooser to select a PDA program 
	 */
	protected void browseActionLanguageFiles() {
		ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
		dialog.setTitle("ActionLanguage Program");
		dialog.setMessage("Select ActionLanguage Program");
		// TODO: enforce single select
		if (dialog.open() == Window.OK) {
			Object[] files = dialog.getResult();
			if (files != null && files.length > 0)
			{
				IFile file = (IFile) files[0];
				programLocationText.setText(file.getFullPath().toString());
			}

		}
		
	}
}
