package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.commands.GitFetchOptions;
import edu.nyu.cs.javagit.api.commands.GitFetchResponse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * Interface representing git-fetch command
 */
public interface IGitFetch {
    public GitFetchResponse fetch(File clonedRepository, File repoPath, GitFetchOptions options)
            throws JavaGitException, IOException;

    public GitFetchResponse fetch(File repoPath, GitFetchOptions options, URL repository)
            throws JavaGitException;

    public GitFetchResponse fetch(File repoPath, GitFetchOptions options, URL repository, Ref ref)
            throws JavaGitException;

    public GitFetchResponse fetch(File repoPath, GitFetchOptions options, String group)
            throws JavaGitException;

    public GitFetchResponse fetch(File repoPath, GitFetchOptions options, Set<URL> repository, Set<String> group)
            throws JavaGitException;
}
