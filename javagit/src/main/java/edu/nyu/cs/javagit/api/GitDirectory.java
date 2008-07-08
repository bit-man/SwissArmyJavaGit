package edu.nyu.cs.javagit.api;

import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <code>GitDirectory</code> represents a directory object in a git working tree.
 * 
 * TODO: Build out the class
 */
public class GitDirectory extends GitFileSystemObject {
  // parent directory
  private GitDirectory parent = null;

  /**
   * The constructor.
   * 
   * @param dir
   *          underlying java.io.File object
   * @param dotGit
   *          The object representing .git directory of the repository
   * @param parent
   *          The parent directory
   */
  public GitDirectory(File dir, DotGit dotGit, GitDirectory parent) {
    super(dir, dotGit);
    this.parent = parent;
  }


  /**
   * Gets the children of this directory.
   * 
   * @return The children of this directory.
   */
  public List<GitFileSystemObject> getChildren() throws IOException {
    List<GitFileSystemObject> children = new Vector<GitFileSystemObject>();
    
    ProcessBuilder pb = new ProcessBuilder("ls");
    pb.directory(new File(file.getPath()));
    pb.redirectErrorStream(true);
    
    Process p = pb.start();
    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while (true) {
      String line = br.readLine();
      if(line == null || line.length() == 0 ) {
        break;
      }
      StringTokenizer st = new StringTokenizer(line);
      while (st.hasMoreTokens()) {
          String name = st.nextToken();
          if((name == null) || (name.length() == 0)) {
            continue;
          }
          String path = file.getPath() + File.separator + name;
          File member = new File(path);
          if(member.isDirectory()) {
            children.add(new GitDirectory(member, dotGit, this));
          }
          else {
            children.add(new GitFile(member, dotGit, this));
          }
      }

    }

    return children;
  }

  /**
   * Adds a GitFile to the working directory.
   * 
   * @param name
   *          The name of the file
   * 
   * @return The GitFile object
   */
  public GitFile addFile(String name) {
    String filePath = file.getPath() + File.separator + name;
    return new GitFile(new File(filePath), dotGit, this);
  }

  /**
   * Adds a GitFile to the working directory.
   * 
   * @param name
   *          The name of the file
   * 
   * @return The GitFile object
   */
  public GitDirectory addDirectory(String name) {
    String dirPath = file.getPath() + File.separator + name;
    return new GitDirectory(new File(dirPath), dotGit, this);
  }

  /**
   * Gets parent directory of this File object
   * 
   * @return parent directory
   */
  public GitDirectory getParent() {
    return parent;
  }

}
