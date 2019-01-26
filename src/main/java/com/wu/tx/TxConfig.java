package com.wu.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * 声明事物
 * 1、导入相关依赖
 *
 * 2、配置数据源、jdbcTemplate
 *
 * 3、给方法上标注@Transactional 表示当前方法是一个事物方法
 *
 * 4、添加@EnableTransactionManagement 开启一个基于注解的事物管理功能
 *
 * 5、配置事物管理器 来控制事物
 *
 * 原理：
 *  1、@EnableTransactionManagement 利用TransactionManagementConfigurationSelector 给容器中导入组件
 *      导入两个组件：
 *          AutoProxyRegistrar
 *          ProxyTransactionManagementConfiguration
 *  2、AutoProxyRegistrar ：给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件
 *      InfrastructureAdvisorAutoProxyCreator的功能是什么？？？
 *      利用后置处理器机制，的在对象创建以后，包装对象，返回一个代理对象(增强器)，代理对象执行方法利用拦截器链进行调用
 *       和AOP差不对
 *
 *  3、ProxyTransactionManagementConfiguration 做什么？？？
 *      给容器中注册事物增强器：
 *          1)、事物增强器要用事物的注解的信息， AnnotationTransactionAttributeSource 解析事物注解、、@Transactional的属性
 *          2)、事物拦截器：
 *              TransactionInterceptor；保存了事物属性信息，事物管理器
 *              他是一个MethodInterceptor
 *              在目标方法执行的时候；
 *                  执行拦截器链；只有一个TransactionInterceptor
 *                  事物拦截器：
 *                      1)、先获取事物相关属性 TransactionAttribute txAttr
 *                      2)、在获取PlatformTransactionManager tm
 *                      3)、执行目标方法
 *                          如果正常，利用事物管理器，提交事物
 *                          如果异常，获取事物管理器，回滚操作
 *
 *
 */
@EnableTransactionManagement
@ComponentScan("com.wu.tx")
@Configuration
public class TxConfig {

    @Bean
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://47.98.195.145:3306/test_1");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception {
        //Spring对@Configuration类有特殊处理。给容器中加组件方法，多次调用都只会调用一次bean
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource());
    }


}
