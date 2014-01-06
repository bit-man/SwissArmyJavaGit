package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.api.commands.GitFetchOptions;
import edu.nyu.cs.javagit.api.commands.GitFetchResponse;
import edu.nyu.cs.javagit.client.IGitFetch;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;
import edu.nyu.cs.javagit.utilities.StringUtilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Command-line implementation of the <code>IGitFetch</code> interface
 */
public class CliGitFetch implements IGitFetch {
    @Override
    public GitFetchResponse fetch(File clonedRepository, URL repoPath, GitFetchOptions options) throws JavaGitException, IOException {
        List<String> commandLine = buildCommand(options, repoPath.getPath(), null, (String) null);
        GitFetchParser parser = new GitFetchParser();

        return (GitFetchResponse) ProcessUtilities.runCommand(clonedRepository, commandLine, parser);
    }

    private List<String> buildCommand(GitFetchOptions options, String repoPath, Ref ref, Set<String> groups) {
        // Avoids issues on stderr redirection to stdout
        // (http://stackoverflow.com/questions/4062862/git-stderr-output-cant-pipe)
        if ( ! options.getProgress())
            options.setProgress();

        final List<String> cmd = options.getOptionArgs();
        cmd.add(0, JavaGitConfiguration.getGitCommand());
        cmd.add(1, "fetch");
        cmd.add( cmd.size(), repoPath );

        if (ref != null)
            cmd.add( cmd.size(), ref.toString());

        for( String g : groups )
            cmd.add( cmd.size(), g);

        return cmd;
    }

    @Override
    public GitFetchResponse fetch(File clonedRepository, URL repoPath, GitFetchOptions options, URL repository, Ref ref) throws JavaGitException, IOException {
        List<String> commandLine = buildCommand(options, repoPath.getPath(), ref, (String) null);
        GitFetchParser parser = new GitFetchParser();

        return (GitFetchResponse) ProcessUtilities.runCommand(clonedRepository, commandLine, parser);
    }

    @Override
    public GitFetchResponse fetch(File clonedRepository, URL repoPath, GitFetchOptions options, String group) throws JavaGitException, IOException {
        List<String> commandLine = buildCommand(options, repoPath.toString(), null, group);
        GitFetchParser parser = new GitFetchParser();

        return (GitFetchResponse) ProcessUtilities.runCommand(clonedRepository, commandLine, parser);
    }

    private List<String> buildCommand(GitFetchOptions options, String repoPath, Ref ref, String group) {
        Set<String> groups = new HashSet<String>();

        if ( group != null )
            groups.add(group);
        return buildCommand(options, repoPath, ref, groups);
    }

    @Override
    public GitFetchResponse fetch(File clonedRepository, File repoPath, GitFetchOptions options, Set<URL> repository, Set<String> group)
            throws JavaGitException, IOException {

        if ( ! options.isMultiple() )
            throw new JavaGitException(414001, ExceptionMessageMap.getMessage("414001"));

        List<String> commandLine = buildCommand(options, repoPath.toString(), null, group);
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

        @Override
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
                // ToDo (bit-man) parse "remote: Compressing objects: 100% (2/2), done."
                //                and   "remote: Total 2 (delta 0), reused 0 (delta 0)"
                if (StringUtilities.obtainElement(1, line, ' ').equals("Counting")) {
                    final String numObjects = StringUtilities.obtainElement(3, line, ' ');
                    cmdResponse.setObjectsTransfered(Integer.parseInt(numObjects.replace(",", "")));
                }

            } else  if (line.startsWith("From ")) {
                parsingFrom = true;
                final char blank = ' ';
                final String url = line.substring(StringUtilities.indexOfNChar(line, blank, 1)).trim();
                currentSource = url;
                cmdResponse.addSource(currentSource);
            } else if (parsingFrom) {
                if (line.startsWith(" "))
                    cmdResponse.addNews(currentSource, line.trim());
                else
                    parsingFrom = false;

            }
        }

        private String protocolizeUrl(String url) {
            if ( url.startsWith("/"))
                return "file:" + url;
            else
                return url;
        }

        @Override
        public void processExitCode(int code) {

        }

        @Override
        public CommandResponse getResponse() throws JavaGitException {
            return cmdResponse;
        }
    }
}
