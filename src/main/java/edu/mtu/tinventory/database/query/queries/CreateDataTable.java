package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.query.Query;

public class CreateDataTable implements Query {

    private String table;

    public CreateDataTable(String tableName) {
        this.table = tableName;
    }

    @Override
    public String getQuery() {
        // TODO: Test to see if the table already exists
        // Upgrade quantities
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (id NVARCHAR(120) , name NVARCHAR(120) , price NVARCHAR(120), quantity NVARCHAR(6000) );",
                table);
    }

}
