package edu.nyu.cs.javagit.client.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * <code>ProcessUtilities</code> contains methods to help managing processes.
 */
public class ProcessUtilities {

  /**
   * Start a process.
   * 
   * @param pb
   *          The <code>ProcessBuilder</code> to use to start the process.
   * @return The started process.
   * @exception IOException
   *              An <code>IOException</code> is thrown if there is trouble starting the
   *              sub-process.
   */
  public static Process startProcess(ProcessBuilder pb) throws IOException {
    try {
      return pb.start();
    } catch (IOException e) {
      IOException toThrow = new IOException(ExceptionMessageMap.getMessage("010100"));
      toThrow.initCause(e);
      throw toThrow;
    }
  }

  /**
   * Reads the output from the process and prints it to stdout.
   * 
   * @param p
   *          The process from which to read the output.
   * @exception IOException
   *              An <code>IOException</code> is thrown if there is trouble reading input from the
   *              sub-process.
   */
  public static void getProcessOutput(Process p, IParser parser) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while (true) {
      try {
        String str = br.readLine();
        if (null == str) {
          break;
        }
        parser.parseLine(str);
      } catch (IOException e) {
        /*
         * TODO: add logging of any information already read from the InputStream. -- jhl388
         * 06.14.2008
         */
        IOException toThrow = new IOException(ExceptionMessageMap.getMessage("010101"));
        toThrow.initCause(e);
        throw toThrow;
      }
    }
    System.out.println();
  }

  /**
   * Waits for a process to terminate and then destroys it.
   * 
   * @param p
   *          The process to wait for and destroy.
   * @return The exit value of the process. By convention, 0 indicates normal termination.
   */
  public static int waitForAndDestroyProcess(Process p) {
    /*
     * I'm not sure this is the best way to handle waiting for a process to complete. -- jhl388
     * 06.14.2008
     */
    while (true) {
      try {
        int i = p.waitFor();
        p.destroy();
        return i;
      } catch (InterruptedException e) {
        // TODO: deal with this interrupted exception in a better manner. -- jhl388 06.14.2008
        continue;
      }
    }
  }

}
