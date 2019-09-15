package com.yunde.website.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value="User对象", description="User")
public class User {

    @ApiModelProperty(value="id",name="id",example="20190101001")
    private String id;
    @ApiModelProperty(value="账号",name="name",example="suwy")
    private String name;
    @ApiModelProperty(value="密码",name="password",example="12345678")
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
