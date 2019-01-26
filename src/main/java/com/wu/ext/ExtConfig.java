package com.wu.ext;

import com.wu.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 扩展原理
 * 1、BeanPostProcessor :bean的后置处理，在bean创建对象初始化前后进行拦截工作的
 *   BeanFactoryPostProcessor：beanFactory的后置处理器
 *      在BeanFactory标准初始化之后调用；使用的bean定义已经保存加载到beanFactory，但是bean的实例还未创建
 *   1)、IOC容器创建对象
 *   2)、refresh()--》invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor
 *      如何找到所有的BeanFactoryPostProcessor并执行他们的方法
 *          1)、直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
 *          2)、在初始化创建其他组件前面执行
 *
 *
 * 2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
 *      在所有bean定义信息将要被加载，bean实例还未创建的
 *
 *      优先于BeanFactoryPostProcessor执行
 *      利用BeanDefinitionRegistryPostProcessor给容器中额外再添加一些组件
 *
 *   原理：
 *      1)、IOC容器创建对象
 *      2)、refresh()--》invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor
 *      3)、从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件
 *          1、依次触发所有的postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)方法
 *          2、再来触发postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)方法
 *      4)、再来从容器中找到BeanFactoryPostProcessor组件，然后依次触发postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)方法
 *
 *
 * 3、ApplicationListener:监听容器中发布的事件。事件驱动模型开发
 *      public interface ApplicationListener<E extends ApplicationEvent>
 *          监听ApplicationEvent及其下面的子事件
 *
 *     步骤：
 *          1)、写一个监听器来监听某个事件(ApplicationEvent及其子类)
 *              @EventListener
 *              原理：使用EventListenerMethodProcessor处理器来解析方法上的 @EventListener注解
 *
 *          2)、把监听器添加到容器中
 *          3)、只要容器中有相关事件的发布，我们就能监听到这个事件
 *              ContextRefreshedEvent：容器刷新完成(所有bean都完全创建)会发布这个事件
 *              ContextClosedEvent：关闭容器会发布这个事件
 *          4)、发布一个事件 applicationContext.publishEvent(new ApplicationEvent())
 *
 *      原理：
 *          1)、IOC容器创建对象
 *          2)、refresh()--》finishRefresh()；容器刷新完成
 *          3)、publishEvent(new ContextRefreshedEvent(this));
 *              事件发布流程：
 *                  1)、获取事件的多播器(派发器)：getApplicationEventMulticaster()
 *                  2)、multicastEvent()派发事件
 *                  3)、获取到所有的applicationListener
 *                      for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 *                      1>、如果有Executor，可以支持使用Executor进行异步派发
 *                          Executor executor = getTaskExecutor()
 *                      2>、否则，同步的方式直接执行listener方法，invokeListener(listener,event);
 *                      拿到listener回调onApplicationEvent方法
 *
 *          【事件多播器<派发器>】
 *              1)、IOC容器创建对象
 *              2)、refresh()--》initApplicationEventMulticaster();初始化ApplicationEventMulticaster
 *                  1)、先去容器中找有没有id="applicationEventMulticaster"的组件
 *                  2)、如果没有，就自己new一个组件：new SimpleApplicationEventMulticaster(beanFactory)，
 *                     并且加到容器中，我们就可以在其他组件要派发事件，自动注入这个ApplicationEventMulticaster
 *          【容器中有哪些监听器】
 *               1)、IOC容器创建对象
 *               2)、refresh()--》registerListeners();注册监听器
 *                      从2019.01.26中拿到所有的监听器，把他们注册到ApplicationEventMulticaster中
 *                      String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *                      将listenerBean添加到派发器中
 * 			              getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *
 * 		   【SmartInitializingSingleton原理】
 *              1)、IOC容器创建对象
 *              2)、refresh()--》finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean
 *                  1)、先创建所有的单实例bean;genBean()
 *                  2)、获取所有的创建好的单实例bean,判断是否是SmartInitializingSingleton类型
 *                      如果是，就调用smartSingleton.afterSingletonsInstantiated();
 */
@ComponentScan("com.wu.ext")
@Configuration
public class ExtConfig {

    @Bean
    public Dog dog(){
        return new Dog();
    }
}
