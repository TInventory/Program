package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.database.DatabaseInterface;
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
	
	@FXML TextField quantity;
	@FXML BorderPane borderPane;
	
	@FXML
	public void initialize() {
		try {
		borderPane.setLeft( new FXMLLoader(TInventory.class.getResource("fxml/inventoryView.fxml")).load());
		}
		catch (Exception e) {
			return;
		}
	}
	/**
	 * Creates product using data entered into TextFields
	 * -ensures price is a valid double value
	 */
	@FXML
	private void updateProduct() {
		//Test if quantity is valid
		try {
			Double.valueOf(quantity.getText());
		} catch (NumberFormatException ex) {
			//errorDisplay.setText("Price must be valid");//Dialog instead?
			quantity.setText("");
			return;
		}
		
	}
	public UpdateProductsController() throws IOException {
	}
}
