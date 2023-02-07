package org.jdqc.sql.core;

/**
 * @FileName: TestUser1.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:43
 * @Created by xuejiaming
 */
public class TestUser1 {
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

    private String id;
    private String name;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
