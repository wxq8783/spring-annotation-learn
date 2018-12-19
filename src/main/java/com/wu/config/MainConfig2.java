package com.wu.config;

import com.wu.bean.Color;
import com.wu.bean.ColorFactoryBean;
import com.wu.bean.Person;
import com.wu.bean.Red;
import com.wu.condition.LinuxCondition;
import com.wu.condition.MyImportBeanDefinitionRegistrar;
import com.wu.condition.MyImportSelector;
import com.wu.condition.WindowsCondition;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

//类中统一设置
@Conditional({WindowsCondition.class})
@Configuration
@Import({Color.class,Red.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    /**
     *
     * 懒加载：针对单实例Bean
     * @return
     */
    @Scope("prototype")
    @Bean("person")
    public Person person(){
        System.out.println("创建对象");
        return new Person("wuxiaoqing",30);
    }


    /**
     * conditional 按照一定条件进行判断
     *
     * 如果系统是windows 就返回bill
     *
     * 如果系统是linux 就返回linus
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01(){
        return new Person("Bill Gates",60);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02(){
        return new Person("linus",60);
    }


//    @Bean
//    public ColorFactoryBean colorFactoryBean(){
//        return new ColorFactoryBean();
//    }
}
