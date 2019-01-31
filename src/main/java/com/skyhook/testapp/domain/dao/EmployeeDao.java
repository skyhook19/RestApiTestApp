package com.skyhook.testapp.domain.dao;

import com.skyhook.testapp.domain.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    public void saveEmployee(Employee employee);

    public List<Employee> getAllEmployees();

    public Employee getEmployee(int id);

    public List<Employee> getEmployeesByDepartmentId(int departmentId);

    public Employee getHeadOfDepartment(int departmentId);
}
