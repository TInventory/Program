package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class ChangePassword implements Query {
    private Employee employee;
    private String newPassword;

    public ChangePassword(Employee employee, String newPassword) {
        this.employee = employee;
        this.newPassword = newPassword;
    }

    @Override
    public String getQuery() {
        return String.format("UPDATE %s SET password = '%s' WHERE id = '%s'", Tables.EMPLOYEES_TABLE_NAME.nameToString(),
                newPassword, employee.getID());
    }
}
