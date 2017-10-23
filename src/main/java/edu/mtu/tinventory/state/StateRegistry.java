package edu.mtu.tinventory.state;

import java.util.Collection;
import java.util.HashMap;

/*
 * Need a method to create states the user wants. Maybe read from a file/the database? -P
 * Should we support removing states? -P
 */
public class StateRegistry {
	private static final HashMap<String, State> map = new HashMap<>(); //TODO: Serialize this somehow...

	//TODO: REMOVE after Presentation
	static {
		registerState(new State("DEFAULT", "Default"));
	}

	private StateRegistry() {} // Singleton

	/**
	 * Registers a new State to be used in the database.
	 * @param state The new state to be registered.
	 * @return false if the registry already has a state with that ID, true if the state was successfully registered.
	 */
	public static boolean registerState(State state) {
		if(map.containsKey(state.getID())) {
			return false;
		} else {
			map.put(state.getID(), state);
			return true;
		}
	}

	/**
	 * Gets the State objected by its unique ID.
	 * @param id The unique ID for the state.
	 * @return The State object associated with that ID, otherwise null if no state has that ID.
	 */
	public static State getState(String id) {
		return map.get(id);
	}

	/**
	 * Returns a collection of the registered states.
	 * @return The collection of registered states.
	 */
	public static Collection<State> getStates() {
		return map.values();
	}

	/**
	 * Removes all states registered.
	 * NOTE: ONLY SHOULD BE USED IN JUNIT TESTS.
	 */
	public static void removeAllStates() {
		map.clear();
	}
}
