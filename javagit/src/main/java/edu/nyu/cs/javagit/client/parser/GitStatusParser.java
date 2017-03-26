package edu.nyu.cs.javagit.client.parser;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.client.GitStatusResponseImpl;
import edu.nyu.cs.javagit.client.cli.IParser;
import edu.nyu.cs.javagit.client.cli.PorcelainParseWrongFormatException;
import edu.nyu.cs.javagit.client.cli.XYSolver;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;
import edu.nyu.cs.javagit.utilities.StringUtilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
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

public class GitStatusParser
        implements IParser
{

    private static Map<Tuple<PorcelainField, PorcelainField>, XYSolver> solver = new
            HashMap<>();
    private GitStatusResponseImpl response;
    private File inputFile;
    // The working directory for the command that was run.
    private File workingDirectory;

    public GitStatusParser()
    {
        this(null);
    }

    public GitStatusParser(File in)
    {
        inputFile = in;
    }

    public void parseLine(String line)
            throws JavaGitException
    {
        try
        {
            PorcelainParseResult p = new PorcelainParser(line).parse();
            if (inputFile == null || inputFile.getPath().equals(p.getHeadPath().getPath()))
            {
                solver.get(p.getFields()).solve(response, p, workingDirectory);
            }
        } catch (PorcelainParseWrongFormatException e)
        {
            throw new JavaGitException(438001,
                    ExceptionMessageMap.getMessage(ExceptionMessageMap.ErrorParsingGitStatusResponse),
                    e);
        }
    }

    public void processExitCode(int code)
    {
    }

    public GitStatusResponse getResponse()
            throws JavaGitException
    {
        if (response.errorState())
        {
            throw new JavaGitException(438000, ExceptionMessageMap.getMessage("438000") +
                    " - git status error message: { " + response.getError() + " }");
        }
        return response;
    }

    @Override
    public void setWorkingDir(String workingDir)
    {
        this.workingDirectory = new File(workingDir);
        response = new GitStatusResponseImpl(workingDir);
    }

    private static class UntrackedXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new UntrackedXYSolver();

        private UntrackedXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }


        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToUntrackedFiles(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class IgnoredXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new IgnoredXYSolver();

        private IgnoredXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToIgnoredFiles(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class DeletedNotUpdatedXYSolver
            extends XYSolver
    {


        private static XYSolver singleInstance = new DeletedNotUpdatedXYSolver();


        private DeletedNotUpdatedXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToDeletedFilesNotUpdated(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class DeletedToCommitXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new DeletedToCommitXYSolver();

        private DeletedToCommitXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToDeletedFilesToCommit(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class ModifiedNotUpdatedXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new ModifiedNotUpdatedXYSolver();

        private ModifiedNotUpdatedXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToModifiedFilesNotUpdated(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class ModifiedToCommitXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new ModifiedToCommitXYSolver();

        private ModifiedToCommitXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToModifiedFilesToCommit(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class NewFilesToCommitXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new NewFilesToCommitXYSolver();

        private NewFilesToCommitXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToNewFilesToCommit(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class RenamedFilesToCommitXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new RenamedFilesToCommitXYSolver();

        private RenamedFilesToCommitXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToRenamedFilesToCommit(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    private static class UnmergedBothModifiedXYSolver
            extends XYSolver
    {

        private static XYSolver singleInstance = new UnmergedBothModifiedXYSolver();

        private UnmergedBothModifiedXYSolver()
        {
        }

        public static XYSolver getInstance()
        {
            return singleInstance;
        }

        public void solve(GitStatusResponseImpl response, PorcelainParseResult result, File workingDirectory)
        {
            response.addToModifiedFilesNotUpdated(new File(workingDirectory, result.getHeadPath().getPath()));
            response.addToModifiedFilesToCommit(new File(workingDirectory, result.getHeadPath().getPath()));
        }

    }

    public static class PorcelainParser
    {
        private final String line;

        PorcelainParser(String line)
        {
            this.line = line;
        }

        public PorcelainParseResult parse()
                throws PorcelainParseWrongFormatException
        {
            if (StringUtilities.isEmptyString(line))
            {
                throw new PorcelainParseWrongFormatException("NULL line");
            }

            /*
             *    In the short-format, the status of each path is shown as
             *
             *    XY PATH1 -> PATH2
             *
             *    where PATH1 is the path in the HEAD, and the " -> PATH2" part is shown only when PATH1
             *    corresponds to a different path in the
             *    index/worktree (i.e. the file is renamed). The XY is a two-letter status code.
             *
             */
            final String fields = line.substring(0, 2);
            String linePaths = line.substring(2).trim();

            if (StringUtilities.isEmptyString(linePaths))
            {
                throw new PorcelainParseWrongFormatException("No file path parsing line '" + line + "'");
            }

            final String[] path = linePaths.split(" ", 3);

            if (path.length < 1 || StringUtilities.isEmptyString(path[0]))
            {
                throw new PorcelainParseWrongFormatException("No file path parsing line '" + line + "'");
            }

            return (path.length == 1) ?
                    new PorcelainParseResult(parseStatusCode(fields),
                            new File(path[0])) :
                    new PorcelainParseResult(parseStatusCode(fields),
                            new File(path[0]), new File(path[2]));
        }

        private Tuple<PorcelainField, PorcelainField> parseStatusCode(String s)
        {
            return Tuple.create(PorcelainField.char2field(s.charAt(0)),
                    PorcelainField.char2field(s.charAt(1)));
        }
    }

    public static class PorcelainParseResult
    {
        private final Tuple<PorcelainField, PorcelainField> fields;

        private final File headPath;
        private final File indexPath;

        PorcelainParseResult(Tuple<PorcelainField, PorcelainField> fields, File headPath)
        {
            this(fields, headPath, null);
        }

        PorcelainParseResult(Tuple<PorcelainField, PorcelainField> fields, File headPath, File indexPath)
        {
            this.fields = fields;
            this.headPath = headPath;
            this.indexPath = indexPath;
        }

        Tuple<PorcelainField, PorcelainField> getFields()
        {
            return fields;
        }

        File getHeadPath()
        {
            return headPath;
        }

        File getIndexPath()
        {
            return indexPath;
        }
    }

    static class Tuple<S, T>
    {
        private S a;

        private T b;

        private Tuple()
        {
        }

        private Tuple(S a, T b)
        {
            this.a = a;
            this.b = b;
        }

        public static <S, T> Tuple<S, T> create(S a, T b)
        {
            return new Tuple<>(a, b);
        }

        public S getA()
        {
            return a;
        }

        public T getB()
        {
            return b;
        }

        public boolean equals(Object that)
        {
            if (!(that instanceof Tuple))
            {
                return super.equals(that);
            }

            @SuppressWarnings("unchecked")
            Tuple<S, T> o = (Tuple<S, T>) that;

            return (o == this) || a.equals(o.a) && b.equals(o.b);
        }

        @Override
        public int hashCode()
        {
            int result = a != null ? a.hashCode() : 0;
            result = 37 * result + (b != null ? b.hashCode() : 0);
            return result;
        }
    }

    static
    {
//            Short Format
//            In the short-format, the status of each path is shown as
//
//            XY PATH1 -> PATH2
//
//            where PATH1 is the path in the HEAD, and the " -> PATH2" part is shown only when PATH1 corresponds to a
// different path in the index/worktree (i.e. the file is renamed). The XY is a two-letter status code.
//
//            The fields (including the ->) are separated from each other by a single space. If a filename contains
// whitespace or other nonprintable characters, that field will be quoted in the manner of a C string literal:
// surrounded by
//            ASCII double quote (34) characters, and with interior special characters backslash-escaped.
//
//                For paths with merge conflicts, X and Y show the modification states of each side of the merge. For
// paths that do not have merge conflicts, X shows the status of the index, and Y shows the status of the work tree. For
//            untracked paths, XY are ??. Other status codes can be interpreted as follows:
//
//            ·   ' ' = unmodified
//
//            ·   M = modified
//
//            ·   A = added
//
//            ·   D = deleted
//
//            ·   R = renamed
//
//            ·   C = copied
//
//            ·   U = updated but unmerged
//
//            Ignored files are not listed, unless --ignored option is in effect, in which case XY are !!.
//
//            X          Y     Meaning
//                    -------------------------------------------------
//            [MD]   not updated
//            M        [ MD]   updated in index
//            A        [ MD]   added to index
//            D         [ M]   deleted from index
//            R        [ MD]   renamed in index
//            C        [ MD]   copied in index
//                    [MARC]           index and work tree matches
//                    [ MARC]     M    work tree changed since index
//            [ MARC]     D    deleted in work tree
//                    -------------------------------------------------
//                    D           D    unmerged, both deleted
//            A           U    unmerged, added by us
//            U           D    unmerged, deleted by them
//            U           A    unmerged, added by them
//            D           U    unmerged, deleted by us
//            A           A    unmerged, both added
//            U           U    unmerged, both modified
//                    -------------------------------------------------
//                    ?           ?    untracked
//            !           !    ignored
//                    -------------------------------------------------
//
//                    If -b is used the short-format status is preceded by a line
//
//            ## branchname tracking info

        solver.put(Tuple.create(PorcelainField.UNTRACKED, PorcelainField.UNTRACKED),
                UntrackedXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.IGNORED, PorcelainField.IGNORED),
                IgnoredXYSolver.getInstance());

        solver.put(Tuple.create(PorcelainField.UNMODIFIED, PorcelainField.MODIFIED),
                ModifiedNotUpdatedXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.UNMODIFIED, PorcelainField.DELETED),
                DeletedNotUpdatedXYSolver.getInstance());

        solver.put(Tuple.create(PorcelainField.MODIFIED, PorcelainField.MODIFIED),
                ModifiedToCommitXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.MODIFIED, PorcelainField.DELETED),
                ModifiedToCommitXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.MODIFIED, PorcelainField.UNMODIFIED),
                ModifiedToCommitXYSolver.getInstance());


        solver.put(Tuple.create(PorcelainField.ADDED, PorcelainField.MODIFIED),
                NewFilesToCommitXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.ADDED, PorcelainField.DELETED),
                NewFilesToCommitXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.ADDED, PorcelainField.UNMODIFIED),
                NewFilesToCommitXYSolver.getInstance());


        solver.put(Tuple.create(PorcelainField.DELETED, PorcelainField.MODIFIED),
                DeletedToCommitXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.DELETED, PorcelainField.UNMODIFIED),
                DeletedToCommitXYSolver.getInstance());


        solver.put(Tuple.create(PorcelainField.RENAMED, PorcelainField.MODIFIED),
                RenamedFilesToCommitXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.RENAMED, PorcelainField.DELETED),
                RenamedFilesToCommitXYSolver.getInstance());
        solver.put(Tuple.create(PorcelainField.RENAMED, PorcelainField.UNMODIFIED),
                RenamedFilesToCommitXYSolver.getInstance());

        solver.put(Tuple.create(PorcelainField.UPDATED_BUT_UNMERGED, PorcelainField.UPDATED_BUT_UNMERGED),
                UnmergedBothModifiedXYSolver.getInstance());
    }

    enum PorcelainField
    {
        MODIFIED('M'), UNMODIFIED(' '), ADDED('A'), DELETED('D'), RENAMED('R'), COPIED('C'), UPDATED_BUT_UNMERGED
            ('U'), UNTRACKED('?'), IGNORED(
            '!');
        private final char c;

        PorcelainField(char c)
        {
            this.c = c;
        }

        public static PorcelainField char2field(char c)
        {
            for (PorcelainField p : values())
            {
                if (p.c == c)
                {
                    return p;
                }
            }
            return null;
        }
    }
}
