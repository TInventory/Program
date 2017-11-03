package edu.mtu.tinventory.state;

import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.util.StringUtils;
import java.util.HashSet;
import java.util.Set;

public class StateRegistry {
	public static final String AVAILABLE_STATE = "AVAILABLE";
	public static final String SOLD_STATE = "SOLD";
	private static final Set<String> SET = new HashSet<>();

	static {
		String states = DatabaseInterface.getInstance().getStatesString();
		if(!StringUtils.isNullOrEmpty(states)) {
			for(String state : states.split(":")) {
				registerState(state.toUpperCase());
			}
		} else {
			registerState(AVAILABLE_STATE);
			registerState(SOLD_STATE);
		}
	}

	private StateRegistry() {} // Singleton

	/**
	 * Registers a new State to be used in the database.
	 * @param state The new state to be registered.
	 * @return false if the registry already has a state with that ID, true if the state was successfully registered.
	 */
	public static boolean registerState(String state) {
		return SET.add(state);
	}

	/**
	 * Checks to see if the given string is a valid State.
	 * @param state The state to check.
	 * @return true if the state is valid.
	 */
	public static boolean isState(String state) {
		return SET.contains(state);
	}

	/**
	 * Returns a collection of the registered states.
	 * @return The collection of registered states.
	 */
	public static Set<String> getStates() {
		return SET;
	}

	/**
	 * Formats states to be inserted into database
	 *
	 * @return A string of states to be inserted into the database
	 */
	public static String formatStates() {
		StringBuilder formatted = new StringBuilder(); // Saves slightly on memory
		for (String string : SET) {
			formatted.append(":").append(string);
		}
		return formatted.substring(1);
	}

	/**
	 * Removes all states registered.
	 * NOTE: ONLY SHOULD BE USED IN JUNIT TESTS.
	 */
	public static void removeAllStates() {
		SET.clear();
	}
}
