package edu.nyu.cs.javagit.test.utilities;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.utilities.FileUtilities;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * File deletion utility
 */
public class Deletor {

    private Set<File> toDelete = new HashSet<File>();

    public void add (File f) {
        toDelete.add(f);
    }

    public void delete() throws JavaGitException {
        for (File f : toDelete)
            if (f.exists())
                FileUtilities.removeDirectoryRecursivelyAndForcefully(f);

    }
}
