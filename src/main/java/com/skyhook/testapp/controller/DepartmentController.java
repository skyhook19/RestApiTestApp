package com.skyhook.testapp.controller;

import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value={"/departments"})
@Api(value="departmentController", description="Operations pertaining to departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(produces = "application/json")
    @ApiOperation(value = "D1: Add new department")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created department list", response = DepartmentDto.class),
            @ApiResponse(code = 400, message = "You have sent invalid parameter values")
    })
    public ResponseEntity<?> addDepartment(@RequestParam(value="parentDepartmentId", required = false) Integer parentDepartmentId,
                                                       @RequestParam(value="name") String name) {
        DepartmentDto departmentDto = departmentService.addDepartment(parentDepartmentId, name);
        if (departmentDto == null) {
            return ResponseEntity.badRequest().body("Invalid name or parentDepartmentId");
        }
        return new ResponseEntity<>(departmentDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "D2: Update name of department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated department's name", response = DepartmentDto.class),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> updateDepartmentName(@PathVariable("id") Integer id, @RequestParam(value="name") String name) {
        DepartmentDto departmentDto = departmentService.updateDepartmentName(id, name);
        if (departmentDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "D3: Delete a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted department"),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> deleteDepartmentName(@PathVariable("id") Integer id) {
        if (!departmentService.deleteDepartment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "D4: Search a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got department by id", response = DepartmentDto.class),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> getDepartmentById(@PathVariable("id") Integer id) {
        DepartmentDto department = departmentService.getDepartmentInfo(id);
        if (department == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(department);
    }

    @GetMapping(value = "/{id}/childDepartments", produces = "application/json")
    @ApiOperation(value = "D5: Get child departments of a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got list of child departments", response = DepartmentDto.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> getChildDepartments(@PathVariable("id") Integer parentDepartmentId) {
        List<DepartmentDto> departmentDtos = departmentService.getChildDepartments(parentDepartmentId);
        if (departmentDtos == null || departmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDtos);
    }

    @GetMapping(value = "/{id}/subDepartments", produces = "application/json")
    @ApiOperation(value = "D6: Get all departments tree under a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got list of subordinate departments tree", response = DepartmentDto.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> getSubDepartmentsTree(@PathVariable("id") Integer parentDepartmentId) {
        List<DepartmentDto> departmentDtos = departmentService.getSubDepartmentsTree(parentDepartmentId);
        if (departmentDtos == null || departmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDtos);
    }

    @PutMapping(value = "/{id}/parentDepartment", produces = "application/json")
    @ApiOperation(value = "D7: Set a new parent department to a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated department's name", response = DepartmentDto.class),
            @ApiResponse(code = 204, message = "Department with this id/parentDepartmentId does not exists")
    })
    public ResponseEntity<?> updateDepartmentParent(@PathVariable("id") Integer id, @RequestParam(value="parentDepartmentId") Integer parentDepartmentId) {
        DepartmentDto departmentDto = departmentService.updateDepartmentParent(id, parentDepartmentId);
        if (departmentDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDto);
    }

    @GetMapping(value = "/{id}/upperDepartments", produces = "application/json")
    @ApiOperation(value = "D8: Get all ancestry departments of a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got list of all superior departments", response = DepartmentDto.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> getUpperDepartmentsTree(@PathVariable("id") Integer departmentId) {
        List<DepartmentDto> departmentDtos = departmentService.getUpperDepartmentsTree(departmentId);

        if (departmentDtos == null || departmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDtos);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    @ApiOperation(value = "D9: Get a department with an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got list of all superior departments", response = DepartmentDto.class),
            @ApiResponse(code = 204, message = "Department with this name does not exists")
    })
    public ResponseEntity<?> getDepartmentByName(@PathVariable(value="name") String name) {
        DepartmentDto department = departmentService.getDepartmentByName(name);
        if (department == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(department);
    }

    @GetMapping(value = "{id}/salarySum", produces = "application/json")
    @ApiOperation(value = "D10: Get information about salary fund in every department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got department's salary sum", response = BigDecimal.class),
            @ApiResponse(code = 204, message = "Department with this id does not exists")
    })
    public ResponseEntity<?> getDepartmentSalarySum(@PathVariable("id") Integer departmentId) {
        BigDecimal salarySum = departmentService.getDepartmentSalarySum(departmentId);

        if (salarySum == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(salarySum);
    }
}
