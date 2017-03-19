package edu.nyu.cs.javagit.client.cli;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProcessBuilderMock
        implements IGitProcessBuilder
{
    private List<String> commandLine;

    @Override
    public void directory(File directory)
    {
    }

    @Override
    public void redirectErrorStream(boolean redirect)
    {
    }

    @Override
    public List<String> getCommand()
    {
        return commandLine;
    }

    @Override
    public String getCommandString()
    {
        return "";
    }

    @Override
    public Process start()
            throws IOException
    {
        InputStream inputStream = mock(InputStream.class);
        Process process = mock(Process.class);
        when(process.getInputStream()).thenReturn(inputStream);
        return process;
    }

    @Override
    public void setCommandLine(List<String> commandLine)
    {
        this.commandLine = commandLine;
    }
}
