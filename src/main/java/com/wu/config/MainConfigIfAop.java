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
             if (instanceWrapper == null) {
             instanceWrapper = createBeanInstance(beanName, mbd, args);
             }
        2）	populateBean(beanName, mbd, instanceWrapper);给bean的各种属性赋值
             // Initialize the bean instance.
             Object exposedObject = bean;
             try {
             populateBean(beanName, mbd, instanceWrapper);
             if (exposedObject != null) {
             exposedObject = initializeBean(beanName, exposedObject, mbd);
             }
             }
        3）initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) 初始化bean
            1)invokeAwareMethods(final String beanName, final Object bean) 处理Aware接口的方法回调
            2）wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);应用后置处理器的postProcessBeforeInitialization(result, beanName)
            3)invokeInitMethods(beanName, wrappedBean, mbd);执行自定义的初始化方法
            4）wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);应用后置处理器的postProcessAfterInitialization(result, beanName)
        4）BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】创建成功
    7）把BeanPostProcessor注册到BeanFactory中
            beanFactory.addBeanPostProcessor(postProcessor)
 ===========================以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程===============================
    AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 4）、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作，创建剩下的单实例bean
    1）、遍历获取容器中的所有的bean,依次创建对象getBean(beanName)
        getBean->doGetBean()->getSingleton()->
    2)、创建bean
        【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，因为InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation】
        1)、先从缓存获取当前bean,如果能获取到，说明bean是之前被创建过的；直接使用，否则在创建
        2)、createBean(),创建bean
            【BeanPostProcessor是在Bean对象创建完成后初始化调用的】
            【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
            // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.翻译：给后置处理器一个机会，返回我们的代理对象来替代我们创建的目标的实例
            1）resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation
                希望后置处理器在此能返回一个代理对象，如果能返回代理对象就使用，如果不能就继续
                1）、后置处理器先尝试返回对象
                     bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
                        拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor；就执行ibp.postProcessBeforeInstantiation(beanClass, beanName);
                     if (bean != null) {
                     bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
                     }

            2)、doCreateBean(beanName, mbdToUse, args);正真的去创建一个bean实例；和上面3。6是一样的

AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】的作用
 1）、每一个bean创建之前，调用PostProcessBeforeInstantiation();
    关系MathCalculator和LogAspects的创建
    1)、判断当前bean是否在advisedBeans中(保存了所有需要增强bean)
    2)、判断当前bean是否是基础类型的Advice 、Pointcut 、 Advisor、 AopInfrastructureBean 或者是否是切面(@Aspect)
    3）、是否需要跳过
        1)、获取候选的增强器(切面里面的通知方法)【List<Advisor> candidateAdvisors】
            每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor
            判断每一个增强器是否AspectJPointcutAdvisor，如果是，返回True
        2)、永远返回false
 2）、创建对象
 postProcessAfterInitialization
    return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
    1）、获取当前bean的所有增强器(通知方法) Object[] specificInterceptors
        1）、找到候选的所有的增强器(找那些通知方法是需要切入当前bean方法的)
        2)、获取到能在当前bean使用的增强器
        3)、给增强器排序
    2)、保存当前bean在advisedBeans
    3）、如果当前bean需要增强，创建当前bean的代理对象
        1）、获取所有增强器(通知方法)
        2)、保存到proxyFactory中
        3)、创建代理对立：spring自动决定
             new JdkDynamicAopProxy(config);JDK动态代理
             new ObjenesisCglibAopProxy(config);cglib的动态代理
    4)、给容器中返回当前组件使用的cglib增强了的代理对象
    5)、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
3)、目标方法的执行
    容器中保存了组件的代理对象(cglib增强后的对象)，这个对象里面保存了详细信息(比如增强器，目标对象....)
    1)、CglibAopProxy.intercept()；拦截目标方法的执行
    2)、根据ProxyFactory对象获取将要执行的目标方法的拦截器连
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
        1)、List<Object> interceptorList 保存了所有的拦截器连 有5个
            一个默认的ExposeInvocationInterceptor和4个增强器
        2)、便利所有的增前器，将其转为Interceptor
            registry.getInterceptors(advisor);
        3)、将增强器转为List<MethodInterceptor>
            如果是MethodInterceptor，则直接加入到集合中
            如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor
            转化完成，返回MethodInterceptor数组
    3)、如果没有拦截器连。直接执行目标方法
        所谓的拦截器连(每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制)
    4)、如果有拦截器连，把需要执行的目标对象，目标方法、拦截器连等信息
        创建一个CglibMethodInvocation对象
        并调用Object retVal = mi.proceed();
    5)、拦截器连的触发过程；
        1)、如果没有拦截器执行目标方法，或者拦截器的索引和拦截器数组-1大小一样(指定到了最后一个拦截器)ReflectiveMethodInvocation类中
        2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完返回以后在来执行
            拦截器连的机制，保证通知方法与目标方法的执行顺序

 总结：
    1)、@EnableAspectJAutoProxy 开启AOP功能
    2)、@EnableAspectJAutoProxy 会给容器中注册一个组件AnnotationAwareAspectJAutoProxyCreator
    3)、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器
    4)、容器的创建流程AbstractApplication.refresh()
        1)、registerBeanPostProcessors(beanFactory) 注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator
        2)、finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean
            1) 、创建业务逻辑组件和切面组件
            2)、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
            3)、组件创建完成之后，判断组件是否需要增强
                是：切面的通知方法，包装成增器(Advisor);给业务逻辑组件创建一个代理对象
    5)、执行目标方法
        1)、代理对象执行目标方法
        2)、CglibAopProxy.intercept()
            1)、得到目标方法的拦截器
            2)、利用拦截器的链式机制，一次进入每一个拦截器执行
            3)、效果：
                正常执行：前置通知-》目标方法-》后置通知-》返回通知
                出现异常：前置通知-》目标方法-》后置通知-》异常通知






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
