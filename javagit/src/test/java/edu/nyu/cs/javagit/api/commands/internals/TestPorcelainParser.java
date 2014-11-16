package edu.nyu.cs.javagit.api.commands.internals;

import edu.nyu.cs.javagit.client.cli.CliGitStatus;
import edu.nyu.cs.javagit.client.cli.PorcelainParseWrongFormatException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestPorcelainParser {

    @Test
    public void testParseNullLine() {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser(null);
        try {
            p.parse();
            fail("Parsing null line should fail");
        } catch (PorcelainParseWrongFormatException e) {

        }
    }

    @Test
    public void testParseEmptyLine() {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser("");
        try {
            p.parse();
            fail("Parsing empty line should fail");
        } catch (PorcelainParseWrongFormatException e) {

        }
    }

    @Test
    public void testParseFieldsOnlyLine() {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser("XY");
        try {
            p.parse();
            fail("Parsing only fields line should fail");
        } catch (PorcelainParseWrongFormatException e) {

        }
    }

    @Test
    public void testParseTwoFieldsLine() throws PorcelainParseWrongFormatException {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser("MM path1");
        CliGitStatus.GitStatusParser.PorcelainParseResult result = p.parse();

        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> expected =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.MODIFIED, CliGitStatus.GitStatusParser.PorcelainField.MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(null, result.getIndexPath());
    }

    @Test
    public void testParseTwoFieldsLineAndOneIsBlank() throws PorcelainParseWrongFormatException {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser(" M path1");
        CliGitStatus.GitStatusParser.PorcelainParseResult result = p.parse();

        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> expected =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.UNMODIFIED, CliGitStatus.GitStatusParser.PorcelainField.MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(null, result.getIndexPath());
    }


    @Test
    public void testParseThreeFieldsLine() throws PorcelainParseWrongFormatException {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser("MM path1 -> path2");
        CliGitStatus.GitStatusParser.PorcelainParseResult result = p.parse();

        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> expected =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.MODIFIED, CliGitStatus.GitStatusParser.PorcelainField.MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(new File("path2"), result.getIndexPath());
    }


    @Ignore("Path with blanks not implemented yet")
    public void testParseTwoFieldsAnPathWithBlanksLine() throws PorcelainParseWrongFormatException {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser("MM path1\\ contains\\ blanks");
        CliGitStatus.GitStatusParser.PorcelainParseResult result = p.parse();

        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> expected =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.MODIFIED, CliGitStatus.GitStatusParser.PorcelainField.MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1\\ contains\\ blanks"), result.getHeadPath());
        assertEquals(null, result.getIndexPath());
    }

    @Ignore("Path with blanks not implemented yet")
    public void testParseTwoFieldsAnPath2WithBlanksLine() throws PorcelainParseWrongFormatException {
        CliGitStatus.GitStatusParser.PorcelainParser p = new CliGitStatus.GitStatusParser.PorcelainParser("MM path1 path2\\ contains\\ blanks");
        CliGitStatus.GitStatusParser.PorcelainParseResult result = p.parse();

        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> expected =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.MODIFIED, CliGitStatus.GitStatusParser.PorcelainField.MODIFIED);

        assertEquals(expected, result.getFields());
        assertEquals(new File("path1"), result.getHeadPath());
        assertEquals(new File("path2\\ contains\\ blanks"), result.getIndexPath());
    }


}
