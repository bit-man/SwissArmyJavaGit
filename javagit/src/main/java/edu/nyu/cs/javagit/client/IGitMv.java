package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.GitFileSystemObject;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitMvOptions;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;

/**
 * An interface to represent the git-mv command.
 */
public interface IGitMv {

  public GitMvResponse mv(File repositoryPath, GitMvOptions options, String source,
      String destination) throws IOException, JavaGitException;
  public GitMvResponse mv(File repositoryPath, GitMvOptions options, File source,
      File destination) throws IOException, JavaGitException;
  public GitMvResponse mv(File repositoryPath, GitMvOptions options, GitFileSystemObject source,
      GitFileSystemObject destination) throws IOException, JavaGitException;

  public GitMvResponse mv(File repositoryPath, String source, String destination)
      throws IOException, JavaGitException;
  public GitMvResponse mv(File repositoryPath, File source, File destination)
      throws IOException, JavaGitException;
  public GitMvResponse mv(File repositoryPath, GitFileSystemObject source, GitFileSystemObject 
      destination) throws IOException, JavaGitException;
}
