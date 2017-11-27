package edu.mtu.tinventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.state.StateRegistry;
import edu.mtu.tinventory.util.DatabaseUtils;
import edu.mtu.tinventory.util.StringUtils;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class for the product creation window
 * @author Brandon Paupore
 */
public class UpdateProductsController extends Controller {
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

	@FXML private TextField productID;
	@FXML private TextField quantity;
	@FXML private Button button;
	@FXML private Button refresh;
		  
	
	@FXML
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

	@FXML
	private void update() {
		db.forceUpdateCache();
		initialize();
	}
	
	@Override
	protected void updateLayout(TabPane tabs) {
		// 30 is the constant height for the tabs themselves.
		table.prefHeightProperty().bind(tabs.heightProperty().subtract(30).subtract(filter.getPrefHeight()));
		table.prefWidthProperty().bind(tabs.widthProperty());
	}
	
	/**
	 * Creates product using data entered into TextFields
	 * -ensures price is a valid double value
	 */
	@FXML
	private void updateProduct() {
		if(DatabaseUtils.isDatabaseFrozen()) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "The database is currently frozen", "The inventory cannot be changed while the database is frozen.");
		} else if(!StringUtils.isNumber(quantity.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Quantity Specified", "Quantity must be a valid number.");
		} else {
			Product p = db.getProduct(productID.getText());
			if (p == null)
				Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Product Specified", "Product ID must be valid.");
			int qty = Integer.parseInt(quantity.getText());
			if(qty <= 0) {
				Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Quantity Specified", "Quantity must be a positive number.");
			} else {
				if (p != null) {
					p.getQuanity().changeQty(StateRegistry.AVAILABLE_STATE, qty);
					DatabaseInterface.getInstance().updateItem(p);
					productID.clear();
					quantity.clear();
					initialize();
				} else {
					Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Product ID Specified", String.format("%s is not a valid Product ID.", productID.getText()));
				}
			}
		}
	}
	
	@FXML
	private void getID() {
		productID.setText(table.selectionModelProperty().get().getSelectedItem().getID());
	}
}
