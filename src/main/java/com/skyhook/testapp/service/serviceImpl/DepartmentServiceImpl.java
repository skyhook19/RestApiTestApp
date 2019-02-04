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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
    public DepartmentDto updateDepartmentName(Integer id, String name) {
        Department department = departmentDao.getDepartment(id);

        // TODO: 04.02.19 create notUniqueNameException

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

    @Override
    @Transactional
    public List<DepartmentDto> getChildDepartments(Integer parentDepartmentId) {
        Department parentDepartment = departmentDao.getDepartment(parentDepartmentId);

        if(parentDepartment == null) {
            return null;
        }

        List<DepartmentDto> departmentDtos = new ArrayList<>();
        parentDepartment.getSubDepartments().forEach((department -> {departmentDtos.add(createDepartmentDto(department));}));
        return departmentDtos;
    }

    @Override
    @Transactional
    public List<DepartmentDto> getSubDepartmentsTree(Integer parentDepartmentId) {
        Department parentDepartment = departmentDao.getDepartment(parentDepartmentId);

        if(parentDepartment == null) {
            return null;
        }

        List<Department> subDepartments = getAllSubDepartments(parentDepartment, new ArrayList<>());
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        subDepartments.forEach((department -> {departmentDtos.add(createDepartmentDto(department));}));
        return departmentDtos;
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartmentParent(Integer id, Integer parentDepartmentId) {
        Department department = departmentDao.getDepartment(id);

        if (parentDepartmentId == null || department == null) {
            return null;
        } else {
            Department parentDepartment = departmentDao.getDepartment(parentDepartmentId);
            if (parentDepartment == null) {
                return null;
            }
            department.setParentDepartment(parentDepartment);
            departmentDao.updateDepartment(department);
            Department savedDepartment = departmentDao.getDepartment(id);
            return createDepartmentDto(savedDepartment);
        }
    }

    @Override
    @Transactional
    public List<DepartmentDto> getUpperDepartmentsTree(Integer departmentId) {
        Department department = departmentDao.getDepartment(departmentId);

        if(department == null) {
            return null;
        }

        List<Department> upperDepartments = getAllUpperDepartments(department, new ArrayList<>());
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        upperDepartments.forEach((upperDepartment -> {departmentDtos.add(createDepartmentDto(upperDepartment));}));
        return departmentDtos;
    }

    @Override
    @Transactional
    public DepartmentDto getDepartmentByName(String name) {
        Department department = departmentDao.getDepartmentByName(name);
        if (department == null) {
            return null;
        } else {
            return createDepartmentDto(department);
        }
    }

    @Override
    @Transactional
    public BigDecimal getDepartmentSalarySum(Integer departmentId) {
        Department department = departmentDao.getDepartment(departmentId);
        if (department == null) {
            return null;
        }

        BigDecimal salarySum = new BigDecimal(0);
        if (!department.getEmployees().isEmpty()) {
            for (Employee employee : department.getEmployees()) {
                salarySum = salarySum.add(employee.getSalary());
            }
        }
        return salarySum;
    }

    private List<Department> getAllUpperDepartments(Department department, List<Department> upperDepartments) {
        Department parentDep = department.getParentDepartment();
        if (parentDep != null) {
            upperDepartments.add(parentDep);
            getAllUpperDepartments(parentDep, upperDepartments);
        }

        return upperDepartments;
    }

    private List<Department> getAllSubDepartments(Department parentDepartment, List<Department> subDepartments) {
        for (Department subDepartment : parentDepartment.getSubDepartments()) {
            subDepartments.add(subDepartment);
            if (subDepartment.getSubDepartments() == null) {
                subDepartments.add(subDepartment);
                continue;
            } else {
                getAllSubDepartments(subDepartment, subDepartments);
            }
        }
        return subDepartments;
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
