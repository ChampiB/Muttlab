package muttlab.loggers;

import java.io.*;
import java.util.Optional;

public class Logger {

    private Optional<Logger> next;
    private LoggingLevel level;
    private String loggingFile;

    /**
     * Logger constructor.
     * @param level : The level of logging the logger must care about.
     * @param loggingFile : The logging file to use for logging.
     */
    public Logger(LoggingLevel level, String loggingFile) {
        this.next = Optional.empty();
        this.loggingFile = loggingFile;
        this.level = level;
    }

    /**
     * Create the file if not exist and append the line/message in the file.
     * @param message : The message to log.
     * @return true if the message have been correctly logged and false otherwise.
     */
    protected boolean log(String message) {
        boolean success = false;
        PrintWriter pw = null;
        try {
            File file = new File(loggingFile);
            if(!file.exists()) file.createNewFile();
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
            pw.println(message);
            success = true;
        } catch(Exception e){
            // Do nothing.
            // If the logging fail, we can't log the error and we don't want the program to exit.
        } finally {
            if (pw != null) pw.close();
        }
        return success;
    }

    /**
     * Getter method.
     * @return the logger's level.
     */
    protected LoggingLevel getLevel() {
        return level;
    }

    /**
     * Log the message if it correspond to the logger's logging level.
     * @param level : The logging level.
     * @param message : The message to log.
     */
    public void log(LoggingLevel level, String message) {
        if (getLevel() == level) {
            log(message);
        }
        next.ifPresent(logger -> logger.log(level, message));
    }

    /**
     * Append a logger in the chain.
     * @param logger : The logger to append.
     * @return the current logger.
     */
    public Logger appendLogger(Logger logger) {
        Logger last = this;
        while (last.next.isPresent())
            last = last.next.get();
        last.next = Optional.ofNullable(logger);
        return this;
    }
}
