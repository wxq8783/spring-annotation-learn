package com.wu.config;

import com.wu.aop.LogAspects;
import com.wu.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 AOP原里【看给容器注册了什么组件，这个组件是时候工作的，这个组件的功能是什么？  原理就比较清楚了】
 @EnableAspectJAutoProxy
 @Import(AspectJAutoProxyRegistrar.class) 给容器中导入AspectJAutoProxyRegistrar
 利用AspectJAutoProxyRegistrar自定义容器中注册bean
 internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator

 给容器中注册一个AnnotationAwareAspectJAutoProxyCreator的类，beanName = internalAutoProxyCreator

 AnnotationAwareAspectJAutoProxyCreator
    --> AspectJAwareAdvisorAutoProxyCreator
        -->AbstractAdvisorAutoProxyCreator
            -->AbstractAutoProxyCreator
                implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
            关注后置处理器(在bean初始化完成前后做事情)、自动装配BeanFactory


 AbstractAutoProxyCreator.setBeanFactory()
 AbstractAutoProxyCreator.有后置处理器的逻辑

 AbstractAdvisorAutoProxyCreator.setBeanFactory() 重写了setBeanFactory()
                    --》initBeanFactory（）

 AnnotationAwareAspectJAutoProxyCreator。initBeanFactory（）



 1）创建IOC容器
 2)注册配置列，刷新容器
 3）registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建
    1）先获取IOC容器已经定义了需要创建对象的所有BeanPostProcessor
    2)给容器中加别的BeanPostProcessor
    3)优先注册实现了PriorityOrdered接口的BeanPostProcessor
    4)在给容器中注册实现了ordered接口的BeanPostProcessor
    5）在给容器中注册没有接口的BeanPostProcessor
    6)注册BeanPostProcessor,实际上就是创建BeanPostProcessor对象，保存在容器中
        创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
        1）创建Bean的实例
        2）	populateBean(beanName, mbd, instanceWrapper);给bean的各种属性赋值
        3）initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) 初始化bean
            1)invokeAwareMethods(final String beanName, final Object bean) 处理Aware接口的方法回调
            2）wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);应用后置处理器的postProcessBeforeInitialization(result, beanName)
            3)invokeInitMethods(beanName, wrappedBean, mbd);执行自定义的初始化方法
            4）wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);应用后置处理器的postProcessAfterInitialization(result, beanName)
        4）BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】创建成功
    7）把BeanPostProcessor注册到BeanFactory中
            beanFactory.addBeanPostProcessor(postProcessor)
 *
 */
@EnableAspectJAutoProxy
public class MainConfigIfAop {

    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
