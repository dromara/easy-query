package com.easy.query.test.entity.notable;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.notable.proxy.QueryLargeColumnTestEntityProxy;
import lombok.Data;

/**
 * create time 2023/5/22 21:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("query_large_column_test")
@EntityProxy
public class QueryLargeColumnTestEntity implements ProxyEntityAvailable<QueryLargeColumnTestEntity , QueryLargeColumnTestEntityProxy> {
    @Column(primaryKey = true)
    private String id;

    private String name;
    private String content;
}
