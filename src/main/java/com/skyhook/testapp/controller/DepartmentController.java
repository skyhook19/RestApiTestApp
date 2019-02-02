package com.skyhook.testapp.controller;

import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value={"/departments"})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //rest api d1 method
    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestParam(value="parentDepartmentId", required = false) Integer parentDepartmentId,
                                                       @RequestParam(value="name") String name) {
        DepartmentDto departmentDto = departmentService.addDepartment(parentDepartmentId, name);
        if (departmentDto == null) {
            return ResponseEntity.unprocessableEntity().body("Invalid name or parentDepartmentId");
        }
        return new ResponseEntity<>(departmentDto, HttpStatus.CREATED);
    }

    //rest api d2 method
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDepartmentName(@PathVariable("id") Integer id, @RequestParam(value="name") String name) {
        DepartmentDto departmentDto = departmentService.updateDepartmentName(id, name);
        if (departmentDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Department with this id does not exists, or this name already used");
        }
        return ResponseEntity.ok().body(departmentDto);
    }

    //rest api d3 method
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDepartmentName(@PathVariable("id") Integer id) {
        if (!departmentService.deleteDepartment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    //rest api d4 method
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable("id") Integer id) {
        DepartmentDto department = departmentService.getDepartmentInfo(id);
        if (department == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(department);
    }

    //rest api d5 method
    @GetMapping(value = "/{id}/childDepartments")
    public ResponseEntity<?> getChildDepartments(@PathVariable("id") Integer parentDepartmentId) {
        List<DepartmentDto> departmentDtos = departmentService.getChildDepartments(parentDepartmentId);
        if (departmentDtos == null || departmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDtos);
    }

    //rest api d6 method
    @GetMapping(value = "/{id}/subDepartments")
    public ResponseEntity<?> getSubDepartmentsTree(@PathVariable("id") Integer parentDepartmentId) {
        List<DepartmentDto> departmentDtos = departmentService.getSubDepartmentsTree(parentDepartmentId);
        if (departmentDtos == null || departmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDtos);
    }

    //rest api d7 method
    @PutMapping(value = "/{id}/parentDepartment")
    public ResponseEntity<?> updateDepartmentParent(@PathVariable("id") Integer id, @RequestParam(value="parentDepartmentId") Integer parentDepartmentId) {
        DepartmentDto departmentDto = departmentService.updateDepartmentParent(id, parentDepartmentId);
        if (departmentDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Departments with this id dont not exist");
        }
        return ResponseEntity.ok().body(departmentDto);
    }

    //rest api d8 method
    @GetMapping(value = "/{id}/upperDepartments")
    public ResponseEntity<?> getUpperDepartmentsTree(@PathVariable("id") Integer departmentId) {
        List<DepartmentDto> departmentDtos = departmentService.getUpperDepartmentsTree(departmentId);

        if (departmentDtos == null || departmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(departmentDtos);
    }

    //rest api d9 method
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getDepartmentById(@PathVariable(value="name") String name) {
        DepartmentDto department = departmentService.getDepartmentByName(name);
        if (department == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(department);
    }

    //rest api d10 method
    @GetMapping(value = "{id}/salarySum")
    public ResponseEntity<?> getDepartmentSalarySum(@PathVariable("id") Integer departmentId) {
        BigDecimal salarySum = departmentService.getDepartmentSalarySum(departmentId);

        if (salarySum == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(salarySum);
    }
}
