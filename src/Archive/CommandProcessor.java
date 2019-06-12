import java.io.*;
import java.util.List;

public class CommandProcessor {
	private Process process;
	private ProcessBuilder build;
	private List<String> list;
	private String current_dir;
	private String previous_dir;
	private String OSName;

	public CommandProcessor() {
		current_dir = System.getProperty("user.dir");
		OSName = System.getProperty("os.name");
	}
	/**
	 * Method for changing directory for all os.
	 * @return is true if directory exists and changed.
	 */
	private boolean changeDirectory() {
		previous_dir = current_dir;//retain for failsafe.
		if (OSName.contains("Windows")) {
			if (list.get(2).equalsIgnoreCase("cd")) {
				if (list.size() > 3) {
					current_dir += "\\"+list.get(3);
				} else if (list.size() == 3) {
					current_dir = System.getProperty("user.home");
				}
				return new File(current_dir).exists();
			}
		} else {
			if (list.get(0).equalsIgnoreCase("cd")) {
				if (list.size() > 1) {
					current_dir += "/"+ list.get(1);
				} else if (list.size() == 1) {
					current_dir = System.getProperty("user.home");
				}
				return new File(current_dir).exists();
			}
		}
		return false;
	}

	public void process(List<String> ls) {
		list = ls;
		if (OSName.contains("Windows")) {
			list.add(0, "cmd.exe");
			list.add(1, "/c");
		}
		
		if(!changeDirectory()) {
			try {
				// process command
				build = new ProcessBuilder(list);
				build.redirectErrorStream(true);
				build.directory(new File(current_dir));
				process = build.start();

				// print any output
				BufferedReader processOutput = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				String result = null;
				while ((result = processOutput.readLine()) != null) {
					System.out.println(result);
				}
				processOutput.close();
			} catch (Exception e) {
				current_dir = previous_dir;
				System.out.println("No such command or directory");
			}
		}
	}

}
