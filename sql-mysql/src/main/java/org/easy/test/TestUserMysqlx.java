package org.easy.test;

import org.easy.query.core.annotation.Column;
import org.easy.query.core.annotation.PrimaryKey;
import org.easy.query.core.annotation.Table;

/**
 * @FileName: TestUserMysqlx.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:42
 * @Created by xuejiaming
 */
public class TestUserMysqlx {
    private String id;
    private String age;
    private String name1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
