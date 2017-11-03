package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateCustomerController extends Controller {
	@FXML private TextField name;
	@FXML private TextField company;
	@FXML private TextField phone;
	@FXML private TextField fax;
	@FXML private TextField address;

	@FXML
	private void createCustomer() {
		if(StringUtils.isNullOrEmpty(name.getText())) {
			Dialogs.showDialog(Dialogs.Type.ERROR, "No Name Specified", "At a minimum, a name must be specified.");
		} else {
			Customer customer = new Customer(name.getText(), company.getText(), phone.getText(), fax.getText(), address.getText());
			if(!DatabaseInterface.getInstance().registerNewCustomer(customer)) {
				Dialogs.showDialogWithException("Error registering new customer", "The attempt to communicate with the database to register this new customer failed.", LocalLog.getLastLoggedException());
			} else {
				Dialogs.showDialog(Dialogs.Type.INFO, null, "Customer created successfully!");
				((SellController)mainApp.getMainController().getControllerForTab(View.SELL_INV)).setCustomer(customer);
				close();
			}
		}
	}

	@FXML
	private void close() {
		stage.close();
	}
}
