package edu.mtu.tinventory.logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The central class to handle all of the local logging to console.
 * This class also handles writing the logs to files.
 * All calls to print to the console should be routed here.
 */
public class LocalLog {
	private static final Logger LOG = Logger.getLogger("TInventory");
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
	private static final Path LOGS_FOLDER = Paths.get("tinventory-logs");
	private static boolean setup = false;

	/**
	 * Logs the exception as a SEVERE message. THe message will be taken from e.getMessage().
	 * Good for when exceptions are encountered, and you don't want to specify your own custom message.
	 * @param e The exception encountered.
	 */
	public static void exception(Exception e) {
		log(Level.SEVERE, e.getMessage(), e);
	}

	/**
	 * Logs a SEVERE message and then prints the stack trace afterwards. Good for when exceptions are encountered.
	 * @param message The message to log.
	 * @param e The exception encountered.
	 */
	public static void exception(String message, Exception e) {
		log(Level.SEVERE, message, e);
	}

	/**
	 * Logs a SEVERE message. Good for when an illegal action has happened in code, but no exception was encountered.
	 * @param message The message to log.
	 */
	public static void error(String message) {
		log(Level.SEVERE, message);
	}

	/**
	 * Logs a WARNING message. Good for when something isn't necessarily wrong, but might be something to look at.
	 * @param message The message to log.
	 */
	public static void warning(String message) {
		log(Level.WARNING, message);
	}

	/**
	 * Logs an INFO message. Good for general logging messages that are mildly important.
	 * @param message The message to log.
	 */
	public static void info(String message) {
		log(Level.INFO, message);
	}

	/**
	 * Logs a FINE message. Good for debugging certain parts of the code.
	 * These messages will only log if the command line switch -debug or -trace were used to start the program.
	 * @param message The message to log.
	 */
	public static void debug(String message) {
		log(Level.FINE, message);
	}

	/**
	 * Logs a FINER message. Good for tracing where your code is going.
	 * These messages will only log if the command line switch -trace was used to start the program.
	 * @param message The message to log.
	 */
	public static void trace(String message) {
		log(Level.FINER, message);
	}

	/**
	 * Sets up the logging interface. Will only run once. Run when the program starts up.
	 * @param loggingLevel The level of messages to actually log to console/file.
	 */
	public static void setupLog(Level loggingLevel) {
		if(!setup) {
			LOG.setLevel(loggingLevel);
			LOG.setUseParentHandlers(false); // Disables the default console handler.
			ConsoleHandler console = new ConsoleHandler();
			console.setFormatter(new LogFormatter());
			LOG.addHandler(console);
			try {
				if (Files.notExists(LOGS_FOLDER)) {
					Files.createDirectory(LOGS_FOLDER);
				}
				FileHandler latest = new FileHandler(LOGS_FOLDER.toString() + File.separator + "latest.log"); // Will always be the latest log
				FileHandler dated = new FileHandler(LOGS_FOLDER.toString() + File.separator + FORMATTER.format(LocalDateTime.now()) + ".log"); // Same log with date, good for looking back if need be.
				latest.setFormatter(new LogFormatter());
				dated.setFormatter(new LogFormatter());
				LOG.addHandler(latest);
				LOG.addHandler(dated);
				setup = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void log(Level level, String message) {
		log(level, message, null);
	}

	private static void log(Level level, String message, Exception exception) {
		StackTraceElement ste = getCaller();
		LogRecord record = new LogRecord(level, message);
		record.setSourceClassName(ste.getClassName());
		record.setSourceMethodName(ste.getMethodName());
		if(exception != null) {
			record.setThrown(exception);
		}
		LOG.log(record);
	}

	private static StackTraceElement getCaller() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		for(StackTraceElement e : stacktrace) {
			if(!e.getClassName().contains("LocalLog") && !e.getClassName().contains("Thread")) {
				return e;
			}
		}
		return stacktrace[0]; // Just to avoid NPEs
	}
}
