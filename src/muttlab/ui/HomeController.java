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

    private HomeController() {
        model = HomeModel.get();
    }

    /**
     * Update the tasks' queue.
     */
    synchronized void updateTasksQueue() {
        model.moveTaskFinishedToHistory();
        if (!model.isCurrentTaskRunning()) {
            model.runNext();
        }
    }

    /**
     * Build the command using the command line and push it to the model.
     * @param command: The command line.
     */
    synchronized public void handleNewCommand(String command) {
        model.handleNewCommand(new CommandTask(CommandFactory.create(command), model.getMatricesStack()));
    }
}