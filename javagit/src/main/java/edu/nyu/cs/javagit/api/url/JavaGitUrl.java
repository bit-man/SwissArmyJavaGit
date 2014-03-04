package edu.nyu.cs.javagit.api.url;

/**
 * Description : URLs for Git
 * Date: 2/23/14
 * Time: 7:05 PM
 */
public abstract class JavaGitUrl {

    private final GitProtocol protocol;
    private final String host;
    private final Integer port;
    private final String file;
    private final String user;


    /**
     *
     * @param protocol URL protocol
     * @param user URL user (null if not specified)
     * @param host host to connect  (or null if not specified)
     * @param port port to connect  (null or -1 if not specified)
     * @param file  file portion (null if not specified)
     */
    protected JavaGitUrl(GitProtocol protocol, String user, String host, Integer port, String file) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.file = file;
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(protocol.name().toLowerCase());
        builder.append("://");

        if (user != null)   {
            builder.append(user);
            builder.append("@");
        }

        if (host != null) {
            builder.append(host);
        }

        if (port != null && ! port.equals(-1)) {
            builder.append(":");
            builder.append(port);
        }

        if (file != null) {
            if ( ! file.startsWith("/")) {
                builder.append("/");
            }
            builder.append(file);
        }

        return  builder.toString();
    }

    public enum GitProtocol {
        SSH, GIT, HTTP, HTTPS, FTP, FTPS, RSYNC, FILE;
    }
}
