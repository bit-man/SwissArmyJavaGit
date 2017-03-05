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
package edu.nyu.cs.javagit.client.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.JavaGitProperty;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * <code>ProcessUtilities</code> contains methods to help managing processes.
 */
public class ProcessUtilities {

    private final static Logger LOGGER = Logger.getLogger("javagit.commands");

    static {

        LOGGER.setUseParentHandlers(false);   // Don't output to console
        if (!JavaGitProperty.LOG_COMMANDS_DISABLE.getPropValueBoolean())
            initLogger();

    }

    private static void initLogger() {
        try {
            FileHandler fileHandler = new FileHandler(JavaGitProperty.LOG_COMMANDS_PATH.getPropValueString(),
                                                      JavaGitProperty.LOG_COMMANDS_APPEND.getPropValueBoolean());
            fileHandler.setFormatter(new JustThisFormatter());

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);

            if (JavaGitProperty.LOG_COMMANDS_APPEND.getPropValueBoolean())
                LOGGER.info("------------------------ new JavaGit run (" + new Date() + ")");

        } catch (IOException e) {
            if (JavaGitProperty.LOG_COMMANDS_FAIL_ON_INIT.getPropValueBoolean()) {
                System.out.println("JavaGit ERROR: error while generating log");
                System.exit(-1);
            } else {
                System.out.println("JavaGit WARNING: error while generating log. Commands won't be logged");
            }
        }
    }

    // TODO (jhl): add unit tests for this class.

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
      IOException toThrow = new IOException(ExceptionMessageMap.getMessage("020100"));
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
  public static void getProcessOutput(Process p, IParser parser) throws JavaGitException {
      BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

      try {
          String str;
          while ((str = br.readLine()) != null) {
              parser.parseLine(str);
          }
      } catch (IOException e) {
          throw new JavaGitException(020101, ExceptionMessageMap.getMessage("020101"), e);
      }

  }

  /**
   * Waits for a process to terminate and then destroys it.
   *
   * @param p
   *          The process to wait for and destroy.
   * @return The exit value of the process. By convention, 0 indicates normal termination.
   */
  public static int waitForAndDestroyProcess(Process p, IParser parser) {
    /*
     * I'm not sure this is the best way to handle waiting for a process to complete. -- jhl388
     * 06.14.2008
     */
    while (true) {
      try {
        int i = p.waitFor();
        parser.processExitCode(p.exitValue());
        p.destroy();
        return i;
      } catch (InterruptedException e) {
        // BUG: if continuing with planned work it could harm the whole GIT command chain
        //      being executed. Work on a method to notify the API caller and let him/her
        //      decide on continuing or stopping the work being carried out
        continue;
      }
    }
  }

  // TODO (jhl388): Add a unit test for this method.
  /*
   * TODO (jhl388): The workingDirectory argument needs to be modified to take a File argument
   * instead of a String argument.
   */
  /**
   * Runs the command specified in the command line with the specified working directory. The
   * IParser is used to parse the response given by the command line.
   *
   * @param workingDirectory
   *          The working directory in with which to start the process.
   * @param parser
   *          The parser to use to parse the command line's response.
   * @param pb
   * @return The command response from the <code>IParser</code>.
   * @throws IOException
   *           Thrown if there are problems with the subprocess.
   * @throws JavaGitException
   */
  public static CommandResponse runCommand(File workingDirectory, IParser parser, ProcessBuilder pb) throws IOException, JavaGitException {

      if (workingDirectory != null) {
          pb.directory(workingDirectory);
      }

      pb.redirectErrorStream(true);
      LOGGER.info(asString(pb.command()));

      Process p = startProcess(pb);
      getProcessOutput(p, parser);
      waitForAndDestroyProcess(p, parser);

      return parser.getResponse();
  }

    private static String asString(List<String> commandLine) {
        String s = "";
        for (String cmd : commandLine)
            s += ( s.equals("") ?  "" : " ") + cmd;

        return s;
    }

    private static class JustThisFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n";
        }
    }
}
