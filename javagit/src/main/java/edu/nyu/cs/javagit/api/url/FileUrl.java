package edu.nyu.cs.javagit.api.url;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Description : GIT URL
 * Date: 2/23/14
 * Time: 7:32 PM
 */
public class FileUrl
        extends JavaGitUrl {

    public FileUrl(String file) throws JavaGitException {
        super(GitProtocol.FILE, null, null, null, file);
    }

}
