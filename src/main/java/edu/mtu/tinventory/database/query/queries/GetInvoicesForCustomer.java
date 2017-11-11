package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.data.Invoice;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetInvoicesForCustomer implements ExecuteQuery {
	private Customer customer;
	private List<Invoice> invoices;
	private boolean waiting;

	public GetInvoicesForCustomer(Customer customer) {
		this.customer = customer;
		this.waiting = true;
	}

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s WHERE customer = '%s'", Tables.INVOICE_TABLE_NAME.nameToString(), customer.getIDString());
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if (data != null) {
				invoices = new ArrayList<>();
				for(HashMap<String, Object> e : data) {
					invoices.add(Invoice.createFromDatabase((int)e.get("id"), e.get("date").toString(), e.get("customer").toString(), e.get("items").toString()));
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
