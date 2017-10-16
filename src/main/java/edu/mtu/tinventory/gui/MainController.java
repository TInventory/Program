package edu.mtu.tinventory.gui;

<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
=======
import edu.mtu.tinventory.TInventory;
import java.io.IOException;
import java.util.EnumMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
>>>>>>> 1a72bcde65866630e3b49346627b4fb45203a018

/**
 * Controller class for the main window of the program
 */
public class MainController {
	@FXML
<<<<<<< HEAD
	private BorderPane root;
	private GridPane inventory;
	
	/** Method to view the inventory, populates the associated GridPane to display data in a table format. 
	 *  Note: types of keys and name of hashtable are placeholders for now. Remove this note when final names are added.
	 */
	public void viewInventory() {
		inventory.add(new Label("Name"), 0, 0);
		inventory.add(new Label("ID"), 1, 0);
		inventory.add(new Label("Price"), 2, 0);
		inventory.add(new Label("Quantities"), 3, 0);
		int i = 1;
		int j = 0;
		
		for (Map.Entry<int, HashMap<int, Object>> entry: inventory.entrySet()) {
			j = 0;
			for (Map.Entry<int,Object> ent: entry.entrySet()) {
				inventory.add(new Label(ent.getValue()), j++, i);
			}
			i++;
		}
		
=======
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
		node.prefHeightProperty().bind(tabs.heightProperty()); // Makes the loaded region take up the whole TabView.
		node.prefWidthProperty().bind(tabs.widthProperty());   // Have to do it programmatically due to communication between FXMLs.
		Tab tab = new Tab(view.getTabName(), node);
		tab.setOnClosed(event -> activeTabs.remove(view));
		tabs.getTabs().add(tab);
		return tab;
>>>>>>> 1a72bcde65866630e3b49346627b4fb45203a018
	}
}
