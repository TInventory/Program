package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
/**
 * Controller class for the product creation window
 * @author Brandon Paupore
 */
public class CreateProductsController {
	DatabaseInterface database;
	
	@FXML TextField productName;
	@FXML TextField productID;
	@FXML TextField productPrice;
	@FXML TextField errorDisplay;
	
	/**
	 * Creates product using data entered into TextFields
	 * -ensures price is a valid double value
	 */
	@FXML
	private void createProduct() {
		//Test if price is valid
		try {
			Double.valueOf(productPrice.getText());
		} catch (NumberFormatException ex) {
			errorDisplay.setText("Price must be valid");//dialogs instead maybe?
			productPrice.setText("");
			return;
		}
		Product product = new Product(productID.getText(), productName.getText(), productPrice.getText());
		database = DatabaseInterface.getInstance();
		//Attempt to register product in database
		if (database.registerNewItem(product)) {
			errorDisplay.setText("Product successfully created and registered to database!");//dialogs instead maybe?
		}
		else {
			errorDisplay.setText("Something went wrong, product may already exist in database.");//dialogs instead maybe?
		}
	}
}
