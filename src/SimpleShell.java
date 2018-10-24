import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.*;

public class SimpleShell {
	public static void main(String[] args) throws java.io.IOException {
		List<String> list1 = new ArrayList<String>();
		Process process;
		ProcessBuilder build;
		// list1.add("ls");
		// // list.add("test");
		// // list.add("abc.txt");
		// System.out.println(System.getProperty("user.home"));
		// build = new ProcessBuilder(list1);
		// build.redirectErrorStream(true);
		//
		// build.directory(new File(".."));
		//
		// process = build.start();
		// build.directory(new File(System.getProperty("user.home")+"/scratch"));
		//
		// process = build.start();
		// System.out.println(System.getProperty("user.dir"));
		// BufferedReader stdInput1 = new BufferedReader(new
		// InputStreamReader(process.getInputStream()));
		// String s1 = null;
		// while ((s1 = stdInput1.readLine()) != null) {
		// System.out.println(s1);
		// }
		// System.out.println("command: " + build.command());

		// creating the procees
		// ProcessBuilder build = new ProcessBuilder(list);
		// build.directory(new File("test"));
		// build.redirectErrorStream(true);
		// starting the process
		// Process process = build.start();

		// System.out.println("command: " + build.command());
		// System.out.println("command: " + build.directory());

		// BufferedReader stdInput = new BufferedReader(new
		// InputStreamReader(process.getInputStream()));
		// String s = null;
		// while( (s = stdInput.readLine()) != null )
		// {
		// System.out.println(s);
		// }

		String commandLine;
		List<String> list;
		Scanner commands;

		List<String> commandLogs = new ArrayList<String>();
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String current_dir = System.getProperty("user.dir");

		// we break out with <control><C>
		while (true) {
			list = new ArrayList<String>();
			// read what the user entered
			System.out.print("jsh>");

			// read a input line
			commandLine = console.readLine();
//			commandLogs.add(commandLine);
			
			//History Feature
			if(commandLine.equals("!!")) {
				commandLine = commandLogs.get((commandLogs.size()-1));//catch
			}else if(commandLine.startsWith("!")) {
				int n = 0;
				Pattern p = Pattern.compile("[0-9]+");
				Matcher m = p.matcher(commandLine);
				while (m.find()) {
				    n = Integer.parseInt(m.group());
				}
				System.out.println(n);
				commandLine = commandLogs.get(n);//catch index out ofbound and show not found
				System.out.println(commandLine);				
			}else {
				commandLogs.add(commandLine);
			}
			
			// read each word
			commands = new Scanner(commandLine);
			while (commands.hasNext())
				list.add(commands.next());
			commands.close();

			//part1
			if (commandLine.equals(""))
				continue;
			else if (commandLine.equals("e"))
				break;
			else if (commandLine.equals("history")) {
				for (int i = 0; i < commandLogs.size(); i++) {
					System.out.println(i + " " + commandLogs.get(i));
				}
			} else if (list.get(0).equals("cd")) {
				if (list.size() > 1) {
					current_dir = list.get(1);
					System.out.println(current_dir);
				} else if (list.size() == 1) {
					current_dir = System.getProperty("user.dir");
					System.out.println(current_dir);
				}
			} else {

				// System.out.println(System.getProperty("user.dir"));
				try {
					build = new ProcessBuilder(list);
					build.redirectErrorStream(true);
					build.directory(new File(current_dir));
					process = build.start();
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String s = null;
					while ((s = stdInput.readLine()) != null) {
						System.out.println(s);
					}
//					System.out.println("command: " + build.command());
				} catch (Exception e) {
//					e.printStackTrace();
					System.out.println("No such command / can only work with current dir.");
				}

			}

			//
			// // if the user entered a return, just loop again
			// if (commandLine.equals(""))
			// continue;

			/**
			 * The steps are: (1) parse the input to obtain the command and any parameters
			 * (2) create a ProcessBuilder object (3) start the process (4) obtain the
			 * output stream (5) output the contents returned by the command
			 */
		} // while

	}// main
}// SimpleShell
