package com.wu.bean;

public class Car {
    public Car(){
        System.out.println("Car---的构造器");
    }

    public void init(){
        System.out.println("Car---的初始化");
    }

    public void destroy(){
        System.out.println("Car---的销毁方法");
    }
}
