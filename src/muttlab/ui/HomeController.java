package muttlab.ui;

import muttlab.commands.CommandFactory;
import muttlab.commands.CommandTask;

public class HomeController {

    private HomeModel model;

    /**
     * Build the home controller.
     * @param model: The home model.
     */
    public HomeController(HomeModel model) {
        this.model = model;
    }

    /**
     * Build and execute the command using the command line.
     * @param command: The command line.
     */
    void executeCommand(String command) {
        model.handleNewCommand(new CommandTask(CommandFactory.create(command)));
    }
}