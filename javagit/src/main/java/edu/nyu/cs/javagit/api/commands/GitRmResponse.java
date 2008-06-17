package edu.nyu.cs.javagit.api.commands;

public class GitRmResponse {
	
	private boolean success;
	
	/*
	 * @param b	indicates whether the command was successful.
	 * @TODO	This needs to be made richer.
	 */
	
	public GitRmResponse(boolean b) {
	}

	public boolean IsSuccessful() {
		return success;
	}

}
