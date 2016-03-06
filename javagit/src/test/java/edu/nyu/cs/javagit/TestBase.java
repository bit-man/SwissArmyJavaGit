package edu.nyu.cs.javagit;

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.test.utilities.RealDeletor;
import edu.nyu.cs.javagit.test.utilities.TestProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import java.io.IOException;

/**
 * Description : Base class for testing
 * Date: 3/30/13
 * Time: 2:14 AM
 */
@Ignore
public class TestBase {

    private RealDeletor d = null;

    @Before
    public void setUp() throws IOException, JavaGitException {
        JavaGitConfiguration.setGitPath(TestProperty.GIT_PATH.asString());
    }


    @After
    public void tearDown() {
        try {
            getDeletor().delete();
        } catch (JavaGitException e) {
            e.printStackTrace();
        }
    }

    protected RealDeletor getDeletor() {
        if ( d == null)
            d = new RealDeletor();

        return d;
    }
}
