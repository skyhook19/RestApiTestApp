package com.skyhook.testapp.domain.dao;

import com.skyhook.testapp.domain.entity.Department;

import java.util.List;

public interface DepartmentDao {

    public void saveDepartment(Department department);

    public List<Department> getAllDepartments();

    public Department getDepartment(int id);

    public void deleteDepartment(int id);
}
