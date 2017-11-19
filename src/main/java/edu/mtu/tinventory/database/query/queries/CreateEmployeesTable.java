package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class CreateEmployeesTable implements Query {

       String tableName;
    
    public CreateEmployeesTable(Tables table) {
        tableName = table.toString();
    }

    @Override
    public String getQuery() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (id NVARCHAR(8), firstName NVARCHAR(120), lastName NVARCHAR(120), password NVARCHAR(120), accessLevel NVARCHAR(120), overrides NVARCHAR(120))",
                tableName);
    }
}
