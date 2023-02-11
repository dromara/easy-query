package org.jdqc.core;

import org.jdqc.core.annotation.Table;

import java.io.Serializable;

/**
 * @FileName: TestUser.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:43
 * @Created by xuejiaming
 */
@Table
public class TestUser implements Serializable {

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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    private String studentName;
}
