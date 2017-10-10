package edu.mtu.tinventory.state;

/**
 * A state that inventory can be in.
 * Dynamically defined in a registry. (to be implemented)
 * NOTE: Not sure we actually need this class, or if we can just Strings.
 *       Leaving it here for now in case we want to define custom logic later.
 */
public class State {
	// Unique ID (used in database to represent state)
	private String id;
	// Human-friendly name
	private String name;

	public State(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}
}
