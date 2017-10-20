package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.data.PurchasedProduct;
import edu.mtu.tinventory.util.StringUtils;
import java.math.BigDecimal;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class for the sell inventory view
 */
public class SellController {
	@FXML private TextField qty;
	@FXML private TextField productID;
	@FXML private TableView<PurchasedProduct> items;
	@FXML private TableColumn<PurchasedProduct, Number> qtyColumn;
	@FXML private TableColumn<PurchasedProduct, String> idColumn;
	@FXML private TableColumn<PurchasedProduct, String> nameColumn;
	@FXML private TableColumn<PurchasedProduct, String> uPriceColumn; // For now, I'm storing the BigDecimals as Strings.
	@FXML private TableColumn<PurchasedProduct, String> tPriceColumn; // May write a wrapper to allow use as BigDecimal later, if need be.
	@FXML private TextField total;

	@FXML
	private void initialize() {
		qtyColumn.setCellValueFactory(data -> new ReadOnlyIntegerWrapper(data.getValue().getQuantity()));
		idColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProductID()));
		nameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProductName()));
		uPriceColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDisplayUnitPrice()));
		tPriceColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDisplayTotalPrice()));
	}

	@FXML
	private void addItemToOrder() {
		if(StringUtils.isNullOrEmpty(qty.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "No quantity specified", "You must specify a quantity for each product.");
		} else if(StringUtils.isNullOrEmpty(productID.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "No product specified", "You must specify a Product ID for each product.");
		} else if (!StringUtils.isNumber(qty.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid quantity specified", "Quantity must be a valid number");
		} else if(Integer.parseInt(qty.getText()) <= 0) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid quantity specified", "Quantity must be greater than 0");
		} else {
			//Product p = DatabaseInterface.getInstance().getProduct(productID.getText()); //TODO: Use this line when Database is implemented
			Product p = new Product(productID.getText(), productID.getText(), Double.toString(Math.random() * 100)); // ONLY FOR TESTING
			if (p == null) { // Product does not exist in the database.
				Dialogs.showDialog(Dialogs.Type.ERROR, "Product does not exist", String.format("%s is not a valid Product ID", productID.getText()));
				productID.clear();
			} else if(contains(productID.getText())) {
				Dialogs.showDialog(Dialogs.Type.ERROR, "Product already in order", String.format("%s is already in the order.", productID.getText()));
			} else {
				PurchasedProduct pp = new PurchasedProduct(p, Integer.parseInt(qty.getText()), p.getPrice());
				items.getItems().add(pp);
				qty.clear();
				productID.clear();
				if (StringUtils.isNullOrEmpty(total.getText())) {
					total.setText(pp.getDisplayTotalPrice());
				} else {
					BigDecimal newTotal = new BigDecimal(total.getText().substring(1)).add(pp.getTotalPrice());
					total.setText(Product.PRICE_FORMAT.format(newTotal));
				}
			}
		}
	}

	@FXML
	private void completeOrder() {
		if(items.getItems().isEmpty()) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "No products specified", "Must specify at least one item");
		} else {
			Invoice i = Invoice.createNewInvoice(items.getItems());
			if(i == null) {
				Dialogs.showDialog(Dialogs.Type.ERROR, "Invoice could not be created", "Problem communicating with Database. Please try again.");
			} else {
				items.getItems().clear();
				Dialogs.showDialog(Dialogs.Type.INFO, "Invoice successfully created", "Invoice No: " + i.getId());
			}
		}
	}

	private boolean contains(String productID) {
		for(PurchasedProduct pp : items.getItems()) {
			if(pp.getProductID().equals(productID)) {
				return true;
			}
		}
		return false;
	}
}