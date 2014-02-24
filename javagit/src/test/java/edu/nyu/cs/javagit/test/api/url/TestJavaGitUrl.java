package edu.nyu.cs.javagit.test.api.url;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.url.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Description : JavaGitUrl (and child classes) tests
 * Date: 2/23/14
 * Time: 9:13 PM
 */
public class TestJavaGitUrl
        extends TestBase {

    @Test
    public void testSshConstructor() throws JavaGitException {
        final JavaGitUrl url = new SshUrl( "host", "user", 100, "myFile");
        assertEquals("ssh://user@host:100/myFile", url.toString());
    }

    @Test
    public void testSshConstructor2() throws JavaGitException {
        final JavaGitUrl url = new SshUrl( "host", "myFile");
        assertEquals("ssh://host:22/myFile", url.toString());
    }


    @Test
    public void testGitConstructor() throws JavaGitException {
        final JavaGitUrl url = new GitUrl( "host", 100, "myFile");
        assertEquals("git://host:100/myFile", url.toString());
    }

    @Test
    public void testGitConstructorNoPort() throws JavaGitException {
        final JavaGitUrl url = new GitUrl( "host",  "myFile");
        assertEquals("git://host/myFile", url.toString());
    }


    @Test
    public void testHttpConstructor() throws JavaGitException {
        final JavaGitUrl url = new HttpUrl( "host", 100, "myFile");
        assertEquals("http://host:100/myFile", url.toString());
    }

    @Test
    public void testHttpConstructorNoPort() throws JavaGitException {
        final JavaGitUrl url = new HttpUrl( "host",  "myFile");
        assertEquals("http://host:80/myFile", url.toString());
    }

    @Test
    public void testHttpsConstructor() throws JavaGitException {
        final JavaGitUrl url = new HttpsUrl( "host", 100, "myFile");
        assertEquals("https://host:100/myFile", url.toString());
    }

    @Test
    public void testHttpsConstructorNoPort() throws JavaGitException {
        final JavaGitUrl url = new HttpsUrl( "host",  "myFile");
        assertEquals("https://host:443/myFile", url.toString());
    }


    @Test
    public void testFtpConstructor() throws JavaGitException {
        final JavaGitUrl url = new FtpUrl( "host", 100, "myFile");
        assertEquals("ftp://host:100/myFile", url.toString());
    }

    @Test
    public void testFtpConstructorNoPort() throws JavaGitException {
        final JavaGitUrl url = new FtpUrl( "host",  "myFile");
        assertEquals("ftp://host:21/myFile", url.toString());
    }

    @Test
    public void testFtpsConstructor() throws JavaGitException {
        final JavaGitUrl url = new FtpsUrl( "host", 100, "myFile");
        assertEquals("ftps://host:100/myFile", url.toString());
    }

    @Test
    public void testFtpsConstructorNoPort() throws JavaGitException {
        final JavaGitUrl url = new FtpsUrl( "host",  "myFile");
        assertEquals("ftps://host:115/myFile", url.toString());
    }


    @Test
    public void testRsyncConstructor() throws JavaGitException {
        final JavaGitUrl url = new RsyncUrl( "host",  "myFile");
        assertEquals("rsync://host/myFile", url.toString());
    }

    @Test
    public void testFileConstructor() throws JavaGitException {
        final JavaGitUrl url = new FileUrl("myFile");
        assertEquals("file:///myFile", url.toString());
    }


}
