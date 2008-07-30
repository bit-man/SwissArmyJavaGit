/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.client.cli;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAdd;
import edu.nyu.cs.javagit.api.commands.GitBranch;
import edu.nyu.cs.javagit.api.commands.GitBranchOptions;
import edu.nyu.cs.javagit.api.commands.GitCommit;
import edu.nyu.cs.javagit.api.commands.GitLogOptions;
import edu.nyu.cs.javagit.api.commands.GitLogResponse;
import edu.nyu.cs.javagit.api.commands.GitLogResponse.Commit;
import edu.nyu.cs.javagit.client.cli.CliGitLog;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;


public class TestCliGitLog {
	private File repoDirectory;
	private GitCommit commit;
	private GitAdd add;

	@Before
	public void setUp() throws IOException, JavaGitException {
		repoDirectory = FileUtilities.createTempDirectory("GitLogTestRepo");
		HelperGitCommands.initRepo(repoDirectory);

		commit = new GitCommit();
		add = new GitAdd();

		File testFile = FileUtilities.createFile(repoDirectory, "log.txt", "README");
		// Add a file to the repo
		List<File> filesToAdd = new ArrayList<File>();
		filesToAdd.add(testFile);
		add.add(repoDirectory, null, filesToAdd);

		// Call commit
		commit.commit(repoDirectory, "Making a first test commit");
	}

	@After
	public void tearDown() throws JavaGitException {
		FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
	}

	/**
	 * 
	 * Test to verify if git logs is successfully parsing logs
	 * 
	 * @throws IOException
	 * @throws JavaGitException
	 */
	@Test
	public void testGitLogGettingLogs() throws IOException, JavaGitException {

		CliGitLog gitLog = new CliGitLog();
		GitLogOptions options = new GitLogOptions();
		options.setOptFileDetails(true);
		List<Commit> log = gitLog.log(repoDirectory, options);
		if(log.size()==0){
			fail("Failed retrieve logs");
		}

	}



}
