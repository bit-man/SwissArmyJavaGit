package edu.nyu.cs.javagit.api.commands;

//import java.util.ArrayList;


public class GitMvResponse {

	/**
	 * @param comment
	 * 				The mv command without any option does not give any message. Hence,
	 * 				the comment string is empty. mv with -k option behaves as above
	 * 				plus the error messages are discarded silently too. MV with -f 
	 * 				option gives messages like "Warning: destination exists; will 
	 * 				overwrite!". And the last option is -n which does nothing but gives
	 * 				lots of comments like "Checking rename of". At present I am just 
	 * 				parsing its first line.
	 */
	public GitMvResponse(String comment) {

	}

}
