package edu.mtu.tinventory.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * Controller class for the inventory view
 */
public class InvViewController {
	@FXML
	private TableView table;
	
	/** Call when table is empty in local cache, pull from database
	 * 
	 */
	private void getInventory() {
		
	}
	
	/* Call to get the data from the local cache, or database if empty.
	 * 
	 */
	private void viewInventory() {
//		if (localCache.isEmpty()) {               //localCache is placeholder name for cached database list
//			getInventory();
//		}
//		
	}
}
