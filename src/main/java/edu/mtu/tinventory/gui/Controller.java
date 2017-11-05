package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Superclass for all our JavaFX Controller classes
 */
public abstract class Controller {
	// A reference to the instance of this app
	protected TInventory mainApp;
	// A reference to the stage that this instance of this controller is attached to
	protected Stage stage;

	public void setMainApp(TInventory mainApp) {
		this.mainApp = mainApp;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Override this method to finish setting up your user view.
	 * This method is called from the MainController. mainApp and
	 * stage should both already be populated when this method is
	 * called, so it is safe to use them. This method will NOT be
	 * called if the view in question is not being loaded into the
	 * main TabPane.
	 * @param tabs The TabPane that the view is being loaded into.
	 */
	protected void updateLayout(TabPane tabs) {} // Defaults to nothing.
}
