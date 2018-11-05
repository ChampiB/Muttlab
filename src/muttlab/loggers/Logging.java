package muttlab.loggers;

public class Logging {

    /**
     * Create the chain of responsibility for logging.
     */
    private static Logger logger = new Logger(LoggingLevel.ERROR, "error.log")
            .appendLogger(new Logger(LoggingLevel.WARNING, "warning.log"))
            .appendLogger(new Logger(LoggingLevel.INFO, "info.log"));

    /**
     * Log the message according to the logging level.
     * @param level : The logging level.
     * @param message : The message to log.
     */
    public static void log(LoggingLevel level, String message) {
        logger.log(level, message);
    }
}
