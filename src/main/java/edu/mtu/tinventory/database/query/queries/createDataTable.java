package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;

import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.query.Query;

public class createDataTable implements Query {

    private String table;

    public createDataTable(String tableName) {
        this.table = tableName;
    }

    @Override
    public String getQuery() {
        // TODO: Test to see if the table already exists
        return String.format(
                "CREATE TABLE %s (id VARCHAR(120) , name VARCHAR(120) , price VARCHAR(120), currency VARCHAR(5), quantity VARCHAR(128));",
                table);
    }

}
