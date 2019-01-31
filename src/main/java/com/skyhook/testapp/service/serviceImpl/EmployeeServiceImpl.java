package com.skyhook.testapp.service.serviceImpl;

import com.skyhook.testapp.domain.dao.EmployeeDao;
import com.skyhook.testapp.domain.entity.Employee;
import com.skyhook.testapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    @Transactional
    public Employee getEmployee(int id) {
        return employeeDao.getEmployee(id);
    }
}
