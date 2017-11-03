package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

/**
 * Used to create new configurations in the config table
 * 
 * @author Kiro
 *
 */
public class CreateConfigurations implements Query{
    private String parameter;
    private String value;
    
    /**
     * Constructor
     * 
     * @param parameter Parameter of the value
     * @param value Value to be inserted at parameter
     */
    public CreateConfigurations (String parameter, String value){
        this.parameter = "'" + parameter + "'";
        this.value = "'" + value + "'";
    }

    @Override
    public String getQuery() {
        return String.format("INSERT INTO %s SET parameter=%s, value=%s;", Tables.CONFIGURATION_TABLE_NAME.toString(), parameter, value);
    }

}
