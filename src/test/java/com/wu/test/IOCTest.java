package com.wu.test;


import com.wu.bean.Person;
import com.wu.config.MainConfig;
import com.wu.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest {

    @Test
    public void testImport(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        printBean(applicationContext);
    }

    private void printBean( ApplicationContext applicationContext){
        String[] names = applicationContext.getBeanDefinitionNames();
        for(String name : names){
            System.out.println(name);
        }
    }
    @Test
    public void test01(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        printBean(applicationContext);
    }

    @Test
    public void test02(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        System.out.println("-------------容器创建完成-----------");

        String[] names = applicationContext.getBeanDefinitionNames();
        for(String name : names){
            System.out.println(name);
        }
    }
}
