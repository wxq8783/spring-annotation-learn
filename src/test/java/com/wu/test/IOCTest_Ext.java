package com.wu.test;

import com.wu.ext.ExtConfig;
import com.wu.tx.TxConfig;
import com.wu.tx.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Ext {

    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        applicationContext.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
        });

        applicationContext.close();
    }
}
