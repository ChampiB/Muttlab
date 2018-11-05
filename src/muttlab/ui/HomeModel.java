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
    synchronized void handleNewCommand(CommandTask ct) {
        getRunningTasks().add(ct);
        if (getRunningTasks().size() == 1)
            ct.execute(getMatricesStack(), this::callbackEndOfTask);
    }

    /**
     * Callback that is called at the end of each task.
     */
    synchronized public void callbackEndOfTask() {
        // Flush the output of the first running task.
        CommandTask ct = getRunningTasks().get(0);
        getConsoleOutput().setValue(ct.flush(getMatricesStack()));
        // Flush the output of the first running task.
        getRunningTasks().remove(0);
        getTasksHistory().add(ct);
        // Execute the next task.
        if (!getRunningTasks().isEmpty())
            getRunningTasks().get(0).execute(getMatricesStack(), this::callbackEndOfTask);
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