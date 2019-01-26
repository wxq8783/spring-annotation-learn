package com.wu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Transactional
    public void insertUser(){
        userDao.insertUser();
        System.out.println("插入成功");
        int i = 10/0;

    }
}
