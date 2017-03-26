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

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.client.GitStatusResponseImpl;
import edu.nyu.cs.javagit.client.IGitStatus;
import edu.nyu.cs.javagit.client.parser.GitStatusParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Command-line implementation of the <code>IGitStatus</code> interface.
 */
public class CliGitStatus
        implements IGitStatus
{

    private final IGitProcessBuilder processBuilder;
    private final Validator validator;
    private final ICommandRunner<GitStatusResponseImpl> commandRunner;
    private final IParser parser;

    CliGitStatus(IGitProcessBuilder processBuilder, Validator validator,
                 ICommandRunner<GitStatusResponseImpl> commandRunner, IParser parser)
    {
        this.processBuilder = processBuilder;
        this.validator = validator;
        this.commandRunner = commandRunner;
        this.parser = parser;
    }

    /**
     * Implementation of <code>IGitStatus</code> method for getting the status of a list of files
     */
    public GitStatusResponse status(File repositoryPath, GitStatusOptions options, List<File> paths)
            throws JavaGitException, IOException
    {
        validator.checkNullArgument(repositoryPath, "RepositoryPath");
        validator.checkFileValidity(repositoryPath);
        processBuilder.setCommandLine(buildCommandLine(options, paths));
        parser.setWorkingDir(repositoryPath.getPath() + File.separator);
        commandRunner.setWorkingDirectory(repositoryPath);
        commandRunner.setParser(parser);
        commandRunner.setProcessBuilder(processBuilder);
        return commandRunner.run();
    }

    /**
     * Implementation of <code>IGitStatus</code> method for getting the status of a file.
     */
    public GitStatusResponse status(File repositoryPath, GitStatusOptions options, File file)
            throws JavaGitException, IOException
    {
        List<File> paths = new ArrayList<>();
        paths.add(file);
        return status(repositoryPath, options, paths);
    }

    /**
     * Implementation of <code>IGitStatus</code> method with only options passed to &lt;git-status&gt; command.
     */
    public GitStatusResponse status(File repositoryPath, GitStatusOptions options)
            throws JavaGitException, IOException
    {
        return status(repositoryPath, options, (List<File>) null);
    }

    /**
     * Implementation of <code>IGitStatus</code> method with file-paths passed to &lt;git-status&gt; command.
     */
    public GitStatusResponse status(File repositoryPath, List<File> paths)
            throws JavaGitException,
            IOException
    {
        return status(repositoryPath, null, paths);
    }

    /**
     * Implementation of <code>IGitStatus</code> method for getting the status of repository
     * with no options or files provided.
     */
    public GitStatusResponse status(File repositoryPath)
            throws JavaGitException, IOException
    {
        return status(repositoryPath, null, (List<File>) null);
    }

    /**
     * Implementation of <code>IGitStatus</code> method with options set to all(-a)
     */
    public GitStatusResponse statusAll(File repositoryPath)
            throws JavaGitException, IOException
    {
        GitStatusOptions options = new GitStatusOptions();
        options.setOptAll(true);
        return status(repositoryPath, options);
    }

    /**
     * Return status for a single <code>File</code>
     *
     * @param repositoryPath Directory path to the root of the repository.
     * @param options        Options that are passed to &lt;git-status&gt; command.
     * @param file           <code>File</code> instance
     * @return <code>GitStatusResponse</code> object
     * @throws JavaGitException Exception thrown if the repositoryPath is null
     * @throws IOException      Exception is thrown if any of the IO operations fail.
     */
    public GitStatusResponse getSingleFileStatus(File repositoryPath, GitStatusOptions options, File file)
            throws JavaGitException, IOException
    {
        validator.checkNullArgument(repositoryPath, "RepositoryPath");
        validator.checkFileValidity(repositoryPath);
        processBuilder.setCommandLine(buildCommandLine(options, null));
        GitStatusParser parser = new GitStatusParser(file);
        parser.setWorkingDir(repositoryPath.getPath() + File.separator);
        commandRunner.setWorkingDirectory(repositoryPath);
        commandRunner.setParser(parser);
        commandRunner.setProcessBuilder(processBuilder);
        return commandRunner.run();
    }

    /**
     * Parses options provided by the <code>GitStatusOptions</code> object and adds them to the
     * command.
     *
     * @param options <code>GitStatusOptions</code> provided by &lt;gitclipse&gt;.
     * @param paths   List of file paths.
     * @return command to be executed.
     */
    private List<String> buildCommandLine(GitStatusOptions options, List<File> paths)
    {
        List<String> command = new ArrayList<>();

        command.add(JavaGitConfiguration.getGitCommand());
        command.add("status");
        command.add("--porcelain");
        command.add("--ignored");

        if (options != null)
        {
            setOptions(command, options);
        }

        if (paths != null)
        {
            for (File file : paths)
            {
                command.add(file.getPath());
            }
        }
        return command;
    }

    private void setOptions(List<String> argsList, GitStatusOptions options)
    {
        if (options.isOptAll())
        {
            argsList.add("-a");
        }
        if (options.isOptQuiet())
        {
            argsList.add("-q");
        }
        if (options.isOptVerbose())
        {
            argsList.add("-v");
        }
        if (options.isOptSignOff())
        {
            argsList.add("-s");
        }
        if (options.isOptEdit())
        {
            argsList.add("-e");
        }
        if (options.isOptInclude())
        {
            argsList.add("-i");
        }
        if (options.isOptOnly())
        {
            argsList.add("-o");
        }
        if (options.isOptNoVerify())
        {
            argsList.add("-n");
        }
        if (options.isOptUntrackedFiles())
        {
            argsList.add("--untracked-files");
        }
        if (options.isOptAllowEmpty())
        {
            argsList.add("--allow-empty");
        }
        if (!options.isOptReadFromLogFileNull())
        {
            argsList.add("-F");
            argsList.add(options.getOptReadFromLogFile().getPath());
        }
        if (!options.isAuthorNull())
        {
            argsList.add("--author");
            argsList.add(options.getAuthor());
        }
    }
}

