package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestCommandRunner {

	private InputStream inputStream;
	private Integer exitCode;
	private Process process;
	private IGitProcessBuilder pb;
	private IParser parser;
	private CommandRunner<Object> runner;

	@Test
	public void noWorkDirAvoidsProcessDirectorySet() throws IOException {
		givenProcessBuilder();
		whenCommandRunnerIsCreatedWithNoWorkdir();
		Mockito.verify(pb, times(0)).directory(any(File.class));
	}

	@Test
	public void runningProcessStartsIt() throws IOException, JavaGitException {
		givenProcessBuilder();
		givenEmptyInputStream();
		givenProcess();
		when(pb.start()).thenReturn(process);
		givenParser();

		whenCommandRunnerIsExecuted();

		Mockito.verify(pb, times(1)).start();
	}

	@Test
	public void runningProcessReturningNoInputAvoidsCallingParser() throws IOException, JavaGitException {
		givenProcessBuilder();
		givenEmptyInputStream();
		givenProcess();
		when(pb.start()).thenReturn(process);
		givenParser();

		whenCommandRunnerIsExecuted();

		Mockito.verify(parser, times(0)).parseLine(anyString());
	}

	@Test
	public void runningProcessMakesParserProcessExitValue() throws IOException, JavaGitException {
		givenEmptyInputStream();
		givenProcess();
		when(process.exitValue()).thenReturn(25);
		givenProcessBuilder();
		givenFullParser();

		whenCommandRunnerIsExecuted();

		assertThat(exitCode).isEqualTo(25);
	}

	private void whenCommandRunnerIsExecuted()
            throws IOException, JavaGitException
	{
		whenCommandRunnerIsCreated();
		runner.run();
	}

	private void whenCommandRunnerIsCreatedWithNoWorkdir() {
		new CommandRunner<>(parser, pb);
	}

	private void whenCommandRunnerIsCreated() {
		this.runner = new CommandRunner<>(new File(""), parser, pb);
	}

	private void givenParser() {
		this.parser = mock(IParser.class);
	}

	private void givenFullParser() {
		parser = new IParser() {

			@Override
			public void parseLine(String line) throws JavaGitException {

			}

			@Override
			public void processExitCode(int code) {
				exitCode = code;
			}

			@Override
			public CommandResponse getResponse() throws JavaGitException {
				return null;
			}

			@Override
			public void setWorkingDir(String workingDir)
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	private void givenProcess() {
		this.process = mock(Process.class);
		when(process.getInputStream()).thenReturn(inputStream);
	}

	private void givenProcessBuilder() throws IOException {
		pb = mock(IGitProcessBuilder.class);
		when(pb.start()).thenReturn(process);
	}

	private void givenEmptyInputStream() {
		this.inputStream = new InputStream() {
			@Override
			public int read() throws IOException {
				return -1;
			}
		};
	}
}
