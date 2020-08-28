package com.zys.service;

import com.zys.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    void delete(Long id);

    void save(User user);

    void update(User user);
}
