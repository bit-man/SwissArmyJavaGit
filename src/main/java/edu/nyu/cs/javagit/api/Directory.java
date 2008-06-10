package edu.nyu.cs.javagit.api;

import java.util.List;

/**
 * A directory object in a git sandbox or repository tree.
 * 
 * TODO: Build out the class
 */
public class Directory implements IGitTreeObject {

	/**
	 * Gets the children of this directory.
	 * 
	 * @return The children of this directory.
	 */
	public List<IGitTreeObject> getChildren() {
		return null;
	}

	public void add() {
	}

	public void commit() {
	}

	public Branch getBranch() {
		return null;
	}

	public String getGitHash() {
		return null;
	}

	public IGitTreeObject getParent() {
		return null;
	}

}
