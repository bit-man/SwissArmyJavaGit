package edu.nyu.cs.javagit.test.utilities;

import edu.nyu.cs.javagit.api.JavaGitException;

import java.io.File;

public class DummyDeletor
        implements Deletor
{
    @Override
    public void add(File f)
    {

    }

    @Override
    public void delete()
            throws JavaGitException
    {

    }
}
