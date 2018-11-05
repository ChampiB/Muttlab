package muttlab.ui;

import muttlab.commands.CommandFactory;
import muttlab.commands.CommandTask;

public class HomeController {

    private HomeModel model;

    /**
     * Singleton design pattern.
     */
    private static HomeController instance = new HomeController();

    public static HomeController get() { return instance; }

    private HomeController() {}

    /**
     * Set the model.
     * @param model: The home model.
     * @return this.
     */
    public HomeController setModel(HomeModel model) {
        this.model = model;
        return this;
    }

    /**
     * Update the tasks' queue.
     */
    void updateTasksQueue() {
        if (!model.isCurrentTaskRunning()) {
            model.moveTaskFinishedToHistory();
            model.runNext();
        }
    }

    /**
     * Build and execute the command using the command line.
     * @param command: The command line.
     */
    public void executeCommand(String command) {
        model.handleNewCommand(new CommandTask(CommandFactory.create(command)));
    }
}