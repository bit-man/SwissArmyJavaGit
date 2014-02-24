package edu.nyu.cs.javagit.api.url;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Description : GIT URL
 * Date: 2/23/14
 * Time: 7:32 PM
 */
public class HttpsUrl
        extends JavaGitUrl {

    public HttpsUrl(String host, int port, String file) throws JavaGitException {
        super(GitProtocol.HTTPS, null, host, port, file);
    }

    public HttpsUrl(String host, String file) throws JavaGitException {
        super(GitProtocol.HTTPS, null, host, 443, file);
    }

}
