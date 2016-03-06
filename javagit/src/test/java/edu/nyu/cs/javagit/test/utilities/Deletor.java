package edu.nyu.cs.javagit.test.utilities;

import edu.nyu.cs.javagit.api.JavaGitException;

import java.io.File;

public interface Deletor
{

    void add (File f);

    void delete() throws JavaGitException;

}
