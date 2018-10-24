import java.io.*;
import java.util.*;

public class SimpleShell {
	public static void main(String[] args) throws java.io.IOException {

		String commandLine;
		List<String> list;
		Scanner commands;

		CommandLogs history = new CommandLogs();
		CommandProcessor cp = new CommandProcessor();
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		// we break out with <control><C>
		while (true) {
			list = new ArrayList<String>();
			// read what the user entered
			System.out.print("jsh>");

			// read a input line
			commandLine = console.readLine();
			
			//History
			try {
				if(commandLine.startsWith("!")) {
					commandLine = history.getLog(commandLine);
				}else {
					history.add(commandLine);
				}
			} catch (Exception e) {
				commandLine = "";
				System.err.println("History log not found!");
			}	
			
			
			// read each word
			commands = new Scanner(commandLine);
			while (commands.hasNext())
				list.add(commands.next());
			commands.close();
			
			//process command
			if (commandLine.equals(""))
				continue;
			else if (commandLine.equals("history"))
				System.out.println(history);
			else {
				cp.process(list);
			}
		}//while
	}//main
}//SimpleShell
