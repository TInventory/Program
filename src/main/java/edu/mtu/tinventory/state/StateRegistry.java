package edu.mtu.tinventory.state;

import java.util.HashSet;
import java.util.Set;

/*
 * Need a method to create states the user wants. Maybe read from a file/the database? -P
 * Should we support removing states? -P
 */
public class StateRegistry {
	private static final Set<String> SET = new HashSet<>();

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
	 * Removes all states registered.
	 * NOTE: ONLY SHOULD BE USED IN JUNIT TESTS.
	 */
	public static void removeAllStates() {
		SET.clear();
	}
}
