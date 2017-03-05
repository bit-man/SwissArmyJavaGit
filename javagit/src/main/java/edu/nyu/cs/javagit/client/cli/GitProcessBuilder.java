package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitProcessBuilder implements IGitProcessBuilder {
	private final ProcessBuilder pb;

	public GitProcessBuilder(List<String> commandLine) {
		this.pb = new ProcessBuilder(commandLine);
	}

	@Override
	public void directory(File directory) {
		pb.directory(directory);
	}

	@Override
	public void redirectErrorStream(boolean redirect) {
		pb.redirectErrorStream(redirect);
	}

	@Override
	public List<String> getCommand() {
		return pb.command();
	}

	@Override
	public String getCommandString() {
		return asString(getCommand());
	}

	@Override
	public Process start() throws IOException {
		return pb.start();
	}

	private String asString(List<String> commandLine) {
		StringBuilder s = new StringBuilder();
		boolean firstCommand = true;
		for (String cmd : commandLine) {
			final String str = (firstCommand ? "" : " ") + cmd;
			s.append(str);
			firstCommand = false;
		}
		return s.toString();
	}

}
