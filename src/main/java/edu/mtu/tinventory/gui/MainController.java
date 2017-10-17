package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import java.io.IOException;
import java.util.EnumMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

/**
 * Controller class for the main window of the program
 */
public class MainController {
	@FXML
	private TabPane tabs;

	private EnumMap<View, Tab> activeTabs;

	// This method is run when the main window is first loaded.
	@FXML
	private void initialize() {
		activeTabs = new EnumMap<>(View.class);
	}

	@FXML
	private void close() {
		Platform.exit();
	}

	@FXML
	private void viewInventory() {
		openTab(View.VIEW_INV);
	}

	@FXML
	private void createNewProduct() {
		openTab(View.CREATE_PRODUCT);
	}

	@FXML
	private void openSellTab() {
		openTab(View.SELL_INV);
	}

	private void openTab(View view) {
		Tab tab = activeTabs.get(view);
		if(tab == null) { // The tab is currently not open. Load it.
			try {
				tab = loadTab(view);
				activeTabs.put(view, tab);
			} catch(IOException e) {
				//TODO: Have a better error handler
				e.printStackTrace();
			}
		}
		tabs.getSelectionModel().select(activeTabs.get(view));
	}

	private Tab loadTab(View view) throws IOException {
		FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/" + view.getFxmlName() + ".fxml"));
		Region node = loader.load();
		node.prefHeightProperty().bind(tabs.heightProperty().subtract(30)); // Makes the loaded region take up the whole TabView, minus the space for the tabs themselves.
		node.prefWidthProperty().bind(tabs.widthProperty());   					  // Have to do it programmatically due to communication between FXMLs.
		Tab tab = new Tab(view.getTabName(), node);
		tab.setOnClosed(event -> activeTabs.remove(view));
		tabs.getTabs().add(tab);
		return tab;
	}
}
