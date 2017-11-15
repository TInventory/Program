package edu.mtu.tinventory.gui;

/**
 * List of all tabbed views used in this program.
 * Any element in this enum is automatically added to the program.
 */
public enum View {
	//NOTE: After adding a view, go to Access and add it to whichever access level best suits it.
	VIEW_INV("View Inventory", "inventoryView"),
	CREATE_PRODUCT("Create New Products", "createProducts"),
	SELL_INV("Sell Inventory", "sell"),
	UPDATE_PRODUCT("Update Products", "updateProducts"),
	VIEW_CUSTOMERS("View Customers", "viewCustomers"),
	VIEW_INVOICES("View Sales", "viewInvoices"),
	ADMIN("Admin", "admin"),
	REPORTSCONTROLLER("Reports", "reportsView");
	
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
