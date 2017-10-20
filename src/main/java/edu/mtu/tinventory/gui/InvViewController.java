package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the inventory view
 */
public class InvViewController {
	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, String> Name;
	@FXML private TableColumn<Product, String> ID;
	@FXML private TableColumn<Product, String> Price;
	@FXML private TableColumn<Product, String> Quantity;


	public void initialize() {
	  Name.setCellValueFactory(new PropertyValueFactory<Product, String>("Product Name"));
	  ID.setCellValueFactory(new PropertyValueFactory<Product, String>("Product ID"));
	  Price.setCellValueFactory(new PropertyValueFactory<Product, String>("Product Price"));
	  Quantity.setCellValueFactory(new PropertyValueFactory<Product, String>("Product Quantity"));
	  
	 // table.getItems().setAll(localCache);      Assuming local data is passed in a list
	}
	
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
