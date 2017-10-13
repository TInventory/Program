package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Controller class for the main window of the program
 */
public class MainController {
	@FXML
	private TabPane tabs;

	@FXML
	private void close() {
		Platform.exit();
	}

	@FXML
	private void viewInventory() {
		try {
			loadView("View Inventory", "inventoryView.fxml");
		} catch(IOException e) {
			//TODO: Have a better error handler
			e.printStackTrace();
		}
	}

	@FXML
	private void createNewProduct() {
		
	}

	private void loadView(String tabName, String filename) throws IOException {
		FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/" + filename));
		Node node = loader.load();
		tabs.getTabs().add(new Tab(tabName, node));
	}
}
