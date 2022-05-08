package newgame.logger;

import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Global Logger to log events on console and log file; static use
 * 
 * @author Benjamin Krüger
 */
public class GameEventsLogger {
    private static final String LOGS_FOLDER_NAME = "logs/";
    private static final String LOG_FILE_EXTENSION = ".log";
    private static final int MAX_LOG_FILE_SIZE = 100 * 1024 * 1024;
    private static final int MAX_LOG_FILES_COUNT = 10;
    private static final boolean SHOULD_APPEND_NEW_LOGS = false;
    private static Logger logger;

    private GameEventsLogger()
    {
        try 
        {
            logger = Logger.getLogger(GameEventsLogger.class.getName());
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime localDateTime = LocalDateTime.now();
            String logFilePath = System.getProperty("user.dir") + "/" + LOGS_FOLDER_NAME;
            File file = new File(logFilePath);
            if (!file.exists())
            {
                if(!file.mkdir())
                {
                    throw new RuntimeException();
                }
            }
            logFilePath += dateTimeFormatter.format(localDateTime) + LOG_FILE_EXTENSION;
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(new EventsLogFormatter());
            logger.addHandler(consoleHandler);
    
            FileHandler fileHandler = new FileHandler(logFilePath, MAX_LOG_FILE_SIZE, MAX_LOG_FILES_COUNT, SHOULD_APPEND_NEW_LOGS);
            fileHandler.setLevel(Level.FINEST);
            fileHandler.setFormatter(new EventsLogFormatter());
            logger.addHandler(fileHandler);
        }
        catch (SecurityException | IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 
     * @return global logger
     */
    public static Logger getLogger()
    {
        if(logger == null)
        {
            new GameEventsLogger();
        }
        return logger;
    }
}
