package com.zys.dao;

import com.zys.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {

    List<User> findAll();

    User findById(Long id);

    void delete(Long id);

    void save(User user);

    void update(User user);
}
