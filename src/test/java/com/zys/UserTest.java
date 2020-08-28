package com.zys;


import com.zys.entity.Emp;
import com.zys.entity.User;
import com.zys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.Cache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void findAll(){
        userService.findAll().forEach(user-> System.out.println(user));
        System.out.println("===================================");
        userService.findAll().forEach(user-> { System.out.println(user); });
    }

    @Test
    public void findById(){
        userService.findById(new Long(2));
        System.out.println("===================================");
        userService.findById(new Long(2));
    }

    @Test
    public void delete(){
        userService.delete(new Long(2));
    }

    @Test
    public void save(){
        User user = new User();
        user.setName("张三").setAge(22).setBirthDay(new Date());
        userService.save(user);
    }

    @Test
    public void update(){
        User user = new User();
        user.setId(new Long(3)).setName("李四").setAge(23).setBirthDay(new Date());
        userService.update(user);
    }

}
