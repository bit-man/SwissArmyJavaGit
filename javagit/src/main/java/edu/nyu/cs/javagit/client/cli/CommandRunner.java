package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.JavaGitProperty;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.logging.*;

public class CommandRunner<T>
		implements ICommandRunner<T>
{

	private static final Logger logger = Logger.getLogger("javagit.commands");
	private IParser parser;
	private IGitProcessBuilder pb;
	private File workingDirectory;
	public CommandRunner()
	{
	}

	public CommandRunner(File workingDirectory, IParser parser, IGitProcessBuilder pb) {
		this.parser = parser;
		this.pb = pb;
		this.workingDirectory = workingDirectory;
	}

	public CommandRunner(IParser parser, IGitProcessBuilder pb) {
		this(null, parser, pb);
	}

	private static void initLogger() {
		try {
			FileHandler fileHandler = new FileHandler(JavaGitProperty.LOG_COMMANDS_PATH.getPropValueString(),
					JavaGitProperty.LOG_COMMANDS_APPEND.getPropValueBoolean());
			fileHandler.setFormatter(new JustThisFormatter());

			logger.addHandler(fileHandler);
			logger.setLevel(Level.INFO);

			if (JavaGitProperty.LOG_COMMANDS_APPEND.getPropValueBoolean())
				logger.info("------------------------ new JavaGit run (" + new Date() + ")");

		} catch (IOException e) {
			if (JavaGitProperty.LOG_COMMANDS_FAIL_ON_INIT.getPropValueBoolean()) {
				System.out.println("JavaGit ERROR: error while generating log");
				System.exit(-1);
			} else {
				System.out.println("JavaGit WARNING: error while generating log. Commands won't be logged");
			}
		}
	}

	private void initPB(File workingDirectory) {
		this.pb.redirectErrorStream(true);
		if (workingDirectory != null) {
			this.pb.directory(workingDirectory);
		}
	}

	public T run() throws IOException, JavaGitException {

		initPB(workingDirectory);
		logger.info(pb.getCommandString());
		Process p = startProcess(pb);
		processOutput(p, parser);
		waitForAndDestroyProcess(p, parser);

		return (T) parser.getResponse();
	}

	@Override
	public void setWorkingDirectory(File workingDirectory)
	{
		this.workingDirectory = workingDirectory;
	}

	@Override
	public void setParser(IParser parser)
	{
		this.parser = parser;
	}

	@Override
	public void setProcessBuilder(IGitProcessBuilder processBuilder)
	{
		this.pb = processBuilder;
	}

	private Process startProcess(IGitProcessBuilder pb) throws IOException {
		try {
			return pb.start();
		} catch (IOException e) {
			IOException toThrow = new IOException(ExceptionMessageMap.getMessage("020100"));
			toThrow.initCause(e);
			throw toThrow;
		}
	}

	private void processOutput(Process p, IParser parser)
			throws JavaGitException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

		try {
			String str;
			while ((str = br.readLine()) != null) {
				parser.parseLine(str);
			}
		} catch (IOException e) {
			throw new JavaGitException(20101, ExceptionMessageMap.getMessage("020101"), e);
		}

	}

	private int waitForAndDestroyProcess(Process p, IParser parser) {
	/*
	 * I'm not sure this is the best way to handle waiting for a process to complete. -- jhl388
     * 06.14.2008
     */
		while (true) {
			try {
				int i = p.waitFor();
				parser.processExitCode(p.exitValue());
				p.destroy();
				return i;
			} catch (InterruptedException e) {
				// BUG: if continuing with planned work it could harm the whole GIT command chain
				//      being executed. Work on a method to notify the API caller and let him/her
				//      decide on continuing or stopping the work being carried out
			}
		}
	}

	private static class JustThisFormatter extends Formatter {
		@Override
		public String format(LogRecord record) {
			return record.getMessage() + "\n";
		}
	}

	static
	{

		logger.setUseParentHandlers(false);   // Don't output to console
		if (!JavaGitProperty.LOG_COMMANDS_DISABLE.getPropValueBoolean())
			initLogger();

	}

}
