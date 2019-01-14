package com.wu.test;

import com.wu.aop.MathCalculator;
import com.wu.config.MainConfigIfAop;
import com.wu.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Aop {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigIfAop.class);
        System.out.println("==========容器创建完成===========");

        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        mathCalculator.div(10,2);
        applicationContext.close();
    }
}
