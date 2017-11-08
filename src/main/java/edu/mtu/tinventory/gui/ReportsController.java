package edu.mtu.tinventory.gui;
import java.util.List;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.DatabaseInterface;
import edu.mtu.tinventory.state.StateRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReportsController extends Controller {
	@FXML private Button salesCSV;
	@FXML private Button invCSV;
	@FXML private Button custCSV;
	private DatabaseInterface db = DatabaseInterface.getInstance();
	
	private void salesButton() {
		List<Product> sales;
	}
	
	private void invButton() {
		List<Product> inv;
	}
	
	private void custButton() {
		List<Customer> cust;
	}
}
