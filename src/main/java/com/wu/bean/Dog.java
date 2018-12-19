package com.wu.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {

    public Dog() {
        System.out.println("dog---构造器");
    }

    @PostConstruct
    public void init(){
        System.out.println("dog---初始化");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("dog---销毁");
    }
}
