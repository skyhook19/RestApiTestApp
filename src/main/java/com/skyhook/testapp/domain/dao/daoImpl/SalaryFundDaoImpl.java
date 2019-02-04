package com.skyhook.testapp.domain.dao.daoImpl;

import com.skyhook.testapp.domain.dao.SalaryFundDao;
import com.skyhook.testapp.domain.entity.SalaryFund;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SalaryFundDaoImpl implements SalaryFundDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveSalaryFund(SalaryFund salaryFund) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(salaryFund);
    }
}
