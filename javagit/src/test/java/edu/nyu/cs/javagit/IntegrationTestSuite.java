package edu.nyu.cs.javagit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.nyu.cs.javagit.api.TestBranching;
import edu.nyu.cs.javagit.api.TestDotGit;
import edu.nyu.cs.javagit.api.TestGitFileSystem;
import edu.nyu.cs.javagit.api.TestJavaGitConfiguration;
import edu.nyu.cs.javagit.api.TestWorkingTree;
import edu.nyu.cs.javagit.api.commands.TestGitAdd;
import edu.nyu.cs.javagit.api.commands.TestGitAddResponse;
import edu.nyu.cs.javagit.api.commands.TestGitBranch;
import edu.nyu.cs.javagit.api.commands.TestGitCheckout;
import edu.nyu.cs.javagit.api.commands.TestGitCheckoutResponse;
import edu.nyu.cs.javagit.api.commands.TestGitCommit;
import edu.nyu.cs.javagit.api.commands.TestGitCommitResponse;
import edu.nyu.cs.javagit.api.commands.TestGitFetch;
import edu.nyu.cs.javagit.api.commands.TestGitFetchOptions;
import edu.nyu.cs.javagit.api.commands.TestGitMv;
import edu.nyu.cs.javagit.api.commands.TestGitMvResponse;
import edu.nyu.cs.javagit.api.commands.TestGitResetOptions;
import edu.nyu.cs.javagit.api.commands.TestGitStatus;
import edu.nyu.cs.javagit.api.commands.TestGitStatusOptions;
import edu.nyu.cs.javagit.api.commands.TestGitStatusResponse;
import edu.nyu.cs.javagit.api.commands.TestGitVersion;
import edu.nyu.cs.javagit.client.cli.TestCliGitAdd;
import edu.nyu.cs.javagit.client.cli.TestCliGitBranch;
import edu.nyu.cs.javagit.client.cli.TestCliGitClone;
import edu.nyu.cs.javagit.client.cli.TestCliGitCommit;
import edu.nyu.cs.javagit.client.cli.TestCliGitInit;
import edu.nyu.cs.javagit.client.cli.TestCliGitLog;
import edu.nyu.cs.javagit.client.cli.TestCliGitMv;
import edu.nyu.cs.javagit.client.cli.TestCliGitPull;
import edu.nyu.cs.javagit.client.cli.TestGitAddResponseImpl;
import edu.nyu.cs.javagit.test.utilities.TestCheckUtilities;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestCliGitBranch.class, TestCliGitClone.class, TestCliGitPull.class, TestCliGitInit.class, TestCliGitMv.class,
		TestCliGitCommit.class, TestCliGitAdd.class, TestGitAddResponseImpl.class, TestCliGitLog.class, TestWorkingTree.class, TestDotGit.class,
		TestBranching.class, TestGitAdd.class, TestGitStatus.class, TestGitMvResponse.class, TestGitFetch.class, TestGitBranch.class,
		TestGitCheckout.class, TestGitAddResponse.class, TestGitCheckoutResponse.class, TestGitCommitResponse.class, TestGitStatusResponse.class,
		TestGitVersion.class, TestGitResetOptions.class, TestGitMv.class, TestGitFetchOptions.class, TestGitCommit.class, TestGitStatusOptions.class,
		TestGitFileSystem.class, TestJavaGitConfiguration.class, TestCheckUtilities.class, })
public class IntegrationTestSuite {

}
