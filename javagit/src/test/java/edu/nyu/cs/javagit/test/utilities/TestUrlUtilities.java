package edu.nyu.cs.javagit.test.utilities;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.url.*;
import edu.nyu.cs.javagit.utilities.UrlUtilities;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Description : URL utilities testing
 * Date: 03-Mar-2014
 * Time: 7:43 PM
 */
public class TestUrlUtilities {

    @Test
    public void testFTP() throws MalformedURLException, JavaGitException {
        FtpUrl url = (FtpUrl) UrlUtilities.url2JavaGitUrl(new URL("ftp", "host", "file"));
        assertEquals("ftp://host:21/file", url.toString());
    }

    @Test(expected = MalformedURLException.class)
    public void testFTPs() throws MalformedURLException, JavaGitException {
        // FTPs is an unknown protocol to Java unless a custom handler is added
        new URL("ftps", "host", "file");
    }

    @Test
    public void testFile() throws MalformedURLException, JavaGitException {
        FileUrl url = (FileUrl) UrlUtilities.url2JavaGitUrl(new URL("file", null, "myFile"));
        assertEquals("file:///myFile", url.toString());
    }

    @Test
    public void testHttps() throws MalformedURLException, JavaGitException {
        HttpsUrl url = (HttpsUrl) UrlUtilities.url2JavaGitUrl(new URL("https", "host", "myFile"));
        assertEquals("https://host:443/myFile", url.toString());
    }

    @Test
    public void testHttp() throws MalformedURLException, JavaGitException {
        HttpUrl url = (HttpUrl) UrlUtilities.url2JavaGitUrl(new URL("http", "host", "myFile"));
        assertEquals("http://host:80/myFile", url.toString());
    }

    @Test(expected = MalformedURLException.class)
    public void testSSH() throws MalformedURLException, JavaGitException {

        // SSH is an unknown protocol to Java unless a custom handler is added
        new URL("ssh", "host", "myFile");
    }

    @Test(expected = MalformedURLException.class)
    public void testRsync() throws MalformedURLException, JavaGitException {

        // RSYNC is an unknown protocol to Java unless a custom handler is added
        new URL("rsync", "host", "myFile");
    }


    @Test(expected = MalformedURLException.class)
    public void testGit() throws MalformedURLException, JavaGitException {
        // GIT is an unknown protocol to Java unless a custom handler is added
        new URL("git", "host", "myFile");
    }
}
