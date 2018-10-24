import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

public class CommandProcessor {
	Process process;
	ProcessBuilder build;
	List<String> ls;
	String current_dir;

	public CommandProcessor() {
		current_dir = System.getProperty("user.dir");
	}

	public void process(List<String> ls) {
		if (ls.get(0).equals("cd")) {
			if (ls.size() > 1) {
				current_dir = ls.get(1);
			} else if (ls.size() == 1) {
				current_dir = System.getProperty("user.dir");
			}
		} else {
			try {
				//process command
				build = new ProcessBuilder(ls);
				build.redirectErrorStream(true);
				build.directory(new File(current_dir));
				process = build.start();
				//output
				BufferedReader processOutput = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				String result = null;
				while ((result = processOutput.readLine()) != null) {
					System.out.println(result);
				}
			} catch (Exception e) {
				System.out.println("No such command / can only work with current dir.");
			}
		}
	}

}
