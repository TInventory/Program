package edu.mtu.tinventory.gui;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Invoice;
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
		List<Invoice> sales;
	}
	
	private void invButton() {
		List<Product> inv;
	}
	
	private void custButton() {
		List<Customer> cust;
	}
	
	private void toCSV (int typeTag) throws Exception {
		String type = null;
		
		if (typeTag == 1) {
			type = "Sales";
		} else if (typeTag == 2) {
			type = "Inventory";
		} else {
			type = "Customers";
		}
		Writer write = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date now = new Date(); 
			File file = new File(type + sdf.format(now) + ".csv");
			write = new BufferedWriter(new FileWriter(file));
			
		} catch (Exception e) {
			
			
		} finally {
			
		}
	}
}
