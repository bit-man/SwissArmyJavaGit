package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitCommandResponse;
import edu.nyu.cs.javagit.client.IGitCommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CliGitCommand
        implements IGitCommand
{
    private final String command;
    private final IGitProcessBuilder processBuilder;
    private final Validator validator;
    private final ICommandRunner<GitCommandResponse> commandRunner;
    private final IParser parser;

    CliGitCommand(String command, IGitProcessBuilder processBuilder,
                  Validator validator,
                  ICommandRunner<GitCommandResponse> commandRunner, IParser parser)
    {
        this.command = command;
        this.processBuilder = processBuilder;
        this.validator = validator;
        this.commandRunner = commandRunner;
        this.parser = parser;
    }

    @Override
    public GitCommandResponse run(File repository, List<String> gitArgs)
            throws IOException, JavaGitException
    {
        validator.checkNullArgument(repository, "RepositoryPath");
        validator.checkFileValidity(repository);
        processBuilder.setCommandLine(buildCommandLine(gitArgs));
        parser.setWorkingDir(repository.getPath() + File.separator);
        commandRunner.setWorkingDirectory(repository);
        commandRunner.setParser(parser);
        commandRunner.setProcessBuilder(processBuilder);
        return commandRunner.run();
    }

    private List<String> buildCommandLine(List<String> gitArgs)
    {
        final List<String> commandLine = new ArrayList<>();
        commandLine.add(JavaGitConfiguration.getGitCommand());
        commandLine.add(command);
        commandLine.addAll(commandLine.size() - 1, gitArgs);
        return commandLine;
    }
}
