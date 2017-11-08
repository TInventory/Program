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
import java.util.List;

public class GetAllEmployees implements ExecuteQuery {
	private List<Employee> employees;
	private boolean waiting = true;

	@Override
	public String getQuery() {
		return String.format("SELECT * FROM %s", Tables.EMPLOYEES_TABLE_NAME.nameToString());
	}

	@Override
	public void execute(ResultSet resultSet) {
		try {
			ArrayList<HashMap<String, Object>> data = DatabaseUtils.getData(resultSet);
			if (data != null) {
				employees = new ArrayList<>();
				for (HashMap<String, Object> e : data) {
					employees.add(new Employee(e.get("id").toString(), e.get("firstName").toString(),
							e.get("lastName").toString(), Access.createFromString(e.get("accessLevel").toString(),
							e.get("overrides").toString())));
				}
			}
			waiting = false;
		} catch (SQLException e) {
			LocalLog.exception(e);
		}
	}

	public List<Employee> getEmployees() {
		while (waiting) {
			System.out.println("");
		}
		return employees;
	}
}
