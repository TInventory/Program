package edu.mtu.tinventory.util;

import edu.mtu.tinventory.data.Employee;
import java.util.UUID;

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

	/**
	 * Checks if the following string is a number.
	 * @param s The string to check
	 * @return true if the string is a number, false otherwise.
	 */
	public static boolean isNumber(String s) {
		if(isNullOrEmpty(s)) {
			return false;
		} else {
			try {
				Double.parseDouble(s);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}

	/**
	 * Adds hyphens back into UUID string and returns the UUID object.
	 * @param string The string of the UUID without hyphens.
	 * @return the UUID object for the String.
	 */
	public static UUID stringToUUID(String string) {
		if (string.length() < 20) {
			return null;
		}
		return UUID.fromString(string.substring(0, 8) +
				'-' + string.substring(8, 12) +
				'-' + string.substring(12, 16) +
				'-' + string.substring(16, 20) +
				'-' + string.substring(20));
	}

	/**
	 * Returns the default password for an employee.
	 * Format is {LASTNAME}FFF1.
	 * @param employee The employee to get the default password for.
	 * @return The default password for the employee.
	 */
	public static String getDefaultPassword(Employee employee) {
		return employee.getLastName().toLowerCase() +
				(employee.getFirstName().length() < 3 ? employee.getFirstName().toLowerCase() : employee.getFirstName().substring(0, 3).toLowerCase()) +
				"1";
	}
}
