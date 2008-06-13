package edu.nyu.cs.javagit.api;

/**
 * The <code>Sandbox</code> represents the base directory of the current
 * branch that is checked out.
 * 
 * TODO: Build out the class
 */
public class Sandbox {

	private String sandboxPath;

	/**
	 * The constructor.
	 * 
	 * @param sandboxPath
	 *            The path to the sandbox represented by the instance being
	 *            created.
	 */
	public Sandbox(String sandboxPath) {
		this.sandboxPath = sandboxPath;
	}

	/**
	 * Adds all known and modified files in the sandbox to the index.
	 */
	public void add() {
	}

	/**
	 * Commits the objects specified in the index to the repository.
	 */
	public void commit() {
	}

	/**
	 * Commits all known and modified objects and all new objects already added
	 * to the index to the repository.
	 */
	public void commitAll() {
	}

	/**
	 * Gets the directory at the root of the sandbox.
	 * 
	 * @return The root directory of the sandbox.
	 */
	public Directory getRoot() {
		return null;
	}

	/**
	 * Gets the currently checked-out branch of the sandbox.
	 * 
	 * @return The currently checked-out branch of the sandbox.
	 */
	public Branch getBranch() {
		return null;
	}

	/**
	 * Gets the path to the sandbox represented by an instance.
	 * 
	 * @return The path to the sandbox represented by an instance.
	 */
	public String getSandboxPath() {
		return sandboxPath;
	}

}
