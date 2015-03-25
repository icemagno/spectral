package graphsimul.app;

import graphsimul.app.command.ActionCommand;
import graphsimul.app.command.ShowEntryFormCommand;
import graphsimul.app.command.SubmitCommand;
import graphsimul.app.command.SubmitJobCommand;

import java.util.HashMap;

public class RequestHelper {
	public HashMap<String, ActionCommand> commands;

	public RequestHelper() {
		setMapCommands();
	}

	public ActionCommand command(String cmd) {
		return this.commands.get(cmd);
	}

	private void setMapCommands() {
		this.commands = new HashMap<String, ActionCommand>();

		this.commands.put("submitValues", new SubmitCommand());
		this.commands.put("submitJob", new SubmitJobCommand());
		this.commands.put("transformarLatex", new ShowEntryFormCommand());
		this.commands.put("listarWorkflows", new WorkflowListCommand());
	}

}