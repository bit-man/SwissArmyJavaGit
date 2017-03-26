package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.client.GitStatusResponseImpl;
import edu.nyu.cs.javagit.client.parser.GitStatusParser;

import java.io.File;

public abstract class XYSolver {

    public abstract void solve(GitStatusResponseImpl response, GitStatusParser.PorcelainParseResult result,
                               File workingDirectory);
}
