package com.skyhook.testapp.controller;

import com.skyhook.testapp.entity.Department;
import com.skyhook.testapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/department"})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") int id) {
        Department department = departmentService.getDepartment(id);
        if (department == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
