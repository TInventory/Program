package edu.mtu.tinventory.gui;

import edu.mtu.tinventory.data.Employee;

public abstract class AdminSubController extends Controller {
    protected Employee employee;

    public void setSelectedEmployee(Employee employee) {
        this.employee = employee;
    }
}
