package edu.nyu.cs.javagit.test.utilities;

import java.io.File;

public class TestFile
{
    private final String name;

    public TestFile(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public File getFile(File parentFolder)
    {
        return new File(parentFolder, getName());
    }

    public File getFile()
    {
        return new File(getName());
    }
}
