package edu.mtu.tinventory.gui;


import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller class for the inventory view
 */
public class InvViewController {
	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, String> nameCol;
	@FXML private TableColumn<Product, String> idCol;
	@FXML private TableColumn<Product, String> priceCol;
	@FXML private TableColumn<Product, Number> qtyCol;
	DatabaseInterface db;

	/**Initializes the table, labels columns, gets any values the database may have.
	 * 
	 */
	public void initialize() {
		nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
		idCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getID()));
		priceCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDisplayPrice()));
		qtyCol.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getQuanity().totalQty()));
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
