package edu.nyu.cs.javagit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.nyu.cs.javagit.api.TestRef;
import edu.nyu.cs.javagit.api.commands.internals.TestPorcelainField;
import edu.nyu.cs.javagit.api.commands.internals.TestPorcelainParser;
import edu.nyu.cs.javagit.api.commands.internals.TestTuple;
import edu.nyu.cs.javagit.test.api.url.TestJavaGitUrl;
import edu.nyu.cs.javagit.test.utilities.TestExceptionMessageMap;
import edu.nyu.cs.javagit.test.utilities.TestStringUtilities;
import edu.nyu.cs.javagit.test.utilities.TestUrlUtilities;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestJavaGitUrl.class, TestTuple.class, TestPorcelainField.class, TestPorcelainParser.class, TestRef.class,
		TestExceptionMessageMap.class, TestStringUtilities.class, TestUrlUtilities.class, })
public class UnitTestSuite {

}
