package edu.nyu.cs.javagit.api.url;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Description : JavaGit GIT URL
 * Date: 2/23/14
 * Time: 7:32 PM
 */
public class GitUrl
        extends JavaGitUrl {

    public GitUrl(String host, int port, String file) {
        super(GitProtocol.GIT, null, host, port, file);
    }

    public GitUrl(String host, String file)  {
        super(GitProtocol.GIT, null, host, null, file);
    }

}
