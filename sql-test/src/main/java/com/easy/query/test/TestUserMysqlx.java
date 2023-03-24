package com.easy.query.test;


import java.time.LocalDateTime;

/**
 * @FileName: TestUserMysqlx.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:42
 * @author xuejiaming
 */
public class TestUserMysqlx {
    private String id;
    private String age;
    private String name1;
    private LocalDateTime time;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

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
