package edu.nyu.cs.javagit.test.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;

/**
 * Some simple file utility methods for helping with testing java git functionality.
 */
public class FileUtilities {

  // The temp directory to use on unix systems.
  private static final String UNIX_TMP_DIR = "/tmp/";

  // The temp directory to use on windows systems.
  private static final String WINDOWS_TMP_DIR = "c:\\";

  // The temp directory for the current running system.
  private static final String BASE_TMP_DIR;

  /** True if running on windows. False if not running on windows (assuming Unix/Linux/OS X then). */
  public static final boolean IS_WINDOWS;

  static {
    if (System.getProperty("os.name").contains("indows")) {
      IS_WINDOWS = true;
      BASE_TMP_DIR = WINDOWS_TMP_DIR;
    } else {
      IS_WINDOWS = false;
      BASE_TMP_DIR = UNIX_TMP_DIR;
    }
  }

  /**
   * Create a temp directory based on the supplied directory base name. The directory will be
   * created under the temp directory for the system running the program.
   * 
   * @param baseDirName
   *          A base name for the directory. If this directory exists, a directory based on this
   *          name will be created.
   * @return A <code>File</code> instance representing the created directory.
   */
  public static File createTempDirectory(String baseDirName) {
    int num = 0;
    File file = new File(BASE_TMP_DIR + baseDirName);
    while (!file.mkdir()) {
      file = new File(BASE_TMP_DIR + baseDirName + Integer.toString(num++));
    }

    return file;
  }

  /**
   * Create a new file and write the contents to it.
   * 
   * @param baseDir
   *          The base directory of the repo.
   * @param filePath
   *          The relative path to the file with respect to the base directory.
   * @param contents
   *          Some contents to write to the file.
   * @return A <code>File</code> object representation of the file.
   * @throws IOException
   *           If there are problems creating the file or writing the contents to the file.
   */
  public static File createFile(File baseDir, String filePath, String contents) throws IOException {
    File file = new File(baseDir.getAbsolutePath() + File.separator + filePath);
    file.createNewFile();
    FileWriter fw = new FileWriter(file);
    fw.write(contents);
    fw.flush();
    return file;
  }

  /**
   * Recursively deletes the specified file/directory.
   * 
   * @param dirOrFile
   *          The file or directory to delete recursively and forcefully.
   * @throws JavaGitException
   *           Thrown if a file or directory was not deleted.
   */
  public static void removeDirectoryRecursivelyAndForcefully(File dirOrFile)
      throws JavaGitException {
    File[] children = dirOrFile.listFiles();
    if (null != children) {
      for (File f : children) {
        removeDirectoryRecursivelyAndForcefully(f);
      }
    }
    if (!dirOrFile.delete()) {
      throw new JavaGitException(-1, "-1:  Unable to delete file/directory.  { path=["
          + dirOrFile.getAbsolutePath() + "] }");
    }
  }
  
  /**
   * Append some text to an existing file.
   * @param file File that will be modified
   * @param appendText The text that will be appended to the file
   * @throws FileNotFoundException Exception thrown if the file does not exist.
   * @throws IOException thrown if the IO operation fails.
   */
  public static void modifyFileContents(File file, String appendText) 
    throws FileNotFoundException, IOException {
    if ( ! file.exists()) {
      throw new FileNotFoundException("File does not exist");
    }
    FileWriter fw = new FileWriter(file);
    fw.append(appendText);
    fw.close();
  }
  
}
