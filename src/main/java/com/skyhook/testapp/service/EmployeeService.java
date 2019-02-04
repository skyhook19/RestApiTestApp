package com.skyhook.testapp.service;

import com.skyhook.testapp.domain.dto.EmployeeDto;
import com.skyhook.testapp.domain.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    public EmployeeDto getEmployee(Integer id);

    public List<EmployeeDto> getEmployeesByDepartmentId(Integer departmentId);

    public EmployeeDto addEmployee(EmployeeDto employeeDto);

    public EmployeeDto updateEmployeeById(Integer id, EmployeeDto employeeDto);

    public Boolean deleteEmployee(Integer id, LocalDate dateOfDischarge);

    public EmployeeDto updateEmployeesDepartment(Integer id, Integer newDepartmentId);

    public List<EmployeeDto> moveEmployeesToDepartment(Integer previousDepartmentId, Integer newDepartmentId);

    public EmployeeDto getHeadOfEmployee(Integer employeeId);

    public List<EmployeeDto> getEmployeesJoinedBeforeDate(LocalDate date);
}
