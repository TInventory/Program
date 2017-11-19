package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;
import edu.mtu.tinventory.util.StringUtils;

public class RegisterNewEmployee implements Query {
    private Employee employee;
    private String tableName;

    public RegisterNewEmployee(Employee employee, Tables table) {
        this.employee = employee;
        this.tableName = table.toString();
    }

    @Override
    public String getQuery() {
        return String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '')",
                tableName, employee.getID(), employee.getFirstName(),
                employee.getLastName(), StringUtils.getDefaultPassword(employee), employee.getAccess().getLevel().toString());
    }
}
