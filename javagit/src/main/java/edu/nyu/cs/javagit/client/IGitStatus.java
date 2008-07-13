package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;

/**
 * An interface to represent the &lt;git-status&gt; command.
 */
public interface IGitStatus {

  public GitStatusResponse status(File repositoryPath, GitStatusOptions options, List<File> paths) 
    throws JavaGitException, IOException;

}
