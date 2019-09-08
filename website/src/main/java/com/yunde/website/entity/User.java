package com.yunde.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: suwy
 * @date: 2019-09-07
 * @decription:
 */

@Entity
@Table(name = "user")
public class User {

    private String id;
    private String name;
    private String password;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
