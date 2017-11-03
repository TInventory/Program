package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public abstract class Controller {
	protected TInventory mainApp;
	protected Stage stage;

	public void setMainApp(TInventory mainApp) {
		this.mainApp = mainApp;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	protected void updateLayout(TabPane tabs) {} // Defaults to nothing.
}
