package com.skyhook.testapp.service;

import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.entity.Department;

import java.math.BigDecimal;
import java.util.List;

public interface DepartmentService {

    public DepartmentDto getDepartmentInfo(int id);

    public DepartmentDto addDepartment(Integer parentDepartmentId, String name);

    public DepartmentDto updateDepartmentName(Integer id, String name);

    public boolean deleteDepartment(int id);

    public List<DepartmentDto> getChildDepartments(Integer parentDepartmentId);

    public List<DepartmentDto> getSubDepartmentsTree(Integer parentDepartmentId);

    public DepartmentDto updateDepartmentParent(Integer id, Integer parentDepartmentId);

    public List<DepartmentDto> getUpperDepartmentsTree(Integer departmentId);

    public DepartmentDto getDepartmentByName(String name);

    public BigDecimal getDepartmentSalarySum(Integer departmentId);
}
