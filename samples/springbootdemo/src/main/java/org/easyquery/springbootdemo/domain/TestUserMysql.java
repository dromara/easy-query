package org.easyquery.springbootdemo.domain;

import org.easy.query.core.annotation.Column;
import org.easy.query.core.annotation.LogicDelete;
import org.easy.query.core.annotation.PrimaryKey;
import org.easy.query.core.annotation.Table;
import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;

import java.time.LocalDateTime;

/**
 * @FileName: TestUserMysql.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:42
 * @Created by xuejiaming
 */
@Table("testuser")
public class TestUserMysql {
    @PrimaryKey
    @Column("Id")
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
}
