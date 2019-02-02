package com.skyhook.testapp.domain.dto;

import com.skyhook.testapp.domain.entity.Employee;
import com.skyhook.testapp.domain.entity.Gender;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDto implements Serializable {

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate joinedDate;
    private LocalDate dischargeDate;
    private String phone;
    private String email;
    private BigDecimal salary;
    private String position;
    private int departmentId;
    private boolean isHeadOfDepartment;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.lastName = employee.getLastName();
        this.gender = employee.getGender();
        this.birthDate = employee.getBirthDate();
        this.joinedDate = employee.getJoinedDate();
        this.dischargeDate = employee.getDischargeDate();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.salary = employee.getSalary();
        this.position = employee.getPosition();
        this.departmentId = employee.getDepartment().getId();
        this.isHeadOfDepartment = employee.isHeadOfDepartment();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getDepartment() {
        return departmentId;
    }

    public void setDepartment(int departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isHeadOfDepartment() {
        return isHeadOfDepartment;
    }

    public void setHeadOfDepartment(boolean headOfDepartment) {
        isHeadOfDepartment = headOfDepartment;
    }
}
