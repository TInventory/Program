package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.logging.LocalLog;

public class GetAllInvoices implements ExecuteQuery {

	String table;
	private List<Invoice> invoices;
	private boolean waiting;

	public GetAllInvoices(Tables table) {
		this.table = table.toString();
		this.waiting = true;
	}

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s", table);
	}

	@Override
	public void execute(ResultSet resultSet) {

		try {
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if (data != null) {
				
				invoices = new ArrayList<>();
				for (HashMap<String, Object> e : data) {
					System.out.println(Integer.parseInt(e.get("id").toString()) + 
							e.get("date").toString() + e.get("customer").toString() + e.get("items").toString());
					
					invoices.add(Invoice.createFromDatabase(Integer.parseInt(e.get("id").toString()),
							e.get("date").toString(), e.get("customer").toString(), e.get("items").toString()));
				}
			}
			waiting = false;
		} catch (SQLException e) {
			LocalLog.exception(e);
		}

	}
	
	public List<Invoice> getInvoices() {
		while (waiting) {
			System.out.print("");
		}
		
		return invoices;
	}

}
