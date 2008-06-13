package edu.nyu.cs.javagit.client.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <code>ProcessUtilities</code> contains methods to help managing processes.
 */
public class ProcessUtilities {

	/**
	 * Start a process.
	 * 
	 * @param pb
	 *            The <code>ProcessBuilder</code> to use to start the process.
	 * @return The started process.
	 */
	public static Process startProcess(ProcessBuilder pb) {
		try {
			return pb.start();
		} catch (IOException e) {
			System.out.println("Error starting process.");
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	/**
	 * Reads the output from the process and prints it to stdout.
	 * 
	 * @param p
	 *            The process from which to read the output.
	 */
	public static void getProcessOutput(Process p) {
		BufferedReader br = new BufferedReader(new InputStreamReader(p
				.getInputStream()));
		while (true) {
			try {
				String str = br.readLine();
				if (null == str) {
					break;
				}
				System.out.println(str);
			} catch (IOException e) {
				System.out
						.println("Error reading a line of input from the process.");
				e.printStackTrace();
				System.exit(1);
			}
		}
		System.out.println();
	}

	/**
	 * Waits for a process to terminate and then destroys it.
	 * 
	 * @param p
	 *            The process to wait for and destroy.
	 */
	public static void waitForAndDestroyProcess(Process p) {
		while (true) {
			try {
				int i = p.waitFor();
				break;
			} catch (InterruptedException e) {
			}
		}
	
		p.destroy();
	}
}
