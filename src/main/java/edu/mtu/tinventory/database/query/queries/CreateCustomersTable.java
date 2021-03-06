package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class CreateCustomersTable implements Query {

    String tableName;
    
    public CreateCustomersTable(Tables table) {
        tableName = table.toString();
    }
	@Override
	public String getQuery() {
		return String.format("CREATE TABLE IF NOT EXISTS %s (id VARCHAR(32), name VARCHAR(120), company VARCHAR(120), phone VARCHAR(20), fax VARCHAR(20), address VARCHAR(240))",
				tableName);
	}
}
