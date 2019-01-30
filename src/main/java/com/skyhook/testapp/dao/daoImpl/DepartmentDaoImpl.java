package com.skyhook.testapp.dao.daoImpl;

import com.skyhook.testapp.dao.DepartmentDao;
import com.skyhook.testapp.entity.Department;
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
        List<Department> departments = (List<Department>) currentSession.createQuery("FROM department").list();
        return departments;
    }

    @Override
    public Department getDepartment(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Department department = currentSession.get(Department.class, id);
        return department;
    }

    @Override
    public void deleteDepartment(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Department departmentToDelete = currentSession.byId(Department.class).load(id);
        currentSession.delete(departmentToDelete);
    }
}
