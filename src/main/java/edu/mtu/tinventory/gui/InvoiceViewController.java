package edu.mtu.tinventory.gui;


import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.state.StateRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class for the invoice view
 */
public class InvoiceViewController extends Controller {
	@FXML private TableView<Invoice> table;
	@FXML private TableColumn<Invoice, Number> idCol;
	@FXML private TableColumn<Invoice, String> dateCol;
	@FXML private TableColumn<Invoice, String> customerCol;
	@FXML private TableColumn<Invoice, String> productsCol;
	@FXML private TableColumn<Invoice, String> priceCol;
	@FXML private TextField filter;
	@FXML private TextField tagFilter;
	private ObservableList<Invoice> list;
	private DatabaseInterface db;
	private ArrayList<String> tagList;

	/**Initializes the table, labels columns, gets any values the database may have.
	 * 
	 */
	public void initialize() {
		idCol.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getId()));
		dateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDate().toString()));
		customerCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCustomer().getName()));
		productsCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProducts().toString()));
		priceCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTotal().toString()));
		list = FXCollections.observableList(db.getInvoices());
		FilteredList<Invoice> filtered = new FilteredList<Invoice>(list, p -> true);
		filter.textProperty().addListener((observable, oldV, newV) ->{ 
			filtered.setPredicate((Invoice product) -> {
				if (newV == null || newV.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newV.toLowerCase();
				if (product.getCustomer().getName().toLowerCase().contains(lowerCaseFilter)) {    //Extend to addition else ifs to add search areas. Convert fields to string
					return true;
				}
				return false;
			});
		});

//		tagFilter.textProperty().addListener((observable, oldV, newV) ->{ 
//			if (tagFilter.getLength() > 0){
//				tagList =  new ArrayList<String>(Arrays.asList(tagFilter.getText()));
//			}
//			filtered.setPredicate((Invoice product) -> {
//				if (newV == null || newV.isEmpty()) {
//					return true;
//				}
//
//				if (product.getTags() != null) {
//					for (String tag: tagList) {
//						if (!product.getTags().contains(tag)) {
//							return false;
//						}
//					}
//				} else {
//					return false;
//				}
//				//				if (filtered.contains(product)) {
//				//					return false;
//				//				}
//				return true;
//			});
//		});
		SortedList<Invoice> sort = new SortedList<>(filtered);
		table.setItems(sort);
		table.skinProperty().addListener(new ResizeColumnsListener(table)); // REFLECTION HACK
	}

	public void refresh() {
		list = FXCollections.observableList(db.getInvoices());
	}

	@Override
	protected void updateLayout(TabPane tabs) {
		// 30 is the constant height for the tabs themselves.
		table.prefHeightProperty().bind(tabs.heightProperty().subtract(30).subtract(filter.getPrefHeight()));
		table.prefWidthProperty().bind(tabs.widthProperty());
	}
}
