package com.wu.config;

import com.wu.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    //给容器注册一个Bean  类型是返回值，id就是方法名
    @Bean("person")
    public Person person01(){
        Person person = new Person();
        person.setName("吴先生");
        person.setAge(33);
        return person;
    }
}
