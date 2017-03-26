package edu.nyu.cs.javagit.client.parser;

import edu.nyu.cs.javagit.client.cli.PorcelainParseWrongFormatException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestPorcelainParser
{

    @Test
    public void testParseNullLine()
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser(null);
        try
        {
            p.parse();
            fail("Parsing null line should fail");
        } catch (PorcelainParseWrongFormatException e)
        {
            // Don't care
        }
    }

    @Test
    public void testParseEmptyLine()
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser("");
        try
        {
            p.parse();
            fail("Parsing empty line should fail");
        } catch (PorcelainParseWrongFormatException e)
        {
            // Don't care
        }
    }

    @Test
    public void testParseFieldsOnlyLine()
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser("XY");
        try
        {
            p.parse();
            fail("Parsing only fields line should fail");
        } catch (PorcelainParseWrongFormatException e)
        {
            // Don't care
        }
    }

    @Test
    public void testParseTwoFieldsLine()
            throws PorcelainParseWrongFormatException
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser("MM path1");
        GitStatusParser.PorcelainParseResult result = p.parse();

        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> expected =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.MODIFIED, GitStatusParser.PorcelainField
                        .MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(null, result.getIndexPath());
    }

    @Test
    public void testParseTwoFieldsLineAndOneIsBlank()
            throws PorcelainParseWrongFormatException
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser(" M path1");
        GitStatusParser.PorcelainParseResult result = p.parse();

        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> expected =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.UNMODIFIED, GitStatusParser
                        .PorcelainField.MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(null, result.getIndexPath());
    }


    @Test
    public void testParseThreeFieldsLine()
            throws PorcelainParseWrongFormatException
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser("MM path1 -> path2");
        GitStatusParser.PorcelainParseResult result = p.parse();

        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> expected =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.MODIFIED, GitStatusParser.PorcelainField
                        .MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(new File("path2"), result.getIndexPath());
    }


    @Ignore("Path with blanks not implemented yet")
    public void testParseTwoFieldsAnPathWithBlanksLine()
            throws PorcelainParseWrongFormatException
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser("MM path1\\ contains\\ blanks");
        GitStatusParser.PorcelainParseResult result = p.parse();

        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> expected =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.MODIFIED, GitStatusParser.PorcelainField
                        .MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1\\ contains\\ blanks"), result.getHeadPath());
        assertEquals(null, result.getIndexPath());
    }

    @Ignore("Path with blanks not implemented yet")
    public void testParseTwoFieldsAnPath2WithBlanksLine()
            throws PorcelainParseWrongFormatException
    {
        GitStatusParser.PorcelainParser p = new GitStatusParser.PorcelainParser("MM path1 path2\\ contains\\ blanks");
        GitStatusParser.PorcelainParseResult result = p.parse();

        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> expected =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.MODIFIED, GitStatusParser.PorcelainField
                        .MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(new File("path2\\ contains\\ blanks"), result.getIndexPath());
    }


}
