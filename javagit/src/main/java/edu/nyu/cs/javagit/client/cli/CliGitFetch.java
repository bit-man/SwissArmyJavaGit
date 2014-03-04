package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.api.commands.GitFetchOptions;
import edu.nyu.cs.javagit.api.commands.GitFetchResponse;
import edu.nyu.cs.javagit.api.url.FileUrl;
import edu.nyu.cs.javagit.api.url.JavaGitUrl;
import edu.nyu.cs.javagit.client.IGitFetch;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;
import edu.nyu.cs.javagit.utilities.StringUtilities;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Command-line implementation of the <code>IGitFetch</code> interface
 */
public class CliGitFetch implements IGitFetch {
    public GitFetchResponse fetch(File clonedRepository, JavaGitUrl repoPath, GitFetchOptions options) throws JavaGitException, IOException {
        List<String> commandLine = buildCommand(options, repoPath, null, (String) null);
        GitFetchParser parser = new GitFetchParser();

        return (GitFetchResponse) ProcessUtilities.runCommand(clonedRepository, commandLine, parser);
    }

    private List<String> buildCommand(GitFetchOptions options, JavaGitUrl repoPath, Ref ref, Set<String> groups, Set<JavaGitUrl> repos) {
        // Avoids issues on stderr redirection to stdout
        // (http://stackoverflow.com/questions/4062862/git-stderr-output-cant-pipe)
        options.setProgress();

        final List<String> cmd = options.getOptionArgs();
        cmd.add(0, JavaGitConfiguration.getGitCommand());
        cmd.add(1, "fetch");
        cmd.add( cmd.size(), repoPath.toString() );

        if (ref != null) {
            cmd.add( cmd.size(), ref.toString());
        }

        if (groups != null) {
            for (String g : groups) {
                cmd.add(cmd.size(), g);
            }
        }

        if (repos != null) {
            for (JavaGitUrl url : repos) {
                cmd.add(cmd.size(), url.toString());
            }
        }

        return cmd;
    }

    public GitFetchResponse fetch(File clonedRepository, JavaGitUrl repoPath, GitFetchOptions options, Ref ref) throws JavaGitException, IOException {
        List<String> commandLine = buildCommand(options, repoPath, ref, (String) null);
        GitFetchParser parser = new GitFetchParser();

        return (GitFetchResponse) ProcessUtilities.runCommand(clonedRepository, commandLine, parser);
    }

    public GitFetchResponse fetch(File clonedRepository, JavaGitUrl repoPath, GitFetchOptions options, String group) throws JavaGitException, IOException {
        List<String> commandLine = buildCommand(options, repoPath, null, group);
        GitFetchParser parser = new GitFetchParser();

        return (GitFetchResponse) ProcessUtilities.runCommand(clonedRepository, commandLine, parser);
    }

    private List<String> buildCommand(GitFetchOptions options, JavaGitUrl repoPath, Ref ref, String group) {
        Set<String> groups = new HashSet<String>();

        if ( group != null )
            groups.add(group);
        return buildCommand(options, repoPath, ref, groups, null);
    }

    public GitFetchResponse fetch(File clonedRepository, File repoPath, GitFetchOptions options, Set<JavaGitUrl> repository, Set<String> group)
            throws JavaGitException, IOException {

        if ( ! options.isMultiple() )
            throw new JavaGitException(414001, ExceptionMessageMap.getMessage("414001"));

        List<String> commandLine = buildCommand(options, new FileUrl(repoPath), null, group, repository);
        GitFetchParser parser = new GitFetchParser();

        return (GitFetchResponse) ProcessUtilities.runCommand(clonedRepository, commandLine, parser);
    }

    private class GitFetchParser
        implements IParser {

        private GitFetchResponse cmdResponse;
        private boolean parsingFrom;
        private String currentSource;

        public GitFetchParser() {
            cmdResponse = new GitFetchResponse();
            parsingFrom = false;
        }

        public void parseLine(String line) {

            /***
             * Sample output
             *
             * remote: Counting objects: 3, done.
             * remote: Compressing objects: 100% (2/2), done.
             * remote: Total 2 (delta 0), reused 0 (delta 0)
             * Unpacking objects: 100% (2/2), done.
             * From /tmp/test
             *     75769a4..34c2cc5  master     -> origin/master
             *     [new branch]      t2         -> origin/t2
             */
            if (line.startsWith("remote:")) {
                // ToDo (bit-man) parse  "remote: Total 2 (delta 0), reused 0 (delta 0)"
                if (StringUtilities.obtainElement(1, line, ' ').equals("Counting")) {
                    final String numObjects = StringUtilities.obtainElement(3, line, ' ');
                    cmdResponse.setObjectsTransfered(Integer.parseInt(numObjects.replace(",", "")));
                }
            } else  if (line.startsWith("From ")) {
                parsingFrom = true;
                final char blank = ' ';
                currentSource = line.substring(StringUtilities.indexOfNChar(line, blank, 1)).trim();
                cmdResponse.addSource(currentSource);
            } else if (parsingFrom) {
                if (line.startsWith(" "))
                    cmdResponse.addNews(currentSource, line.trim());
                else
                    parsingFrom = false;

            }
        }

        public void processExitCode(int code) {

        }

        public CommandResponse getResponse() throws JavaGitException {
            return cmdResponse;
        }
    }
}
