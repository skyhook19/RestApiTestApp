package com.skyhook.testapp.domain.dao.daoImpl;

import com.skyhook.testapp.domain.dao.EmployeeDao;
import com.skyhook.testapp.domain.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveEmployee(Employee employee) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        Session currentSession = sessionFactory.getCurrentSession();
        return (List<Employee>) currentSession.createQuery("FROM employee").list();
    }

    @Override
    public Employee getEmployee(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Employee.class, id);
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(int departmentId) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        employeeCriteriaQuery.select(employeeRoot);
        employeeCriteriaQuery.where(criteriaBuilder.equal(employeeRoot.get("department").get("id"), departmentId));
        return currentSession.createQuery(employeeCriteriaQuery).getResultList();
    }

    @Override
    public Employee getHeadOfDepartment(int departmentId) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        employeeCriteriaQuery.select(employeeRoot);

        Predicate employeeInDepartment = criteriaBuilder.equal(employeeRoot.get("department").get("id"), departmentId);
        Predicate employeeIsHeadOfDepartment = criteriaBuilder.equal(employeeRoot.get("isHeadOfDepartment"), true);
        employeeCriteriaQuery.where(criteriaBuilder.and(employeeInDepartment, employeeIsHeadOfDepartment));
        return currentSession.createQuery(employeeCriteriaQuery).getSingleResult();
    }

    @Override
    public Boolean existsByEmail(String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        employeeCriteriaQuery.select(employeeRoot);

        Predicate existsEmail = criteriaBuilder.equal(employeeRoot.get("email"), email);
        employeeCriteriaQuery.where(existsEmail);

        if (currentSession.createQuery(employeeCriteriaQuery).getSingleResult() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean existsByPhone(String phone) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        employeeCriteriaQuery.select(employeeRoot);

        Predicate existsPhone = criteriaBuilder.equal(employeeRoot.get("phone"), phone);
        employeeCriteriaQuery.where(existsPhone);

        if (currentSession.createQuery(employeeCriteriaQuery).getSingleResult() != null) {
            return true;
        } else {
            return false;
        }
    }
}
