package edu.mtu.tinventory.database;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.mtu.tinventory.data.Access.Level;
import edu.mtu.tinventory.data.Employee;

public class EmployeeTests {

    MySQL sqlConnection = new MySQL("cs3141", "taco", "tinventory", "kiro47.ddns.net", 9999);
    DatabaseAPI api = new DatabaseAPI(sqlConnection, true);
    boolean testing = true;

    @Before
    public void before() {
        api.setupDatabase(testing);
    }

    @Test
    public void addStocker() {
        Employee employee = new Employee("FirstM", "LastM", Level.STOCKER);
        api.registerNewEmployee(employee, Tables.TESTING_EMPLOYEE_TABLE);
        List<Employee> list = api.getEmployees(Tables.TESTING_EMPLOYEE_TABLE);

        Assert.assertTrue("Employee is not found in table!", list.contains(employee));
    }
    
    @Test
    public void addSalesman() {
        Employee employee = new Employee("FirstName", "LastName", Level.SALESMAN);
        api.registerNewEmployee(employee, Tables.TESTING_EMPLOYEE_TABLE);

        List<Employee> list = api.getEmployees(Tables.TESTING_EMPLOYEE_TABLE);

        Assert.assertTrue("Employee is not found in table!", list.contains(employee));
    }

    @Test
    public void addManager() {
        Employee employee = new Employee("FirstM", "LastM", Level.MANAGER);
        api.registerNewEmployee(employee, Tables.TESTING_EMPLOYEE_TABLE);
        List<Employee> list = api.getEmployees(Tables.TESTING_EMPLOYEE_TABLE);

        Assert.assertTrue("Employee is not found in table!", list.contains(employee));
    }

    @Test
    public void addAdmin() {
        Employee employee = new Employee("Firsta", "LastA", Level.ADMINISTRATOR);
        api.registerNewEmployee(employee, Tables.TESTING_EMPLOYEE_TABLE);
        List<Employee> list = api.getEmployees(Tables.TESTING_EMPLOYEE_TABLE);

        Assert.assertTrue("Employee is not found in table!", list.contains(employee));
    }
    
    //TODO: add in checks on names, and leveling access
    @After
    public void close() {
        api.deleteDataTable(Tables.TESTING_EMPLOYEE_TABLE);
        api.deleteDataTable(Tables.TESTING_INVENTORY_TABLE);
        api.deleteDataTable(Tables.TESTING_CONFIGURATION_TABLE);
        api.deleteDataTable(Tables.TESTING_CUSTOMER_TABLE);
        api.deleteDataTable(Tables.TESTING_INVOICES_TABLE);
        api.quit();
        System.exit(0);
    }
}
