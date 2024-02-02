package com.easy.query.test.common;

import java.time.LocalDateTime;
import java.lang.Integer;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.EasyAnonymous;

/**
 * 这是匿名对象类型由easy-query插件生成请勿修改
 * easy-query-plugin automatic generation
 */
@EntityProxy
@EasyAnonymous(moduleName = "sql-test", packageName = "com.easy.query.test.common")
public class AnonymousTypeName {

    private LocalDateTime name;

    private Integer age;


    public LocalDateTime getName() {
        return name;
    }

    public void setName(LocalDateTime name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
