package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.query.Query;

public class DropTable implements Query {

    /**
     * Name of the table to be dropped
     */
    private String table;
    
    /**
     * Constructor
     * @param table Name of the table to be dropped
     */
    public DropTable(String table) {
        this.table = table;
    }
    @Override
    public String getQuery() {
        return String.format("DROP TABLE IF EXISTS %s;" , table);
    }

}
