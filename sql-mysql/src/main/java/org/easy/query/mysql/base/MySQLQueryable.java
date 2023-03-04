package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.select.AbstractQueryable1;
import org.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: MySqlSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:04
 * @Created by xuejiaming
 */
public class MySQLQueryable<T1> extends AbstractQueryable1<T1> {


    public MySQLQueryable(Class<T1> t1Class, SqlEntityQueryExpression sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
    }

    @Override
    public String toSql() {
        return sqlEntityExpression.toSql();
    }

}
