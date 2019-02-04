package com.skyhook.testapp.scheduling;

import com.skyhook.testapp.domain.dao.DepartmentDao;
import com.skyhook.testapp.domain.dao.SalaryFundDao;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.domain.entity.Employee;
import com.skyhook.testapp.domain.entity.SalaryFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    SalaryFundDao salaryFundDao;

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void saveSalaryFund() {
        List<Department> departments = departmentDao.getAllDepartments();

        for (Department department : departments) {
            BigDecimal salarySum = new BigDecimal(0);
            List<Employee> employees = department.getEmployees();
            for (Employee employee : employees) {
                salarySum = salarySum.add(employee.getSalary());
            }

            SalaryFund salaryFund = new SalaryFund();
            salaryFund.setDepartment(department);
            salaryFund.setSalaryFund(salarySum);
            salaryFundDao.saveSalaryFund(salaryFund);
        }

        System.out.println("I SAVED IT");
    }
}
