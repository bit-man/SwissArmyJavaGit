package edu.nyu.cs.javagit.api.commands.test;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.client.GitCommitResponseImpl;
import edu.nyu.cs.javagit.client.GitCommitResponseImpl.AddedOrDeletedFile;
import edu.nyu.cs.javagit.client.GitCommitResponseImpl.CopiedOrMovedFile;

import junit.framework.TestCase;

/**
 * Test class for testing the <code>GitCommitResponse</code> object.
 */
public class TestGitCommitResponse extends TestCase {

  private GitCommitResponseImpl resp;
  private GitCommitResponseImpl respSame;

  @Before
  protected void setUp() {
    resp = new GitCommitResponseImpl("3d3ef1a", "A Comment");
    respSame = new GitCommitResponseImpl("3d3ef1a", "A Comment");
  }

  @Test
  public void testGitCommitResponseBasicFunctionality() {
    // These tests use the variables set up in setUp().
    assertEquals("Short hash name is not the same.", "3d3ef1a", resp.getCommitShortHashName());
    assertEquals("Short comment is not the same.", "A Comment", resp.getCommitShortComment());

    resp.setFilesChanged(5);
    assertEquals("Number of files changed is not the same.", 5, resp.getFilesChanged());

    resp.setLinesDeleted(403);
    assertEquals("Number of lines deleted is not the same.", 403, resp.getLinesDeleted());

    resp.setLinesInserted(6423);
    assertEquals("Number of lines inserted is not the same.", 6423, resp.getLinesInserted());

    resp.addAddedFile("/some/path/to/file.txt", "100644");
    resp.addAddedFile("/gumbee/fiddle/friars/whine.txt", "100777");
    Iterator<AddedOrDeletedFile> adIter = resp.getAddedFilesIterator();
    AddedOrDeletedFile adf = adIter.next();
    assertEquals("Excpected different path to added file", "/some/path/to/file.txt", adf
        .getPathTofile());
    assertEquals("Excpected different mode for added file", "100644", adf.getMode());
    adf = adIter.next();
    assertEquals("Excpected different path to added file", "/gumbee/fiddle/friars/whine.txt", adf
        .getPathTofile());
    assertEquals("Excpected different mode for added file", "100777", adf.getMode());

    resp.addDeletedFile("/some/path/to/deleted/file.txt", "100644");
    resp.addDeletedFile("/gumbee/fiddle/friars/del/whine.txt", "100777");
    adIter = resp.getDeletedFilesIterator();
    adf = adIter.next();
    assertEquals("Excpected different path to deleted file", "/some/path/to/deleted/file.txt", adf
        .getPathTofile());
    assertEquals("Excpected different mode for deleted file", "100644", adf.getMode());
    adf = adIter.next();
    assertEquals("Excpected different path to deleted file", "/gumbee/fiddle/friars/del/whine.txt",
        adf.getPathTofile());
    assertEquals("Excpected different mode for deleted file", "100777", adf.getMode());

    resp.addCopiedFile("/starter/path.txt", "/copied/path.txt", 99);
    resp.addCopiedFile("/filler/filet.txt", "/floppin/fandiggery.txt", 87);
    Iterator<CopiedOrMovedFile> cmIter = resp.getCopiedFilesIterator();
    CopiedOrMovedFile cmf = cmIter.next();
    assertEquals("Excpected different path for from copied file", "/starter/path.txt", cmf
        .getSourceFilePath());
    assertEquals("Excpected different path for to copied file", "/copied/path.txt", cmf
        .getDestinationFilePath());
    assertEquals("Excpected different percentage for copied file", 99, cmf.getPercentage());
    cmf = cmIter.next();
    assertEquals("Excpected different path for from copied file", "/filler/filet.txt", cmf
        .getSourceFilePath());
    assertEquals("Excpected different path for to copied file", "/floppin/fandiggery.txt", cmf
        .getDestinationFilePath());
    assertEquals("Excpected different percentage for copied file", 87, cmf.getPercentage());

    resp.addRenamedFile("/starter/path.txt", "/renamed/path.txt", 99);
    resp.addRenamedFile("/filler/filet.txt", "/floppin/fandiggery.txt", 87);
    cmIter = resp.getRenamedFilesIterator();
    cmf = cmIter.next();
    assertEquals("Excpected different path for from copied file", "/starter/path.txt", cmf
        .getSourceFilePath());
    assertEquals("Excpected different path for to copied file", "/renamed/path.txt", cmf
        .getDestinationFilePath());
    assertEquals("Excpected different percentage for copied file", 99, cmf.getPercentage());
    cmf = cmIter.next();
    assertEquals("Excpected different path for from copied file", "/filler/filet.txt", cmf
        .getSourceFilePath());
    assertEquals("Excpected different path for to copied file", "/floppin/fandiggery.txt", cmf
        .getDestinationFilePath());
    assertEquals("Excpected different percentage for copied file", 87, cmf.getPercentage());

  }

  @Test
  public void testGitCommitResponseEqualsMethod() {
    // These tests use the variables set up in setUp().
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

    resp.setFilesChanged(5);
    assertTrue("GitCommitResponseObjects are equal when they should not be.", !resp
        .equals(respSame));
    respSame.setFilesChanged(5);
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

    resp.setLinesDeleted(403);
    assertTrue("GitCommitResponseObjects are equal when they should not be.", !resp
        .equals(respSame));
    respSame.setLinesDeleted(403);
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

    resp.setLinesInserted(6423);
    assertTrue("GitCommitResponseObjects are equal when they should not be.", !resp
        .equals(respSame));
    respSame.setLinesInserted(6423);
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

    resp.addAddedFile("/some/path/to/file.txt", "100644");
    assertTrue("GitCommitResponseObjects are equal when they should not be.", !resp
        .equals(respSame));
    respSame.addAddedFile("/some/path/to/file.txt", "100644");
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

    resp.addDeletedFile("/another/path/to/file.txt", "100644");
    assertTrue("GitCommitResponseObjects are equal when they should not be.", !resp
        .equals(respSame));
    respSame.addDeletedFile("/another/path/to/file.txt", "100644");
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

    resp.addCopiedFile("/from/this/path.txt", "/to/this/altered-path.txt", 56);
    assertTrue("GitCommitResponseObjects are equal when they should not be.", !resp
        .equals(respSame));
    respSame.addCopiedFile("/from/this/path.txt", "/to/this/altered-path.txt", 56);
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

    resp.addRenamedFile("/from/another/path.txt", "/to/another/altered-path.txt", 23);
    assertTrue("GitCommitResponseObjects are equal when they should not be.", !resp
        .equals(respSame));
    respSame.addRenamedFile("/from/another/path.txt", "/to/another/altered-path.txt", 23);
    assertEquals("GitCommitResponse objects are not equal when they should be", resp, respSame);

  }

  @Test
  public void testAddedOrDeletedFile() {
    GitCommitResponseImpl.AddedOrDeletedFile addDel = resp.new AddedOrDeletedFile(
        "/a/path/to/add/del/file.txt", "100644");
    GitCommitResponseImpl.AddedOrDeletedFile addDelSame = resp.new AddedOrDeletedFile(
        "/a/path/to/add/del/file.txt", "100644");
    GitCommitResponseImpl.AddedOrDeletedFile addDelDiff1 = resp.new AddedOrDeletedFile(
        "/another/path/to/add/del/file.txt", "100644");
    GitCommitResponseImpl.AddedOrDeletedFile addDelDiff2 = resp.new AddedOrDeletedFile(
        "/a/path/to/add/del/file.txt", "100777");

    assertEquals("AddedOrDeletedFile instances not equal when they should be equal", addDel,
        addDelSame);

    assertTrue("AddedOrDeletedFile instances equal when they should not be equal", !(addDel
        .equals(addDelDiff1)));
    assertTrue("AddedOrDeletedFile instances equal when they should not be equal", !addDel
        .equals(addDelDiff2));

    assertEquals("AddedOrDeletedFile hashcodes not equal when they should be equal.", addDel
        .hashCode(), addDelSame.hashCode());
  }

  @Test
  public void testCopiedOrMovedFile() {
    GitCommitResponseImpl.CopiedOrMovedFile copyMove = resp.new CopiedOrMovedFile(
        "c:\\path\\1\\txt.txt", "c:\\other\\path\\bob.txt", 32);
    GitCommitResponseImpl.CopiedOrMovedFile copyMoveSame = resp.new CopiedOrMovedFile(
        "c:\\path\\1\\txt.txt", "c:\\other\\path\\bob.txt", 32);
    GitCommitResponseImpl.CopiedOrMovedFile copyMoveDiff1 = resp.new CopiedOrMovedFile(
        "c:\\path\\1\\notSame.txt", "c:\\other\\path\\bob.txt", 32);
    GitCommitResponseImpl.CopiedOrMovedFile copyMoveDiff2 = resp.new CopiedOrMovedFile(
        "c:\\path\\1\\txt.txt", "c:\\path\\1\\bob.txt", 32);
    GitCommitResponseImpl.CopiedOrMovedFile copyMoveDiff3 = resp.new CopiedOrMovedFile(
        "c:\\path\\1\\txt.txt", "c:\\other\\path\\bob.txt", 83);

    assertEquals("CopiedOrMovedFile instances not equal when they should be equal", copyMove,
        copyMoveSame);

    assertTrue("CopiedOrMovedFile instances equal when they should not be equal", !copyMove
        .equals(copyMoveDiff1));
    assertTrue("CopiedOrMovedFile instances equal when they should not be equal", !copyMove
        .equals(copyMoveDiff2));
    assertTrue("CopiedOrMovedFile instances equal when they should not be equal", !copyMove
        .equals(copyMoveDiff3));

    assertEquals("CopiedOrMovedFile hashcodes not equal when they should be equal.", copyMove
        .hashCode(), copyMoveSame.hashCode());
  }

}
