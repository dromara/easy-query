package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.select.AbstractQueryable;
import org.easy.query.core.expression.context.SelectContext;
import org.easy.query.mysql.util.MySQLUtil;

/**
 * @FileName: MySqlSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:04
 * @Created by xuejiaming
 */
public class MySQLQueryable<T1> extends AbstractQueryable<T1> {

    private final SelectContext selectContext;

    public MySQLQueryable(Class<T1> t1Class, SelectContext selectContext) {
        super(t1Class, selectContext);
        this.selectContext = selectContext;
    }

    @Override
    public String toSql() {
        return MySQLUtil.toSelectSql(selectContext);
    }

}
