package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.TableNoKeyProxy;
import lombok.Data;

/**
 * create time 2025/3/17 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("no_key_table")
public class TableNoKey implements ProxyEntityAvailable<TableNoKey , TableNoKeyProxy> {
    private String column1;
    private String column2;
    private String column3;
}
