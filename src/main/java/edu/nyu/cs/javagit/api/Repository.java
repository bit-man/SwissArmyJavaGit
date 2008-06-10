package edu.nyu.cs.javagit.api;

import java.util.List;

/**
 * The <code>Repository</code> represents the git repository.
 * 
 * TODO: Build out the class
 */
public class Repository {

	private String repositoryPath;

	/**
	 * The constructor.
	 * 
	 * @param repositoryPath
	 *            The path to the repository represented by the instance being
	 *            created.
	 */
	public Repository(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}

	/**
	 * Gets a list of the branches in the repository.
	 * 
	 * @return The branches in the repository.
	 */
	public List<Branch> getBranches() {
		return null;
	}

	/**
	 * Gets the repository path represented by this repository object.
	 * 
	 * @return
	 */
	public String getRepositoryPath() {
		return repositoryPath;
	}

}
