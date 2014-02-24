package edu.nyu.cs.javagit.api.url;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Description : GIT URL
 * Date: 2/23/14
 * Time: 7:32 PM
 */
public class RsyncUrl
        extends JavaGitUrl {

    public RsyncUrl(String host, String file) throws JavaGitException {
        super(GitProtocol.RSYNC, null, host, null, file);
    }

}
