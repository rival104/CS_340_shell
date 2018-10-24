import java.util.*;
import java.util.regex.*;

public class CommandLogs {
	private List<String> logs;
	
	public CommandLogs() {
		logs = new ArrayList<String>();
	}

	public void add(String command) {
		logs.add(command);
	}
	
	public String getLog(String log) throws Exception{
		try {
			if(log.equals("!!")) {
				//get the last log
				return logs.get((logs.size()-1));
			}else {
				//get the desired log with index number
				int n = 0;
				Pattern p = Pattern.compile("[0-9]+");
				Matcher m = p.matcher(log);
				while (m.find()) {
				    n = Integer.parseInt(m.group());
				}
				return logs.get(n);
			}
		}catch(IndexOutOfBoundsException iobe) {
			throw new Exception("Log not found!");
		}
	}
	//prints all logs
	public String toString() {
		String s = "";
		for (int i = 0; i < logs.size(); i++) {
			s += (i + " " + logs.get(i) + "\n");
		}
		return s;
	}
	
}
