package edu.mtu.tinventory.data;

import edu.mtu.tinventory.state.StateQtyMap;

public class Product {
	// The Model # / SKU / Unique Identifier
	private String id;
	// The human-friendly name
	private String name;
	// The quantity of items in each state
	// TODO: Create proxy methods in this class? Something to get qtys.
	private final StateQtyMap quantities;

	public Product(String id, String name) {
		this.id = id;
		this.name = name;
		this.quantities = new StateQtyMap();
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	//NOTE: Will need to update database after this call.
	public void setName(String name) {
		this.name = name;
	}
}
