package com.skyhook.testapp.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class DepartmentDto implements Serializable {

    private int id;
    private String name;
    private LocalDate dateOfCreation;
    private int employeesCount;
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
