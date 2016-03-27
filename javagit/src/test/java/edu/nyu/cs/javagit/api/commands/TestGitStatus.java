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
package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.GitRepositoryBuilder;
import org.assertj.core.api.iterable.Extractor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class TestGitStatus extends TestBase {

    private static final String TEST_DIRECTORY_NAME = "testDirectory";
    private static final String FOOBAR_01_NAME = "foobar01";
    private static final String FOOBAR_02_NAME = "foobar02";
    private static final String FOOBAR_03_NAME = "foobar03";
    private File repositoryDirectory;
    private GitCommit gitCommit;
    private GitAdd gitAdd;
    private GitStatus gitStatus;
    private GitStatusOptions options;

    private File file1;
    private File file2;
    private File testDir;
    private File file3;

    @Before
    public void setUp() throws JavaGitException, IOException {
        super.setUp();

        repositoryDirectory = new GitRepositoryBuilder(getDeletor())
                .addFile(FOOBAR_01_NAME, "Test File1")
                .addFile(FOOBAR_02_NAME, "Test File2")
                .addFolder(TEST_DIRECTORY_NAME)
                    .addFile(FOOBAR_03_NAME, "Sample contents of foobar03 under testDir\n")
                .build();
        file1 = new File(repositoryDirectory, FOOBAR_01_NAME);

        file2 = new File(repositoryDirectory, FOOBAR_02_NAME);
        testDir = new File(repositoryDirectory, TEST_DIRECTORY_NAME);
        file3 = new File( new File(repositoryDirectory, TEST_DIRECTORY_NAME), FOOBAR_03_NAME);

        gitCommit = new GitCommit();
        gitAdd = new GitAdd();
        gitStatus = new GitStatus();
        options = new GitStatusOptions();
    }

    /**
     * Test for IOException where Repository Directory is invalid.
     *
     * @throws JavaGitException
     */
    @Test
    public void testIOExceptionThrownForInvalidRepositoryDirectory() throws JavaGitException {
        File tempRepoDirectory = new File("/_______non_existing_dir_______");
        try {
            gitStatus.status(tempRepoDirectory);
            fail("IOException not thrown for non-existing repoDirectory");
        } catch (IOException expected) {
            FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
        }
    }

    /**
     * Second Test for IOException being thrown where Repository directory does not exist and we try
     * to create a file in that directory.
     */

    @Test
    public void testIOExceptionThrownForInvalidRepositoryDirectory2() throws JavaGitException {
        File tempRepoDirectory = new File("/_______non_existing_dir________");
        try {
            // Create couple of file
            FileUtilities.createFile(tempRepoDirectory, FOOBAR_01_NAME, "Sample Contents");
            List<File> paths = null;
            gitStatus.status(repositoryDirectory, options, paths);
            fail("Failed to throw JavaGitException");
        } catch (IOException expected) {
            FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
        }
    }

    /**
     * Test for verifying the branch name of the repository on which git-status command is run
     *
     * @throws JavaGitException
     * @throws IOException
     */

    @Ignore("Should branch be obtained from git status ?")
    public void testBranch() throws JavaGitException, IOException {
        List<File> paths = null;
        GitStatusResponse response = gitStatus.status(repositoryDirectory, options, paths);
        String branch = response.getBranch().getName();
        assertEquals("Branch does not match", "master", branch);
    }


    /**
     * Test for files that will be committed next time &lt;git-commit&gt; is executed.
     *
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testReadyToCommitNewFiles() throws IOException, JavaGitException {
        List<File> filesToAdd = new ArrayList<>();
        filesToAdd.add(file1);
        filesToAdd.add(file2);
        filesToAdd.add(new File(TEST_DIRECTORY_NAME));
        filesToAdd.add(file3);
        GitAddOptions addOptions = new GitAddOptions();
        gitAdd.add(repositoryDirectory, addOptions, filesToAdd);
        List<File> statusPath = null;
        GitStatusResponse status = gitStatus.status(repositoryDirectory, options, statusPath);
        int noOfNewFilesToCommit = status.getNewFilesToCommitSize();
        assertEquals("Error. No of New Files to commit does not match", 3, noOfNewFilesToCommit);
        assertEquals("Error. Filename does not match", FOOBAR_01_NAME, status
                .getFileFromNewFilesToCommit(0).getName());
        assertEquals("Error. Filename does not match", FOOBAR_02_NAME, status
                .getFileFromNewFilesToCommit(1).getName());
        assertEquals("Error. Filename does not match", repositoryDirectory.getPath() +
                File.separator + TEST_DIRECTORY_NAME + File.separator + FOOBAR_03_NAME,
                status.getFileFromNewFilesToCommit(2).getAbsolutePath());
    }

    /**
     * Test for files that are indexed and have been modified but git-add or git-rm command need to be
     * run to get them ready for committing next time <git-commit> is executed.
     *
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testModifiedNotUpdatedFiles() throws IOException, JavaGitException {
        List<File> filesToAdd = new ArrayList<>();
        filesToAdd.add(file1);
        filesToAdd.add(file2);
        filesToAdd.add(new File(TEST_DIRECTORY_NAME));
        filesToAdd.add(file3 );
        GitAddOptions addOptions = new GitAddOptions();
        // Add the files for committing
        gitAdd.add(repositoryDirectory, addOptions, filesToAdd);
        // Commit the added files
        gitCommit.commit(repositoryDirectory, "Test commit of two files");
        // modify one of the committed files
        FileUtilities.modifyFileContents(file1, "Test append text\n");
        FileUtilities.modifyFileContents(file3, "Another sample text added to foobar03\n");
        List<File> statusPath = null;
        GitStatusResponse status = gitStatus.status(repositoryDirectory, options, statusPath);
        // run status to find the modified but not updated files
        checkModifiedFiles(status);
        if (file2.delete()) {
            status = gitStatus.status(repositoryDirectory, options, statusPath);
                assertEquals("No of deleted files not equal", 1, status.getDeletedFilesNotUpdatedSize());

            checkModifiedFiles(status);
            //    assertEquals("No of modified but not updated files not equal", 2, modifiedNotUpdatedFiles);
        } else {
            fail("Failed to delete file \"foobar02\"");
        }
    }

    @Test
    public void testAddedToIndexUnmodified()
            throws IOException, JavaGitException
    {

        File repository = new GitRepositoryBuilder(getDeletor())
                .addFile(FOOBAR_01_NAME, "Test File1").build();
        File foobar01 = new File(repository, FOOBAR_01_NAME);
        gitAdd.add(repository, foobar01);
        assertThat(gitStatus.status(repository).getNewFilesToCommit())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR_01_NAME);
    }

    @Test
    public void testAddedToIndexModified()
            throws IOException, JavaGitException
    {

        File repository = new GitRepositoryBuilder(getDeletor())
                .addFile(FOOBAR_01_NAME, "Test File1").build();
        File foobar01 = new File(repository, FOOBAR_01_NAME);
        gitAdd.add(repository, foobar01);
        FileUtilities.modifyFileContents(foobar01, "More text pleeeeease");
        assertThat(gitStatus.status(repository).getNewFilesToCommit())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR_01_NAME);
    }

    @Test
    public void testAddedToIndexDeleted()
            throws IOException, JavaGitException
    {

        File repository = new GitRepositoryBuilder(getDeletor())
                .addFile(FOOBAR_01_NAME, "Test File1").build();
        File foobar01 = new File(repository, FOOBAR_01_NAME);
        gitAdd.add(repository, foobar01);
        foobar01.delete();
        assertThat(gitStatus.status(repository).getNewFilesToCommit())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR_01_NAME);
    }

    /**
     * Test for new files that are created but not yet added by git-add or git-rm commands. These
     * files are under Untracked files section of the output.
     *
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testUntrackedNewFiles() throws IOException, JavaGitException {
        List<File> paths = null;
        GitStatusResponse response = gitStatus.status(repositoryDirectory, options, paths);
        assertEquals("Error.No of untracked files does not Match.", 3, response.getUntrackedFilesSize());
        assertEquals("Error. Filename does not match.", FOOBAR_01_NAME,
                response.getFileFromUntrackedFiles(0).getName());
        assertEquals("Error. Filename does not match.", FOOBAR_02_NAME,
                response.getFileFromUntrackedFiles(1).getName());
        assertEquals("Error. Filename does not match.", TEST_DIRECTORY_NAME,
                response.getFileFromUntrackedFiles(2).getName());
    }


    @Test
    public void testIgnoredFile()
            throws IOException, JavaGitException
    {
        FileUtilities.createFile(repositoryDirectory, ".gitignore", FOOBAR_01_NAME);
        assertThat(gitStatus.status(repositoryDirectory).getIgnoredFiles())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR_01_NAME);
    }

    private Extractor<File, String> getFileNameExtractor()
    {
        return new Extractor<File, String>()
            {
                @Override
                public String extract(File input)
                {
                    return input.getName();
                }
            };
    }

    private void checkModifiedFiles(GitStatusResponse status) throws JavaGitException {
        int modifiedNotUpdatedFiles = status.getModifiedFilesNotUpdatedSize();

        assertEquals("No of modified but not updated files not equal", 2, modifiedNotUpdatedFiles);

    }

    /**
     * Test for getSingleFileStatus
     *
     * @throws JavaGitException IOException
     */
    @Test
    public void testSingelFileStatus() throws JavaGitException, IOException {
        File file = FileUtilities.createFile(repositoryDirectory, "single_file_test", "Test File1");
        GitStatusResponse response = gitStatus.getSingleFileStatus(repositoryDirectory, file);
        assertEquals("File should be untracked.", 1, response.getUntrackedFilesSize());
    }


    /**
     * Test for files that are indexed and have been modified but git-add or git-rm command need to be
     * run to get them ready for committing next time <git-commit> is executed.
     *
     * @throws IOException
     * @throws JavaGitException
     */
    /**
     * @Test public void testModifiedFiles() throws IOException, JavaGitException {
     * List<File> filesToAdd = new ArrayList<File>();
     * //File file4 = new File(testDir.getAbsoluteFile() + File.separator + "foobar04");
     * //file4.createNewFile();
     * File file4 = FileUtilities.createFile(testDir, "foobar04", "Test File4");
     * <p/>
     * filesToAdd.add(file1);
     * filesToAdd.add(file2);
     * //filesToAdd.add(testDir);
     * //filesToAdd.add(file3);
     * filesToAdd.add(new File("testDirectory"));
     * filesToAdd.add(new File( "testDirectory" + File.separator + file3.getPath()));
     * //filesToAdd.add(file4);
     * filesToAdd.add(new File( "testDirectory" + File.separator + file4.getPath()));
     * <p/>
     * GitAddOptions addOptions = new GitAddOptions();
     * // Add the files for committing
     * gitAdd.add(repositoryDirectory, addOptions, filesToAdd);
     * // Commit the added files
     * gitCommit.commit(repositoryDirectory, "Test commit of two files");
     * // modify one of the committed files
     * FileUtilities.modifyFileContents(new File(testDir.getPath() + File.separator + file3.getPath()), "Test append text\n");
     * FileUtilities.modifyFileContents(new File(testDir.getPath() + File.separator + file4.getPath()), "Another sample text added to foobar03\n");
     * File tmpFile = new File( testDir.getName() + File.separator + file3.getPath());
     * try
     * {
     * GitAddResponse addResponse = gitAdd.add(repositoryDirectory, addOptions, tmpFile);
     * if ( addResponse.numberOfErrors() > 0 ) {
     * System.out.println(addResponse.getError(0));
     * }
     * GitStatusResponse response = gitStatus.status(repositoryDirectory, options);
     * assertEquals(1, response.getModifiedFilesNotUpdatedSize());
     * assertEquals("foobar04", response.getModifiedFilesNotUpdatedIterator().iterator().next().getName());
     * assertEquals(1, response.getModifiedFilesToCommitSize());
     * assertEquals("foobar03", response.getModifiedFilesToCommitIterator().iterator().next().getName());
     * } catch ( JavaGitException e ) {
     * e.printStackTrace();
     * } catch ( IOException e ){
     * e.printStackTrace();
     * }
     * }
     */

}
