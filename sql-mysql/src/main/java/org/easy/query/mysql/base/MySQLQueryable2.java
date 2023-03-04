package org.easy.query.mysql.base;

import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.basic.api.select.AbstractQueryable2;
import org.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: MySqlSelects.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:05
 * @Created by xuejiaming
 */
public class MySQLQueryable2<T1,T2> extends AbstractQueryable2<T1,T2> {

    public MySQLQueryable2(Class<T1> t1Class, Class<T2> t2Class, SqlEntityQueryExpression sqlEntityExpression, MultiTableTypeEnum selectTableInfoType) {
        super(t1Class,t2Class, sqlEntityExpression, selectTableInfoType);
    }

    @Override
    public String toSql() {
         return sqlEntityExpression.toSql();
    }

}
