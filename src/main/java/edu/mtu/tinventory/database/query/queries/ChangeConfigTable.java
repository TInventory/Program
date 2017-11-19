package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class ChangeConfigTable implements Query{

    private String parameter;
    private String value;
    private String tableName;
    
    public ChangeConfigTable(String parameter, String value, Tables table) {
        this.parameter = "'" + parameter + "'";
        this.value = "'" + value + "'";
        this.tableName = table.toString();
    }

    @Override
    public String getQuery() {
        return String.format("UPDATE %s SET value=%s WHERE parameter=%s;", tableName ,value ,parameter);
    }

}
