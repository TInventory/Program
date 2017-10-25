package edu.mtu.tinventory.state;

import java.util.HashMap;

/**
 * Wrapper around HashMap to make things more convenient.
 */
//TODO: Update database after calling most of these methods. Probably want more methods to, idk.
public class StateQtyMap {
	private final HashMap<State, Integer> map;

	public StateQtyMap() {
		map = new HashMap<>();
	}

	public static StateQtyMap createFromString(String s) {
		StateQtyMap ret = new StateQtyMap();
		for(String e : s.split(";")) {
			String[] split = e.split(":");
			ret.changeQty(split[0], Integer.parseInt(split[1]));
		}
		return ret;
	}

	/**
	 * Updates the inventory by adding/subtracting the amount specified from that state.
	 * @param stateID The state ID that you want to change the quantity of.
	 * @param offset The amount to be added/subtracted from the current quantity of that state.
	 * @return true if the quantity was successfully updated, false if the stateID is invalid.
	 */
	public boolean changeQty(String stateID, int offset) {
		State state = StateRegistry.getState(stateID);
		if(state != null) {
			Integer qty = map.get(state);
			if(qty != null) { // null if the state is not in the map, meaning qty for that state is 0.
				map.put(state, qty + offset);
			} else {
				map.put(state, offset);
			}
			return true;
		}
		return false;
	}

	/**
	 * Moves a specified quantity from one state to another.
	 * @param oldState The state from which items are moving from.
	 * @param newState The state from which items are moving to.
	 * @param amount The amount to move between states.
	 * @return true if the move was successful, false otherwise.
	 */
	public boolean moveQty(String oldState, String newState, int amount) {
		State oldS = StateRegistry.getState(oldState);
		State newS = StateRegistry.getState(newState);
		if(oldS != null && newS != null) {
			Integer oldQty = map.get(oldS);
			Integer newQty = map.get(newS);
			if(oldQty != null && newQty != null) { // null if the state is not in the map, meaning qty for that state is 0.
				map.put(newS, newQty + amount);
				map.put(oldS, oldQty - amount);
			} else if(newQty == null && oldQty != null) {
				map.put(newS, amount);
				map.put(oldS, oldQty - amount);
			} else { // oldQty == null...which shouldn't happen, as if someone's trying to move an amount from that state, there needs to be stuff there
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns the amount of items in the specified state.
	 * @param stateID The state to get the quantity from.
	 * @return The quantity of items in that state.
	 */
	public int getQty(String stateID) {
		return map.get(StateRegistry.getState(stateID));
	}

	/**
	 * Returns the total number of items in all states.
	 * @return The total number of items in every state combined.
	 */
	public int totalQty() {
		int ret = 0;
		for(Integer i : map.values()) {
			ret += i;
		}
		return ret;
	}
	
	/**
	 * Getter method for HashMap of StateQtyMap
	 * 
	 * @return HashMap of type <State, Integer>
	 */
	public HashMap<State, Integer> getMap() {
	    return map;
	}
}
