package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

/**
 * 
 * @author JC Helm
 * 
 * Query set up for creating a new table for:
 * String (120), String (120), String(120), String(6000)
 *
 */
public class CreateDataTable implements Query {

    private String table;

    public CreateDataTable(Tables table) {
        this.table = table.toString();
    }

    @Override
    public String getQuery() {
        // Upgrade quantities
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (id NVARCHAR(120) , name NVARCHAR(120) , price NVARCHAR(120), quantity NVARCHAR(6000) );",
                table);
    }

}
