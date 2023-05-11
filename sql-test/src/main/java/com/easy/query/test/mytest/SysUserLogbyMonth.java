package com.easy.query.test.mytest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;

import java.time.LocalDateTime;

/**
 * @FileName: SysUserLogbyMonth.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:43
 * @author xuejiaming
 */
@Table("sys_user_logby_month_202103")
public class SysUserLogbyMonth {
    @Column(value = "Id",primaryKey = true)
    private String id;
    @Column("Time")
    private LocalDateTime time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SysUserLogbyMonth{" +
                "id='" + id + '\'' +
                ", time=" + time +
                '}';
    }
}
