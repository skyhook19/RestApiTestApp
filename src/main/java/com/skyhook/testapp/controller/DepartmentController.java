package com.skyhook.testapp.controller;

import com.skyhook.testapp.domain.dto.DepartmentDto;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/department"})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //rest api d1 method
    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestParam(value="parentDepartmentId", required = false) Integer parentDepartmentId,
                                                       @RequestParam(value="name") String name) {
        DepartmentDto departmentDto = departmentService.addDepartment(parentDepartmentId, name);
        if (departmentDto == null) {
            return new ResponseEntity<>("Invalid name or parentDepartmentId", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(departmentDto, HttpStatus.CREATED);
    }

    //rest api d2 method
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDepartmentName(@PathVariable("id") int id, @RequestParam(value="name") String name) {
        DepartmentDto departmentDto = departmentService.updateDepartmentName(id, name);
        if (departmentDto == null) {
            return new ResponseEntity<>("Department with this id does not exists, or this name already used", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    //rest api d3 method
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDepartmentName(@PathVariable("id") int id) {
        if (!departmentService.deleteDepartment(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //rest api d4 method
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable("id") int id) {
        DepartmentDto department = departmentService.getDepartmentInfo(id);
        if (department == null){
            return new ResponseEntity<>("Department with this id does not exists", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }


}
