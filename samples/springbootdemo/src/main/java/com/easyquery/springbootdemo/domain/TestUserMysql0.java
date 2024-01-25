package com.easyquery.springbootdemo.domain;

import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;

import java.time.LocalDateTime;

/**
 * @FileName: TestUserMysql.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:42
 * @author xuejiaming
 */
@Table("testuser")
@CacheEntitySchema
public class TestUserMysql0 implements CacheKvEntity {
    @Column(value = "Id",primaryKey = true)
    private String id;
    @Column("Age")
    private Integer age;
    @Column("Name")
    private String name;

    @LogicDelete(strategy = LogicDeleteStrategyEnum.LOCAL_DATE_TIME)
    private LocalDateTime deleteAt;

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String cacheIdValue() {
        return getId();
    }
}
