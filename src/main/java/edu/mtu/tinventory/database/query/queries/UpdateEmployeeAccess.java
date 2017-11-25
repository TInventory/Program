package edu.mtu.tinventory.database.query.queries;

import edu.mtu.tinventory.data.Access;
import edu.mtu.tinventory.data.Employee;
import edu.mtu.tinventory.database.Tables;
import edu.mtu.tinventory.database.query.Query;

public class UpdateEmployeeAccess implements Query {
    private Employee employee;
    private Access access;
    private Tables table;

    public UpdateEmployeeAccess(Employee employee, Access access, Tables table) {
        this.employee = employee;
        this.access = access;
        this.table = table;
    }

    @Override
    public String getQuery() {
        return String.format("UPDATE %s SET accessLevel = '%s', overrides = '%s' WHERE id = '%s'",
                table.nameToString(), access.getLevel().toString(), access.getOverridesString(), employee.getID());
    }
}
