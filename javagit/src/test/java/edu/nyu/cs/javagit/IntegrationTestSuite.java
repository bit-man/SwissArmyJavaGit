package edu.nyu.cs.javagit;

import edu.nyu.cs.javagit.api.*;
import edu.nyu.cs.javagit.api.commands.*;
import edu.nyu.cs.javagit.client.cli.*;
import edu.nyu.cs.javagit.test.utilities.TestCheckUtilities;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestCliGitBranch.class, TestCliGitClone.class, TestCliGitPull.class, TestCliGitInit.class, TestCliGitMv.class,
		TestCliGitCommit.class, TestCliGitAdd.class, TestGitAddResponseImpl.class, TestCliGitLog.class, TestWorkingTree.class, TestDotGit.class,
		TestBranching.class, TestGitAdd.class, TestGitStatus.class, TestGitMvResponse.class, TestGitFetch.class, TestGitBranch.class,
		TestGitCheckout.class, TestGitAddResponse.class, TestGitCheckoutResponse.class, TestGitCommitResponse.class,
		TestGitVersion.class, TestGitResetOptions.class, TestGitMv.class, TestGitFetchOptions.class, TestGitCommit.class, TestGitStatusOptions.class,
		TestGitFileSystem.class, TestJavaGitConfiguration.class, TestCheckUtilities.class, })
public class IntegrationTestSuite {

}
