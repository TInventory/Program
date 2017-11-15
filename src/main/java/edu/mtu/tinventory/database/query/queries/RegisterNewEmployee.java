package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class RegisterNewEmployee implements Query {
    private Employee employee;

    public RegisterNewEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String getQuery() {
        return String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '')",
                Tables.EMPLOYEES_TABLE_NAME.nameToString(), employee.getID(), employee.getFirstName(),
                employee.getLastName(), getPassword(), employee.getAccess().getLevel().toString());
    }

    public String getPassword() {
        return employee.getLastName().toLowerCase() +
                (employee.getFirstName().length() < 3 ? employee.getFirstName().toLowerCase() : employee.getFirstName().substring(0, 3).toLowerCase()) +
                "1";
    }
}
