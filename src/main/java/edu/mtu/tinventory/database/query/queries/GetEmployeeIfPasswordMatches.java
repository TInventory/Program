package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Access;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.ExecuteQuery;
import edu.mtu.tinventory.database.utils.DatabaseUtils;
import edu.mtu.tinventory.logging.LocalLog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetEmployeeIfPasswordMatches implements ExecuteQuery {
	private String employeeID;
	private String passwdToMatch;
	private Employee employee;
	private boolean waiting;

	public GetEmployeeIfPasswordMatches(String employeeID, String passwdToMatch) {
		this.employeeID = employeeID;
		this.passwdToMatch = passwdToMatch;
		this.employee = null;
		this.waiting = true;
	}

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s WHERE id='%s'", Tables.EMPLOYEES_TABLE_NAME.nameToString(), employeeID);
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if(!data.isEmpty()) {
				HashMap<String, Object> row = data.get(0);
				if (passwdToMatch.equals(row.get("password"))) {
					employee = new Employee(row.get("id").toString(), row.get("firstName").toString(),
							row.get("lastName").toString(), Access.createFromString(row.get("accessLevel").toString(),
							row.get("overrides").toString()));
				}
			}
			waiting = false;
		} catch (SQLException e) {
			LocalLog.exception(e);
		}
	}

	public Employee getEmployee() {
		while (waiting) {
			System.out.print("");
		}
		return employee;
	}
}
