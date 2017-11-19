package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;

public class CheckConfigurations implements ExecuteQuery{

    private String parameter;
    private boolean waiting;
    private String value;
    private String tableName;
    
    public CheckConfigurations(String parameter, Tables table) {
        this.parameter = "'" + parameter + "'";
        this.value = "";
        this.waiting = true;
        this.tableName = table.toString();
    }
    @Override
    public String getQuery() {
        return String.format("SELECT DISTINCT value FROM %s WHERE parameter=%s;", tableName, parameter);
    }

    @Override
    public void execute(ResultSet resultSet) {
        try {
           ArrayList<HashMap<String, Object>> data =  DatabaseUtils.getData(resultSet);
           if (!data.isEmpty()) {
               value = data.get(0).get("value").toString();
           }
           else {
            // Okay we really messed up
               LocalLog.error(String.format("PARAMETER: %s DOES NOT EXIST IN THE CONFIGURATION TABLE", parameter));
           }
           waiting = false;
        }
        catch (Exception exception) {
            LocalLog.exception(exception);
        }
    }
    
    public String getValue() {
        while (waiting) {
            System.out.print("");
        }
        return value;
    }

}
