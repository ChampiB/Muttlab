package muttlab.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

public class TasksManager {

    private List<Runnable> tasks = new LinkedList<>();
    private HomeController controller;

    // The only instance of the task manager.
    private static TasksManager instance = null;

    // Private constructor to ensure that there is only on instance of the class.
    private TasksManager(HomeController controller) {
        // Keep the reference of the controller in memory.
        this.controller = controller;
        // Frequently check if we need to run tasks.
        Timeline cron = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            controller.updateRunningTasks();
            if (!tasks.isEmpty()) {
                tasks.get(0).run();
                tasks.remove(0);
            }
        }));
        cron.setCycleCount(Timeline.INDEFINITE);
        cron.play();
    }

    // Initializer.
    public static void init(HomeController controller) {
        if (instance == null)
            instance = new TasksManager(controller);
    }

    /**
     * Add a new task into the list of tasks to execute.
     * @param task: The task to add.
     */
    public static void push(Runnable task) {
        instance.tasks.add(task);
    }

    /**
     * Push a new command to the controller.
     * @param command: The command to push.
     */
    public static void pushToController(String command) {
        instance.controller.handleNewCommand(command);
    }
}