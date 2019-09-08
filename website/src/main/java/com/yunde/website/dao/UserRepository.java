package com.yunde.website.dao;

import com.yunde.website.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: suwy
 * @date: 2019-09-07
 * @decription:
 */

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query(value = "SELECT u FROM User u WHERE name=:name and password=:password")
    User findByNameAndPassword(@Param("name") String name, @Param("password") String password);
}
