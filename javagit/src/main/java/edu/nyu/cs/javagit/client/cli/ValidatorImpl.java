package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.utilities.CheckUtilities;

import java.io.File;
import java.io.IOException;

public class ValidatorImpl
        implements Validator
{
    @Override
    public void checkNullArgument(File file, String message)
    {
        CheckUtilities.checkNullArgument(file, message);
    }

    @Override
    public void checkFileValidity(File file)
            throws IOException
    {
        CheckUtilities.checkFileValidity(file);
    }
}