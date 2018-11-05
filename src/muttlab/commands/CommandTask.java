package muttlab.commands;

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
     * Execute the command.
     * @param stack: The current stack of matrices.
     * @param p: The procedure to call after the execution.
     */
    public void execute(ObservableStackWrapper<Matrix> stack, Procedure p) {
        Thread t = new Thread(() -> {
            status = Status.FAIL;
            try {
                command.execute(out, stack);
                status = Status.WAITING_FOR_OUTPUT;
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
            p.call();
        });
        t.setDaemon(true);
        t.run();
    }

    /**
     * Flush the output.
     * @return the method output.
     */
    public String flush() {
        // TODO call command.flush()
        if (status != Status.FAIL)
            status = Status.SUCCESS;
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