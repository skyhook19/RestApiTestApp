package com.skyhook.testapp.service;

import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.entity.Department;

public interface DepartmentService {

    public DepartmentDto getDepartmentInfo(int id);
}
