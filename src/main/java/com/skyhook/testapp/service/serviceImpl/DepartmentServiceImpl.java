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

        if(department == null) {
            return null;
        }
        return createDepartmentDto(department);
    }

    @Override
    @Transactional
    public DepartmentDto addDepartment(Integer parentDepartmentId, String name) {
        if ((parentDepartmentId == null && isRootDepartmentExists()) || sameNameDepartmentExists(name)) {
            return null;
        }

        Department department = new Department();
        department.setName(name);

        if (parentDepartmentId != null) {
            Department parentDepartment = departmentDao.getDepartment(parentDepartmentId);
            department.setParentDepartment(parentDepartment);
        }

        Integer departmentId = departmentDao.saveDepartment(department);
        Department savedDepartment = departmentDao.getDepartment(departmentId);
        return createDepartmentDto(savedDepartment);
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartmentName(int id, String name) {
        Department department = departmentDao.getDepartment(id);

        if(department == null || sameNameDepartmentExists(name)) {
            return null;
        }

        department.setName(name);
        departmentDao.updateDepartment(department);
        Department savedDepartment = departmentDao.getDepartment(id);
        return createDepartmentDto(savedDepartment);
    }

    @Override
    @Transactional
    public boolean deleteDepartment(int id) {
        Department department = departmentDao.getDepartment(id);
        List<Employee> employees = employeeDao.getEmployeesByDepartmentId(id);
        if (department != null && employees.isEmpty()) {
            departmentDao.deleteDepartment(department);
            return true;
        }
        return false;
    }


    private boolean isRootDepartmentExists() {
        List<Department> departments = departmentDao.getAllDepartments();

        for (Department department : departments) {
            if (department.getParentDepartment() == null) {
                return true;
            }
        }
        return false;
    }

    private boolean sameNameDepartmentExists(String name) {
        List<Department> departments = departmentDao.getAllDepartments();

        for (Department department : departments) {
            if (department.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public DepartmentDto createDepartmentDto(Department department) {
        List<Employee> employees = employeeDao.getEmployeesByDepartmentId(department.getId());
        EmployeeDto headEmployeeDto = null;
        if(!employees.isEmpty()) {
            headEmployeeDto = new EmployeeDto(employeeDao.getHeadOfDepartment(department.getId()));
        }

        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDateOfCreation(department.getDateOfCreation());
        dto.setEmployeesCount(employees.size());
        dto.setHeadOfDepartment(headEmployeeDto);
        return dto;
    }
}
