package com.skyhook.testapp.service;

import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.entity.Department;

public interface DepartmentService {

    public DepartmentDto getDepartmentInfo(int id);

    public DepartmentDto addDepartment(Integer parentDepartmentId, String name);

    public DepartmentDto updateDepartmentName(int id, String name);

    public boolean deleteDepartment(int id);
}
