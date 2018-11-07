package muttlab.commands;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import muttlab.exceptions.UserException;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.*;
import java.util.Arrays;

public class CommandTask {

    private PipedInputStream in;
    private PipedOutputStream out;
    private Command command;
    private String commandOutput;
    private ObservableStackWrapper<Matrix> matrices;
    private Status status;
    private Button runButton;

    /**
     * The tasks' status.
     */
    enum Status {
        WAITING_FOR_RUN("./src/muttlab/ui/img/not-started-status.png"),
        RUNNING("./src/muttlab/ui/img/running-status.png"),
        RUN_SUCCESS("./src/muttlab/ui/img/running-status.png"),
        RUN_FAIL("./src/muttlab/ui/img/running-status.png"),
        TASK_FAIL("./src/muttlab/ui/img/fail-status.png"),
        TASK_SUCCESS("./src/muttlab/ui/img/success-status.png");

        String text;

        Status(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * Create the command task, which is a wrapper around a command.
     * @param command: The command to wrap.
     */
    public CommandTask(Command command, ObservableStackWrapper<Matrix> matrices) {
        try {
            in = new PipedInputStream();
            out = new PipedOutputStream(in);
        } catch (Exception e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
        }
        this.matrices = matrices;
        this.status = Status.WAITING_FOR_RUN;
        this.command = command;
        this.runButton = createRunButton();
    }

    /**
     * Execute the procedure and handle the error.
     * @param p: The procedure.
     * @return the error status: true if an error occurred and false otherwise.
     */
    private boolean handleErrorWrapper(Procedure p) {
        try {
            p.call();
            return false;
        } catch (UserException e) {
            DisplayHelper.println(out, e.getMessage());
        } catch (IOException e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
            DisplayHelper.println(out, MuttLabStrings.IO_EXCEPTION.toString());
        } catch (Exception e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
            Logging.log(LoggingLevel.ERROR, Arrays.toString(e.getStackTrace()));
            DisplayHelper.println(out, MuttLabStrings.COMMAND_FAILED_CHECK_LOG.toString());
        }
        return true;
    }

    /**
     * Execute the command.
     */
    public void execute() {
        Thread thread = new Thread(() -> {
            status = Status.RUNNING;
            if (handleErrorWrapper(() -> command.execute(out, matrices))) {
                status = Status.RUN_FAIL;
            } else {
                status = Status.RUN_SUCCESS;
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Check if the task's run is over.
     * @return true if the task's run is over and false otherwise.
     */
    public boolean isRunOver() {
        return status == Status.RUN_FAIL || status == Status.RUN_SUCCESS;
    }

    /**
     * Check true if the task has been run and false otherwise.
     * @return true if the task has been run and false otherwise.
     */
    public boolean hasBeenRun() {
        return status != Status.WAITING_FOR_RUN;
    }

    /**
     * Flush the output.
     * @param stack: The current stack of matrices.
     * @return the method output.
     */
    public String flush(ObservableStackWrapper<Matrix> stack) {
        // Apply the command's modification.
        boolean error = false;
        if (status != Status.RUN_FAIL)
            error = handleErrorWrapper(() -> command.flush(stack));
        // Get the command's textual output.
        try {
            out.close();
            StringBuilder output = new StringBuilder();
            BufferedReader s = new BufferedReader(new InputStreamReader(in));
            s.lines().forEach(line -> output.append(line).append("\n"));
            commandOutput = output.toString();
            if (commandOutput.equals(""))
                commandOutput = MuttLabStrings.TASK_DONE.toString() + "\n";
        } catch (Exception e) {
            commandOutput = MuttLabStrings.FAIL_TO_FLUSH_COMMAND_OUTPUT.toString();
            error = true;
        }
        // Set the final tasks status.
        status = (error || status == Status.RUN_FAIL) ? Status.TASK_FAIL : Status.TASK_SUCCESS;
        return commandOutput;
    }

    /**
     * Create the button to run the task.
     * @return the button.
     */
    private Button createRunButton() {
        // Create the button.
        Button runButton = new Button();
        // Set the background.
        File img = new File("./src/muttlab/ui/img/green-arrow.png");
        Image image = new Image(img.toURI().toString(), 20, 20, true, true);
        Background background= new Background(new BackgroundImage(
                image,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        runButton.setBackground(background);
        // Set the callback.
        runButton.setOnAction(event -> {
            if (hasBeenRun())
                return;
            this.execute();
        });
        return runButton;
    }

    /**
     * Getter.
     * @return the task's output.
     */
    public String getOutput() {
        return commandOutput;
    }

    /**
     * Getter.
     * @return the task's name.
     */
    public String getName() {
        return command.getName();
    }

    /**
     * Getter.
     * @return the task's status.
     */
    public ImageView getStatus() {
        File file = new File(status.toString());
        Image img = new Image(file.toURI().toString(), 20, 20, true, true);
        return new ImageView(img);
    }

    /**
     * Getter.
     * @return the button that run the task.
     */
    public Button getRunButton() {
        return runButton;
    }
}