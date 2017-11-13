package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
	@FXML private Button revertInvoice;
	private ObservableList<Invoice> list;
	private DatabaseInterface db;
	private Image icon;
	
	/**Initializes the table, labels columns, gets any values the database may have.
	 * 
	 */
	public void initialize() {
		db = DatabaseInterface.getInstance();
		idCol.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getId()));
		dateCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDate().toString()));
		//If no customer stored then display blank name
		customerCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCustomer() != null ? data.getValue().getCustomer().getName() : ""));
		productsCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProductNamesString()));
		priceCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTotal().toString()));
		list = FXCollections.observableList(db.getInvoices());
		FilteredList<Invoice> filtered = new FilteredList<Invoice>(list, p -> true);
		filter.textProperty().addListener((observable, oldV, newV) ->{ 
			filtered.setPredicate((Invoice product) -> {
				if (newV == null || newV.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newV.toLowerCase();
				if (product.getCustomer() != null) {
					if (product.getCustomer().getName().toLowerCase().contains(lowerCaseFilter)) {    //Extend to addition else ifs to add search areas. Convert fields to string
						return true;
					}
				}
				if (product.getDate().toString().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		//If desire to implement tags later, this should be a good start
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
	
	@FXML
	public void revert() throws Exception {
		icon = new IconLoader().getIcon();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/revertInvoice.fxml"));
		GridPane root = loader.load();
		Stage stage = new Stage();
		ConfirmRevertController c = loader.getController();
		c.setMainApp(this.mainApp);
		c.setStage(stage);
		Invoice i = table.getSelectionModel().getSelectedItem();
		stage.setTitle("Verify Revert Invoice #" + i.getId());
		c.invoice = i;
		stage.setScene(new Scene(root));
		stage.getIcons().add(icon);
		stage.showAndWait();
		refresh();
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
