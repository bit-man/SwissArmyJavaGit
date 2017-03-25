package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.api.commands.GitPullResponse;
import edu.nyu.cs.javagit.client.IGitPull;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CliGitPull
        implements IGitPull
{
    public GitPullResponse pull(File repoDirectory)
            throws JavaGitException
    {

        try
        {
            CheckUtilities.checkFileValidity(repoDirectory);
            GitPullParser parser = new GitPullParser();
            List<String> command = buildCommand(repoDirectory);
            GitPullResponse response = new CommandRunner<GitPullResponse>(repoDirectory, parser, new GitProcessBuilder(command)).run();
            if (response.containsError())
            {
                throw new JavaGitException(444001, "Git pull error", response.getOutput());
            }
            return response;
        } catch (IOException e)
        {

            throw new JavaGitException(444002, "Git pull error", e);
        }

    }

    private List<String> buildCommand(File repoDirectory)
    {
        ArrayList<String> cmd = new ArrayList<>(2);
        cmd.add("git");
        cmd.add("pull");
        return cmd;
    }

    public static class GitPullParser
            implements IParser
    {
        private Collection<String> output = new ArrayList<>();
        private int exitCode;

        @Override
        public void parseLine(String line)
                throws JavaGitException
        {
            output.add(line);
        }

        @Override
        public void processExitCode(int code)
        {
            exitCode = code;
        }

        @Override
        public CommandResponse getResponse()
                throws JavaGitException
        {
            return new GitPullResponse(exitCode, output);
        }

        @Override
        public void setWorkingDir(String workingDir)
        {
            throw new UnsupportedOperationException();
        }
    }
}
