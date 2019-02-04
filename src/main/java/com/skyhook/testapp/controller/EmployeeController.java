package com.skyhook.testapp.controller;

import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.dto.EmployeeDto;
import com.skyhook.testapp.service.EmployeeService;
import com.skyhook.testapp.validation.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value={"/employees"})
@Api(value="employeeController", description="Operations pertaining to employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/department/{id}", produces = "application/json")
    @ApiOperation(value = "E1: Get list of all employees working in a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got list of employees by department id", response = EmployeeDto.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> getEmployeesByDepartmentId(@PathVariable("id") Integer departmentId) {
        List<EmployeeDto> employeeDtos = employeeService.getEmployeesByDepartmentId(departmentId);
        if (employeeDtos == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDtos);
    }

    @PostMapping(produces = "application/json")
    @ApiOperation(value = "E2: Add a new employee from an EmployeeDto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added employee", response = EmployeeDto.class),
            @ApiResponse(code = 400, message = "Invalid values of some parameters", response = ErrorResponse.class)
    })
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployeeDto = employeeService.addEmployee(employeeDto);
        return ResponseEntity.ok().body(createdEmployeeDto);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "E3: Update parameters of employee with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated employee by id", response = EmployeeDto.class),
            @ApiResponse(code = 204, message = "Employee with this id does not exists")
    })
    public ResponseEntity<?> updateEmployeeById(@PathVariable("id") Integer employeeId,
                                                @Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployeeDto = employeeService.updateEmployeeById(employeeId, employeeDto);
        if (updatedEmployeeDto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(updatedEmployeeDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "E4: Delete an employee with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted employee by id"),
            @ApiResponse(code = 204, message = "Employee with this id does not exists")
    })
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Integer employeeId,
                                            @RequestParam(name="dateOfDischarge") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate dateOfDischarge) {
        if(!employeeService.deleteEmployee(employeeId, dateOfDischarge)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "E5: Get an employee with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got employee by id", response = EmployeeDto.class),
            @ApiResponse(code = 204, message = "Employee with this id does not exists")
    })
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Integer id) {
        EmployeeDto employeeDto = employeeService.getEmployee(id);
        if (employeeDto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDto);
    }

    @PutMapping(value = "/{id}/department", produces = "application/json")
    @ApiOperation(value = "E6: Update parameter Department ID of employee with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated parameter 'Department' of employee with an ID", response = EmployeeDto.class),
            @ApiResponse(code = 204, message = "Employee/department with this id does not exists")
    })
    public ResponseEntity<?> updateEmployeesDepartment(@PathVariable("id") Integer employeeId,
                                                       @RequestParam(name="newDepartmentId") Integer newDepartmentId) {
        EmployeeDto employeeDto = employeeService.updateEmployeesDepartment(employeeId, newDepartmentId);
        if (employeeDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDto);
    }

    @PutMapping(value = "/department", produces = "application/json")
    @ApiOperation(value = "E7: Move all employees from one department to another")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success. Returns all employees of new department, moved employees included",
                    response = EmployeeDto.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> moveEmployeesToDepartment(@RequestParam(name="previousDepartmentId") Integer previousDepartmentId,
                                                       @RequestParam(name="newDepartmentId") Integer newDepartmentId) {
        List<EmployeeDto> employeeDtos = employeeService.moveEmployeesToDepartment(previousDepartmentId, newDepartmentId);
        if (employeeDtos == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDtos);
    }

    @GetMapping(value = "/{id}/head", produces = "application/json")
    @ApiOperation(value = "E8: Get a chief of employee with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got a chief of employee with an ID", response = EmployeeDto.class),
            @ApiResponse(code = 204, message = "Employee with this id does not exists")
    })
    public ResponseEntity<?> getHeadOfEmployee(@PathVariable("id") Integer employeeId) {
        EmployeeDto headEmployee = employeeService.getHeadOfEmployee(employeeId);
        if (headEmployee == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(headEmployee);
    }

    @GetMapping(value = "/joinedDate/before", produces = "application/json")
    @ApiOperation(value = "E9: Get all employees who joined the company before date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success. Returns list of employees, who joined the company before date",
                    response = EmployeeDto.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "Employees who joined the company before date not found")
    })
    public ResponseEntity<?> getEmployeesJoinedBeforeDate(@RequestParam(name="date") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate date) {
        List<EmployeeDto> employeeDtos = employeeService.getEmployeesJoinedBeforeDate(date);
        if (employeeDtos == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(employeeDtos);
    }
}