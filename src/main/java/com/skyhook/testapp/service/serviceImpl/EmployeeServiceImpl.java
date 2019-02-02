package com.skyhook.testapp.service.serviceImpl;

import com.skyhook.testapp.domain.dao.DepartmentDao;
import com.skyhook.testapp.domain.dao.EmployeeDao;
import com.skyhook.testapp.domain.dto.EmployeeDto;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.domain.entity.Employee;
import com.skyhook.testapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public EmployeeDto getEmployee(Integer id) {
        Employee employee = employeeDao.getEmployee(id);
        if (employee == null) {
            return null;
        }
        return new EmployeeDto(employee);
    }

    @Override
    @Transactional
    public List<EmployeeDto> getEmployeesByDepartmentId(Integer departmentId) {
        Department department = departmentDao.getDepartment(departmentId);
        if (department == null || department.getEmployees().isEmpty()) {
            return null;
        }

        List<EmployeeDto> employeeDtos = new ArrayList<>();
        department.getEmployees().forEach((employee) -> employeeDtos.add(new EmployeeDto(employee)));
        return employeeDtos;
    }

    @Override
    @Transactional
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = createEmployeeFromEmployeeDto(employeeDto);
        employeeDao.saveEmployee(employee);
        Employee savedEmployeeInstance = employeeDao.getEmployee(employee.getId());
        return new EmployeeDto(savedEmployeeInstance);
    }

    private Employee createEmployeeFromEmployeeDto(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setMiddleName(employeeDto.getMiddleName());
        employee.setLastName(employeeDto.getLastName());
        employee.setGender(employeeDto.getGender());
        employee.setBirthDate(employeeDto.getBirthDate());
        employee.setJoinedDate(employeeDto.getJoinedDate());
        employee.setDischargeDate(employeeDto.getDischargeDate());
        employee.setPhone(employeeDto.getPhone());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setPosition(employeeDto.getPosition());
        employee.setDepartment(departmentDao.getDepartment(employeeDto.getDepartment()));
        employee.setHeadOfDepartment(employeeDto.isHeadOfDepartment());

        return employee;
    }
}
