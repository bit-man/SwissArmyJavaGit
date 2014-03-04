package edu.nyu.cs.javagit.utilities;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.url.*;

import java.net.URL;

/**
 * Description : URL Utilities
 * Date: 03-Mar-2014
 * Time: 6:42 PM
 */
public class UrlUtilities {

    public static JavaGitUrl url2JavaGitUrl(URL url) throws JavaGitException {

        // Protocol Validation
        JavaGitUrl.GitProtocol protocol = null;
        try {
            protocol = JavaGitUrl.GitProtocol.valueOf(url.getProtocol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JavaGitException(8, ExceptionMessageMap.getMessage("000008") + " (" + url.getProtocol() + ")", e);
        }

        switch (protocol) {
            case FILE :
                return new FileUrl(url.getFile());

            case FTPS:
                return url.getPort() == -1 ?
                        new FtpsUrl(url.getHost(), url.getFile()) :
                        new FtpsUrl(url.getHost(), url.getPort(), url.getFile());

            case FTP:
                return url.getPort() == -1 ?
                        new FtpUrl(url.getHost(), url.getFile()) :
                        new FtpUrl(url.getHost(), url.getPort(), url.getFile());

            case GIT:
                return url.getPort() == -1 ?
                        new GitUrl(url.getHost(), url.getFile()) :
                        new GitUrl(url.getHost(), url.getPort(), url.getFile());

            case HTTPS:
                return url.getPort() == -1 ?
                        new HttpsUrl(url.getHost(), url.getFile()) :
                        new HttpsUrl(url.getHost(), url.getPort(), url.getFile());

            case HTTP:
                return url.getPort() == -1 ?
                        new HttpUrl(url.getHost(), url.getFile()) :
                        new HttpUrl(url.getHost(), url.getPort(), url.getFile());

            case RSYNC:
                return new RsyncUrl(url.getHost(), url.getFile());

            case SSH:
                return url.getPort() == -1 ?
                        new SshUrl(url.getHost(), url.getFile()) :
                        new SshUrl(url.getHost(), url.getUserInfo(), url.getPort(), url.getFile());

            default:
                return null;
        }

    }

}
