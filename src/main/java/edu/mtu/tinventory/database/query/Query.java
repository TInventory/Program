package edu.mtu.tinventory.database.query;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Used to as an easy extension of Executing Queries,
 *        simple way to add mass similar methods.
 */
public interface Query {
    
    /**
     * Getter method for the query
     * 
     * @return String: Returns the Query
     */
    public String getQuery();
}
