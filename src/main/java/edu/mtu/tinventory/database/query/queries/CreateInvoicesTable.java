package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class CreateInvoicesTable implements Query {

    private String tableName;
    
    public CreateInvoicesTable(Tables table) {
        this.tableName = table.toString();
    }
	@Override
	public String getQuery() {
		return String.format("CREATE TABLE IF NOT EXISTS %s (id INT, date NVARCHAR(10), customer NVARCHAR(32), items NVARCHAR(6000))",
				tableName);
	}
}
