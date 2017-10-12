package edu.mtu.tinventory.database.query;

import java.sql.ResultSet;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used to as an implementation on all Queries,
 *        primarily here so I don't forget to add this method in.
 */
public interface ExecuteQuery extends Query {

    /**
     * Executes the results
     * 
     * @param resultSet Result Set to execute
     */
    public void execute(ResultSet resultSet);
}
