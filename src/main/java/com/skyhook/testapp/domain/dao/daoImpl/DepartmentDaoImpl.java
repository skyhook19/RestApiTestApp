package com.skyhook.testapp.domain.dao.daoImpl;

import com.skyhook.testapp.domain.dao.DepartmentDao;
import com.skyhook.testapp.domain.entity.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveDepartment(Department department) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        Session currentSession = sessionFactory.getCurrentSession();
        return (List<Department>) currentSession.createQuery("FROM department").list();
    }

    @Override
    public Department getDepartment(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Department.class, id);
    }

    @Override
    public void deleteDepartment(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Department departmentToDelete = currentSession.byId(Department.class).load(id);
        currentSession.delete(departmentToDelete);
    }
}
