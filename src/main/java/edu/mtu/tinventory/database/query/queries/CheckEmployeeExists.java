package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckEmployeeExists implements ExecuteQuery {
    private String employeeID;
    private boolean exists;
    private boolean waiting;

    public CheckEmployeeExists(String employeeID) {
        this.employeeID = employeeID;
        this.exists = false;
        this.waiting = true;
    }

    @Override
    public String getQuery() {
        return String.format("SELECT * FROM %s WHERE id = '%s'", Tables.EMPLOYEES_TABLE_NAME.nameToString(), employeeID);
    }

    @Override
    public void execute(ResultSet resultSet) {
        try {
            exists = !DatabaseUtils.getData(resultSet).isEmpty();
            waiting = false;
        } catch (SQLException e) {
            LocalLog.exception(e);
        }
    }

    public boolean exists() {
        while (waiting) {
            System.out.print("");
        }
        return exists;
    }
}
