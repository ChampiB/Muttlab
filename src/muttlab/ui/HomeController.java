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
    synchronized void updateTasksQueue() {
        if (!model.isCurrentTaskRunning()) {
            model.moveTasksFinishedToHistory();
            model.runNext();
        }
    }

    /**
     * Build the command using the command line and push it to the model.
     * @param command: The command line.
     */
    synchronized public void handleNewCommand(String command) {
        model.handleNewCommand(new CommandTask(CommandFactory.create(command)));
    }
}