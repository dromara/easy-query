package org.jdqc.test;

import org.jdqc.core.annotation.Column;
import org.jdqc.core.annotation.PrimaryKey;
import org.jdqc.core.annotation.Table;

/**
 * @FileName: TestUser.java
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
    private String age;
    @Column("Name")
    private String name;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
