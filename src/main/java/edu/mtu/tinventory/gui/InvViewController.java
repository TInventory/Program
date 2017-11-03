package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.state.StateRegistry;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Controller class for the inventory view
 */
public class InvViewController extends Controller {
	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, String> nameCol;
	@FXML private TableColumn<Product, String> idCol;
	@FXML private TableColumn<Product, String> priceCol;
	@FXML private TableColumn<Product, Number> qtyCol;
	@FXML private TextField filter;
	@FXML private TextField tagFilter;
	private ObservableList<Product> list;
	private DatabaseInterface db;
	private ArrayList<String> tagList;

	/**Initializes the table, labels columns, gets any values the database may have.
	 * 
	 */
	public void initialize() {
		nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
		idCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getID()));
		priceCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDisplayPrice()));
		qtyCol.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getQuanity().getQty(StateRegistry.AVAILABLE_STATE)));
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

		tagFilter.textProperty().addListener((observable, oldV, newV) ->{ 
			if (tagFilter.getLength() > 0){
				tagList =  new ArrayList<String>(Arrays.asList(tagFilter.getText()));
			}
			filtered.setPredicate((Product product) -> {
				if (newV == null || newV.isEmpty()) {
					return true;
				}

				if (product.getTags() != null) {
					for (String tag: tagList) {
						if (!product.getTags().contains(tag)) {
							return false;
						}
					}
				} else {
					return false;
				}
				//				if (filtered.contains(product)) {
				//					return false;
				//				}
				return true;
			});
		});
		SortedList<Product> sort = new SortedList<>(filtered);
		table.setItems(sort);
		table.skinProperty().addListener(new ResizeColumnsListener(table)); // REFLECTION HACK
	}

	public void refresh() {
		list = FXCollections.observableList(db.getProducts());
	}

	@Override
	protected void updateLayout(TabPane tabs) {
		// 30 is the constant height for the tabs themselves.
		table.prefHeightProperty().bind(tabs.heightProperty().subtract(30).subtract(filter.getPrefHeight()));
		table.prefWidthProperty().bind(tabs.widthProperty());
	}
}
