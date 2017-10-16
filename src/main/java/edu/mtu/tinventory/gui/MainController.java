package edu.mtu.tinventory.gui;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Controller class for the main window of the program
 */
public class MainController {
	@FXML
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
		
	}
}
