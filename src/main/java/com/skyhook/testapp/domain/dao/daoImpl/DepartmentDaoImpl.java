package com.skyhook.testapp.domain.dao.daoImpl;

import com.skyhook.testapp.domain.dao.DepartmentDao;
import com.skyhook.testapp.domain.entity.Department;
import com.skyhook.testapp.domain.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer saveDepartment(Department department) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (Integer) currentSession.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        Session currentSession = sessionFactory.getCurrentSession();
        return (List<Department>) currentSession.createQuery("FROM Department").list();
    }

    @Override
    public Department getDepartment(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Department.class, id);
    }

    @Override
    public Department getDepartmentByName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Department> depCriteriaQuery = criteriaBuilder.createQuery(Department.class);
        Root<Department> departmentRoot = depCriteriaQuery.from(Department.class);
        depCriteriaQuery.select(departmentRoot);
        depCriteriaQuery.where(criteriaBuilder.equal(departmentRoot.get("name"), name));
        List<Department> results = currentSession.createQuery(depCriteriaQuery).getResultList();
        if (results.isEmpty()){
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public void deleteDepartment(Department department) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(department);
    }

    @Override
    public void updateDepartment(Department department) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(department);
    }
}
