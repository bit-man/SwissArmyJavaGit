package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.client.GitStatusResponseImpl;
import edu.nyu.cs.javagit.utilities.StringUtilities;

import java.io.File;

public abstract class XYSolver {

    public abstract void solve(GitStatusResponseImpl response, CliGitStatus.GitStatusParser.PorcelainParseResult result, File workingDirectory);
}
