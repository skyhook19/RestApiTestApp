package com.skyhook.testapp.service.serviceImpl;

import com.skyhook.testapp.dao.DepartmentDao;
import com.skyhook.testapp.entity.Department;
import com.skyhook.testapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional
    public Department getDepartment(int id) {
        return departmentDao.getDepartment(id);
    }
}
