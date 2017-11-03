package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class ChangeConfigTable implements Query{

    private String parameter;
    private String value;
    
    public ChangeConfigTable(String parameter, String value) {
        this.parameter = "'" + parameter + "'";
        this.value = "'" + value + "'";
    }

    @Override
    public String getQuery() {
        return String.format("UPDATE %s SET value=%s WHERE parameter=%s;", Tables.CONFIGURATION_TABLE_NAME ,value ,parameter);
    }

}
