
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
import edu.nyu.cs.javagit.client.cli.CliGitStatus;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;


//Short Format
//        In the short-format, the status of each path is shown as
//
//        XY PATH1 -> PATH2
//
//        where PATH1 is the path in the HEAD, and the " -> PATH2" part is shown only when PATH1 corresponds to a
// different path in the index/worktree (i.e. the file is renamed). The XY is a two-letter status code.
//
//        The fields (including the ->) are separated from each other by a single space. If a filename contains
// whitespace or other nonprintable characters, that field will be quoted in the manner of a C string literal:
// surrounded by
//        ASCII double quote (34) characters, and with interior special characters backslash-escaped.
//
//        For paths with merge conflicts, X and Y show the modification states of each side of the merge. For paths
// that do not have merge conflicts, X shows the status of the index, and Y shows the status of the work tree. For
//        untracked paths, XY are ??. Other status codes can be interpreted as follows:
//
//        ·   ' ' = unmodified
//
//        ·   M = modified
//
//        ·   A = added
//
//        ·   D = deleted
//
//        ·   R = renamed
//
//        ·   C = copied
//
//        ·   U = updated but unmerged
//
//        Ignored files are not listed, unless --ignored option is in effect, in which case XY are !!.
//
//        X          Y     Meaning
//        -------------------------------------------------
//        [MD]   not updated
//        M        [ MD]   updated in index
//        A        [ MD]   added to index
//        D         [ M]   deleted from index
//        R        [ MD]   renamed in index
//        C        [ MD]   copied in index
//        [MARC]           index and work tree matches
//        [ MARC]     M    work tree changed since index
//        [ MARC]     D    deleted in work tree
//        -------------------------------------------------
//        D           D    unmerged, both deleted
//        A           U    unmerged, added by us
//        U           D    unmerged, deleted by them
//        U           A    unmerged, added by them
//        D           U    unmerged, deleted by us
//        A           A    unmerged, both added
//        U           U    unmerged, both modified
//        -------------------------------------------------
//        ?           ?    untracked
//        !           !    ignored
//        -------------------------------------------------
//
//        If -b is used the short-format status is preceded by a line
//
//        ## branchname tracking info

public class TestGitStatusResponse
        extends TestBase
{
    private CliGitStatus.GitStatusParser gitStatusParser;
    private String repo;

    @Before
    public void setup()
            throws IOException, JavaGitException
    {
        repo = FileUtilities.createTempDirectory(this.getClass().getSimpleName()).getAbsolutePath();
        this.gitStatusParser = new CliGitStatus.GitStatusParser(repo);

        getDeletor().add(new File(repo));
        super.setUp();
    }

    @Test
    public void testIgnoredFiles()
            throws JavaGitException
    {
        gitStatusParser.parseLine("!! ignoredFile");
        GitStatusResponse response = gitStatusParser.getResponse();
        assertThat(response.getIgnoredFiles()).containsOnly(new File(repo, "ignoredFile"));
        assertEquals(response.getIgnoredFilesSize(),1);
        assertEquals(response.getUntrackedFilesSize(),0);
        assertEquals(response.getDeletedFilesNotUpdatedSize(),0);
        assertEquals(response.getDeletedFilesToCommitSize(),0);
        assertEquals(response.getModifiedFilesNotUpdatedSize(),0);
        assertEquals(response.getModifiedFilesToCommitSize(),0);
        assertEquals(response.getNewFilesToCommitSize(),0);
        assertEquals(response.getRenamedFilesToCommitSize(),0);
        assertEquals(response.getErrorCount(),0);
    }

    @Test
    public void testUntrackedFiles()
            throws JavaGitException
    {
        gitStatusParser.parseLine("?? untrackedFile");
        GitStatusResponse response = gitStatusParser.getResponse();
        assertThat(response.getUntrackedFiles()).containsOnly(new File(repo, "untrackedFile"));
        assertEquals(response.getIgnoredFilesSize(),0);
        assertEquals(response.getUntrackedFilesSize(),1);
        assertEquals(response.getDeletedFilesNotUpdatedSize(),0);
        assertEquals(response.getDeletedFilesToCommitSize(),0);
        assertEquals(response.getModifiedFilesNotUpdatedSize(),0);
        assertEquals(response.getModifiedFilesToCommitSize(),0);
        assertEquals(response.getNewFilesToCommitSize(),0);
        assertEquals(response.getRenamedFilesToCommitSize(),0);
        assertEquals(response.getErrorCount(),0);
    }

}
