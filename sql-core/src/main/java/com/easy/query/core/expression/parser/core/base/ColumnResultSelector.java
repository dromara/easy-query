package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface ColumnResultSelector<T1> extends EntitySQLTableOwner<T1> {

    void column(String property);
}
