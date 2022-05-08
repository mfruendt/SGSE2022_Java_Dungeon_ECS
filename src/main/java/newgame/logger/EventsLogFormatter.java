package newgame.logger;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;    

/** Class to formats log, therefore the level, timestamp, message und origin are logged
 *
 * @author Benjamin Krüger
 */
public class EventsLogFormatter extends SimpleFormatter
{
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /** Format to be logged record
     * 
     * level, timestamp, message and origin (class & method) will be logged
     * 
     * @param record To be formatted and logged record
     * @return The formatted record as string
     */
    @Override
    public String format(LogRecord record)
    {
        LocalTime currentTime = LocalTime.now();
        String logEntry = "[" + record.getLevel() + "] ";
        logEntry += "(" + currentTime.format(dateTimeFormatter) + ")";
        logEntry += ": " + record.getMessage() + " ";
        logEntry += "<" + record.getSourceClassName() + "." + record.getSourceMethodName() + ">\n"; 
        return logEntry;
    }
}
