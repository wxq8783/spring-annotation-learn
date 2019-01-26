package com.wu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public void insertUser(){
        String sql = "INSERT INTO tbl_user(name, age) VALUES (?,?)";
        String userName = UUID.randomUUID().toString().substring(0,5);
        jdbcTemplate.update(sql,userName,33);
    }
}
