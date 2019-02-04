package com.skyhook.testapp.controller;

import com.skyhook.testapp.domain.dto.EmployeeDto;
import com.skyhook.testapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployeeDto = employeeService.addEmployee(employeeDto);
        if (createdEmployeeDto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(createdEmployeeDto);
    }

    //rest api e3 method
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable("id") Integer employeeId,
                                                @Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployeeDto = employeeService.updateEmployeeById(employeeId, employeeDto);
        if (updatedEmployeeDto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(updatedEmployeeDto);
    }

    //rest api e4 method
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Integer employeeId,
                                            @RequestParam(name="dateOfDischarge") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate dateOfDischarge) {
        if(!employeeService.deleteEmployee(employeeId, dateOfDischarge)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
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

    //rest api e6 method
    @PutMapping(value = "/{id}/department")
    public ResponseEntity<?> updateEmployeesDepartment(@PathVariable("id") Integer employeeId,
                                                       @RequestParam(name="newDepartmentId") Integer newDepartmentId) {
        EmployeeDto employeeDto = employeeService.updateEmployeesDepartment(employeeId, newDepartmentId);
        if (employeeDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDto);
    }

    //rest api e7 method
    @PutMapping(value = "/department")
    public ResponseEntity<?> moveEmployeesToDepartment(@RequestParam(name="previousDepartmentId") Integer previousDepartmentId,
                                                       @RequestParam(name="newDepartmentId") Integer newDepartmentId) {
        List<EmployeeDto> employeeDtos = employeeService.moveEmployeesToDepartment(previousDepartmentId, newDepartmentId);
        if (employeeDtos == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDtos);
    }

    //rest api e8 method
    @GetMapping(value = "/{id}/head")
    public ResponseEntity<?> getHeadOfEmployee(@PathVariable("id") Integer employeeId) {
        EmployeeDto headEmployee = employeeService.getHeadOfEmployee(employeeId);
        if (headEmployee == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(headEmployee);
    }

    //rest api e9 method
    @GetMapping(value = "/joinedDate/before")
    public ResponseEntity<?> getEmployeesJoinedBeforeDate(@RequestParam(name="date") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate date) {
        List<EmployeeDto> employeeDtos = employeeService.getEmployeesJoinedBeforeDate(date);
        if (employeeDtos == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDtos);
    }
}