package edu.nyu.cs.javagit.api.commands;

/**
 * Command options interface
 */
public interface ICommandOption {
    public Object getDefault();

    Iterable<? extends String> getOption(Object value);
}
