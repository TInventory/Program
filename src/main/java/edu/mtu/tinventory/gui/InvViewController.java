package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.application.Platform;
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
	private ObservableList<Product> list;
	private DatabaseInterface db;
	private Method resizeColumnMethod;

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
		SortedList<Product> sort = new SortedList<>(filtered);
		table.setItems(sort);

		// REFLECTION HACK (KIDS, DON'T TRY THIS AT HOME)
		try {
			resizeColumnMethod = Class.forName("com.sun.javafx.scene.control.skin.TableViewSkin").getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
			resizeColumnMethod.setAccessible(true);
		} catch (ReflectiveOperationException e) {
			LocalLog.exception("Failed to grab method to automatically resize columns! Things will look funky...", e);
		}
		table.skinProperty().addListener((observable, oldValue, newValue) -> { // Have to wait for the skin to not be null, so we can do this.
			if(newValue != null && resizeColumnMethod != null) {
				Platform.runLater(() -> { // This threads the resizing of columns, which buys us just enough time for everything to not be null.
					for (Object column : table.getColumns()) {
						try {
							resizeColumnMethod.invoke(table.getSkin(), column, -1);
						} catch (IllegalAccessException | InvocationTargetException e) {
							LocalLog.exception("Failed to automatically resize column...somehow...", e);
						}
					}
				});
			}
		});
		// END REFLECTION HACK
	}

	@Override
	protected void updateLayout(TabPane tabs) {
		// 30 is the constant height for the tabs themselves.
		table.prefHeightProperty().bind(tabs.heightProperty().subtract(30).subtract(filter.getPrefHeight()));
		table.prefWidthProperty().bind(tabs.widthProperty());
	}
}
