package com.skyhook.testapp.service.serviceImpl;

import com.skyhook.testapp.domain.dao.DepartmentDao;
import com.skyhook.testapp.domain.dao.EmployeeDao;
import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.dto.EmployeeDto;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.domain.entity.Employee;
import com.skyhook.testapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    @Transactional
    public DepartmentDto getDepartmentInfo(int id) {
        Department department = departmentDao.getDepartment(id);
        List<Employee> employees = employeeDao.getEmployeesByDepartmentId(id);
        Employee headEmployee = employeeDao.getHeadOfDepartment(id);

        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDateOfCreation(department.getDateOfCreation());
        dto.setEmployeesCount(employees.size());
        dto.setHeadOfDepartment(new EmployeeDto(headEmployee));
        return dto;
    }
}
