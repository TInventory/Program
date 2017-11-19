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

/**
* Retrieves a product from the database
*
* @author 
*
* @since 
*/
public class GetProduct implements ExecuteQuery {
	// Product ID of Product to retrieve
	private String productID;
	// Raw Data of from SQL database to be parsed
	private ArrayList<HashMap<String, Object>> data;
	// Waiting boolean for SQL execution
	private boolean waiting;

	private String tableName;
	/**
	* Constructor
	* Instance of Executable GetProduct
	*
	* @param productID String: ID of product to retrieve
	*/
	public GetProduct(String productID, Tables table) {
		this.productID = productID;
		this.waiting = true;
		this.tableName = table.toString();
	}

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s WHERE id = '%s'", tableName, productID);
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			// Apply raw dataset to data hashmap
			this.data = DatabaseUtils.getData(resultSet);
			waiting = false;
		} catch(SQLException e) {
			LocalLog.exception("Error in parsing data from database", e);
		}
	}

	/**
	* Returns an instance of the Product from the data
	*
	* @return An instance of Product retrieved from this instance
	*/ 
	public Product getProduct() {
		while (waiting) {
			//Do the waiting thing
			System.out.print("");
		}
		// If there was no product to be found return null
		if(data.isEmpty()) {
			return null;
		} else {
			// Apply to map from raw data
			HashMap<String, Object> row = data.get(0);
			// Create product from raw data
			// TODO: (LARGE) Make all data be parsed from new Database parsing class
			return Product.createFromDatabse(row.get("id").toString(), row.get("name").toString(), row.get("price").toString(), row.get("quantity").toString());
		}
	}
}
