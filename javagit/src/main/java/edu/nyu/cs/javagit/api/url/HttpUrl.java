package edu.nyu.cs.javagit.api.url;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Description : JavaGit HTTP URL
 * Date: 2/23/14
 * Time: 7:32 PM
 */
public class HttpUrl
        extends JavaGitUrl {

    public HttpUrl(String host, int port, String file) {
        super(GitProtocol.HTTP, null, host, port, file);
    }

    public HttpUrl(String host, String file) {
        super(GitProtocol.HTTP, null, host, 80, file);
    }

}
