package game;

public class Command {
	private final String command;
	private final String parameter;
	private final String information;
	
	public Command (String command, String parameter, String information) {
		this.command = command;
		this.parameter = parameter;
		this.information = information;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getInformation() {
		return information;
	}
	
	public String getParameter() {
		return parameter;
	}
	
	@Override
	public String toString () {
		return command + " " + parameter + (parameter.length() > 0 ? " " : "") + information; 
	}
}
