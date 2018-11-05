package muttlab.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import muttlab.commands.CommandTask;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

public class HomeModel {

    private ObservableList<CommandTask> runningTasks;
    private ObservableList<CommandTask> tasksHistory;
    private ObservableStackWrapper<Matrix> matrices;
    private StringProperty consoleOutput;

    /**
     * Create the home model.
     */
    public HomeModel() {
        runningTasks = FXCollections.observableArrayList();
        tasksHistory = FXCollections.observableArrayList();
        matrices = new ObservableStackWrapper<>();
        consoleOutput = new SimpleStringProperty();
    }

    /**
     * Add the command in the running tasks and execute it if needed.
     * @param ct: The command to execute.
     */
    void handleNewCommand(CommandTask ct) {
        getRunningTasks().add(ct);
    }

    /**
     * TODO
     * @return
     */
    boolean isCurrentTaskRunning() {
        if (getRunningTasks().isEmpty())
            return false;
        CommandTask current = getRunningTasks().get(0);
        return current.hasBeenRun() && !current.isRunOver();
    }

    /**
     * TODO
     * @return
     */
    void moveTaskFinishedToHistory() {
        for (int i = 0; i < getRunningTasks().size(); ++i) {
            CommandTask task = getRunningTasks().get(i);
            if (task.isRunOver()) {
                task.flush(getMatricesStack());
                getTasksHistory().add(task);
                getRunningTasks().remove(i);
            }
        }
    }

    /**
     * Run the next that need to be run.
     */
    void runNext() {
        if (getRunningTasks().isEmpty())
            return;
        getRunningTasks().get(0).execute(getMatricesStack());
    }

    /**
     * Getter.
     * @return the observable list (running tasks).
     */
    ObservableList<CommandTask> getRunningTasks() {
        return runningTasks;
    }

    /**
     * Getter.
     * @return the observable list (tasks history).
     */
    ObservableList<CommandTask> getTasksHistory() {
        return tasksHistory;
    }

    /**
     * Getter.
     * @return the observable list (matrices).
     */
    public ObservableList<Matrix> getMatrices() {
        return matrices.get();
    }

    /**
     * Getter.
     * @return the console output.
     */
    public StringProperty getConsoleOutput() {
        return consoleOutput;
    }

    /**
     * Getter.
     * @return the observable stack (matrices).
     */
    public ObservableStackWrapper<Matrix> getMatricesStack() {
        return matrices;
    }

}