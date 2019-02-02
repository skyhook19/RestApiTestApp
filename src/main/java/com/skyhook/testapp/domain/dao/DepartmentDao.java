package com.skyhook.testapp.domain.dao;

import com.skyhook.testapp.domain.entity.Department;

import java.util.List;

public interface DepartmentDao {

    public Integer saveDepartment(Department department);

    public List<Department> getAllDepartments();

    public Department getDepartment(Integer id);

    public Department getDepartmentByName(String name);

    public void deleteDepartment(Department department);

    public void updateDepartment(Department department);
}
