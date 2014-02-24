package edu.nyu.cs.javagit.api.url;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Description : GIT URL
 * Date: 2/23/14
 * Time: 7:32 PM
 */
public class FtpsUrl
        extends JavaGitUrl {

    public FtpsUrl(String host, int port, String file) throws JavaGitException {
        super(GitProtocol.FTPS, null, host, port, file);
    }

    public FtpsUrl(String host, String file) throws JavaGitException {
        super(GitProtocol.FTPS, null, host, 115, file);
    }

}
