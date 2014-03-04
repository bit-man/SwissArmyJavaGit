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
        JavaGitUrl.GitProtocol protocol;
        try {
            protocol = JavaGitUrl.GitProtocol.valueOf(url.getProtocol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JavaGitException(8, ExceptionMessageMap.getMessage("000008") + " (" + url.getProtocol() + ")", e);
        }

        switch (protocol) {
            case FILE :
                return new FileUrl(url.getFile());

            case FTPS:
                return new FtpsUrl(url.getHost(), url.getPort(), url.getFile());

            case FTP:
                return new FtpUrl(url.getHost(), url.getPort(), url.getFile());

            case GIT:
                return new GitUrl(url.getHost(), url.getPort(), url.getFile());

            case HTTPS:
                return new HttpsUrl(url.getHost(), url.getPort(), url.getFile());

            case HTTP:
                return  new HttpUrl(url.getHost(), url.getPort(), url.getFile());

            case RSYNC:
                return new RsyncUrl(url.getHost(), url.getFile());

            case SSH:
                return new SshUrl(url.getHost(), url.getUserInfo(), url.getPort(), url.getFile());

        }

        throw new JavaGitException(8, ExceptionMessageMap.getMessage("000008") + " (" + url.getProtocol() + ")");
    }

}
