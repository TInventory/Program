package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class for the inventory view
 */
public class InvViewController {
	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, String> nameCol;
	@FXML private TableColumn<Product, String> idCol;
	@FXML private TableColumn<Product, String> priceCol;
	@FXML private TableColumn<Product, Number> qtyCol;
	@FXML private TextField filter;
	ObservableList<Product> list;
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
		list = FXCollections.observableList(db.getProducts());
		FilteredList<Product> filtered = new FilteredList<Product>(list, p -> true);
		filter.textProperty().addListener((observable, oldV, newV) ->{ 
			filtered.setPredicate((Product product) -> {
				if (newV == null || newV.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newV.toLowerCase();
				if (product.getName().toLowerCase().contains(lowerCaseFilter)) {    //Extend to addition else ifs to add search areas. Convert fields to string
					return true;
				} else if (product.getID().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
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
