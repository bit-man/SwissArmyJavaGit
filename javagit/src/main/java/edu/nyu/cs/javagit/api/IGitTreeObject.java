package edu.nyu.cs.javagit.api;

/**
 * <code>IGitTreeObject</code> is the base interface for the hierarchy of
 * objects in a git sandbox and repository tree.
 * 
 * TODO: Build out the class
 */
public interface IGitTreeObject {

  /**
   * Adds the git object to the git index
   */
  public void add();

   /**
   * Commits the git object
   * 
   * @param comment
   *      Developer's comment
   */
  public void commit(String comment);

   /**
   * Moves or renames the git object
   * 
   * @param dest
   *      destination
   */
  public void mv(String dest);

  /**
   * Removes the git object from the working tree and the index
   */ 
  public void rm();

  /**
   *  Checks out some earlier version of the git object
   *  
   * @param sha1
   *      Commit id
   */
  public void checkout(String sha1);

  /**
   *  Show differences between current git object and index version of it
   * 
   * @return diff between working directory and git index
   */
  public Diff diff();
  
  /**
   *  Show differences between current git object and some commit
   * 
   * @param commit
   *      Git commit to compare with
   *      
   * @return diff between working directory and a given git commit
   */
  public Diff diff(Commit commit);

  /**
   * Gets the type of the git object
   */
  public Type getType();

  /**
   *  Show git object's status in the working directory
   * 
   * @return status (untracked, changed but not updated, etc 
   */
  public Status status();
  
  /**
   * Gets the parent object of this object.
   * 
   * @return The parent object of this object.
   */
  public IGitTreeObject getParent();

  //potentially others. SymLink, blob?
  public static enum Type {
    FILE, DIRECTORY
  }
  
  public static enum Status {
    //untracked
    UNTRACKED,
    //changed, but not updated
    MODIFIED,
    //changed and added to the index
    CHANGED_IN_INDEX,
    //in repository
    IN_REPOSITORY
  }

}
