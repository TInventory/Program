package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.database.utils.DatabaseUtils;
import java.util.ArrayList;
import java.util.Arrays;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.util.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
/**
 * Controller class for the product creation window
 * @author Brandon Paupore
 */
public class CreateProductsController extends Controller {
	DatabaseInterface database;

	@FXML TextField productName;
	@FXML TextField productID;
	@FXML TextField productPrice;
	@FXML TextField productTags;
		  ArrayList<String> tagList;
	@FXML
	private void initialize() {
		database = DatabaseInterface.getInstance();
	}
	
	/**
	 * Creates product using data entered into TextFields
	 * -ensures price is a valid double value
	 */
	@FXML
	private void createProduct() {
		if(DatabaseUtils.isDatabaseFrozen()) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "The database is currently frozen", "The inventory cannot be changed while the database is frozen.");
		} else if(StringUtils.isNullOrEmpty(productID.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Product ID Required", "Must specify a Product ID");
		} else if(StringUtils.isNullOrEmpty(productName.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Product Name Required", "Must specify a Product Name");
		} else if(StringUtils.isNullOrEmpty(productPrice.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Product Price Required", "Must specify a Product Price");
		} else if(database.getProduct(productID.getText()) != null) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Item ID already exists", String.format("%s already exists in the database. Choose a new ID.", productID.getText()));
		} else if(!StringUtils.isNumber(productPrice.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid price entered", "Price must be a valid number.");
			productPrice.clear();
		} else {
			tagList = new ArrayList<String>(Arrays.asList(productTags.getText().split(",")));
			Product product = new Product(productID.getText(), productName.getText(), productPrice.getText(),tagList);
			database = DatabaseInterface.getInstance();
			//Attempt to register product in database
			//TODO: You'll need to see which data table you're inserting it into, for now it's just the default one.
			if (database.registerNewItem(product)) {
				Dialogs.showDialog(Dialogs.Type.INFO, null, "Item created successfully!");
				productName.clear();
				productID.clear();
				productPrice.clear();
				productTags.clear();
			} else {
				Dialogs.showDialog(Dialogs.Type.ERROR, "Item could not be registered", "");
			}
		}
	}
}
