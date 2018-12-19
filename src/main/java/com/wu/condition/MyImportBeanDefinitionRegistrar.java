package com.wu.condition;

import com.wu.bean.RainBow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean definition = registry.containsBeanDefinition("com.wu.bean.Blue");
        boolean definition1 = registry.containsBeanDefinition("com.wu.bean.Yellow");
        if(definition && definition1){
            BeanDefinition beanDefinition = new RootBeanDefinition();
            ((RootBeanDefinition) beanDefinition).setBeanClass(RainBow.class);
            registry.registerBeanDefinition("rainBow",beanDefinition);
        }
    }
}
