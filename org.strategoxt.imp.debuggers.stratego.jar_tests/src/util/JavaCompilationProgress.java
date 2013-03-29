package util;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.strategoxt.imp.debuggers.stratego.instrumentation.actions.ITaskProgress;

public class JavaCompilationProgress extends CompilationProgress implements ITaskProgress {

	private int remaining = 0;
	private int completed = 0;
	
	private boolean isDone = false;
	private boolean isCanceled = false;

	private String currentTaskName;
	
	@Override
	public void begin(int remaining) {
		this.remaining = remaining;
	}

	@Override
	public void done() {
		this.isDone = true;
	}

	@Override
	public boolean isCanceled() {
		return this.isCanceled;
	}

	@Override
	public void setTaskName(String taskName) {
		this.currentTaskName = taskName;
	}

	@Override
	public void worked(int completed, int remaining) {
		this.completed = completed;
		this.remaining = remaining;
	}

}
