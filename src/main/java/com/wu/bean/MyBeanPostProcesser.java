package com.wu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 加入到容器中
 */
@Component
public class MyBeanPostProcesser implements BeanPostProcessor {

    /**
     * 执行初始化之前的逻辑
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization----"+beanName+"===>"+bean);
        return bean;
    }

    /**
     * 执行初始化之后的逻辑
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization----"+beanName+"===>"+bean);
        return bean;
    }
}
