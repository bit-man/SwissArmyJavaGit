package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitPullResponse;

import java.io.File;

public interface IGitPull
{
    GitPullResponse pull(File repoDirectory)
            throws JavaGitException;
}
