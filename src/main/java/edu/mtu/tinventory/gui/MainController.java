package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Controller class for the main window of the program
 */
public class MainController {
	@FXML
	private BorderPane root;

	@FXML
	public void close(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	public void viewInventory() {
		try {
			loadView("inventoryView.fxml");
		} catch(IOException e) {
			//TODO: Have a better error handler
			e.printStackTrace();
		}
	}

	@FXML
	public void createNewProduct() {
		
	}

	private void loadView(String filename) throws IOException {
		FXMLLoader loader = new FXMLLoader(TInventory.class.getResource("fxml/" + filename));
		Node node = loader.load();
		root.setCenter(node);
	}
}
