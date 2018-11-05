package muttlab.commands;

import javafx.concurrent.Task;
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

    /**
     * The tasks' status.
     */
    enum Status {
        WAITING_FOR_RUN,
        WAITING_FOR_OUTPUT,
        SUCCESS,
        FAIL
    }

    private PipedInputStream in;
    private PipedOutputStream out;
    private Command command;
    private Status status;

    /**
     * Create the command task, which is a wrapper around a command.
     * @param command: The command to wrap.
     */
    public CommandTask(Command command) {
        try {
            in = new PipedInputStream();
            out = new PipedOutputStream(in);
        } catch (Exception e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
        }
        status = Status.WAITING_FOR_RUN;
        this.command = command;
    }

    /**
     * Execute the procedure and handle the error.
     * @param p: The procedure.
     */
    private void handleErrorWrapper(Procedure p) {
        try {
            p.call();
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
    }

    /**
     * Execute the command.
     * @param stack: The current stack of matrices.
     * @param p: The procedure to call after the execution.
     */
    public void execute(ObservableStackWrapper<Matrix> stack, Runnable p) {
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                status = Status.FAIL;
                handleErrorWrapper(() -> {
                    command.execute(out, stack);
                    status = Status.WAITING_FOR_OUTPUT;
                });
                return null;
            }
        };
        t.setOnSucceeded((x) -> p.run());
        Thread thread = new Thread(t);
        thread.setDaemon(true);
        thread.run();
    }

    /**
     * Flush the output.
     * @param stack: The current stack of matrices.
     * @return the method output.
     */
    public String flush(ObservableStackWrapper<Matrix> stack) {
        if (status != Status.FAIL) {
            handleErrorWrapper(() -> command.flush(stack));
            status = Status.SUCCESS;
        }
        try {
            out.close();
            StringBuilder output = new StringBuilder();
            BufferedReader s = new BufferedReader(new InputStreamReader(in));
            s.lines().forEach(line -> output.append(line).append("\n"));
            return output.toString();
        } catch (Exception e) {
            return MuttLabStrings.FAIL_TO_FLUSH_COMMAND_OUTPUT.toString();
        }
    }

    /**
     * Convert the task into a string.
     * @return the string.
     */
    @Override
    public String toString() {
        return command.getName() + ": " + status.toString();
    }
}