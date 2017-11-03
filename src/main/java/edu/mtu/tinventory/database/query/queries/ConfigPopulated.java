package edu.mtu.tinventory.database.query.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.logging.LocalLog;

public class ConfigPopulated implements ExecuteQuery{

    private boolean waiting = true;
    private boolean isPopulated = false;
    
    @Override
    public String getQuery() {
        return String.format("SELECT NULL FROM %s LIMIT 1;", Tables.CONFIGURATION_TABLE_NAME.toString());
    }

    @Override
    public void execute(ResultSet resultSet) {

        try {
            ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
            if (data.isEmpty()) {
                isPopulated = false;
                waiting = false;
                return;
            }
            else if (data.get(0).isEmpty()) {
                isPopulated = false;
                waiting = false;
                return;
            }
            else {
                isPopulated = true;
                waiting = false;
                return;
            }
        } catch(SQLException e) {
            LocalLog.exception("Error in parsing data from database", e);
        }
    
        
    }

    public boolean isPopulated() {
        while (waiting) {
            System.out.print("");
        }
        return isPopulated;
    }
}
