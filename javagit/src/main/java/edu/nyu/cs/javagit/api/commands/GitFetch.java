package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.url.JavaGitUrl;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitFetch;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * <code>GitFetch</code> provides an API for git-fetch operation in a git repository.
 */
public final class GitFetch {
    public GitFetchResponse fetch(File clonedRepository, JavaGitUrl repoPath, GitFetchOptions options)
            throws JavaGitException, IOException {
        CheckUtilities.checkNullArgument(options, "options");

        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitFetch gitFetch = client.getGitFetchInstance();
        return gitFetch.fetch(clonedRepository, repoPath, options);
    }


    public GitFetchResponse fetch(JavaGitUrl repoPath, GitFetchOptions options, Ref ref, File clonedRepository)
            throws JavaGitException, IOException {
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullArgument(ref, "ref");

        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitFetch gitFetch = client.getGitFetchInstance();
        return gitFetch.fetch(clonedRepository, repoPath, options, ref);
    }


    public GitFetchResponse fetch(File clonedRepository, JavaGitUrl repoPath, GitFetchOptions options, String group)
            throws JavaGitException, IOException {
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullArgument(group, "group");

        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitFetch gitFetch = client.getGitFetchInstance();
        return gitFetch.fetch(clonedRepository, repoPath, options, group);
    }

    public GitFetchResponse fetch(File clonedRepository, File repoPath, GitFetchOptions options, Set<JavaGitUrl> repository, Set<String> group)
            throws JavaGitException, IOException {

        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullArgument(repository, "repository");
        CheckUtilities.checkNullArgument(group, "group");

        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitFetch gitFetch = client.getGitFetchInstance();
        return gitFetch.fetch(clonedRepository, repoPath, options, repository, group);
    }


}
