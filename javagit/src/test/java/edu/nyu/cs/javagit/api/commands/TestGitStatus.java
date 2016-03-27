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
import edu.nyu.cs.javagit.test.utilities.TestFile;
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

public class TestGitStatus
        extends TestBase
{

    private static final TestFile TEST_DIRECTORY = new TestFile("testDirectory");
    private static final TestFile FOOBAR01 = new TestFile("foobar01");
    private static final TestFile FOOBAR02 = new TestFile("foobar02");
    private static final TestFile FOOBAR03 = new TestFile("foobar03");

    private File repositoryDirectory;
    private GitCommit gitCommit;
    private GitAdd gitAdd;
    private GitStatus gitStatus;
    private GitStatusOptions options;

    private File file1;
    private File file2;
    private File file3;
    private GitRepositoryBuilder repositoryBuilder;

    @Before
    public void setUp()
            throws JavaGitException, IOException
    {
        super.setUp();

        repositoryDirectory = new GitRepositoryBuilder(getDeletor())
                .addFile(FOOBAR01.getName(), "Test File1")
                .addFile(FOOBAR02.getName(), "Test File2")
                .addFolder(TEST_DIRECTORY.getName())
                .addFile(FOOBAR03.getName(), "Sample contents of foobar03 under testDir\n")
                .build();
        file1 = FOOBAR01.getFile(repositoryDirectory);
        file2 = FOOBAR02.getFile(repositoryDirectory);
        file3 = FOOBAR03.getFile(TEST_DIRECTORY.getFile(repositoryDirectory));

        gitCommit = new GitCommit();
        gitAdd = new GitAdd();
        gitStatus = new GitStatus();
        options = new GitStatusOptions();

        repositoryBuilder = new GitRepositoryBuilder(getDeletor());

    }

    /**
     * Test for IOException where Repository Directory is invalid.
     *
     * @throws JavaGitException
     */
    @Test
    public void testIOExceptionThrownForInvalidRepositoryDirectory()
            throws JavaGitException
    {
        File tempRepoDirectory = new File("/_______non_existing_dir_______");
        try
        {
            gitStatus.status(tempRepoDirectory);
            fail("IOException not thrown for non-existing repoDirectory");
        } catch (IOException expected)
        {
            FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
        }
    }

    /**
     * Second Test for IOException being thrown where Repository directory does not exist and we try
     * to create a file in that directory.
     */

    @Test
    public void testIOExceptionThrownForInvalidRepositoryDirectory2()
            throws JavaGitException
    {
        File tempRepoDirectory = new File("/_______non_existing_dir________");
        try
        {
            // Create couple of file
            FileUtilities.createFile(tempRepoDirectory, FOOBAR01.getName(), "Sample Contents");
            List<File> paths = null;
            gitStatus.status(repositoryDirectory, options, paths);
            fail("Failed to throw JavaGitException");
        } catch (IOException expected)
        {
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
    public void testBranch()
            throws JavaGitException, IOException
    {
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
    public void testReadyToCommitNewFiles()
            throws IOException, JavaGitException
    {
        List<File> filesToAdd = new ArrayList<>();
        filesToAdd.add(file1);
        filesToAdd.add(file2);
        filesToAdd.add(TEST_DIRECTORY.getFile());
        filesToAdd.add(file3);
        GitAddOptions addOptions = new GitAddOptions();
        gitAdd.add(repositoryDirectory, addOptions, filesToAdd);
        List<File> statusPath = null;
        GitStatusResponse status = gitStatus.status(repositoryDirectory, options, statusPath);
        int noOfNewFilesToCommit = status.getNewFilesToCommitSize();
        assertEquals("Error. No of New Files to commit does not match", 3, noOfNewFilesToCommit);
        assertEquals("Error. Filename does not match", FOOBAR01.getName(), status
                .getFileFromNewFilesToCommit(0).getName());
        assertEquals("Error. Filename does not match", FOOBAR02.getName(), status
                .getFileFromNewFilesToCommit(1).getName());
        assertEquals("Error. Filename does not match", file3.getAbsolutePath(),
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
    public void testModifiedNotUpdatedFiles()
            throws IOException, JavaGitException
    {
        List<File> filesToAdd = new ArrayList<>();
        filesToAdd.add(file1);
        filesToAdd.add(file2);
        filesToAdd.add(TEST_DIRECTORY.getFile());
        filesToAdd.add(file3);
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
        if (file2.delete())
        {
            status = gitStatus.status(repositoryDirectory, options, statusPath);
            assertEquals("No of deleted files not equal", 1, status.getDeletedFilesNotUpdatedSize());

            checkModifiedFiles(status);
            //    assertEquals("No of modified but not updated files not equal", 2, modifiedNotUpdatedFiles);
        } else
        {
            fail("Failed to delete file \"foobar02\"");
        }
    }


    // ~~--------------------------------------------- New tests (porcelain usage related)
//    X          Y     Meaning
//    -------------------------------------------------
//    M        [ MD]   updated in index
//    D         [ M]   deleted from index
//    R        [ MD]   renamed in index
//    C        [ MD]   copied in index
//    [MARC]           index and work tree matches
//    [ MARC]     M    work tree changed since index
//    [ MARC]     D    deleted in work tree
//    -------------------------------------------------
//    D           D    unmerged, both deleted
//    A           U    unmerged, added by us
//    U           D    unmerged, deleted by them
//    U           A    unmerged, added by them
//    D           U    unmerged, deleted by us
//    A           A    unmerged, both added
//    U           U    unmerged, both modified


    @Test
    public void testModifiedNotUpdated()
            throws IOException, JavaGitException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        File foobar01 = FOOBAR01.getFile(repository);
        gitAdd.add(repository, foobar01);
        gitCommit.commit(repository, "Commit 101");
        FileUtilities.modifyFileContents(foobar01, "more data");
        assertThat(gitStatus.status(repository).getModifiedFilesNotUpdated())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR01.getName());
    }

    @Test
    public void testDeletedFilesNotUpdated()
            throws IOException, JavaGitException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        File foobar01 = FOOBAR01.getFile(repository);
        gitAdd.add(repository, foobar01);
        gitCommit.commit(repository, "Commit 101");
        foobar01.delete();
        assertThat(gitStatus.status(repository).getDeletedFilesNotUpdated())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR01.getName());
    }


    @Test
    public void testAddedToIndexUnmodified()
            throws IOException, JavaGitException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        gitAdd.add(repository, FOOBAR01.getFile(repository));
        assertThat(gitStatus.status(repository).getNewFilesToCommit())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR01.getName());
    }

    @Test
    public void testAddedToIndexModified()
            throws IOException, JavaGitException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        File foobar01 = FOOBAR01.getFile(repository);
        gitAdd.add(repository, foobar01);
        FileUtilities.modifyFileContents(foobar01, "More text pleeeeease");
        assertThat(gitStatus.status(repository).getNewFilesToCommit())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR01.getName());
    }

    @Test
    public void testAddedToIndexDeleted()
            throws IOException, JavaGitException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        File foobar01 = FOOBAR01.getFile(repository);
        gitAdd.add(repository, foobar01);
        foobar01.delete();
        assertThat(gitStatus.status(repository).getNewFilesToCommit())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR01.getName());
    }

    /**
     * Test for new files that are created but not yet added by git-add or git-rm commands. These
     * files are under Untracked files section of the output.
     *
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testUntrackedNewFiles()
            throws IOException, JavaGitException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        List<File> paths = null;
        GitStatusResponse response = gitStatus.status(repository, options, paths);
        assertEquals("Error.No of untracked files does not Match.", 1, response.getUntrackedFilesSize());
        assertEquals("Error. Filename does not match.", FOOBAR01.getName(),
                response.getFileFromUntrackedFiles(0).getName());
    }


    @Test
    public void testIgnoredFile()
            throws IOException, JavaGitException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        FileUtilities.createFile(repository, ".gitignore", FOOBAR01.getName());
        assertThat(gitStatus.status(repository).getIgnoredFiles())
                .extracting(getFileNameExtractor()).containsOnly(FOOBAR01.getName());
    }

    /**
     * Test for getSingleFileStatus
     *
     * @throws JavaGitException IOException
     */
    @Test
    public void testSingelFileStatus()
            throws JavaGitException, IOException
    {

        File repository = repositoryBuilder
                .addFile(FOOBAR01.getName(), "Test File1").build();
        GitStatusResponse response = gitStatus.getSingleFileStatus(repository, FOOBAR01.getFile());
        assertEquals("File should be untracked.", 1, response.getUntrackedFilesSize());
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

    private void checkModifiedFiles(GitStatusResponse status)
            throws JavaGitException
    {
        int modifiedNotUpdatedFiles = status.getModifiedFilesNotUpdatedSize();

        assertEquals("No of modified but not updated files not equal", 2, modifiedNotUpdatedFiles);

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
     * FileUtilities.modifyFileContents(new File(testDir.getPath() + File.separator + file3.getPath()), "Test append
     * text\n");
     * FileUtilities.modifyFileContents(new File(testDir.getPath() + File.separator + file4.getPath()), "Another
     * sample text added to foobar03\n");
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
