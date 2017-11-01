package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.query.Query;

/**
 * 
 * @author JC Helm
 * 
 * Query set up for configuration (parameter, value)
 * String (120), String (120), String(120), String(6000)
 *
 */
public class CreateConfigTable implements Query {

    private String table;

    public CreateConfigTable(String tableName) {
        this.table = tableName;
    }

    @Override
    public String getQuery() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (parameter NVARCHAR(120) , value NVARCHAR(6000) );",
                table);
    }

}
