package com.skyhook.testapp.service;

import com.skyhook.testapp.domain.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public EmployeeDto getEmployee(Integer id);

    public List<EmployeeDto> getEmployeesByDepartmentId(Integer departmentId);

    public EmployeeDto addEmployee(EmployeeDto employeeDto);
}
