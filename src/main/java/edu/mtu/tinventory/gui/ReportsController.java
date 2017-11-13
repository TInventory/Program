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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReportsController<P> extends Controller {
	@FXML private Button salesCSV;
	@FXML private Button invCSV;
	@FXML private Button custCSV;
	private DatabaseInterface db = DatabaseInterface.getInstance();
	
	private void salesButton(ActionEvent event) {
		List<Invoice> sales =  FXCollections.observableList(db.getInvoices());
//		toCSV(1, sales);
	}
	
	private void invButton(ActionEvent event) {
		List<Product> inv = FXCollections.observableList(db.getProducts());
//		toCSV(2, inv);
	}
	
	private void custButton(ActionEvent event) {
		List<Customer> cust =  FXCollections.observableList(db.getCustomers());
//		toCSV(3, cust);
	}
	
	private void toCSV (int typeTag, List<P> list) throws Exception {
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
	
	private void initialize() {
		salesCSV.setOnAction(this::salesButton);
		invCSV.setOnAction(this::invButton);
		custCSV.setOnAction(this::custButton);
	}
}
