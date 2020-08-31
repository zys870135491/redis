package com.zys;


import com.zys.entity.Emp;
import com.zys.entity.User;
import com.zys.service.EmpService;
import com.zys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmpTest {

    @Autowired
    private EmpService empService;

    @Test
    public void findAll(){
        empService.findEmpAll().forEach(emp-> System.out.println(emp));
        System.out.println("===================================");
        empService.findEmpAll().forEach(emp-> { System.out.println(emp); });
        Emp emp = new Emp();
    }



}
