package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
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

    private String tableName;

    public CreateConfigTable(Tables table) {
        this.tableName = table.toString();
    }

    @Override
    public String getQuery() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (parameter NVARCHAR(120) , value NVARCHAR(6000) );",
                tableName);
    }

}
