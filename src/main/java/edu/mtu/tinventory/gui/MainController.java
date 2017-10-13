package edu.mtu.tinventory.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

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
	
	public void viewInvetory() {
		
	}
	
	public void createNewProducts() {
		
	}
}
