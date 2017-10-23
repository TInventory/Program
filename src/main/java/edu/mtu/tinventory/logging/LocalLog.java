package edu.mtu.tinventory.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalLog {
	private static final Logger LOG = Logger.getLogger("TInventory");

	static {
		//TODO: Set up logger here
	}

	public static void exception(String message, Exception e) {
		LOG.log(Level.SEVERE, message, e);
	}

	public static void error(String message) {
		LOG.log(Level.SEVERE, message);
	}

	public static void warning(String message) {
		LOG.log(Level.WARNING, message);
	}

	public static void info(String message) {
		LOG.log(Level.INFO, message);
	}

	public static void debug(String message) {
		LOG.log(Level.FINE, message);
	}
}
