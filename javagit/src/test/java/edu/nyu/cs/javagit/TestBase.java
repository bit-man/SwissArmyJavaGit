package edu.nyu.cs.javagit;

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.utilities.TestProperty;
import junit.framework.TestCase;
import org.junit.Before;

import java.io.IOException;

/**
 * Description : Base class for testing
 * Date: 3/30/13
 * Time: 2:14 AM
 */
public class TestBase extends TestCase {

    @Before
    public void setUp() throws IOException, JavaGitException {
        JavaGitConfiguration.setGitPath(TestProperty.GIT_PATH.asString());
    }
}
