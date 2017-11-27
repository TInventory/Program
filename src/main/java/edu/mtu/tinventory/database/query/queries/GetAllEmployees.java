package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Access;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.logging.LocalLog;
import edu.mtu.tinventory.util.DatabaseUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* Executable Query to retrieve all employees from the database
*
* @author 
*
* @since 
*
*/
public class GetAllEmployees implements ExecuteQuery {
	// dataset of employees to fill
	private List<Employee> employees;
	// waiting value of SQL completion
	private boolean waiting = true;

	private String tableName;
	
	public GetAllEmployees(Tables table) {
	    this.tableName = table.toString();
	}
	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s", tableName);
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			// array list of employee data to parse
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if (data != null) {
				employees = new ArrayList<>();
				// Each hashmap is a raw dataset to be parsed
				for (HashMap<String, Object> e : data) {
					employees.add(new Employee(e.get("id").toString(), e.get("firstName").toString(),
							e.get("lastName").toString(), Access.createFromString(e.get("accessLevel").toString(),
							e.get("overrides").toString())));
				}
			}
			// SQL execution complete, can retrieve data
			waiting = false;
		} catch (SQLException e) {
			LocalLog.exception(e);
		}
	}

	/**
	* Retrieves a List<Employee> from the database
	* 
	* @return Lisr of employees from the database
	*/
	public List<Employee> getEmployees() {
	// Wait on SQL to finish executing
		while (waiting) {
			System.out.print("");
		}
		// Data getter
		return employees;
	}
}
