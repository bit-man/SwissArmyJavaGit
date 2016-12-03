package edu.nyu.cs.javagit.api;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.test.utilities.TestErrorException;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.TestProperty;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class TestJavaGitConfiguration
        extends TestBase
{

    private String pathString;
    private File pathFile;

    @Test(expected = NullPointerException.class)
    public void testSetNullPathShouldGenerateNPE()
            throws IOException, JavaGitException
    {
        pathString = null;
        JavaGitConfiguration.setGitPath(pathString);
        fail("Invalid path - this should never be reached.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyPathShouldGenerateIllegalArgumentException()
            throws IOException, JavaGitException
    {
        pathString = "";
        JavaGitConfiguration.setGitPath(pathString);
        fail("Invalid path - this should never be reached.");
    }

    @Test
    public void testSetEmptyPathShouldGenerateIOException()
    {
        pathFile = new File("");
        try
        {
            JavaGitConfiguration.setGitPath(pathFile);
            fail("Invalid path - this should never be reached.");
        } catch (IOException e)
        {
            // This is fine.
        } catch (Exception e)
        {
            fail("Empty path (String) generated wrong kind of exception: " + e.getClass().getName());
        }
    }

    @Test
    public void testSetFilePathInsteadOfFolderShouldGenerateJavaGitException()
    {
        try
        {
            pathFile = File.createTempFile("xyz", null);
            JavaGitConfiguration.setGitPath(pathFile);
            fail("Invalid path - this should never be reached.");
        } catch (JavaGitException e)
        {
            assertEquals(e.getCode(), 020002);
            pathFile.delete();
        } catch (Exception e)
        {
            fail("Non-directory path (File) generated wrong kind of exception: " + e.getClass().getName());
        }
    }

    @Test
    public void testSetFilePathWothNoGitExecInsideItShouldGenerateJavaGitException()
    {
        try
        {
            pathFile = FileUtilities.createTempDirectory("abc");
            JavaGitConfiguration.setGitPath(pathFile);
            fail("Invalid path - this should never be reached.");
        } catch (JavaGitException e)
        {
            assertEquals(e.getCode(), 100002);
            pathFile.delete();
        } catch (Exception e)
        {
            fail("Temp path not containing git generated wrong kind of exception: "
                    + e.getClass().getName());
        }
    }

    @Test
    public void testSetFilePathToNullWipesOutPreviouslySetGitPath()
    {
    /*
     * Set the path using null as the File argument. This is valid - it's saying: wipe out whatever
     * was set before (via previous calls to setGitPath) and just look on the PATH for git.
     */
        pathFile = null;
        try
        {
            JavaGitConfiguration.setGitPath(pathFile);
        } catch (Exception e)
        {
            fail("Null path (File) generated exception: " + e.getClass().getName());
        }
    }

    @Test
    public void testGrabbingGitVersionShoudlBeExceptionF5ee()
            throws JavaGitException
    {
        JavaGitConfiguration.getGitVersion();
    }

    @Test
    public void testPathSeparator_issue2()
            throws TestErrorException
    {
        try
        {
            JavaGitConfiguration.setGitPath(TestProperty.GIT_PATH.asString());
            JavaGitConfiguration.getGitVersion();
        } catch (Exception e)
        {
            throw new TestErrorException("May be you forgot to set property " + TestProperty.GIT_PATH.getName()
                    + "to a valid Git path ( value : '" + TestProperty.GIT_PATH.asString() + "' )", e);
        }
    }
}
