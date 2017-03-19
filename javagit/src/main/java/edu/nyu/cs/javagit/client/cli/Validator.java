package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;

public interface Validator
{
    void checkNullArgument(File file, String message);

    void checkFileValidity(File file)
            throws IOException;
}
