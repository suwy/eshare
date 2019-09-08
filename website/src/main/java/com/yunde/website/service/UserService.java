package com.yunde.website.service;

import com.yunde.website.dao.UserRepository;
import com.yunde.website.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: suwy
 * @date: 2019-09-07
 * @decription:
 */
@Service
public class UserService extends BaseService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByNameAndPassword(String name, String password) {
        User user = userRepository.findByNameAndPassword(name, password);
        return user;
    }
}
