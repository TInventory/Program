package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomerViewController {
	/**
	 * Controller class for the inventory view
	 */
	@FXML private TableView<Customer> table;
	@FXML private TableColumn<Customer, String> companyNameCol;
	@FXML private TableColumn<Customer, String> personalNameCol;
	@FXML private TableColumn<Customer, String> phoneNumberCol;
	@FXML private TableColumn<Customer, String> addressCol;
	@FXML private TableColumn<Customer, String> faxNumberCol;
	@FXML private TableColumn<Customer, String> pastSalesCol;
	DatabaseInterface db;

	//check InvViewController for inspiration
	/**Initializes the table, labels columns, gets any values the database may have.
	 * 
	 */
	public void initialize() {
		//			nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
		//			idCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getID()));
		//			priceCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDisplayPrice()));
		//			qtyCol.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getQuanity().totalQty()));
		//			db = DatabaseInterface.getInstance();
		//			table.getItems().setAll(db.getProducts());      //Assuming local data is passed in a list
	}


	/* Initializes if table has not been initialized yet, else will update. 
	 * 
	 */
	private void viewInventory() {
		//			if (db != null) {               
		//				db = DatabaseInterface.getInstance();
		//				table.getItems().setAll(db.getProducts());
		//			} else {
		//				initialize();
		//			}

	}
}
