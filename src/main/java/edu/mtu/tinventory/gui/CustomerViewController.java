package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerViewController extends Controller {
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
	@FXML private TextField filter;
	ObservableList<Customer> list;
	DatabaseInterface db;

	//check InvViewController for inspiration
	/**Initializes the table, labels columns, gets any values the database may have.
	 * 
	 */
	public void initialize() {
		companyNameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCompanyName()));
		personalNameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPersonName()));
		phoneNumberCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPhoneNumber()));
		addressCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAddress()));
		faxNumberCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFaxNumber()));
		pastSalesCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getPastSales()));
		db = DatabaseInterface.getInstance();
		list = FXCollections.observableList(db.getCustomers());
		FilteredList<Customer> filtered = new FilteredList<Customer>(list, p -> true);
		filter.textProperty().addListener((observable, oldV, newV) -> {
			filtered.setPredicate((Customer customer) -> {
				if (newV == null || newV.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newV.toLowerCase();
				if (customer.getCompanyName().toLowerCase().contains(lowerCaseFilter)) {    //Extend to addition else ifs to add search areas. Convert fields to string
					return true;
				} else if (customer.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (customer.getPersonName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		SortedList<Customer> sort = new SortedList<>(filtered);
		table.setItems(sort);
		table.skinProperty().addListener(new ResizeColumnsListener(table)); // REFLECTION HACK
		table.setRowFactory(param -> { // This allows the double-clicking in select customer for Sell Inventory to select a customer.
			TableRow<Customer> row = new TableRow<>();
			if(!stage.equals(mainApp.getMainWindow())) {
				row.setOnMouseClicked(event -> {
					if(event.getClickCount() == 2 && !row.isEmpty()) {
						((SellController)mainApp.getMainController().getControllerForTab(View.SELL_INV)).setCustomer(row.getItem());
					}
				});
			}
			return row;
		});
	}

	@Override
	protected void updateLayout(TabPane tabs) {
		// 30 is the constant height for the tabs themselves.
		table.prefHeightProperty().bind(tabs.heightProperty().subtract(30).subtract(filter.getPrefHeight()));
		table.prefWidthProperty().bind(tabs.widthProperty());
	}
}
