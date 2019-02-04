package com.skyhook.testapp.domain.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;

public class DepartmentDto implements Serializable {

    @ApiModelProperty(notes = "The database generated Department ID")
    private int id;

    @ApiModelProperty(notes = "The name of department")
    private String name;

    @ApiModelProperty(notes = "The date of department creation")
    private LocalDate dateOfCreation;

    @ApiModelProperty(notes = "Count of employees working in this department")
    private int employeesCount;

    @ApiModelProperty(notes = "Information about head employee of the department")
    private EmployeeDto headOfDepartment;

    public DepartmentDto() {
    }

    public DepartmentDto(int id, String name, LocalDate dateOfCreation, int employeesCount, EmployeeDto headOfDepartment) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.employeesCount = employeesCount;
        this.headOfDepartment = headOfDepartment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public EmployeeDto getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(EmployeeDto headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public int getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }
}
