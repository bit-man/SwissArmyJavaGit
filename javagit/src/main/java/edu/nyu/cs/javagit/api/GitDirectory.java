package edu.nyu.cs.javagit.api;

import java.util.List;
import java.util.Vector;
import java.io.File;
import java.io.IOException;


/**
 * <code>GitDirectory</code> represents a directory object in a git working tree.
 * 
 * TODO: Build out the class
 */
public class GitDirectory extends GitFileSystemObject {

  /**
   * The constructor.
   * 
   * @param dir
   *          underlying <code>java.io.File</code> object
   */
  public GitDirectory(File dir) throws JavaGitException {
    super(dir);
  }
  
  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GitDirectory)) {
      return false;
    }

    GitFileSystemObject gitObj = (GitFileSystemObject) obj;
    return super.equals(gitObj);
  }

  /**
   * Gets the children of this directory.
   * 
   * @return The children of this directory.
   */
  public List<GitFileSystemObject> getChildren() throws IOException {
    List<GitFileSystemObject> children = new Vector<GitFileSystemObject>();

    //get all of the file system objects currently located under this directory
    File directoryFiles[] = file.listFiles();
    for(int i=0; i<directoryFiles.length; ++i) {
      File memberFile = directoryFiles[i];
      //check if this file is hidden
      if(memberFile.isHidden()) {
        //ignore (could be .git directory)
        continue;
      }

      //now, just check for the type of the filesystem object
      try {
        if(memberFile.isDirectory()) {
          children.add(new GitDirectory(memberFile));
        }
        else {
          children.add(new GitFile(memberFile));
        }
      }
      catch (JavaGitException e) {
        //TODO(ma1683): is this really possible scenario? Ignoring at the moment
        continue;
      }
    }

    return children;
  }

  /**
   * Adds a <code>GitFile</code> to the working directory. Does not create a physical file
   * 
   * @param name
   *          The name of the file
   * 
   * @return The <code>GitFile</code> object
   */
  public GitFile addFile(String name) throws JavaGitException {
    String filePath = file.getPath() + File.separator + name;
    return new GitFile(new File(filePath));
  }

  /**
   * Adds a <code>GitDirectory</code> to the working directory. Does not create a physical dir
   * 
   * @param name
   *          The name of the file
   * 
   * @return The <code>GitDirectory</code> object
   */
  public GitDirectory addDirectory(String name) throws JavaGitException {
    String dirPath = file.getPath() + File.separator + name;
    return new GitDirectory(new File(dirPath));
  }

}
