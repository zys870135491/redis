package com.zys.dao;

import com.zys.entity.Emp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpDAO {

    List<Emp> findEmpAll();

}
