package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Product;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetProduct implements ExecuteQuery {
	private String productID;
	private ArrayList<HashMap<String, Object>> data;
	private boolean waiting;

	public GetProduct(String productID) {
		this.productID = productID;
		this.waiting = true;
	}

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s WHERE id = '%s'", Tables.INVENTORY_TABLE_NAME.nameToString(), productID);
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			this.data = DatabaseUtils.getData(resultSet);
			waiting = false;
		} catch(SQLException e) {
			LocalLog.exception("Error in parsing data from database", e);
		}
	}

	public Product getProduct() {
		while (waiting) {
			//Do the waiting thing
			System.out.print("");
		}
		if(data.isEmpty()) {
			return null;
		} else {
			HashMap<String, Object> row = data.get(0);
			return Product.createFromDatabse(row.get("id").toString(), row.get("name").toString(), row.get("price").toString(), row.get("quantity").toString());
		}
	}
}
