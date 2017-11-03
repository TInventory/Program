package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.TInventory;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.state.StateRegistry;
import edu.mtu.tinventory.util.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controller class for the product creation window
 * @author Brandon Paupore
 */
public class UpdateProductsController extends Controller {
	DatabaseInterface database;

	@FXML private TextField productID;
	@FXML private TextField quantity;
	@FXML private BorderPane borderPane;
		  private VBox invView;
	
	@FXML
	public void initialize() {
		database = DatabaseInterface.getInstance();
		try {
			invView = new FXMLLoader(TInventory.class.getResource("fxml/inventoryView.fxml")).load();
			borderPane.setLeft(invView);
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
					p.getQuanity().changeQty(StateRegistry.AVAILABLE_STATE, qty);
					productID.clear();
					quantity.clear();
					((TableView)invView.getChildren().get(1)).refresh();
				} else {
					Dialogs.showDialog(Dialogs.Type.ERROR, "Invalid Product ID Specified", String.format("%s is not a valid Product ID.", productID.getText()));
				}
			}
		}
	}
}
