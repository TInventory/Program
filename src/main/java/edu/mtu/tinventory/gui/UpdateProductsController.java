package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.StringUtils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
/**
 * Controller class for the product creation window
 * @author Brandon Paupore
 */
public class UpdateProductsController {
	DatabaseInterface database;

	@FXML TextField productID;
	@FXML TextField quantity;
	@FXML BorderPane borderPane;
	
	@FXML
	public void initialize() {
		database = DatabaseInterface.getInstance();
		try {
			borderPane.setLeft(new FXMLLoader(TInventory.class.getResource("fxml/inventoryView.fxml")).load());
		}
		catch (Exception e) {
			LocalLog.exception(e);
		}
	}
	/**
	 * Creates product using data entered into TextFields
	 * -ensures price is a valid double value
	 */
	@FXML
	private void updateProduct() {
		//TODO: LOOK AT AGAIN after Presentation
		if(!StringUtils.isNumber(quantity.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Quantity Specified", "Quantity must be a valid number.");
		} else {
			Product p = database.getProduct(productID.getText());
			int qty = Integer.parseInt(quantity.getText());
			if(qty <= 0) {
				Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Quantity Specified", "Quantity must be a positive number.");
			} else {
				if (p != null) {
					p.getQuanity().changeQty("DEFAULT", qty);
					productID.clear();
					quantity.clear();
				} else {
					Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Product ID Specified", String.format("%s is not a valid Product ID.", productID.getText()));
				}
			}
		}
	}
	public UpdateProductsController() throws IOException {
	}
}
