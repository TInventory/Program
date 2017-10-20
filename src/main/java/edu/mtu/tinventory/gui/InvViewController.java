package edu.mtu.tinventory.gui;


import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller class for the inventory view
 */
public class InvViewController {
	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, String> Name;
	@FXML private TableColumn<Product, String> ID;
	@FXML private TableColumn<Product, String> Price;
	@FXML private TableColumn<Product, String> Quantity;
	DatabaseInterface db;

	/**Initializes the table, labels columns, gets any values the database may have.
	 * 
	 */
	public void initialize() {
		Name.setCellValueFactory(data -> new ReadOnlyStringWrapper("Name"));
		ID.setCellValueFactory(data -> new ReadOnlyStringWrapper("ID"));
		Price.setCellValueFactory(data -> new ReadOnlyStringWrapper("Price"));
		Quantity.setCellValueFactory(data -> new ReadOnlyStringWrapper("ID"));
		db = DatabaseInterface.getInstance();
		table.getItems().setAll(db.getProducts());      //Assuming local data is passed in a list
	}


	/* Initializes if table has not been initialized yet, else will update. 
	 * 
	 */
	private void viewInventory() {
		if (db != null) {               
			db = DatabaseInterface.getInstance();
			table.getItems().setAll(db.getProducts());
		} else {
			initialize();
		}

	}
}
