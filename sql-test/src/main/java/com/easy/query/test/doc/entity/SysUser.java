package com.easy.query.test.doc.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/11/21 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_sys_user")
@EntityProxy
@Data
public class SysUser {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String account;
    private String departName;
    private String phone;
    private LocalDateTime createTime;
}
