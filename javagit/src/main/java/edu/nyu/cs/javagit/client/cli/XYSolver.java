package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.client.GitStatusResponseImpl;

public interface XYSolver {
    public void solve(GitStatusResponseImpl response, CliGitStatus.GitStatusParser.PorcelainParseResult result);
}
