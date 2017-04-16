package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitCommand;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitCommand
{
    private final String command;

    public GitCommand(String command)
    {
        this.command = command;
    }

    public GitCommandResponse run(File repository, List<String> gitArgs)
            throws IOException, JavaGitException
    {
        CheckUtilities.checkFileValidity(repository);
        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitCommand cmdStatus = client.getGitCommandInstance(command);
        return cmdStatus.run(repository, gitArgs);
    }
}
