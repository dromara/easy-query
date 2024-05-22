package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MultiColumnEntityProxy;
import lombok.Getter;
import lombok.Setter;

/**
 * create time 2024/3/26 13:07
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
@Setter
@Table("multi")
@EntityProxy
public class MultiColumnEntity implements ProxyEntityAvailable<MultiColumnEntity , MultiColumnEntityProxy> {
    @Column(primaryKey = true)
    private String id;
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String col5;
    private String col6;
    private String col7;
    private String col8;
    private String col9;
    private String col10;

}
