package com.wu.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    /**
     * 返回值就是要导入的bean
     * 返回的是全限定名路径
     * @param importingClassMetadata
     * @return
     */
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.wu.bean.Yellow","com.wu.bean.Blue"};
    }
}
