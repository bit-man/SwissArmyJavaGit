/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitPull;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

import java.io.File;

/**
 * <code>GitPull</code> provides an interface to fetch and integrate  with another repository or a local branch
 */
public final class GitPull
{

    /**
     * ToDo JavaDoc
     */
    public void pull(File repositoryPath)
            throws JavaGitException
    {
        CheckUtilities.checkNullArgument(repositoryPath, "repository");

        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitPull gitLog = client.getGitPullInstance();
        gitLog.pull(repositoryPath);
    }
}
