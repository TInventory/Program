package edu.mtu.tinventory.util;

/**
 * Utility methods for Strings.
 * Because I'm too lazy to add Guava as a dependency just for these.
 */
public class StringUtils {

	/**
	 * Returns if a string is null or empty.
	 * @param s The string to check
	 * @return true if the string is null or empty, false otherwise.
	 */
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}
}
