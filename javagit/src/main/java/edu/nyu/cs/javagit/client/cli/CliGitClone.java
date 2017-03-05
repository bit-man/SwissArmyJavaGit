/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.api.commands.GitCloneOptions;
import edu.nyu.cs.javagit.api.commands.GitCloneResponse;
import edu.nyu.cs.javagit.api.url.JavaGitUrl;
import edu.nyu.cs.javagit.client.GitCloneResponseImpl;
import edu.nyu.cs.javagit.client.IGitClone;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;
import edu.nyu.cs.javagit.utilities.UrlUtilities;

/**
 * Command-line implementation of the <code>IGitClone</code> interface.
 */
public class CliGitClone implements IGitClone {

    @Deprecated
    public GitCloneResponse clone(File workingDirectoryPath, URL repository) throws IOException, JavaGitException, URISyntaxException {
        return clone(workingDirectoryPath, UrlUtilities.url2JavaGitUrl(repository));
    }

    /**
    * Process the git-clone command, to make a clone of the git repository.
    *
    *
    * @param workingDirectoryPath A <code>File</code> instance for the path to the working directory. This argument
    *                             must represent the absolute path to the desired directory as returned by the
    *                             <code>File.getPath()</code> method. If null is passed, a
    *                             <code>NullPointerException</code> will be thrown.
    * @param repository           A <code>URL</code> instance for the repository to be cloned. If null is passed, a
    *                             <code>NullPointerException</code> will be thrown.
    * @return The result of the git clone.
    * @throws IOException      There are many reasons for which an <code>IOException</code> may be thrown.
    *                          Examples include:
    *                          <ul>
    *                          <li>a directory doesn't exist</li>
    *                          <li>a command is not found on the PATH</li>
    *                          </ul>
    * @throws JavaGitException Thrown when there is an error executing git-clone.
    */
    public GitCloneResponseImpl clone(File workingDirectoryPath, JavaGitUrl repository) throws IOException,
            JavaGitException, URISyntaxException {
        return cloneProcess(workingDirectoryPath, null, repository, null);
    }

    @Deprecated
    public GitCloneResponse clone(File workingDirectoryPath, GitCloneOptions options, URL repository) throws IOException, JavaGitException, URISyntaxException {
        return clone(workingDirectoryPath, options, UrlUtilities.url2JavaGitUrl(repository));
    }

    /**
     * Process the git-clone command, to make a clone of the git repository.
     *
     *
     * @param workingDirectoryPath A <code>File</code> instance for the path to the working directory. This argument
     *                             must represent the absolute path to the desired directory as returned by the
     *                             <code>File.getPath()</code> method. If null is passed, a
     *                             <code>NullPointerException</code> will be thrown.
     * @param options              The options for the git-clone command. If the value is null, a
     *                             <code>NullPointerException</code> will be thrown.
     * @param repository           A <code>URL</code> instance for the repository to be cloned. If null is passed, a
     *                             <code>NullPointerException</code> will be thrown.
     * @return The result of the git clone.
     * @throws IOException      There are many reasons for which an <code>IOException</code> may be thrown.
     *                          Examples include:
     *                          <ul>
     *                          <li>a directory doesn't exist</li>
     *                          <li>a command is not found on the PATH</li>
     *                          </ul>
     * @throws JavaGitException Thrown when there is an error executing git-clone.
     */
    public GitCloneResponseImpl clone(File workingDirectoryPath, GitCloneOptions options, JavaGitUrl repository)
            throws IOException, JavaGitException, URISyntaxException {
        return cloneProcess(workingDirectoryPath, options, repository, null);
    }

    @Deprecated
    public GitCloneResponse clone(File workingDirectoryPath, URL repository, File directory) throws IOException, JavaGitException, URISyntaxException {
        return clone(workingDirectoryPath, UrlUtilities.url2JavaGitUrl(repository), directory);
    }

    /**
     * Process the git-clone command, to make a clone of the git repository.
     *
     *
     * @param workingDirectoryPath A <code>File</code> instance for the path to the working directory. This argument
     *                             must represent the absolute path to the desired directory as returned by the
     *                             <code>File.getPath()</code> method. If null is passed, a
     *                             <code>NullPointerException</code> will be thrown.
     * @param repository           A <code>URL</code> instance for the repository to be cloned. If null is passed, a
     *                             <code>NullPointerException</code> will be thrown.
     * @param directory            A <code>File</code> instance for the directory where the repository is to be cloned.
     *                             If null is passed, a <code>NullPointerException</code> will be thrown.
     * @return The result of the git clone.
     * @throws IOException      There are many reasons for which an <code>IOException</code> may be thrown.
     *                          Examples include:
     *                          <ul>
     *                          <li>a directory doesn't exist</li>
     *                          <li>a command is not found on the PATH</li>
     *                          </ul>
     * @throws JavaGitException Thrown when there is an error executing git-clone.
     */
    public GitCloneResponseImpl clone(File workingDirectoryPath, JavaGitUrl repository, File directory)
            throws IOException, JavaGitException, URISyntaxException {
        return cloneProcess(workingDirectoryPath, null, repository, directory);
    }

    @Deprecated
    public GitCloneResponse clone(File workingDirectoryPath, GitCloneOptions options, URL repository, File directory) throws IOException, JavaGitException, URISyntaxException {
        return clone(workingDirectoryPath, options, UrlUtilities.url2JavaGitUrl(repository), directory);
    }

    /**
     * Process the git-clone command, to make a clone of the git repository.
     *
     *
     * @param workingDirectoryPath A <code>File</code> instance for the path to the working directory. This argument
     *                             must represent the absolute path to the desired directory as returned by the
     *                             <code>File.getPath()</code> method. If null is passed, a
     *                             <code>NullPointerException</code> will be thrown.
     * @param options              The options for the git-clone command. If the value is null, a
     *                             <code>NullPointerException</code> will be thrown.
     * @param repository           A <code>URL</code> instance for the repository to be cloned. If null is passed, a
     *                             <code>NullPointerException</code> will be thrown.
     * @param directory            A <code>File</code> instance for the directory where the repository is to be cloned.
     *                             If null is passed, a <code>NullPointerException</code> will be thrown.
     * @return The result of the git clone.
     * @throws IOException      There are many reasons for which an <code>IOException</code> may be thrown.
     *                          Examples include:
     *                          <ul>
     *                          <li>a directory doesn't exist</li>
     *                          <li>a command is not found on the PATH</li>
     *                          </ul>
     * @throws JavaGitException Thrown when there is an error executing git-clone.
     */
    public GitCloneResponseImpl clone(File workingDirectoryPath, GitCloneOptions options, JavaGitUrl repository,
                                      File directory) throws IOException, JavaGitException, URISyntaxException {
        return cloneProcess(workingDirectoryPath, options, repository, directory);
    }

    private GitCloneResponseImpl cloneProcess(File workingDirectoryPath, GitCloneOptions options,
                                             JavaGitUrl repository, File directory) throws IOException, JavaGitException, URISyntaxException {
        List<String> commandLine = buildCommand(options, repository, directory);
        GitCloneParser parser = new GitCloneParser();

        return (GitCloneResponseImpl) ProcessUtilities.runCommand(workingDirectoryPath, parser, new ProcessBuilder(commandLine));
    }

    /**
     * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
     *
     *
     * @param options    The options for the git-clone command. If the value is null, a
     *                   <code>NullPointerException</code> will be thrown.
     * @param repository A <code>URL</code> instance for the repository to be cloned. If null is passed, a
     *                   <code>NullPointerException</code> will be thrown.
     * @param directory  A <code>File</code> instance for the directory where the repository is to be cloned.
     *                   If null is passed, a <code>NullPointerException</code> will be thrown.
     * @return The result of the git clone.
     */
    protected List<String> buildCommand(GitCloneOptions options, JavaGitUrl repository, File directory) throws URISyntaxException {
        List<String> cmd = new ArrayList<String>();

        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("clone");
        cmd.add(repository.toString());

        if ( directory != null)
            cmd.add(directory.getAbsolutePath());

        return cmd;
    }

    /**
     * Implementation of the <code>IParser</code> interface in GitCloneParser class.
     */
    public class GitCloneParser implements IParser {

        private GitCloneResponseImpl response = new GitCloneResponseImpl();

        public CommandResponse getResponse() throws JavaGitException {
            if (response.isError()) {
                throw new JavaGitException(408000, ExceptionMessageMap.getMessage("408000") +
                        " - git clone error message: { " + response.getError() + " }");
            }
            return response;
        }

        public void parseLine(String line) {
            if (response.isError()) {
                if (line.startsWith("fatal:"))
                    response.addMsg(line);
            } else if (line.startsWith("fatal:")) {
                response.addMsg(line);
                response.setError(true);
            }
        }

        public void processExitCode(int code) {
            // TODO Auto-generated method stub

        }
    }
}
