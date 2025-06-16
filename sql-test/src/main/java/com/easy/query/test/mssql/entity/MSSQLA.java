package com.easy.query.test.mssql.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mssql.entity.proxy.MSSQLAProxy;
import lombok.Data;

/**
 * create time 2025/6/16 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_mssql_a")
@EntityProxy
public class MSSQLA implements ProxyEntityAvailable<MSSQLA , MSSQLAProxy> {

    @Column(value = "Id")
    private String id;
    @Column(value = "Code")
    private String code;
    @Column(value = "Name")
    private String name;
}
