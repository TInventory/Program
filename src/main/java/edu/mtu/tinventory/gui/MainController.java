package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import java.io.IOException;
import java.util.EnumMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

/**
 * Controller class for the main window of the program
 */
public class MainController extends Controller {
	@FXML private TabPane tabs;
	@FXML private Menu actions;

	private EnumMap<View, Tab> activeTabs;
	private EnumMap<View, Controller> controllers;

	/**
	 * Returns the controller for the open tab for the specified view.
	 * If the view does not have a tab open, null is returned.
	 * @param view The view to get the controller for.
	 * @return The controller for the open tab for the view, or null if the view is not open in a tab presently.
	 */
	public Controller getControllerForTab(View view) {
		return controllers.get(view);
	}

	// This method is run when the main window is first loaded.
	@FXML
	private void initialize() {
		activeTabs = new EnumMap<>(View.class);
		controllers = new EnumMap<>(View.class);
		for(View view : View.values()) {
			MenuItem mi = new MenuItem(view.getTabName());
			mi.setOnAction(event -> openTab(view));
			actions.getItems().add(mi);
		}
	}

	@FXML
	private void logout() {
		mainApp.setLoggedIn(null);
		stage.close();
		try {
			mainApp.showLoginDialog();
			mainApp.showMainWindow();
		} catch(IOException e) {
			LocalLog.exception("Exception encountered when trying to open login dialog.", e);
			Dialogs.showDialogWithException("IOException encountered", "Failed to open login dialog.", e);
		}
	}

	@FXML
	private void close() {
		DatabaseInterface.getInstance().quit();
		Platform.exit();
	}

	private void openTab(View view) {
		Tab tab = activeTabs.get(view);
		if(tab == null) { // The tab is currently not open. Load it.
			try {
				loadTab(view);
			} catch(IOException e) {
				LocalLog.exception("Failed to load " + view.getFxmlName() + ".fxml", e);
				Dialogs.showDialogWithException("Failed to load view", view.getTabName() + " failed to load. See the stacktrace below for more details.", e);
			}
		}
		tabs.getSelectionModel().select(activeTabs.get(view));
	}

	private void loadTab(View view) throws IOException {
		FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/" + view.getFxmlName() + ".fxml"));
		Region node = loader.load();
		Controller c = loader.getController();
		c.setMainApp(mainApp);
		c.setStage(mainApp.getMainWindow());
		c.updateLayout(tabs);
		node.prefHeightProperty().bind(tabs.heightProperty().subtract(30)); // Makes the loaded region take up the whole TabView, minus the space for the tabs themselves.
		node.prefWidthProperty().bind(tabs.widthProperty());				// Have to do it programmatically due to communication between FXMLs.
		Tab tab = new Tab(view.getTabName(), node);
		tab.setOnClosed(event -> {
			activeTabs.remove(view);
			controllers.remove(view);
		});
		tabs.getTabs().add(tab);
		activeTabs.put(view, tab);
		controllers.put(view, c);
	}
}
