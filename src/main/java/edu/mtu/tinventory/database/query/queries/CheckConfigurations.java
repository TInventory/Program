package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.logging.LocalLog;

public class CheckConfigurations implements ExecuteQuery{

    private String parameter;
    private boolean waiting;
    private String value;
    
    public CheckConfigurations(String parameter) {
        this.parameter = "'" + parameter + "'";
        this.value = "";
        this.waiting = true;
    }
    @Override
    public String getQuery() {
        return String.format("SELECT DISTINCT value FROM %s WHERE parameter=%s;", Tables.CONFIGURATION_TABLE_NAME, parameter);
    }

    @Override
    public void execute(ResultSet resultSet) {
        try {
           ArrayList<HashMap<String, Object>> data =  DatabaseUtils.getData(resultSet);
           if (!data.isEmpty()) {
               value = data.get(0).get("value").toString();
               System.out.println(value);
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
