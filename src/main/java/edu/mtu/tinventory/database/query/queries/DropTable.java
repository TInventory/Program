package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

/**
 * 
 * @author JC Helm
 * 
 * Query to drop the table
 *
 */
public class DropTable implements Query {

    /**
     * Name of the table to be dropped
     */
    private String table;
    
    /**
     * Constructor
     * @param table Name of the table to be dropped
     */
    public DropTable(Tables table) {
        this.table = table.toString();
    }
    @Override
    public String getQuery() {
        return String.format("DROP TABLE IF EXISTS %s;" , table);
    }

}
