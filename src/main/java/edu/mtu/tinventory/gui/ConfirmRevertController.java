package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.data.PurchasedProduct;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.state.StateRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ConfirmRevertController extends Controller {
	@FXML private Button confirm;
	@FXML private Button cancel;
	public Invoice invoice;
	
	@FXML
	private void confirm() {
		DatabaseInterface database = DatabaseInterface.getInstance();
		database.deleteInvoice(invoice); //remove from database
		for (PurchasedProduct pp : invoice.getProducts()) { //return products to database
			pp.getProduct().getQuanity().moveQty(StateRegistry.SOLD_STATE, StateRegistry.AVAILABLE_STATE, pp.getQuantity());
			DatabaseInterface.getInstance().updateItem(pp.getProduct());
		}
		stage.close();
	}
	
	@FXML
	private void close() {
		stage.close();
	}
}
