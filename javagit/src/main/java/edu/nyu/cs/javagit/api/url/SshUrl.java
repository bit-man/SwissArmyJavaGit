package edu.nyu.cs.javagit.api.url;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Description : SSH URL
 * Date: 2/23/14
 * Time: 7:32 PM
 */
public class SshUrl
    extends JavaGitUrl {

    public SshUrl(String host, String user, int port, String file) throws JavaGitException {
        super(GitProtocol.SSH, user, host, port, file);
    }

    public SshUrl(String host, String file) throws JavaGitException {
        super(GitProtocol.SSH, null, host, 22, file);
    }
}
