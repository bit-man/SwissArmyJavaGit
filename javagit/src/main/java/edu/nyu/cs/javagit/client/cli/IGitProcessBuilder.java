package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IGitProcessBuilder {
	void directory(File directory);

	void redirectErrorStream(boolean redirect);

	List<String> getCommand();

	String getCommandString();

	Process start() throws IOException;

	void setCommandLine(List<String> commandLine);
}
