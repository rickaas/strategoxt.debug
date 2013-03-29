package org.strategoxt.imp.debuggers.stratego.instrumentation.actions;

/**
 * Interface for instrumentation/compilation progress.
 * Copied from org.eclipse.jdt.core.compiler.CompilationProgress
 * @author rlindeman
 *
 */
public interface ITaskProgress {

	/**
	 * Notifies that the compilation is beginning. This is called exactly once per batch compilation.
	 * An estimated amount of remaining work is given. This amount will change as the compilation
	 * progresses. The new estimated amount of remaining work is reported using {@link #worked(int, int)}.
	 * <p>
	 * Clients should not call this method.
	 * </p>
	 *
	 * @param remainingWork the estimated amount of remaining work.
	 */
	void begin(int remainingWork);

	/**
	 * Notifies that the work is done; that is, either the compilation is completed
	 * or a cancellation was requested. This is called exactly once per batch compilation.
	 * <p>
	 * Clients should not call this method.
	 * </p>
	 */
	void done();

	/**
	 * Returns whether cancellation of the compilation has been requested.
	 *
	 * @return <code>true</code> if cancellation has been requested,
	 *    and <code>false</code> otherwise
	 */
	boolean isCanceled();

	/**
	 * Reports the name (or description) of the current task.
	 * <p>
	 * Clients should not call this method.
	 * </p>
	 *
	 * @param name the name (or description) of the current task
	 */
	void setTaskName(String name);


	/**
	 * Notifies that a given amount of work of the compilation
	 * has been completed. Note that this amount represents an
	 * installment, as opposed to a cumulative amount of work done
	 * to date.
	 * Also notifies an estimated amount of remaining work. Note that this
	 * amount of remaining work  may be greater than the previous estimated
	 * amount as new compilation units are injected in the compile loop.
	 * <p>
	 * Clients should not call this method.
	 * </p>
	 *
	 * @param workIncrement a non-negative amount of work just completed
	 * @param remainingWork  a non-negative amount of estimated remaining work
	 */
	void worked(int workIncrement, int remainingWork);

}
