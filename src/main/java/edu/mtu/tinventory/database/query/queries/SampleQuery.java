package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.util.Map;

import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.utils.DatabaseUtils;

/**
 * 
 * @author James Helm
 * @since 10/12/2017
 * 
 *        Sample class to show how data will be grabbed using predefined
 *        queries,
 *        Primarily here because not entirely sure how we are going to store the
 *        items
 *        from an organizational standpoint.
 */
public class SampleQuery implements ExecuteQuery {

    /**
     * table to be accessed
     */
    private String table;
    /**
     * column to be selected from
     */
    private String column;
    /**
     * row to be chosen from
     */
    private String row;
    
    
    @Override
    public String getQuery() {
        // Return the string, can be a lot of checks here to watch for
        // Format: "SELECT * FROM '[database/table]' WHERE '[column]'='[row]';
        // Important, SQL commands require semicolon at end
        return String.format("SELECT * FROM '%s' WHERE '%s'='%s';", table, column, row);
    }

    
    @Override
    public void execute(ResultSet resultSet) {
        try {
            // Map used so we can convert between different types of maps easily
            Map<String, Object> data = DatabaseUtils.getData(resultSet);
            // Obviously would not normally just clear it, but this is a sample set
            data.clear();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
