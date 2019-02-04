package com.skyhook.testapp.service.serviceImpl;

import com.skyhook.testapp.domain.dao.DepartmentDao;
import com.skyhook.testapp.domain.dao.EmployeeDao;
import com.skyhook.testapp.domain.dto.EmployeeDto;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.domain.entity.Employee;
import com.skyhook.testapp.service.EmployeeService;
import com.skyhook.testapp.validation.exceptions.DoubleHeadOfDepartmentException;
import com.skyhook.testapp.validation.exceptions.NotAcceptableRequestParamException;
import com.skyhook.testapp.validation.exceptions.NotUniqueEmailException;
import com.skyhook.testapp.validation.exceptions.NotUniquePhoneException;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
        Employee employeeToSave = new Employee();

        String newEmail = employeeDto.getEmail();
        if (employeeDao.existsByEmail(newEmail)) {
            throw new NotUniqueEmailException();
        }

        String newPhone = employeeDto.getPhone();
        if (employeeDao.existsByPhone(newPhone)) {
            throw new NotUniquePhoneException();
        }

        setEmployeeDtoFieldsToEntity(employeeDto, employeeToSave);
        employeeDao.saveEmployee(employeeToSave);
        Employee employeeAfterSave = employeeDao.getEmployee(employeeToSave.getId());
        return new EmployeeDto(employeeAfterSave);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployeeById(Integer id, EmployeeDto employeeDto) {
        Employee employeeBeforeUpdate = employeeDao.getEmployee(id);
        if (employeeBeforeUpdate == null) {
            return null;
        }

        String newEmail = employeeDto.getEmail();
        if (!newEmail.equals(employeeBeforeUpdate.getEmail()) && employeeDao.existsByEmail(newEmail)) {
            throw new NotUniqueEmailException();
        }

        String newPhone = employeeDto.getPhone();
        if (!newPhone.equals(employeeBeforeUpdate.getPhone()) && employeeDao.existsByPhone(newPhone)) {
            throw new NotUniquePhoneException();
        }

        setEmployeeDtoFieldsToEntity(employeeDto, employeeBeforeUpdate);
        employeeDao.saveEmployee(employeeBeforeUpdate);
        Employee employeeAfterUpdate = employeeDao.getEmployee(id);
        return new EmployeeDto(employeeAfterUpdate);
    }

    @Override
    @Transactional
    public Boolean deleteEmployee(Integer id, LocalDate dateOfDischarge) {
        Employee employee = employeeDao.getEmployee(id);

        if (employee == null) {
            return false;
        }

        if (dateOfDischarge == null || dateOfDischarge.compareTo(employee.getJoinedDate()) <= 0) {
            throw new NotAcceptableRequestParamException("Employee's date of discharge must be greater than date of joining");
        }

        employee.setDischargeDate(dateOfDischarge);
        employeeDao.saveEmployee(employee);
        return true;
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployeesDepartment(Integer id, Integer newDepartmentId) {
        Department newDepartment = departmentDao.getDepartment(newDepartmentId);
        Employee employeeBeforeUpdate = employeeDao.getEmployee(id);

        if (newDepartment == null || employeeBeforeUpdate == null) {
            return null;
        }

        employeeBeforeUpdate.setDepartment(newDepartment);
        employeeDao.saveEmployee(employeeBeforeUpdate);
        Employee employeeAfterUpdate = employeeDao.getEmployee(id);
        EmployeeDto employeeDto = new EmployeeDto(employeeAfterUpdate);
        return employeeDto;
    }

    @Override
    @Transactional
    public List<EmployeeDto> moveEmployeesToDepartment(Integer previousDepartmentId, Integer newDepartmentId) {
        if (previousDepartmentId == null || newDepartmentId == null) {
            throw new IllegalArgumentException("Parameters must be not null");
        }

        List<Employee> employeesFromPrevDep = employeeDao.getEmployeesByDepartmentId(previousDepartmentId);
        Department newDepartment = departmentDao.getDepartment(newDepartmentId);

        if (employeesFromPrevDep == null || newDepartment == null) {
            return null;
        }

        Boolean doesNewDepHaveHead = doesDepartmentHaveHead(newDepartmentId);
        for (Employee emp : employeesFromPrevDep) {
            if (emp.isHeadOfDepartment() && doesNewDepHaveHead) {
                throw new DoubleHeadOfDepartmentException("Department {" + newDepartmentId + "} already has Head of Department. " +
                        "Change parameter 'isHeadOfDepartment' of Employee {" + emp.getId() + "} to the false value and try to move employees again");
            } else {
                newDepartment.addEmployee(emp);
            }
        }

        departmentDao.saveDepartment(newDepartment);
        return getEmployeesByDepartmentId(newDepartmentId);
    }

    @Override
    @Transactional
    public EmployeeDto getHeadOfEmployee(Integer employeeId) {
        Employee emp = employeeDao.getEmployee(employeeId);

        if (emp != null) {
            Department department = emp.getDepartment();
            List<Employee> employees = department.getEmployees();
            for (Employee employee : employees) {
                if (employee.isHeadOfDepartment()) {
                    return new EmployeeDto(employee);
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public List<EmployeeDto> getEmployeesJoinedBeforeDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Parameter date must be not null");
        }

        List<Employee> employees = employeeDao.getEmployeesJoinedBeforeDate(date);
        if (employees == null) {
            return null;
        }

        List<EmployeeDto> employeeDtos = new ArrayList<>(employees.size());
        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(employee));
        }

        return employeeDtos;
    }

    private void setEmployeeDtoFieldsToEntity(EmployeeDto dto, Employee emp) {
        emp.setFirstName(dto.getFirstName());
        emp.setMiddleName(dto.getMiddleName());
        emp.setLastName(dto.getLastName());
        emp.setGender(dto.getGender());
        emp.setBirthDate(dto.getBirthDate());
        emp.setJoinedDate(dto.getJoinedDate());
        emp.setDischargeDate(dto.getDischargeDate());
        emp.setPhone(dto.getPhone());
        emp.setEmail(dto.getEmail());
        emp.setSalary(dto.getSalary());
        emp.setPosition(dto.getPosition());
        emp.setDepartment(departmentDao.getDepartment(dto.getDepartment()));
        emp.setHeadOfDepartment(dto.isHeadOfDepartment());
    }

    private Boolean doesDepartmentHaveHead(Integer departmentId) {
        List<Employee> employees = employeeDao.getEmployeesByDepartmentId(departmentId);

        for (Employee emp : employees) {
            if (emp.isHeadOfDepartment()) {
                return true;
            }
        }
        return false;
    }
}
