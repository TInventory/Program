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
import java.util.List;

public class GetAllCustomers implements ExecuteQuery {
	private List<Customer> customers;
	private boolean waiting = true;

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s", Tables.CUSTOMER_TABLE_NAME.nameToString());
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if (data != null) {
				customers = new ArrayList<>();
				for (HashMap<String, Object> e : data) {
					customers.add(new Customer(StringUtils.stringToUUID(e.get("id").toString()),
							e.get("name").toString(), e.get("company").toString(), e.get("phone").toString(),
							e.get("fax").toString(), e.get("address").toString()));
				}
			}
			waiting = false;
		} catch (SQLException e) {
			LocalLog.exception(e);
		}
	}

	public List<Customer> getCustomers() {
		while (waiting) {
			System.out.print("");
		}
		return customers;
	}
}
