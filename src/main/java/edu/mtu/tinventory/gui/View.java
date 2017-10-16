package edu.mtu.tinventory.gui;

public enum View {
	VIEW_INV("View Inventory", "inventoryView"),
	CREATE_PRODUCT("Create New Products", "createProducts");

	private String tabName;
	private String fxmlName;

	View(String tabName, String fxmlName) {
		this.tabName = tabName;
		this.fxmlName = fxmlName;
	}

	public String getTabName() {
		return tabName;
	}

	public String getFxmlName() {
		return fxmlName;
	}
}
