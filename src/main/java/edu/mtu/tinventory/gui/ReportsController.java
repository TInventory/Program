package edu.mtu.tinventory.gui;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

//8675309
public class ReportsController extends Controller {
	@FXML private Button salesCSV;
	@FXML private Button invCSV;
	@FXML private Button custCSV;
	private DatabaseInterface db = DatabaseInterface.getInstance();
	
	@FXML
	private void initialize() {
		salesCSV.setOnAction((event) -> {
			try {
				salesButton(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		invCSV.setOnAction((event) -> {
			try {
				invButton(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		custCSV.setOnAction((event) -> {
			try {
				custButton(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	@FXML
	private void salesButton(ActionEvent event) throws Exception {
		List<Invoice> sales =  (List<Invoice>) FXCollections.observableList(db.getInvoices());
		Writer write = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date now = new Date(); 
			File file = new File("SalesReport" + sdf.format(now) + ".csv");
			write = new BufferedWriter(new FileWriter(file));
			
			for (Invoice sale: sales) {
				String buff = sale.getCustomer() + "," + sale.getDate() + "," + sale.getProductsString() + "," + sale.getTotal() + "\n";
				write.write(buff);
			}
			
		} catch (Exception e) {
			
		} finally {
			write.flush();
			write.close();
		}

	}
	
	@FXML
	private void invButton(ActionEvent event)  throws Exception {
		List<Product> inv = (List<Product>) FXCollections.observableList(db.getProducts());
		Writer write = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date now = new Date(); 
			File file = new File("InventoryReport" + sdf.format(now) + ".csv");
			write = new BufferedWriter(new FileWriter(file));
			
			for (Product prod: inv) {
				String buff = prod.getName() + "," + prod.getID() + "," + prod.getPrice() + "," + prod.getQuanity() + "\n";
				write.write(buff);
			}
			
		} catch (Exception e) {
			
		} finally {
			write.flush();
			write.close();
		}

	}
	
	@FXML
	private void custButton(ActionEvent event) throws Exception {
		List<Customer> cust =  (List<Customer>) FXCollections.observableList(db.getCustomers());
		Writer write = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date now = new Date(); 
			File file = new File("CustomerReport" + sdf.format(now) + ".csv");
			write = new BufferedWriter(new FileWriter(file));
			
			for (Customer cus: cust) {
				String buff = cus.getCompanyName() + "," + cus.getPersonName() + "," + cus.getPhoneNumber() + "," + cus.getAddress() + "," + cus.getFaxNumber() + "," + cus.getPastSales() + "\n";
				write.write(buff);
			}
			
		} catch (Exception e) {
			
		} finally {
			write.flush();
			write.close();
		}
	}	
}
