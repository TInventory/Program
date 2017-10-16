package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
/**
 * Controller class for the product creation window
 */
public class CreateProductsController {
	@FXML TextField productName;
	@FXML TextField productID;
	@FXML TextField productPrice;
	
	@FXML
	private void createProduct() {
		Product product = new Product(productID.getText(), productName.getText(), productPrice.getText());
		System.out.println(product);
		
	}
}
