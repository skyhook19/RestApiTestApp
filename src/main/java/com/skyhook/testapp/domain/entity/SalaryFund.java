package com.skyhook.testapp.domain.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "salary_fund")
public class SalaryFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "salary_fund")
    private BigDecimal salaryFund;

    @CreationTimestamp
    @Column(name = "date_of_record", columnDefinition = "DATE")
    private Timestamp dateOfRecord;

    public SalaryFund() {
    }

    public SalaryFund(Department department, BigDecimal salaryFund, Timestamp dateOfRecord) {
        this.department = department;
        this.salaryFund = salaryFund;
        this.dateOfRecord = dateOfRecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public BigDecimal getSalaryFund() {
        return salaryFund;
    }

    public void setSalaryFund(BigDecimal salaryFund) {
        this.salaryFund = salaryFund;
    }

    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Timestamp dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }
}
