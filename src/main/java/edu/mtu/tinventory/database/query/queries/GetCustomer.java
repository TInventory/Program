package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Customer;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;
import edu.mtu.tinventory.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetCustomer implements ExecuteQuery {
	private String customerID;
	private Customer customer;
	private boolean waiting;
	private String tableName;
	
	public GetCustomer(String customerID, Tables table) {
		this.customerID = customerID;
		this.waiting = true;
		this.tableName = table.toString();
	}

	@Override
	public String getQuery() {
		
		String s = String.format("SELECT * FROM %s WHERE id = '%s'", tableName,
				customerID);
		return s;
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if (data != null) {
				HashMap<String, Object> e = data.get(0);
				customer = new Customer(StringUtils.stringToUUID(e.get("id").toString()), e.get("name").toString(),
						e.get("company").toString(), e.get("phone").toString(), e.get("fax").toString(),
						e.get("address").toString());
			}
			waiting = false;
		} catch (SQLException e) {
			LocalLog.exception(e);
		}
	}

	public Customer getCustomer() {
		while (waiting) {
			System.out.print("");
		}
		return customer;
	}
}
