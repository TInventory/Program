package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

/**
 * 
 * @author James Helm
 *
 * Query style for creating a new database
 */
public class CreateDatabase implements Query {

    private String databaseName;

    public CreateDatabase(Tables database) {
        this.databaseName = database.toString();
    }

    @Override
    public String getQuery() {
        // TODO: maybe upgrade this to send back a response message
        return String.format("CREATE DATABASE IF NOT EXISTS %s;", databaseName);
    }

}
