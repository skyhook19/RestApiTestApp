package com.skyhook.testapp.controller;

import com.skyhook.testapp.domain.dto.EmployeeDto;
import com.skyhook.testapp.service.DepartmentService;
import com.skyhook.testapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value={"/employees"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //rest api e1 method
    @GetMapping(value = "/department/{id}")
    public ResponseEntity<?> getEmployeesByDepartmentId(@PathVariable("id") Integer departmentId) {
        List<EmployeeDto> employeeDtos = employeeService.getEmployeesByDepartmentId(departmentId);
        if (employeeDtos == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDtos);
    }

    //rest api e2 method
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployeeDto = employeeService.addEmployee(employeeDto);
        if (createdEmployeeDto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(createdEmployeeDto);
    }

    //rest api e5 method
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Integer id) {
        EmployeeDto employeeDto = employeeService.getEmployee(id);
        if (employeeDto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDto);
    }

}