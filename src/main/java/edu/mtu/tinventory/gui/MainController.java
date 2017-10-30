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

	public TabPane getTabPane() {
		return tabs;
	}

	// This method is run when the main window is first loaded.
	@FXML
	private void initialize() {
		activeTabs = new EnumMap<>(View.class);
		for(View view : View.values()) {
			MenuItem mi = new MenuItem(view.getTabName());
			mi.setOnAction(event -> openTab(view));
			actions.getItems().add(mi);
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
				tab = loadTab(view);
				activeTabs.put(view, tab);
			} catch(IOException e) {
				//TODO: Have a better error handler (Maybe with a Dialog box?)
				LocalLog.exception(e);
			}
		}
		tabs.getSelectionModel().select(activeTabs.get(view));
	}

	private Tab loadTab(View view) throws IOException {
		FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/" + view.getFxmlName() + ".fxml"));
		Region node = loader.load();
		((Controller)loader.getController()).setMainApp(mainApp);
		node.prefHeightProperty().bind(tabs.heightProperty().subtract(30)); // Makes the loaded region take up the whole TabView, minus the space for the tabs themselves.
		node.prefWidthProperty().bind(tabs.widthProperty());   					  // Have to do it programmatically due to communication between FXMLs.
		Tab tab = new Tab(view.getTabName(), node);
		tab.setOnClosed(event -> activeTabs.remove(view));
		tabs.getTabs().add(tab);
		return tab;
	}
}
