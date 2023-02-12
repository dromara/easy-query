package org.easy.test;

import org.easy.query.core.annotation.Column;
import org.easy.query.core.annotation.PrimaryKey;
import org.easy.query.core.annotation.Table;

import java.time.LocalDateTime;

/**
 * @FileName: SysUserLogbyMonth.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:43
 * @Created by xuejiaming
 */
@Table("sys_user_logby_month_202103")
public class SysUserLogbyMonth {
    @PrimaryKey
    @Column("Id")
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
}
