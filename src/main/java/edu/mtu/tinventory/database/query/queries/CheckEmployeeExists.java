package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* Executable SQL Query to see if a specfic employee exists
* 
* @author 
*
* @since 
*
*/
public class CheckEmployeeExists implements ExecuteQuery {
	// UUID of employee to check for
    private String employeeID;
	// boolean of existence
    private boolean exists;
	// SQL Executable waiting boolean 
    private boolean waiting;

    private String tableName;
	/**
	* Constructor 
	* Executable SQL Instance of CheckEmployeeExists
	*
	* @param employeeID String : Unhyphenated UUID of employee to chck for
	*/
    public CheckEmployeeExists(String employeeID, Tables table) {
        this.employeeID = employeeID;
        this.exists = false;
        this.waiting = true;
        this.tableName = table.toString();
    }

    @Override
    public String getQuery() {
        return String.format("SELECT * FROM %s WHERE id = '%s'", tableName, employeeID);
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

	/**
	* Checks the result for the existance of an employee
	*
	* @return Returns true if the employee exists, false otherwise
	*/
    public boolean exists() {
	// Wait for query to finish executing
        while (waiting) {
            System.out.print("");
        }
	// Return vale
        return exists;
    }
}
