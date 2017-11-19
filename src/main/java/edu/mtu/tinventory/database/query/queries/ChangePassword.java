package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class ChangePassword implements Query {
    private Employee employee;
    private String newPassword;
    private String tableName;

    public ChangePassword(Employee employee, String newPassword, Tables table) {
        this.employee = employee;
        this.newPassword = newPassword;
        this.tableName = table.toString();
    }

    @Override
    public String getQuery() {
        return String.format("UPDATE %s SET password = '%s' WHERE id = '%s'", tableName,
                newPassword, employee.getID());
    }
}
