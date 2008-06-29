package edu.nyu.cs.javagit.test.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.client.cli.IParser;
import edu.nyu.cs.javagit.client.cli.ProcessUtilities;

/**
 * This class contains methods that implement some git commands that aren't coded into javagit yet
 * but which are required to test other commands already written.
 */
public class HelperGitCommands {

  /**
   * Initialize a git repository.
   * 
   * @param repoDirectory
   *          The root directory of the repository.
   * @throws IOException
   *           If IO errors happen.
   * @throws JavaGitException
   *           If errors happen while initializing the repo.
   */
  public static void initRepo(File repoDirectory) throws IOException, JavaGitException {
    List<String> cmdLine = new ArrayList<String>();

    cmdLine.add("git-init");

    ProcessUtilities.runCommand(repoDirectory.getAbsolutePath(), cmdLine, new IParser() {
      public CommandResponse getResponse() {
        return null;
      }

      public void parseLine(String line) {
      }
    });
  }

}
