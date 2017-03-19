package edu.nyu.cs.javagit;

import edu.nyu.cs.javagit.api.TestRef;
import edu.nyu.cs.javagit.api.commands.internals.TestPorcelainField;
import edu.nyu.cs.javagit.api.commands.internals.TestPorcelainParser;
import edu.nyu.cs.javagit.api.commands.internals.TestTuple;
import edu.nyu.cs.javagit.client.cli.CliGitStatusTest;
import edu.nyu.cs.javagit.client.cli.TestCommandRunner;
import edu.nyu.cs.javagit.test.api.url.TestJavaGitUrl;
import edu.nyu.cs.javagit.test.utilities.TestExceptionMessageMap;
import edu.nyu.cs.javagit.test.utilities.TestStringUtilities;
import edu.nyu.cs.javagit.test.utilities.TestUrlUtilities;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestJavaGitUrl.class, TestTuple.class, TestPorcelainField.class, TestPorcelainParser.class, TestRef.class,
        TestExceptionMessageMap.class, TestStringUtilities.class, TestUrlUtilities.class, TestCommandRunner.class,
        CliGitStatusTest.class})
public class UnitTestSuite {

}
