package com.wu;

import com.wu.bean.Person;
import com.wu.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
//
//        Person person = (Person) applicationContext.getBean("person");
//        System.out.println(person.toString());

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person bean = (Person) applicationContext.getBean(Person.class);
        System.out.println(bean);
        String[] nameArr = applicationContext.getBeanNamesForType(Person.class);
        for(String name : nameArr){
            System.out.println(name);
        }

    }
}
