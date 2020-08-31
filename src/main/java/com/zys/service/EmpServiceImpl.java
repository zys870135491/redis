package com.zys.service;

import com.zys.dao.EmpDAO;
import com.zys.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpDAO empDAO;


    @Override
    public List<Emp> findEmpAll() {
        List<Emp> all = empDAO.findEmpAll();
        return empDAO.findEmpAll();
    }
}
