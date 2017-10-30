package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;

public abstract class Controller {
	protected TInventory mainApp;

	public void setMainApp(TInventory mainApp) {
		this.mainApp = mainApp;
	}
}
