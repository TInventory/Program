package edu.mtu.tinventory.state;

import edu.mtu.tinventory.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper around HashMap to make things more convenient.
 */
public class StateQtyMap {
    private final HashMap<String, Integer> map;

    public StateQtyMap() {
        map = new HashMap<>();
    }

    /**
     * Creates a StateQtyMap from a raw database string
     * 
     * @param string String to create map from
     * @return A StateQtyMap from String string
     */
    public static StateQtyMap createFromString(String string) {

        StateQtyMap ret = new StateQtyMap();
        
        string = string.replace(" ", "");
        // Check if the string is empty at all
        if (!StringUtils.isNullOrEmpty(string)) {
            for (String e : string.split(";")) {
                if (!StringUtils.isNullOrEmpty(string)) {
                     String[] split = e.split(":");
                     ret.changeQty(split[0], Integer.parseInt(split[1]));
                }
            }
        }
        return ret;

    }

    /**
     * Updates the inventory by adding/subtracting the amount specified from
     * that state.
     * 
     * @param state
     *            The state that you want to change the quantity of.
     * @param offset
     *            The amount to be added/subtracted from the current quantity of
     *            that state.
     * @return true if the quantity was successfully updated, false if the
     *         stateID is invalid.
     */
    public boolean changeQty(String state, int offset) {
        if (StateRegistry.isState(state)) {
            Integer qty = map.get(state);
            if (qty != null) { // null if the state is not in the map, meaning qty for that state is 0.
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
     * 
     * @param oldState
     *            The state from which items are moving from.
     * @param newState
     *            The state from which items are moving to.
     * @param amount
     *            The amount to move between states.
     * @return true if the move was successful, false otherwise.
     */
    public boolean moveQty(String oldState, String newState, int amount) {
        if (StateRegistry.isState(oldState) && StateRegistry.isState(newState)) {
            Integer oldQty = map.get(oldState);
            Integer newQty = map.get(newState);
            if (oldQty != null && newQty != null) {
                // null if the state is not in the map, meaning qty for that state is 0.
                map.put(newState, newQty + amount);
                map.put(oldState, oldQty - amount);
            } else if (newQty == null && oldQty != null) {
                map.put(newState, amount);
                map.put(oldState, oldQty - amount);
            } else {
                // oldQty == null...which shouldn't happen, as if someone's
                // trying to move an amount from that state, there needs to
                // be stuff there
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the amount of items in the specified state.
     * 
     * @param state
     *            The state to get the quantity from.
     * @return The quantity of items in that state, or -1 if the state is not valid.
     */
    public int getQty(String state) {
        if (StateRegistry.isState(state)) {
            return map.getOrDefault(state, 0);
        } else {
            return -1;
        }
    }

    /**
     * Returns the total number of items in all states.
     * 
     * @return The total number of items in every state combined.
     */
    public int totalQty() {
        int ret = 0;
        for (Integer i : map.values()) {
            ret += i;
        }
        return ret;
    }

    /**
     *
     * Stores quantities string in following format:
     * State:Integer;State:Integer; etc. Semicolon separates two entry sets
     * Colon separates the entry set into the State and Integer
     *
     * @return String of quantities into a single string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            sb.append(e.getKey()).append(":").append(e.getValue()).append(";");
        }
        if(sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
