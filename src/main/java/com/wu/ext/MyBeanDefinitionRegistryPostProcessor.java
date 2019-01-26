package com.wu.ext;

import com.wu.bean.Blue;
import com.wu.bean.Car;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory....bean的数量："+beanFactory.getBeanDefinitionCount());
    }
    //BeanDefinitionRegistry Bean定义信息的保存中心，以后BeanFactory就是按照beanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry...bean的数量："+registry.getBeanDefinitionCount());
        //RootBeanDefinition beanDefinition = new RootBeanDefinition(Car.class);
        AbstractBeanDefinition definition = BeanDefinitionBuilder.rootBeanDefinition(Car.class).getBeanDefinition();
        registry.registerBeanDefinition("hello",definition);
    }



}
